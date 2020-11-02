package com.example.liverpooldirectory

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.liverpooldirectory.players.Player
import kotlinx.android.synthetic.main.player_popup.*

class PlayerDialogFragment private constructor() : DialogFragment() {

    companion object {
        private const val KEY_PLAYER = "player"

        fun newInstance(player: Player): PlayerDialogFragment {
            val fragment = PlayerDialogFragment()
            val arguments = Bundle()
            arguments.putParcelable(KEY_PLAYER, player)
            fragment.arguments = arguments
            return fragment
        }
    }

    private var mp: MediaPlayer? = null
    lateinit var player: Player

    override fun onAttach(context: Context) {
        super.onAttach(context)
        player = requireArguments().getParcelable(KEY_PLAYER)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.player_popup, container, false)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        stopPlayer()
    }

    private fun stopPlayer() {
        val mp = mp ?: return
        mp.stop()
        mp.reset()
        mp.release()
        this.mp = null
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sound = player.sound
        val button = play_button
        if (sound != 0) {
            mainSound(sound)
            button.visibility = VISIBLE
        } else {
            button.visibility = GONE
        }

        closeButton.setOnClickListener {
            dismiss()
        }
        playerImageView.setImageResource(player.photoRes)
        nameAndAgeTextView.text  = "${player.firstName} ${player.lastName}, ${player.age} лет"
        biographyTextView.text = player.biography

        play_button.setOnClickListener {
            if (mp !== null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
                play_button.setImageResource(R.drawable.play)
            } else if (mp == null) {
                mainSound(sound)
                play_button.setImageResource(R.drawable.stop)
            }
        }
    }

    private fun mainSound(id: Int) {
        val mp = MediaPlayer.create(requireContext(), id)
        this.mp = mp
        Log.d("MainActivity", "ID: ${mp.audioSessionId}")
        mp.start()
        Log.d("LibraryActivity", "Duration: ${mp.duration / 1000} seconds")
    }
}