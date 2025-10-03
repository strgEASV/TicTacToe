package dk.easv.tictactoe.bll;

public class GameBoard implements IGameBoard {
    public int player = 1;
    private final int[][] board = new int[3][3];

    private int winner = 0; // 0 = žádný, -1 = remíza, 1 nebo 2 = hráči
    private int[][] winningCombination = null; // uloží 3 výherní pole

    public int getNextPlayer() {
        if (player == 1) {
            player = 0;
        } else if (player == 0) {
            player = 1;
        }
        return player;
    }

    public boolean play(int col, int row) {
        if (isGameOver()) {
            return false; // hra už skončila
        }

        if (board[row][col] == 0) {
            board[row][col] = player + 1;
            getNextPlayer();
            return true;
        } else {
            return false;
        }
    }

    public boolean isGameOver() {
        // reset winningCombination při každé kontrole
        winningCombination = null;

        // řádky
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                winner = board[i][0];
                winningCombination = new int[][]{{i,0}, {i,1}, {i,2}};
                return true;
            }
        }

        // sloupce
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != 0 && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                winner = board[0][j];
                winningCombination = new int[][]{{0,j}, {1,j}, {2,j}};
                return true;
            }
        }

        // hlavní diagonála
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            winner = board[0][0];
            winningCombination = new int[][]{{0,0}, {1,1}, {2,2}};
            return true;
        }

        // vedlejší diagonála
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            winner = board[0][2];
            winningCombination = new int[][]{{0,2}, {1,1}, {2,0}};
            return true;
        }

        // remíza – žádná pole volná
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return false; // hra pokračuje
                }
            }
        }

        winner = -1;
        return true;
    }

    public int getWinner() {
        return winner;
    }

    public int[][] getWinningCombination() {
        return winningCombination;
    }

    public void newGame() {
        winner = 0;
        player = 1;
        winningCombination = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
    }
}