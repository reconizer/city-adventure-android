package pl.reconizer.cityadventure.presentation.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.transition.ChangeBounds
import androidx.transition.TransitionInflater
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.view_gallery_item.*
import pl.reconizer.cityadventure.R
import java.lang.Exception

class GalleryItemFragment : Fragment() {

    private val imageUrl by lazy { arguments!![IMAGE_URL_PARAM] as String }
    private val sourceTransitionName by lazy { arguments!![SOURCE_TRANSITION_NAME_PARAM] as String }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.gallery_transition)
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.gallery_transition)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.view_gallery_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView.transitionName = imageUrl
        Picasso.get()
                .load(imageUrl)
                .noFade()
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        startPostponedEnterTransition()
                    }

                    override fun onError(e: Exception?) {
                        startPostponedEnterTransition()
                    }

                })
    }

    companion object {
        const val IMAGE_URL_PARAM = "image_url"
        const val SOURCE_TRANSITION_NAME_PARAM = "transition_name"

        fun newInstance(imageUrl: String, sourceTransitionName: String): GalleryItemFragment {
            return GalleryItemFragment().apply {
                arguments = bundleOf(
                        IMAGE_URL_PARAM to imageUrl,
                        SOURCE_TRANSITION_NAME_PARAM to sourceTransitionName
                )
            }
        }
    }
}