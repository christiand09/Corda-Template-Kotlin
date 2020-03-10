package sample.controller.student

import sample.contractAndState.student.StudentState

data class StudentDTO(
        val name: String,
        val age: Int,
        val id: String,
        val linearId: String
)

fun mapToStudentDTO(state: StudentState): StudentDTO{
    return StudentDTO(
            name = state.name,
            age = state.age,
            id = state.id,
            linearId = state.linearId.toString()
    )
}