package studio.inprogress.yetanothertestapi.utils

sealed class Resulted<out T : Any> {
    class Success<out T : Any>(
        val data: T
    ) : Resulted<T>()

    object Loading : Resulted<Nothing>()
    object Empty : Resulted<Nothing>()
    class Error(val code: Int, val message: String) : Resulted<Nothing>()
}

inline fun <reified T : Any> successResult(data: T) = Resulted.Success(data)
fun loading() = Resulted.Loading
fun emptyResult() = Resulted.Empty
fun errorResult(code: Int, message: String) = Resulted.Error(code, message)