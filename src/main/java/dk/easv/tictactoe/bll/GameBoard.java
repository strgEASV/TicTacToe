
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
        if (isGameOver()) {
            return false; // hra už skončila, tah neplatný
        }

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
    private int winner = 0; //save the winner

    public boolean isGameOver() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                winner = board[i][0];
                return true;
            }
        }
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != 0 && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                winner = board[0][j];
                return true;
            }
        }
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            winner = board[0][0];
            return true;
        }
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            winner = board[0][2];
            return true;
        }

        // Draw/no more moves possible
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        winner = -1;
        return true;
    }


    /**
     * Gets the id of the winner, -1 if its a draw.
     *
     * @return int id of winner, or -1 if draw.
     */
    public int getWinner()
    {
        return winner;
    }

    /**
     * Resets the game to a new game state.
     */
    public void newGame()
    {
        winner = 0;
        player = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
    }
}
