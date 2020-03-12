package sample.flows.student

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.contracts.Command
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.contracts.requireThat
import net.corda.core.flows.*
import net.corda.core.node.services.queryBy
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import sample.contractAndState.employee.EmployeeContract
import sample.contractAndState.employee.EmployeeContract.Companion.EMPLOYEE_ID
import sample.contractAndState.employee.EmployeeState
import sample.function.FlowFunctions

class RegisterStudentFlow (
        private val name: String,
        private val age: Int,
        private val id: String
): FlowFunctions() {

    @Suspendable
    override fun call(): SignedTransaction {
        val session = (outputState().participants - ourIdentity).map { initiateFlow(it) }
        val transactionSignedByAllParties = collectSignature(verifyAndSign(transaction()), session)
        return subFlow(FinalityFlow(transactionSignedByAllParties, session))
    }

    /** SAMPLE INPUT
    private fun inputState(): StateAndRef<EmployeeState> {
        val employeeStateRef = serviceHub.vaultService.queryBy<EmployeeState>().states
        return employeeStateRef.find {
            it.state.data.id == id
        }?: throw IllegalArgumentException("no found id: $id")
    }
    **/

    private fun outputState(): EmployeeState {
        val partyA = stringToParty("PartyA") //sample only
        return EmployeeState(
                name = name,
                age = age,
                id = id,
                linearId = UniqueIdentifier(),
                participants = listOf(ourIdentity, partyA) // add the other name of node in other party
        )
    }

    private fun transaction(): TransactionBuilder {
        val registerCommand = Command(EmployeeContract.Commands.Register(), outputState().participants.map { it.owningKey })
        val builder = TransactionBuilder(notary())
        builder.addOutputState(outputState(), EMPLOYEE_ID)
        builder.addCommand(registerCommand)
        return builder
    }
}

@InitiatedBy(RegisterStudentFlow::class)
class RegisterStudentFlowResponder(val flowSession: FlowSession) : FlowLogic<SignedTransaction>() {

    @Suspendable
    override fun call(): SignedTransaction {
        val signTransactionFlow = object : SignTransactionFlow(flowSession) {
            override fun checkTransaction(stx: SignedTransaction) = requireThat {
            }
        }
        val signedTransaction = subFlow(signTransactionFlow)
        return subFlow(ReceiveFinalityFlow(otherSideSession = flowSession, expectedTxId = signedTransaction.id))
    }
}