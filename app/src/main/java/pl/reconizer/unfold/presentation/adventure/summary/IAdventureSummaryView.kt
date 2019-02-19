package pl.reconizer.unfold.presentation.adventure.summary

import pl.reconizer.unfold.presentation.mvp.IView

interface IAdventureSummaryView : IView {

    fun showUserRanking()

    fun showSummary()

}