package pastukh.vova.data.server.entity.base

data class ServerResult<T>(val success: Boolean, val message: String?, val result: T?)