package sample.controller.student

import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RestController
import sample.common.HandlerCompletion
import sample.common.Response
import sample.common.ResponseDTO
import sample.flows.student.RegisterStudentFlow
import sample.webserver.NodeRPCConnection

@Service
class StudentImpl (
        private val rpc: NodeRPCConnection,
        private val fhc: HandlerCompletion,
        private val response: Response
): StudentService{

    companion object {
        val logger = LoggerFactory.getLogger(RestController::class.java)!!
    }

    override fun registerStudent(request: RegisterStudentDTO): ResponseEntity<ResponseDTO> {
        return try {
            val flowReturn = rpc.proxy.startFlowDynamic(
                    RegisterStudentFlow::class.java,
                    request.name,
                    request.age,
                    request.id
            )
            fhc.handlerCompletion(flowReturn)

            response.successfulResponse(
                    response = "success",
                    message = "register a new student"
            )
        } catch (ex: Exception) {
            response.failedResponse(
                    exception = ex,
                    message = "register a new student"
            )
        }
    }
}