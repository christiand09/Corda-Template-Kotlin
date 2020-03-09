package sample.contractAndState.employee

import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.LinearState
import net.corda.core.contracts.UniqueIdentifier
import net.corda.core.identity.Party
import sample.contractAndState.student.StudentContract

@BelongsToContract(EmployeeContract::class)
data class EmployeeState(
        val name: String,
        val age: Int,
        val id: String,
        override val linearId: UniqueIdentifier,
        override val participants: List<Party>
): LinearState