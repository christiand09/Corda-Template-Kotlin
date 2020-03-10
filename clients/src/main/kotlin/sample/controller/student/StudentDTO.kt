package sample.controller.student

import com.fasterxml.jackson.annotation.JsonCreator

data class RegisterStudentDTO @JsonCreator constructor(
        val name: String,
        val age: Int,
        val id: String
)