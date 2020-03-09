package sample.flows.employee

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.contracts.Command
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.FinalityFlow
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder
import sample.contractAndState.employee.EmployeeContract
import sample.contractAndState.employee.EmployeeContract.Companion.EMPLOYEE_ID
import sample.contractAndState.employee.EmployeeState
import sample.function.FlowFunctions

class RegisterEmployeeFlow (
        private val name: String,
        private val age: Int,
        private val id: String
): FlowFunctions() {

    @Suspendable
    override fun call(): SignedTransaction {
        return subFlow(FinalityFlow(verifyAndSign(transaction()), emptyList()))
    }

    private fun outputState(): EmployeeState{
        return EmployeeState(
                name = name,
                age = age,
                id = id,
                linearId = UniqueIdentifier(),
                participants = listOf(ourIdentity)
        )
    }

    private fun transaction(): TransactionBuilder {
        val registerCommand = Command(EmployeeContract.Commands.Register(), ourIdentity.owningKey)
        val builder = TransactionBuilder(notary())
        builder.addOutputState(outputState(), EMPLOYEE_ID)
        builder.addCommand(registerCommand)
        return builder
    }
}