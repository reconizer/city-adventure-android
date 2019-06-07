package pl.reconizer.unfold.presentation.creatorprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_creator_profile.*
import pl.reconizer.unfold.R
import pl.reconizer.unfold.di.Injector
import pl.reconizer.unfold.presentation.common.BaseFragment
import javax.inject.Inject

class CreatorProfileFragment : BaseFragment(), ICreatorProfileView {

    @Inject
    lateinit var presenter: CreatorProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildCreatorProfileComponent(
                arguments?.get(CREATOR_ID_PARAM) as String? ?: throw IllegalStateException("Creator id is required.")
        ).inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_creator_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener { navigator.goBack() }

    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        if (presenter.profile == null) {
            presenter.fetchProfile()
        }
        showProfile()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearCreatorProfileComponent()
    }

    override fun showProfile() {
        creatorName.text =  presenter.profile?.name
        description.text = presenter.profile?.description
        favoritesCounter.text = presenter.profile?.favoriteCount.toString()

        Picasso.get()
                .load(presenter.profile?.logo)
                .into(logo)
    }

    companion object {
        const val CREATOR_ID_PARAM = "creator_id"
    }

}