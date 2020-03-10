package sample.controller.student

import org.springframework.http.ResponseEntity
import sample.common.ResponseDTO

interface StudentService {

    /** Register New Student **/
    fun registerStudent(request: RegisterStudentDTO): ResponseEntity<ResponseDTO>

}