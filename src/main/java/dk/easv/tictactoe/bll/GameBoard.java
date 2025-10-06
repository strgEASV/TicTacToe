package dk.easv.tictactoe.bll;

import javafx.scene.control.Button;

public class GameBoard implements IGameBoard {
    public int player = 1;
    private final int[][] board = new int[3][3];
    public int typeOfGame = 0; //0 for 2 players, 1 for randomAI, 2 for smartAI (minimax)

    private int winner = 0; // 0 = žádný, -1 = remíza, 1 nebo 2 = hráči
    private int[][] winningCombination = null; // uloží 3 výherní pole

    private static final int PLAYER_X = 2; // Human player
    private static final int PLAYER_O = 3; // AI player

    public int getNextPlayer() {
        if (player == 1 && typeOfGame == 0) {
            player = 0;
        }
        else if (player == 0 && typeOfGame == 0) {
            player = 1;
        }
        return player;
    }

    public int getTypeOfGame() {
        return typeOfGame;
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

    public void AIPlay(int col, int row) {
        board[row][col] = 3;
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
        typeOfGame = 0;
        player = 1;
        winningCombination = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
    }

    public void newRandomAIGame() {
        winner = 0;
        typeOfGame = 1;
        player = 0;
        winningCombination = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
    }

    public void newSmartAIGame() {
        winner = 0;
        typeOfGame = 2;
        player = 0;
        winningCombination = null;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
            }
        }
    }

    // Enhanced Minimax with strategic positioning
    public int[] getBestMove() {
        // First move optimization - take center if available, otherwise corner
        if (isEmpty()) {
            return new int[]{1, 1}; // Center
        }
        
        int moveCount = countMoves();
        if (moveCount == 1) {
            // If center is taken, take a corner
            if (board[1][1] != 0) {
                return new int[]{0, 0}; // Top-left corner
            }
            // If corner is taken, take center
            return new int[]{1, 1};
        }

        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[]{-1, -1};
        
        // Check for immediate win
        int[] winMove = findWinningMove(PLAYER_O);
        if (winMove[0] != -1) {
            return winMove;
        }
        
        // Check for blocking opponent's win
        int[] blockMove = findWinningMove(PLAYER_X);
        if (blockMove[0] != -1) {
            return blockMove;
        }
        
        // Strategic move ordering: center > corners > edges
        int[][] moveOrder = {
            {1, 1}, // center
            {0, 0}, {0, 2}, {2, 0}, {2, 2}, // corners
            {0, 1}, {1, 0}, {1, 2}, {2, 1}  // edges
        };
        
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;
        
        for (int[] move : moveOrder) {
            int i = move[0];
            int j = move[1];
            
            if (board[i][j] == 0) {
                board[i][j] = PLAYER_O;
                int score = minimax(0, false, alpha, beta);
                board[i][j] = 0;
                
                // Prefer moves with higher strategic value when scores are equal
                if (score > bestScore) {
                    bestScore = score;
                    bestMove[0] = i;
                    bestMove[1] = j;
                }
                
                alpha = Math.max(alpha, score);
            }
        }
        
        return bestMove;
    }

    private int[] findWinningMove(int player) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    board[i][j] = player;
                    boolean wins = checkWinnerForMinimax() == player;
                    board[i][j] = 0;
                    if (wins) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return new int[]{-1, -1};
    }

    private boolean isEmpty() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private int countMoves() {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 0) {
                    count++;
                }
            }
        }
        return count;
    }

    private int minimax(int depth, boolean isMaximizing, int alpha, int beta) {
        int result = checkWinnerForMinimax();
        
        if (result == PLAYER_O) {
            return 100 - depth; // AI wins - prefer faster wins
        }
        if (result == PLAYER_X) {
            return depth - 100; // Player wins - penalize
        }
        
        if (isBoardFull()) {
            return 0; // Draw
        }
        
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = PLAYER_O;
                        int score = minimax(depth + 1, false, alpha, beta);
                        board[i][j] = 0;
                        
                        bestScore = Math.max(score, bestScore);
                        alpha = Math.max(alpha, score);
                        
                        if (beta <= alpha) {
                            return bestScore;
                        }
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == 0) {
                        board[i][j] = PLAYER_X;
                        int score = minimax(depth + 1, true, alpha, beta);
                        board[i][j] = 0;
                        
                        bestScore = Math.min(score, bestScore);
                        beta = Math.min(beta, score);
                        
                        if (beta <= alpha) {
                            return bestScore;
                        }
                    }
                }
            }
            return bestScore;
        }
    }

    private int checkWinnerForMinimax() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != 0 && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                return board[i][0];
            }
        }
        
        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] != 0 && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                return board[0][j];
            }
        }
        
        // Check diagonals
        if (board[0][0] != 0 && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            return board[0][0];
        }
        if (board[0][2] != 0 && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            return board[0][2];
        }
        
        return 0;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getBoard() {
        return board;
    }
}
