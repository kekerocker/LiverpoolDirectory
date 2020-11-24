package com.example.liverpooldirectory.DBHelper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper2(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VER) {
    companion object {
        private const val DATABASE_VER = 2
        private const val DATABASE_NAME = "EPLTDB.db"

        //TABLE
        const val TABLE_NAME = "EPLTable"
        const val COL_POSITIONS = "Position"
        const val COL_CLUB_NAME = "Club_name"
        const val COL_MATCHES = "Matches"
        const val COL_WIN = "Win"
        const val COL_DRAW = "Draw"
        const val COL_LOSE = "Lose"
        const val COL_POINTS = "Points"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLEQUERY =
            ("CREATE TABLE $TABLE_NAME ($COL_POSITIONS INTEGER PRIMARY KEY, $COL_CLUB_NAME TEXT, $COL_MATCHES INTEGER, $COL_WIN INTEGER, $COL_DRAW INTEGER, $COL_LOSE INTEGER, $COL_POINTS INTEGER)")
        db!!.execSQL(CREATE_TABLEQUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //CRUD
    val allTable: List<Table>
        get() {
            val lstTable = ArrayList<Table>()
            val selectQuery = "SELECT * FROM $TABLE_NAME"
            val db = this.writableDatabase
            val cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
                    val table = Table()
                    table.positions = cursor.getInt(cursor.getColumnIndex((COL_POSITIONS)))
                    table.clubName = cursor.getString(cursor.getColumnIndex(COL_CLUB_NAME))
                    table.matches = cursor.getInt(cursor.getColumnIndex(COL_MATCHES))
                    table.wins = cursor.getInt(cursor.getColumnIndex(COL_WIN))
                    table.draws = cursor.getInt(cursor.getColumnIndex(COL_DRAW))
                    table.loses = cursor.getInt(cursor.getColumnIndex(COL_LOSE))
                    table.points = cursor.getInt(cursor.getColumnIndex(COL_POINTS))
                    lstTable.add(table)
                } while (cursor.moveToNext())
            }
            db.close()
            return lstTable
        }

    fun addTable(table: Table) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_POSITIONS, table.positions)
        values.put(COL_CLUB_NAME, table.clubName)
        values.put(COL_MATCHES, table.matches)
        values.put(COL_WIN, table.wins)
        values.put(COL_DRAW, table.draws)
        values.put(COL_LOSE, table.loses)
        values.put(COL_POINTS, table.points)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun updateTable(table: Table): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_POSITIONS, table.positions)
        values.put(COL_CLUB_NAME, table.clubName)
        values.put(COL_MATCHES, table.matches)
        values.put(COL_WIN, table.wins)
        values.put(COL_DRAW, table.draws)
        values.put(COL_LOSE, table.loses)
        values.put(COL_POINTS, table.points)

        return db.update(TABLE_NAME, values, "$COL_POSITIONS=?", arrayOf(table.positions.toString()))
    }

    fun deleteTable(table: Table) {
        val db = this.writableDatabase

        db.delete(TABLE_NAME, "$COL_POSITIONS=?", arrayOf(table.positions.toString()))
        db.close()
    }
}