package sample.controller.student

import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sample.common.ResponseDTO

private const val CONTROLLER_NAME = "/student"

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping(CONTROLLER_NAME) /** The paths for HTTP requests are relative to this base path. **/
class CompanyController (private val studentService: StudentService) {


    @PostMapping(value = [""], produces = ["application/json"])
    @ApiOperation(value = "Register Student")
    private fun registerStudent(@RequestBody request: RegisterStudentDTO): ResponseEntity<ResponseDTO> {
        return studentService.registerStudent(request)
    }

}