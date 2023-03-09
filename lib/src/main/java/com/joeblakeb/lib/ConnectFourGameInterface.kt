package com.joeblakeb.lib

interface ConnectFourGameInterface {
    /** The width of the game, conventionally 7 */
    val columns: Int
    /** The height of the game, conventionally 6 */
    val rows: Int

    /** Determines the player who's turn it currently is. */
    val playerTurn: Int

    /** Returns the state of the game grid at a specified column and row number. */
    fun getToken(column: Int, row: Int): Int

    /**
     * Start animating a play of a token in the specified column.
     * @param column The column to play the token in
     * @param CALLBACK (optional) if dropTokenContinue should be called, should be true outside of testing
     * @return 0 if valid play, 1 if token is already falling, 2 if column full
     */
    fun playToken(column: Int, CALLBACK: Boolean = true): Int

    /**
     * Continue animating a play of a token in the specified column.
     * @param column The column to play the token in
     * @param row The current row the token is in
     * @param CALLBACK (optional) if this should be called again, should be true outside of testing
     */
    fun dropTokenContinue(column: Int, row: Int, CALLBACK: Boolean = true)

    /**
     * @param listener The listener to add to the listener array
     */
    fun addGameChangeListener(listener: GameChangeListener)

    /**
     * @param listener The listener to remove from the listener array
     */
    fun removeGameChangeListener(listener: GameChangeListener)

    fun interface GameChangeListener {
        /**
         * Called when the grid of the game is automatically updated
         * by the falling of a token.
         */
        fun onGameChange()
    }
}