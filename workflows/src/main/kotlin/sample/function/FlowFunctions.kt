package sample.function

import co.paralleluniverse.fibers.Suspendable
import net.corda.core.contracts.ContractState
import net.corda.core.contracts.StateAndRef
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.flows.CollectSignaturesFlow
import net.corda.core.flows.FinalityFlow
import net.corda.core.flows.FlowLogic
import net.corda.core.flows.FlowSession
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party
import net.corda.core.node.services.queryBy
import net.corda.core.transactions.SignedTransaction
import net.corda.core.transactions.TransactionBuilder

abstract class FlowFunctions : FlowLogic<SignedTransaction>(){
    fun notary(): Party = serviceHub.networkMapCache.notaryIdentities.first()

    fun verifyAndSign(transaction: TransactionBuilder): SignedTransaction{
        transaction.verify(serviceHub)
        return serviceHub.signInitialTransaction(transaction)
    }

    @Suspendable
    fun collectSignature(
            transaction: SignedTransaction,
            sessions: List<FlowSession>
    ): SignedTransaction = subFlow(CollectSignaturesFlow(transaction, sessions))

    @Suspendable
    fun recordTransactionWithOtherParty(transaction: SignedTransaction, sessions: List<FlowSession>): SignedTransaction{
        return subFlow(FinalityFlow(transaction, sessions))
    }

    @Suspendable
    fun recordTransactionWithoutOtherParty(transaction: SignedTransaction): SignedTransaction{
        return subFlow(FinalityFlow(transaction, emptyList()))
    }

    fun myIdentityParty(): Party{
        return ourIdentity
    }

    fun myIdentityNameOnly(): String{
        return serviceHub.myInfo.legalIdentities.first().name.commonName.toString()
        /** Sample Output **/
        /** QuantumCrowd **/
    }

    fun myIdentityName(): String{
        return serviceHub.myInfo.legalIdentities.first().name.toString()
        /** Sample Output **/
        /** O=QuantumCrowd,L=London,C=GB **/
    }

    /** BroadCast Flow **/
    fun partyC(): Party {
        return serviceHub.identityService.wellKnownPartyFromX500Name(CordaX500Name.parse("PartyC"))
                ?: throw IllegalArgumentException("No match found for PartyC")
    }

    /** Convert **/
    fun stringToParty(name: String): Party{
        return serviceHub.identityService.wellKnownPartyFromX500Name(CordaX500Name.parse(name))
                ?: throw Exception("No match found for $name")
    }

    fun stringToUniqueIdentifier(id: String): UniqueIdentifier{
        return UniqueIdentifier.fromString(id)
    }
}