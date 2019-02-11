package pl.reconizer.cityadventure.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.*
import com.zhuinden.simplestack.StateChange
import kotlinx.android.synthetic.main.fragment_splash_start.*
import pl.reconizer.cityadventure.R
import pl.reconizer.cityadventure.di.Injector
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import pl.reconizer.cityadventure.presentation.navigation.keys.AuthenticationStartKey
import pl.reconizer.cityadventure.presentation.navigation.keys.MapKey
import javax.inject.Inject

class SplashFragment : BaseFragment(), ISplashView {

    @Inject
    lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.buildSplashComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash_start, container, false)
    }

    override fun onStart() {
        super.onStart()
        view?.post {
            val newConstraintSet = ConstraintSet()
            newConstraintSet.clone(context, R.layout.fragment_splash_end)
            val transitions = TransitionSet().apply {
                addTransition(ChangeBounds().apply {
                    interpolator = AccelerateDecelerateInterpolator()
                    duration = 200
                })
                addTransition(Fade(Fade.IN).apply {
                    interpolator = AccelerateDecelerateInterpolator()
                    duration = 500
                })
                ordering = TransitionSet.ORDERING_TOGETHER

            }
            transitions.addListener(object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition) {
                    presenter.notifyViewAboutUserState()
                }

                override fun onTransitionCancel(transition: Transition) {
                    presenter.notifyViewAboutUserState()
                }

                override fun onTransitionResume(transition: Transition) {}
                override fun onTransitionPause(transition: Transition) {}
                override fun onTransitionStart(transition: Transition) {}

            })
            TransitionManager.beginDelayedTransition(container, transitions)
            newConstraintSet.applyTo(container)
        }

    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
        presenter.checkUser()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injector.clearSplashComponent()
    }

    override fun authenticatedUser() {
        navigator.setHistory(
                mutableListOf(MapKey.Builder.buildAdventuresMapKey()),
                StateChange.REPLACE
        )
    }

    override fun unauthenticatedUser() {
        navigator.setHistory(
                mutableListOf(AuthenticationStartKey()),
                StateChange.REPLACE
        )
    }
}