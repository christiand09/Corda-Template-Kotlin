package sample.common.broadcast

import net.corda.core.flows.FlowLogic
import net.corda.core.identity.CordaX500Name
import net.corda.core.identity.Party

abstract class BroadcastFunction : FlowLogic<Unit>() {
    fun partyC(): Party {
        return serviceHub.identityService.wellKnownPartyFromX500Name(CordaX500Name.parse("PartyC"))
                ?: throw IllegalArgumentException("No match found for PartyC")
    }
}
