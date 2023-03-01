package com.joeblakeb.logic

import com.joeblakeb.lib.ConnectFourGameInterface
import org.junit.jupiter.api.Assertions.*

import com.joeblakeb.testlib.ConnectFourTest

internal class ConnectFourGameLogicTest : ConnectFourTest() {
    override fun createGame(columns: Int, rows: Int): ConnectFourGameInterface {
        return ConnectFourGameLogic()
    }
}