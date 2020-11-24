package com.example.liverpooldirectory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.liverpooldirectory.DBHelper.DBHelper2
import com.example.liverpooldirectory.DBHelper.Table
import com.example.liverpooldirectory.adapters.ListTableAdapter
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    internal lateinit var db: DBHelper2
    internal var lstTables: List<Table> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        supportActionBar?.hide()

        db = DBHelper2(this)
        refreshData()
        btn_add2.setOnClickListener {
            val table = Table(
                Integer.parseInt(edt_position.text.toString()),
                edt_clubname.text.toString(),
                Integer.parseInt(edt_matches.text.toString()),
                Integer.parseInt(edt_win.text.toString()),
                Integer.parseInt(edt_draw.text.toString()),
                Integer.parseInt(edt_lose.text.toString()),
                Integer.parseInt(edt_points.text.toString())
            )
            db.addTable(table)
            refreshData()
        }

        btn_update2.setOnClickListener {
            val table = Table(
                Integer.parseInt(edt_position.text.toString()),
                edt_clubname.text.toString(),
                Integer.parseInt(edt_matches.text.toString()),
                Integer.parseInt(edt_win.text.toString()),
                Integer.parseInt(edt_draw.text.toString()),
                Integer.parseInt(edt_lose.text.toString()),
                Integer.parseInt(edt_points.text.toString())
            )
            db.updateTable(table)
            refreshData()
        }

        btn_delete2.setOnClickListener {
            val table = Table(
                Integer.parseInt(edt_position.text.toString()),
                edt_clubname.text.toString(), Integer.parseInt(edt_matches.text.toString()),
                Integer.parseInt(edt_win.text.toString()),
                Integer.parseInt(edt_draw.text.toString()),
                Integer.parseInt(edt_lose.text.toString()),
                Integer.parseInt(edt_points.text.toString())
            )
            db.deleteTable(table)
            refreshData()
        }
    }

    private fun refreshData() {
        lstTables = db.allTable
        val adapter = ListTableAdapter(this, lstTables, edt_position, edt_clubname, edt_matches, edt_win, edt_draw, edt_lose, edt_points)
        list_table.adapter = adapter
    }
}