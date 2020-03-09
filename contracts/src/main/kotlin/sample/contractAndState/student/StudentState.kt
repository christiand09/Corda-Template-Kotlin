package sample.contractAndState.student

import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party

@BelongsToContract(StudentContract::class)
data class StudentState(
        val name: String,
        val age: Int,
        val id: String,
        override val linearId: UniqueIdentifier,
        override val participants: List<Party>
): LinearState