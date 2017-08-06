/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimax.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author kmhasan
 */
public class State {

    byte board[][];
    Player currentPlayer;

    public State(int dimension, Player currentPlayer) {
        board = new byte[dimension][dimension];
        this.currentPlayer = currentPlayer;
    }

    public void setRow(int row, String line) {
//        for (int c = 0; c < line.length(); c++)
//            board[row][c] = line.charAt(c);

        System.arraycopy(line.getBytes(), 0, board[row], 0, line.length());
    }

    private String boardToString() {
        String output = "";
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                output += (char) board[r][c];
            }
            output += "\n";
        }
        return output;
    }

    public boolean isTerminal() {
        boolean foundFreeCell = false;
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] == '.') {
                    foundFreeCell = true;
                }
            }
        }
        if (!foundFreeCell) {
            return true;
        } else if (getUtility() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public int getUtility() {
        boolean hasSameSymbol;
        byte firstSymbol;

        // testing for the rows to see if we have a winner
        for (int r = 0; r < board.length; r++) {
            firstSymbol = board[r][0];
            hasSameSymbol = true;
            for (int c = 0; c < board[r].length; c++) {
                if (board[r][c] != firstSymbol) {
                    hasSameSymbol = false;
                }
            }
            if (hasSameSymbol == true && ((char) firstSymbol != '.')) {
                if (("" + (char) firstSymbol).equals(currentPlayer.name())) {
                    return +1;
                } else {
                    return -1;
                }
            }
        }

        // testing for the columns to see if we have a winner
        for (int c = 0; c < board.length; c++) {
            firstSymbol = board[0][c];
            hasSameSymbol = true;
            for (int r = 0; r < board[c].length; r++) {
                if (board[r][c] != firstSymbol) {
                    hasSameSymbol = false;
                }
            }
            if (hasSameSymbol == true && ((char) firstSymbol != '.')) {
                if (("" + (char) firstSymbol).equals(currentPlayer.name())) {
                    return +1;
                } else {
                    return -1;
                }
            }
        }

        // testing for the main diagonal to see if we have a winner
        firstSymbol = board[0][0];
        hasSameSymbol = true;
        for (int c = 0; c < board.length; c++) {
            if (board[c][c] != firstSymbol) {
                hasSameSymbol = false;
            }
        }

        if (hasSameSymbol == true && ((char) firstSymbol != '.')) {
            if (("" + (char) firstSymbol).equals(currentPlayer.name())) {
                return +1;
            } else {
                return -1;
            }
        }

        // testing for the inverse diagonal to see if we have a winner
        firstSymbol = board[0][board.length - 1];
        hasSameSymbol = true;
        for (int c = 0; c < board.length; c++) {
            if (board[c][board.length - c - 1] != firstSymbol) {
                hasSameSymbol = false;
            }
        }

        if (hasSameSymbol == true && ((char) firstSymbol != '.')) {
            if (("" + (char) firstSymbol).equals(currentPlayer.name())) {
                return +1;
            } else {
                return -1;
            }
        }
        
        return 0;
    }

    public List<Cell> getFreeCells() {
        List<Cell> freeCellList = new ArrayList<>();
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[r].length; c++)
                if (board[r][c] == '.')
                    freeCellList.add(new Cell(r, c));
        return freeCellList;
    }
    
    public State getNextState(Cell cell) {
        Player nextPlayer = null;
        if (currentPlayer == Player.X)
            nextPlayer = Player.O;
        else nextPlayer = Player.X;

        State nextState = new State(board.length, nextPlayer);
        
        for (int r = 0; r < board.length; r++)
            for (int c = 0; c < board[r].length; c++)
                nextState.board[r][c] = board[r][c];
        
        nextState.board[cell.getRow()][cell.getCol()] = (byte) currentPlayer.name().charAt(0);
        return nextState;
    }
    
    @Override
        public String toString() {
        return "State{" + "board=\n" + boardToString() + ", currentPlayer=" + currentPlayer + '}';
    }

}
