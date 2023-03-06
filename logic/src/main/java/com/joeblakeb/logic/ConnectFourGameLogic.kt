package com.joeblakeb.logic

import com.joeblakeb.lib.ConnectFourGameInterface
import java.util.Timer
import kotlin.concurrent.timerTask

const val DROP_DELAY: Long = 80

class ConnectFourGameLogic (
    override val columns: Int = 7,
    override val rows: Int = 6
): ConnectFourGameInterface {
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

    /**
     * Store the list of listeners
     */
    private val gameChangeListeners = mutableListOf<ConnectFourGameInterface.GameChangeListener>()

    override fun addGameChangeListener(listener: ConnectFourGameInterface.GameChangeListener) {
        if (listener !in gameChangeListeners) {
            gameChangeListeners.add(listener)
        }
    }

    override fun removeGameChangeListener(listener: ConnectFourGameInterface.GameChangeListener) {
        gameChangeListeners.remove(listener)
    }

    /**
     * Call all of the listeners
     */
    private fun callListeners() {
        for (listener in gameChangeListeners) {
            listener.onGameChange()
        }
    }

    override fun getToken(column: Int, row: Int): Int {
        return data[column][row]
    }

    override fun playToken(column: Int, CALLBACK: Boolean): Int {
        if (playerTurn !in 1..2) {
            return 1
        }

        if (data[column][rows-1] == 0) {
            data[column][rows-1] = playerTurn
            playerTurn = 0

            if (CALLBACK) {
                if (data[column][rows-2] == 0) {
                    Timer().schedule(timerTask {
                        dropTokenContinue(column, rows - 1)
                    }, DROP_DELAY)
                } else {
                    dropTokenContinue(column, rows - 1)
                }
            }
            return 0
        }
        else {
            return 2
        }
    }

    override fun dropTokenContinue(column: Int, row: Int, CALLBACK: Boolean) {
        if (row > 0 && data[column][row-1] == 0) {
            data[column][row-1] = data[column][row]
            data[column][row] = 0

            if (CALLBACK && row >= 2 && data[column][row - 2] == 0) {
                Timer().schedule(timerTask {
                    dropTokenContinue(column, row - 1)
                }, DROP_DELAY)
            } else {
                playerTurn = (data[column][row-1]%2) + 1
            }
            return callListeners()
        } else {
            playerTurn = (data[column][row]%2) + 1
            callListeners()
        }
    }
}