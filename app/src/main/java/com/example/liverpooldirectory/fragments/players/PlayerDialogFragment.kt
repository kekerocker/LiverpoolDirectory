package com.example.liverpooldirectory.fragments.players

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.liverpooldirectory.R
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

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener {
            dismiss()
        }
        playerImageView.setImageResource(player.photoRes)
        nameAndAgeTextView.text  = "${player.firstName} ${player.lastName}, ${player.age} лет"
        biographyTextView.text = player.biography
    }
}