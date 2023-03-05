package com.joeblakeb.logic

import com.joeblakeb.lib.ConnectFourGameInterface

class ConnectFourGameLogic (override val columns: Int = 7, override val rows: Int = 6): ConnectFourGameInterface {
    /**
     * Set up two-dimensional array of integer, of size columns x rows
     * and fill it with zeroes.
     */
    private var data: Array<IntArray> = Array(columns) { IntArray(rows) { 0 } }

    /**
     * Determines the player who's turn it currently is.
     */
    var playerTurn: Int = 1
        private set

    override fun getToken(column: Int, row: Int): Int {
        return data[column][row]
    }

    override fun playToken(column: Int, player: Int): Boolean {
        require(player in 1..2) {
            "Player must be 1 or 2. $player is invalid."
        }

        for (row in 0 until rows) {
            if (data[column][row] == 0) {
                data[column][row] = player
                return true
            }
        }
        return false
    }

    fun playToken(column: Int): Boolean {
        val status = playToken(column, playerTurn)
        playerTurn = (playerTurn%2) + 1
        return status
    }
}