package com.example.liverpooldirectory

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_players.*

class PlayersActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)
        supportActionBar?.hide()

        val text = "Нажмите на имя игрока, чтобы прочитать его биографию."
        val duration = Toast.LENGTH_LONG
        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()

        textAlisson.setOnClickListener {
            val alisson = AlissonDialogFragment()

            alisson.show(supportFragmentManager, "AlissonDialog")
        }

        textMane.setOnClickListener {
            val mane = ManeDialogFragment()

            mane.show(supportFragmentManager, "ManeDialog")
        }

        textSalah.setOnClickListener {
            val salah = SalahDialogFragment()

            salah.show(supportFragmentManager, "SalahDialog")
        }

        textFirmino.setOnClickListener {
            val firmino = FirminoDialogFragment()

            firmino.show(supportFragmentManager, "FirminoDialog")
        }

        textTAA.setOnClickListener {
            val taa = TaaDialogFragment()

            taa.show(supportFragmentManager, "TaaDialog")
        }

        textGomez.setOnClickListener {
            val gomez = GomezDialogFragment()

            gomez.show(supportFragmentManager, "GomezDialog")
        }

        textVirgil.setOnClickListener {
            val virgil = VirgilDialogFragment()

            virgil.show(supportFragmentManager, "VirgilDialog")
        }

        textRobertson.setOnClickListener {
            val robertson = RobertsonDialogFragment()

            robertson.show(supportFragmentManager, "RobertsonDialog")
        }

        textWijnaldum.setOnClickListener {
            val wijnaldum = WijnaldumDialogFragment()

            wijnaldum.show(supportFragmentManager, "WijnaldumDialog")
        }

        textFabinho.setOnClickListener {
            val fabinho = FabinhoDialogFragment()

            fabinho.show(supportFragmentManager, "FabinhoDialog")
        }

        textHenderson.setOnClickListener {
            val henderson = HendersonDialogFragment()

            henderson.show(supportFragmentManager, "HendersonDialog")
        }
    }
}

