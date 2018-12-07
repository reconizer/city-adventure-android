package pl.reconizer.cityadventure.presentation.adventure.journal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_adventure_journal.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.domain.entities.Adventure
import pl.reconizer.cityadventure.domain.entities.AdventureStartPoint
import pl.reconizer.cityadventure.presentation.common.BaseFragment

class JournalFragment : BaseFragment() {

    val adventure by lazy { arguments?.get(ADVENTURE_PARAM) as Adventure? }
    val adventureStartPoint by lazy { arguments?.get(ADVENTURE_START_POINT_PARAM) as AdventureStartPoint? }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_adventure_journal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        journalAdventureDescriptionView.rateable = false
        journalAdventureDescriptionView.isCompleted = adventure?.completed ?: false

        Picasso.get()
                .load(R.drawable.journal_background)
                .into(backgroundImage)

        adventureStartPoint?.let {
            journalAdventureDescriptionView.adventureStartPoint = it
        }

        //journalAdventureDescriptionView.scaleX = 0.79f
        //journalAdventureDescriptionView.scaleY = 0.79f
        journalPageDescriptionView.forceLayout()
    }

    companion object {
        const val ADVENTURE_PARAM = "adventure"
        const val ADVENTURE_START_POINT_PARAM = "adventure_start_point"

        fun newInstance(adventure: Adventure, adventureStartPoint: AdventureStartPoint): JournalFragment {
            return JournalFragment().apply {
                arguments = bundleOf(
                        ADVENTURE_PARAM to adventure,
                        ADVENTURE_START_POINT_PARAM to adventureStartPoint
                )
            }
        }
    }
}