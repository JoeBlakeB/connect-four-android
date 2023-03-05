package com.joeblakeb.logic

import com.joeblakeb.lib.ConnectFourGameInterface

import com.joeblakeb.testlib.ConnectFourGameTest

internal class ConnectFourGameLogicTest : ConnectFourGameTest() {
    override fun createGame(columns: Int, rows: Int): ConnectFourGameInterface {
        return ConnectFourGameLogic()
    }
}