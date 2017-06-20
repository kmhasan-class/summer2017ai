/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazebfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

enum Action {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

class State {

    private int row;
    private int col;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.row;
        hash = 97 * hash + this.col;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if (this.row != other.row) {
            return false;
        }
        if (this.col != other.col) {
            return false;
        }
        return true;
    }

    public State(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        return "State{" + "row=" + row + ", col=" + col + '}';
    }

    State getNextState(Action action, char maze[][]) {
        State nextState = null;

        switch (action) {
            case UP:
                nextState = new State(this.row - 1, this.col);
                break;
            case DOWN:
                nextState = new State(this.row + 1, this.col);
                break;
            case LEFT:
                nextState = new State(this.row, this.col - 1);
                break;
            case RIGHT:
                nextState = new State(this.row, this.col + 1);
                break;
        }

        if (nextState.row < 0 || nextState.row >= maze.length) {
            return null;
        }
        if (nextState.col < 0 || nextState.col >= maze[0].length) {
            return null;
        }
        if (maze[nextState.row][nextState.col] == '#') {
            return null;
        }

        return nextState;
    }

    /*
    // Bad choice
    State getState(String action) {
        State nextState = null;
        
        switch (action) {
            case "UP":
                nextState = new State(this.row - 1, this.col);
                break;
            case "DOWN":
                nextState = new State(this.row + 1, this.col);
                break;
        }
        return nextState;
    }
     */
}

/**
 *
 * @author kmhasan
 */
public class MazeBFS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        char maze[][];

        try {
            // replace with try with resources (google it)
            RandomAccessFile input = new RandomAccessFile("maze.in", "r");
            String line = input.readLine();
            String tokens[] = line.split("\\ ");
            int rows = Integer.parseInt(tokens[0]);
            int cols = Integer.parseInt(tokens[1]);

            System.out.println(rows + "x" + cols);
            maze = new char[rows][cols];

            for (int r = 0; r < rows; r++) {
                line = input.readLine();
                System.out.println(line);
                for (int c = 0; c < cols; c++) {
                    maze[r][c] = line.charAt(c);
                }
            }

            int sRow = -1, sCol = -1;
            int gRow = -1, gCol = -1;
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (maze[r][c] == 'S') {
                        sRow = r;
                        sCol = c;
                    } else if (maze[r][c] == 'E') {
                        gRow = r;
                        gCol = c;
                    }
                }
            }

            boolean found = false;
            State initialState = new State(sRow, sCol);
            State goalState = new State(gRow, gCol);
            if (initialState.equals(goalState)) {
                System.out.println("Solution found");
                found = true;
            }

            Queue<State> frontier = new LinkedList<>();
            Set<State> exploredSet = new HashSet<>();
            frontier.add(initialState);

            while (!frontier.isEmpty() && !found) {
                State currentState = frontier.remove();

                exploredSet.add(currentState);

                for (Action action : Action.values()) {
                    State nextState = currentState.getNextState(action, maze);
                    if (nextState != null
                            && !frontier.contains(nextState)
                            && !exploredSet.contains(nextState)) {

                        if (nextState.equals(goalState)) {
                            System.out.println("Solution found");
                            found = true;
                            break;
                        }

                        frontier.add(nextState);
                    }
                }
            }

            if (!found) {
                System.out.println("Solution does not exist");
            }

            // Homework:
            // Look up trails on The Java Tutorial by Oracle
            // 1. Study the "Collections Framework"
            // 2. "Generics"
            // 3. Create a Node class, and put the State inside it
            // 4. Rewrite the code so that we know how many steps it took to reach the solution
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MazeBFS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MazeBFS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
