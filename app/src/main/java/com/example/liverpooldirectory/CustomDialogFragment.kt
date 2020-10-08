package com.example.liverpooldirectory

import android.content.DialogInterface
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.alisson_popup.view.*
import kotlinx.android.synthetic.main.salah_popup.*
import kotlinx.android.synthetic.main.virgil_popup.*
import kotlinx.android.synthetic.main.virgil_popup.closebutton1

class AlissonDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.alisson_popup, container, false)

        rootView.closebutton1.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}

class ManeDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.mane_popup, container, false)

        rootView.closebutton1.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}

class SalahDialogFragment : DialogFragment() {

    private var mp: MediaPlayer? = null
    private var currentSong: MutableList<Int> = mutableListOf(R.raw.salahchant)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.salah_popup, container, false)

        rootView.closebutton1.setOnClickListener {
            dismiss()
        }

        return rootView
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mp?.stop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainSound(currentSong[0])
        closebutton1.setOnClickListener {
            if (mp !== null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
                dismiss()
            } else if (mp == null) {
                dismiss()
            }
        }
        play_stop2.setOnClickListener {
            if (mp !== null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
                play_stop2.setImageResource(R.drawable.play)
            } else if (mp == null) {
                mainSound(currentSong[0])
                play_stop2.setImageResource(R.drawable.stop)
            }
        }
    }

    private fun mainSound(id: Int) {
        if (mp == null) {
            mp = MediaPlayer.create(requireContext(), id)
            Log.d("MainActivity", "ID: ${mp!!.audioSessionId}")
        }
        mp?.start()
        Log.d("LibraryActivity", "Duration: ${mp!!.duration / 1000} seconds")
    }
}

class FirminoDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.firmino_popup, container, false)

        rootView.closebutton1.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}

class HendersonDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.henderson_popup, container, false)

        rootView.closebutton1.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}

class FabinhoDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fabinho_popup, container, false)

        rootView.closebutton1.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}

class WijnaldumDialogFragment : DialogFragment() {

    private var mp: MediaPlayer? = null
    private var currentSong: MutableList<Int> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.wijnaldum_popup, container, false)

        rootView.closebutton1.setOnClickListener {
            dismiss()
        }

        return rootView
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mp?.stop()
    }
}

class RobertsonDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.robertson_popup, container, false)

        rootView.closebutton1.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}

class VirgilDialogFragment : DialogFragment() {

    private var mp: MediaPlayer? = null
    private var currentSong: MutableList<Int> = mutableListOf(R.raw.virgil_chant)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val rootView: View = inflater.inflate(R.layout.virgil_popup, container, false)
        return rootView
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mp?.stop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainSound(currentSong[0])

        closebutton1.setOnClickListener {
            if (mp !== null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
                dismiss()
            } else if (mp == null) {
                dismiss()
            }
        }
        playstop1.setOnClickListener {
            if (mp !== null) {
                mp?.stop()
                mp?.reset()
                mp?.release()
                mp = null
                playstop1.setImageResource(R.drawable.play)
            } else if (mp == null) {
                mainSound(currentSong[0])
                playstop1.setImageResource(R.drawable.stop)
            }
        }
    }

    private fun mainSound(id: Int) {
        if (mp == null) {
            mp = MediaPlayer.create(requireContext(), id)
            Log.d("MainActivity", "ID: ${mp!!.audioSessionId}")
        }
        mp?.start()
        Log.d("LibraryActivity", "Duration: ${mp!!.duration / 1000} seconds")
    }
}

class GomezDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.gomez_popup, container, false)

        rootView.closebutton1.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}

class TaaDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.taa_popup, container, false)

        rootView.closebutton1.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}