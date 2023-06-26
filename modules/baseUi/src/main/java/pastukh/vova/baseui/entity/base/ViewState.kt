package pastukh.vova.baseui.entity.base

import pastukh.vova.data.server.entity.base.ResponseResult

sealed class ViewState<out T> {
    data class Data<O>(val data: O) : ViewState<O>()
    data class Error(val message: String) : ViewState<Nothing>()
    object Loading : ViewState<Nothing>()
}

suspend inline fun <T> ViewState<T>.onDataState(crossinline block: suspend (T) -> Unit) {
    when (this) {
        is ViewState.Data<T> -> block(this.data)
        else -> {}
    }
}

fun <I : Any, O : Any> ResponseResult<I>.mapTo(block: (I) -> O): ResponseResult<O> =
    when (this) {
        is ResponseResult.Success<I> -> ResponseResult.Success(
            block(data)
        )

        is ResponseResult.Error -> ResponseResult.Error(
            error
        )
    }

fun <I : Any> ResponseResult<I>.toViewState(): ViewState<I> =
    when (this) {
        is ResponseResult.Success<I> -> ViewState.Data(data)
        is ResponseResult.Error -> ViewState.Error(
            error.message ?: "Unknown"
        )
    }