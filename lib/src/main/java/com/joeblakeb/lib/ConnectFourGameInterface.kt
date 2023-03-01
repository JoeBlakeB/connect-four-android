package com.joeblakeb.lib

interface ConnectFourGameInterface {
    /** The width of the game, conventionally 7 */
    val columns: Int
    /** The height of the game, conventionally 6 */
    val rows: Int

    /** Returns the state of the game grid at a specified column and row number. */
    fun getToken(column: Int, row: Int): Int

    /**
     * Changes the contents of the game grid at a specified column.
     * @param column The column to play the token in
     * @param player The player who's token to play
     * @return `true` if a valid move, `false` if not.
     */
    fun playToken(column: Int, player: Int): Boolean
}