package sample.common

data class ResponseDTO (
        var status: String?,
        var message: String?,
        var result: Any?
):java.io.Serializable

data class NodeInfoDTO(
        val name: String,
        val address: String
)