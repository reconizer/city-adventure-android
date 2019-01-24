package pl.reconizer.cityadventure.presentation.adventure.summary

import pl.reconizer.cityadventure.presentation.mvp.IView

interface IAdventureSummaryView : IView {

    fun showUserRanking()

    fun showSummary()

}