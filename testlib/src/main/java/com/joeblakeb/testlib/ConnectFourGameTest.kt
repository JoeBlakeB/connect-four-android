package com.joeblakeb.testlib

import com.joeblakeb.lib.ConnectFourGameInterface
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class ConnectFourGameTest {
    private lateinit var defaultGame: ConnectFourGameInterface

    abstract fun createGame(columns: Int, rows: Int): ConnectFourGameInterface

    @BeforeEach
    fun createTestGrid() {
        defaultGame = createGame(7, 6)
    }

    @Test
    fun testGetToken() {
        for(col in 0 until defaultGame.columns) {
            for (row in 0 until defaultGame.rows) {
                val token = defaultGame.getToken(col, row)
                assertEquals(0, token)
            }
        }
    }

    @Test
    fun testPlayToken() {
        for (i in 1..2) {
            assertEquals(i, defaultGame.playerTurn)
            defaultGame.playToken(0, false)
            assertEquals(i, defaultGame.getToken(0, 5))
            assertEquals(0, defaultGame.getToken(0, 4))

            assertEquals(0, defaultGame.playerTurn)
            assertEquals(1, defaultGame.playToken(0, false))

            for (j in 5 downTo i) {
                defaultGame.dropTokenContinue(0, j, false)
                assertEquals(i, defaultGame.getToken(0, j - 1))
                assertEquals(0, defaultGame.getToken(0, j))
            }
        }

        assertEquals(1, defaultGame.getToken(0, 0))
        assertEquals(0, defaultGame.getToken(1, 0))
        assertEquals(1, defaultGame.playerTurn)
    }
}