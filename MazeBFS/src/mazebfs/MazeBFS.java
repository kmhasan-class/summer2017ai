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

class Node {
    private State state;
    private Node previousNode;
    private Action previousAction;
    private int steps;

    public Node(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


    public Action getPreviousAction() {
        return previousAction;
    }

    public void setPreviousAction(Action previousAction) {
        this.previousAction = previousAction;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }
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
    
    public String printCoordinates() {
        return "(" + row + "," + col + ")";
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

    private static void printPath(Node currentNode) {
        if (currentNode.getPreviousNode() != null) {
            printPath(currentNode.getPreviousNode());
            System.out.print(" - " + currentNode.getPreviousAction() + " - " +
                    currentNode.getState().printCoordinates());
        } else {
            System.out.print(currentNode.getState().printCoordinates());
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        MazeGenerator generator = new MazeGenerator();
//        generator.generateMaze("randommaze.in", 1000, 1000, 0.9);
//        System.exit(0);
        
        char maze[][];

        try {
            // replace with try with resources (google it)
//            RandomAccessFile input = new RandomAccessFile("randommaze.in", "r");
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

            Queue<Node> frontier = new LinkedList<>();
            Set<State> exploredSet = new HashSet<>();
            
            int maxFrontierSize = 0;
            
            frontier.add(new Node(initialState));
            maxFrontierSize = Math.max(maxFrontierSize, frontier.size());
            
            while (!frontier.isEmpty() && !found) {
                Node currentNode = frontier.remove();
                State currentState = currentNode.getState();

                exploredSet.add(currentState);

                for (Action action : Action.values()) {
                    State nextState = currentState.getNextState(action, maze);
                    if (nextState != null
                            && !frontier.contains(nextState)
                            && !exploredSet.contains(nextState)) {

                        Node nextNode = new Node(nextState);
                        nextNode.setPreviousNode(currentNode);
                        nextNode.setPreviousAction(action);
                        nextNode.setSteps(currentNode.getSteps() + 1);
                        
                        if (nextState.equals(goalState)) {
                            System.out.println("Solution found");
                            System.out.println("Took " + nextNode.getSteps() + " step(s)");
                            //printPath(nextNode);
                            found = true;
                            break;
                        }
                        
                        frontier.add(nextNode);
                        maxFrontierSize = Math.max(maxFrontierSize, frontier.size());
                    }
                }
            }

            if (!found) {
                System.out.println("Solution does not exist");
            }
            System.out.println("Max frontier size " + maxFrontierSize);

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

/*
Homework:
1. Measure how much time it takes for the program to run
2. Count how many states are generated
3. Count how many nodes go into the frontier
4. Count how many states go into the explored set
 */