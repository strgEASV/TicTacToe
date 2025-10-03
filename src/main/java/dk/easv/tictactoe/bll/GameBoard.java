
package dk.easv.tictactoe.bll;

import dk.easv.tictactoe.gui.controller.TicTacViewController;

/**
 *
 * @author EASV
 */
public class GameBoard implements IGameBoard
{
    public int player = 1;
    private final int[][] board = new int[3][3];
    /**
     * Returns 0 for player 0, 1 for player 1.
     *
     * @return int Id of the next player.
     */
    public int getNextPlayer()
    {
        if (player == 1) {
            player = 0;
        }
        else if (player == 0) {
            player = 1;
        }
        return player;
    }

    /**
     * Attempts to let the current player play at the given coordinates. If the
     * attempt is succesful the current player has ended his turn and it is the
     * next players turn.
     *
     * @param col column to place a marker in.
     * @param row row to place a marker in.
     * @return true if the move is accepted, otherwise false. If gameOver == true
     * this method will always return false.
     */
    public boolean play(int col, int row)
    {
        String message = "";
        if (board[row][col] == 0) {
            board[row][col] = player + 1;
            getNextPlayer();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Tells us if the game has ended either by draw or by meeting the winning
     * condition.
     *
     * @return true if the game is over, else it will retun false.
     */
    public boolean isGameOver() {
        int checkWin = -1;
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == 1 && board[i][1] == 1 && board[i][2] == 1) {
                return true;
            }
            if (board[i][0] == 2 && board[i][1] == 2 && board[i][2] == 2) {
                return true;
            }
        }
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == 1 && board[1][j] == 1 && board[2][j] == 1) {
                return true;
            }
            if (board[0][j] == 2 && board[1][j] == 2 && board[2][j] == 2) {
                return true;
            }
        }
        if (board[0][0] == 1 && board[1][1] == 1 && board[2][2] == 1) {
            return true;
        }
        if (board[0][0] == 2 && board[1][1] == 2 && board[2][2] == 2) {
            return true;
        }
        if (board[0][2] == 1 && board[1][1] == 1 && board[2][0] == 1) {
            return true;
        }
        if (board[0][2] == 2 && board[1][1] == 2 && board[2][0] == 2) {
            return true;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        player = -1;
        return true;
    }

    /**
     * Gets the id of the winner, -1 if its a draw.
     *
     * @return int id of winner, or -1 if draw.
     */
    public int getWinner()
    {
        return player;
    }

    /**
     * Resets the game to a new game state.
     */
    public void newGame()
    {
        player = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
    }
}
