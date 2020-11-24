package com.example.liverpooldirectory.DBHelper

class Table {
    var positions: Int = 0
    var clubName: String? = null
    var matches: Int = 0
    var wins: Int = 0
    var draws: Int = 0
    var loses: Int = 0
    var points: Int = 0

    constructor() {}

    constructor(
        positions: Int,
        clubName: String,
        matches: Int,
        wins: Int,
        draws: Int,
        loses: Int,
        points: Int
    ) {
        this.positions = positions
        this.clubName = clubName
        this.matches = matches
        this.wins = wins
        this.draws = draws
        this.loses = loses
        this.points = points
    }
}