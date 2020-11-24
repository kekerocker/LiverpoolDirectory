package com.example.liverpooldirectory.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import com.example.liverpooldirectory.R
import kotlinx.android.synthetic.main.row_layout2.view.*

class ListTableAdapter(
    private var activity: Activity,
    private var lstTable:List<com.example.liverpooldirectory.DBHelper.Table>,
    internal var edt_position: EditText,
    internal var edt_clubName: EditText,
    internal var edt_matches: EditText,
    internal var edt_win: EditText,
    internal var edt_draw: EditText,
    internal var edt_lose: EditText,
    internal var edt_points: EditText,
) : BaseAdapter() {

   internal var inflater:LayoutInflater

   init{
       inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

   }

    override fun getView(position: Int, converterView: View?, parent: ViewGroup?): View {
        val rowView:View
        rowView = inflater.inflate(R.layout.row_layout2, null)

        rowView.txt_position_id.text = lstTable[position].positions.toString()
        rowView.txt_clubname.text = lstTable[position].clubName.toString()
        rowView.txt_matches.text = lstTable[position].matches.toString()
        rowView.txt_win.text = lstTable[position].wins.toString()
        rowView.txt_draw.text = lstTable[position].draws.toString()
        rowView.txt_lose.text = lstTable[position].loses.toString()
        rowView.txt_points.text = lstTable[position].points.toString()

        rowView.setOnClickListener {
            edt_position.setText(rowView.txt_position_id.text.toString())
            edt_clubName.setText(rowView.txt_clubname.text.toString())
            edt_matches.setText(rowView.txt_matches.text.toString())
            edt_win.setText(rowView.txt_win.text.toString())
            edt_lose.setText(rowView.txt_draw.text.toString())
            edt_draw.setText(rowView.txt_lose.text.toString())
            edt_points.setText(rowView.txt_points.text.toString())
        }
        return rowView
    }

    override fun getItem(position: Int): Any {
        return lstTable[position]
    }

    override fun getItemId(position: Int): Long {
        return lstTable[position].positions.toLong()
    }

    override fun getCount(): Int {
        return lstTable.size
    }
}