package studio.inprogress.yetanothertestapi.ui

interface BaseLoader<T> {
    fun showError(message: String)
    fun showData(data: List<T>)
    fun showLoading()
    fun hideLoading()
}