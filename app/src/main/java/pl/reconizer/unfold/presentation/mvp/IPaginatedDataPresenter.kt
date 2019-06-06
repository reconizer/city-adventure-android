package pl.reconizer.unfold.presentation.mvp

interface IPaginatedDataPresenter<T> {

    val items: List<T>

    val page: Int
    val requestedPage: Int
    val hasGotMorePages: Boolean

    fun fetchFirstPage()
    fun fetchNextPage()

}