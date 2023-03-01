package com.joeblakeb.logic

import com.joeblakeb.lib.ConnectFourGameInterface

class ConnectFourGameLogic (override val columns: Int = 7, override val rows: Int = 6): ConnectFourGameInterface {
    override fun getToken(column: Int, row: Int): Int {
        TODO()
    }

    override fun playToken(column: Int, player: Int): Boolean {
        TODO()
    }
}