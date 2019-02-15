package pl.reconizer.unfold.presentation.customviews.loader

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.animation.LinearInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.view_loader.view.*
import pl.reconizer.unfold.R

class Loader @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    //val loaderFootLeft1FadeInAnimation: Animation
//    val loaderFootLeft1FadeOutAnimation: Animation
//    val loaderFootLeft2Animation: Animation
//    val loaderFootLeft3Animation: Animation
//    val loaderFootLeft4Animation: Animation
//    val loaderFootLeft5Animation: Animation
//    val loaderFootLeft6Animation: Animation
//    val loaderFootLeft7Animation: Animation
//    val loaderFootLeft8Animation: Animation
//    val loaderFootLeft9Animation: Animation
//    val loaderFootLeft10Animation: Animation
//    val loaderFootLeft11Animation: Animation
//    val loaderFootLeft12Animation: Animation

    init {
        LayoutInflater.from(context).inflate(R.layout.view_loader, this, true)

//        val fadeAnimation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot_fade_in)
//
//        val leftFootAnimationSet = AnimatorSet()
//        leftFootAnimationSet.playSequentially(
//                AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot_fade_in),
//                AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot_fade_in).apply {  }
//        )
//        loaderFootLeft1FadeInAnimation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot_fade_in).apply {
//            startOffset = 0
//            setAnimationListener(LoaderAnimationListener(loaderFootLeft1, 3600))
//        }

//        loaderFootLeft2Animation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot).apply {
//            startOffset =
//            setAnimationListener(LoaderAnimationListener(loaderFootLeft1, 1500))
//        }
//
//        loaderFootLeft1Animation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot).apply {
//            startOffset = 0
//            setAnimationListener(LoaderAnimationListener(loaderFootLeft1, 1500))
//        }
//
//        loaderFootLeft1Animation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot).apply {
//            startOffset = 0
//            setAnimationListener(LoaderAnimationListener(loaderFootLeft1, 1500))
//        }
//
//        loaderFootLeft1Animation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot).apply {
//            startOffset = 0
//            setAnimationListener(LoaderAnimationListener(loaderFootLeft1, 1500))
//        }
//
//        loaderFootLeft1Animation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot).apply {
//            startOffset = 0
//            setAnimationListener(LoaderAnimationListener(loaderFootLeft1, 1500))
//        }
//
//        loaderFootLeft1Animation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot).apply {
//            startOffset = 0
//            setAnimationListener(LoaderAnimationListener(loaderFootLeft1, 1500))
//        }
//
//        loaderFootLeft1Animation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot).apply {
//            startOffset = 0
//            setAnimationListener(LoaderAnimationListener(loaderFootLeft1, 1500))
//        }
//
//        loaderFootLeft1Animation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot).apply {
//            startOffset = 0
//            setAnimationListener(LoaderAnimationListener(loaderFootLeft1, 1500))
//        }
//
//        loaderFootLeft1Animation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot).apply {
//            startOffset = 0
//            setAnimationListener(LoaderAnimationListener(loaderFootLeft1, 1500))
//        }
//
//        loaderFootLeft1Animation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot).apply {
//            startOffset = 0
//            setAnimationListener(LoaderAnimationListener(loaderFootLeft1, 1500))
//        }
//
//        loaderFootLeft1Animation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot).apply {
//            startOffset = 0
//            setAnimationListener(LoaderAnimationListener(loaderFootLeft1, 1500))
//        }

//        loaderFootLeft1.startAnimation(loaderFootLeft1FadeInAnimation)
        //postDelayed({
            //loaderFootLeft1.alpha = 0f

//            val loaderFootLeft2Animation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot).apply {
//                startOffset = 500
//                setAnimationListener(LoaderAnimationListener(loaderFootLeft2, 1500))
//            }
//            loaderFootLeft2.startAnimation(loaderFootLeft2Animation)
//
//            val loaderFootLeft3Animation = AnimationUtils.loadAnimation(context, R.anim.loader_fade_foot).apply {
//                startOffset = 1000
//                setAnimationListener(LoaderAnimationListener(loaderFootLeft3, 1500))
//            }
//            loaderFootLeft3.startAnimation(loaderFootLeft3Animation)
        //}, 1000)

//        val leftFoot1Animator = AnimatorInflater.loadAnimator(context, R.animator.loader_fade_foot).apply {
//            setTarget(loaderFootLeft1)
//        }

//        val animators = listOf(
//                loaderFootLeft1,
//                loaderFootLeft2,
//                loaderFootLeft3,
//                loaderFootLeft4,
//                loaderFootLeft5,
//                loaderFootLeft6,
//                loaderFootLeft7,
//                loaderFootLeft8,
//                loaderFootLeft9,
//                loaderFootLeft10,
//                loaderFootLeft11,
//                loaderFootLeft12
//        ).mapIndexed { index, foot ->
//            AnimatorSet().apply {
//                startDelay = index * ANIM_OFFSET
//                playSequentially(
//                        ObjectAnimator.ofFloat(foot, "alpha", 0f, 1f).apply {
//                            duration = ANIM_OFFSET
//                        },
//                        ObjectAnimator.ofFloat(foot, "alpha", 1f, 0f).apply {
//                            duration = ANIM_OFFSET
//                            startDelay = 5 * ANIM_OFFSET
//                        }
//                )
//            }
//        }

//        val leftFoot1Animator = AnimatorSet().apply {
//            playSequentially(
//                    ObjectAnimator.ofFloat(loaderFootLeft1, "alpha", 0f, 1f).apply {
//                        duration = ANIM_OFFSET
//                    },
//                    ObjectAnimator.ofFloat(loaderFootLeft1, "alpha", 1f, 0f).apply {
//                        duration = ANIM_OFFSET
//                        startDelay = 3 * ANIM_OFFSET
//                    }
//            )
//        }
//        val leftFoot2Animator = AnimatorSet().apply {
//            startDelay = ANIM_OFFSET
//            playSequentially(
//                    ObjectAnimator.ofFloat(loaderFootLeft2, "alpha", 0f, 1f).apply {
//                        duration = ANIM_OFFSET
//                    },
//                    ObjectAnimator.ofFloat(loaderFootLeft2, "alpha", 1f, 0f).apply {
//                        duration = ANIM_OFFSET
//                        startDelay = 3 * ANIM_OFFSET
//                    }
//            )
//        }
//        val leftFoot3Animator = AnimatorSet().apply {
//            startDelay = ANIM_OFFSET * 2
//            playSequentially(
//                    ObjectAnimator.ofFloat(loaderFootLeft3, "alpha", 0f, 1f).apply {
//                        duration = ANIM_OFFSET
//                    },
//                    ObjectAnimator.ofFloat(loaderFootLeft3, "alpha", 1f, 0f).apply {
//                        duration = ANIM_OFFSET
//                        startDelay = 3 * ANIM_OFFSET
//                    }
//            )
//        }
//        val leftFoot1AnimatorFadeIn = ObjectAnimator.ofFloat(loaderFootLeft1, "alpha", 0f, 1f).apply {
//                    duration = ANIM_OFFSET
//        }
//        val leftFoot1AnimatorFadeOut = ObjectAnimator.ofFloat(loaderFootLeft1, "alpha", 1f, 0f).apply {
//            duration = 1200
//            startDelay = 1200
//        }
//        val leftFoot2Animator = AnimatorInflater.loadAnimator(context, R.animator.loader_fade_foot).apply {
//            setTarget(loaderFootLeft2)
//            //startDelay = ANIM_OFFSET
//        }
//        val leftFoot3Animator = AnimatorInflater.loadAnimator(context, R.animator.loader_fade_foot).apply {
//            setTarget(loaderFootLeft3)
//            //startDelay = 2 * ANIM_OFFSET
//        }
//        val leftFoot4Animator = AnimatorInflater.loadAnimator(context, R.animator.loader_fade_foot).apply {
//            setTarget(loaderFootLeft4)
//            //startDelay = 3 * ANIM_OFFSET
//        }
//        val leftFoot5Animator = AnimatorInflater.loadAnimator(context, R.animator.loader_fade_foot).apply {
//            setTarget(loaderFootLeft5)
//            //startDelay = 4 * ANIM_OFFSET
//        }
//        val leftFoot6Animator = AnimatorInflater.loadAnimator(context, R.animator.loader_fade_foot).apply {
//            setTarget(loaderFootLeft6)
//            //startDelay = 5 * ANIM_OFFSET
//        }
//            val loaderAnimatorSet = AnimatorSet().apply {
//                playTogether(
//                        animators
//                )
//            }

//        AnimatorSet().apply {
//            playTogether(
//                    AnimatorSet().apply {
//                        playTogether(
//                                animators
//                        )
//                    },
//                    AnimatorSet().apply {
//                        startDelay = 11 * ANIM_OFFSET
//                        playTogether(
//                                animators
//                        )
//                    }
//            )
//            postDelayed({
//                start()
//            },1000)
//        }
//        val loaderAnimatorSet = AnimatorSet().apply {
//            playTogether(
//                    leftFoot1Animator//,
////                    leftFoot2Animator,
////                    leftFoot3Animator,
////                    leftFoot4Animator,
////                    leftFoot5Animator,
////                    leftFoot6Animator
//            )
//            addListener(LoaderAnimatorListener())
//        }
//        loaderAnimatorSet.start()
        //}
        val leftFeet = listOf(
                loaderFootLeft1,
                loaderFootLeft2,
                loaderFootLeft3,
                loaderFootLeft4,
                loaderFootLeft5,
                loaderFootLeft6,
                loaderFootLeft7,
                loaderFootLeft8,
                loaderFootLeft9,
                loaderFootLeft10,
                loaderFootLeft11,
                loaderFootLeft12
        )
        val loaderInterpolator = LoaderInterpolator(CHANGE_ANIM_DURATION, LIFESPAN_DURATION)
        val valueAnimator = ValueAnimator.ofFloat(0f, 1000000f).apply {
            duration = ANIMATION_DURATION * REPEAT_COUNT
            interpolator = LinearInterpolator()
        }
        valueAnimator.addUpdateListener {
            val realValue = (it.animatedValue as Float).toDouble() / 1000000
            val maxValuePerCycle = 1f / REPEAT_COUNT
            val valueInCycle = (realValue % maxValuePerCycle) / maxValuePerCycle
            Log.d("LOADER", "value: $valueInCycle")
            leftFeet.mapIndexed { footIndex, foot ->
                val maxTimeValue = (NUMBER_OF_ELEMENTS * CHANGE_ANIM_DURATION + LIFESPAN_DURATION + CHANGE_ANIM_DURATION).toFloat()
                val startTimeValue = (CHANGE_ANIM_DURATION * footIndex).toFloat()
                val endTimeValue = startTimeValue + LIFESPAN_DURATION + CHANGE_ANIM_DURATION
                val startTimeValuePer = startTimeValue / maxTimeValue
                val endTimeValuePer = endTimeValue / maxTimeValue
                if (valueInCycle in startTimeValuePer..endTimeValuePer) {
                    //foot.alpha = loaderInterpolator.getInterpolation(((valueInCycle * ANIMATION_DURATION - startTimeValue)/(endTimeValue - startTimeValue)).toFloat())
                    foot.alpha = loaderInterpolator.getInterpolation((valueInCycle / endTimeValuePer).toFloat())
                } else {
                    foot.alpha = 0f
                }

            }
        }
        postDelayed({
            valueAnimator.start()
        },1000)
    }

    companion object {
        const val CHANGE_ANIM_DURATION = 600L
        const val NUMBER_OF_ELEMENTS = 12
        const val LIFESPAN_DURATION = 5 * CHANGE_ANIM_DURATION
        const val ANIMATION_DURATION = (NUMBER_OF_ELEMENTS) * CHANGE_ANIM_DURATION
        const val REPEAT_COUNT = 120
    }
}