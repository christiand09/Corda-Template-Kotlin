package sample.contractAndState.student

import net.corda.core.contracts.*
import net.corda.core.transactions.LedgerTransaction

class StudentContract : Contract {

    companion object {
        const val STUDENT_ID = "sample.contractAndState.student.StudentContract"
    }

    interface Commands : CommandData {
        class Register : TypeOnlyCommandData(), Commands
        class Verified : TypeOnlyCommandData(), Commands
    }

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Commands>()

        when (command.value) {
            is Commands.Register -> requireThat {  }
            is Commands.Verified -> requireThat {  }
        }
    }
}