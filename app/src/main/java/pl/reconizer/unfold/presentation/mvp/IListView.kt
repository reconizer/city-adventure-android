package pl.reconizer.unfold.presentation.mvp

interface IListView<T> : IView {

    fun showFirstPage(collection: List<T>)
    fun showNextPage(collection: List<T>)
    fun refresh()

    fun showListLoader()
    fun hideListLoader()

}