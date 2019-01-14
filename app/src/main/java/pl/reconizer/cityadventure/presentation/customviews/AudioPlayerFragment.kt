package pl.reconizer.cityadventure.presentation.customviews

import android.media.MediaPlayer
import androidx.core.os.bundleOf
import pl.reconizer.cityadventure.presentation.common.BaseFragment
import android.media.AudioManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_audio_player.*
import pl.reconizer.cityadventure.R


class AudioPlayerFragment : BaseFragment(), MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    val audioUrl: String? by lazy { arguments?.get(AUDIO_URL_PARAM) as String? }

    private lateinit var mediaPlayer: MediaPlayer
    private var isAudioReady: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_audio_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionButton.setImageResource(R.drawable.icon_play_audio)

        closeButton.setOnClickListener {
            navigator.goBack()
        }

        actionButton.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                pauseAudio()
            } else {
                playAudio()
            }
        }

        stopButton.setOnClickListener {
            stopAudio()
        }
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer = MediaPlayer().apply {
            setAudioStreamType(AudioManager.STREAM_MUSIC)
            setDataSource(audioUrl)
            setOnPreparedListener(this@AudioPlayerFragment)
            setOnCompletionListener(this@AudioPlayerFragment)
        }
    }

    override fun onPause() {
        super.onPause()
        actionButton.setImageResource(R.drawable.icon_play_audio)
        mediaPlayer.release()
        isAudioReady = false
    }

    override fun onPrepared(mp: MediaPlayer?) {
        isAudioReady = true
        playAudio()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        stopAudio()
    }

    private fun playAudio() {
        if (isAudioReady) {
            mediaPlayer.start()
            actionButton.setImageResource(R.drawable.icon_pause_audio)
        } else {
            mediaPlayer.prepareAsync()
        }
    }

    private fun pauseAudio() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            actionButton.setImageResource(R.drawable.icon_play_audio)
        }
    }

    private fun stopAudio() {
        if (isAudioReady) {
            pauseAudio()
            mediaPlayer.seekTo(0)
        }
    }

    companion object {
        const val AUDIO_URL_PARAM = "audio_url"

        fun newInstance(url: String): AudioPlayerFragment {
            return AudioPlayerFragment().apply {
                arguments = bundleOf(
                        AUDIO_URL_PARAM to url
                )
            }
        }
    }

}