/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimax.demo;

/**
 *
 * @author kmhasan
 */
public class MoveGenerator {
    private Player firstPlayer;
    
    private int getUtility(State state) {
        Player winner = state.getWinner();
        if (winner == null)
            return 0;
        if (winner == firstPlayer)
            return +1;
        else return -1;
    }
    
    private double getMaxValue(State currentState) {
        double bestUtility = Double.NEGATIVE_INFINITY;
        //System.out.println("MAX " + currentState);
        if (currentState.isTerminal()) {
            //System.out.println("Utility: " + getUtility(currentState));
            return getUtility(currentState);
        }

        for (Cell cell : currentState.getFreeCells()) {
            State nextState = currentState.getNextState(cell);
            double utility = getMinValue(nextState);
            if (utility > bestUtility) {
                bestUtility = utility;
            }
        }
        return bestUtility;
    }

    private double getMinValue(State currentState) {
        double bestUtility = Double.POSITIVE_INFINITY;
        //System.out.println("MIN " + currentState);
        if (currentState.isTerminal()) {
            //System.out.println("Utility: " + getUtility(currentState));
            return getUtility(currentState);
        }

        for (Cell cell : currentState.getFreeCells()) {
            State nextState = currentState.getNextState(cell);
            double utility = getMaxValue(nextState);
            if (utility < bestUtility) {
                bestUtility = utility;
            }
        }

        return bestUtility;
    }

    public Cell doMinimax(State initialState) {
        Cell bestCell = null;
        double bestUtility = Double.NEGATIVE_INFINITY;
        firstPlayer = initialState.currentPlayer;
        
        for (Cell cell : initialState.getFreeCells()) {
            State nextState = initialState.getNextState(cell);
            double utility = getMinValue(nextState);
            if (utility > bestUtility) {
                bestUtility = utility;
                bestCell = cell;
            }
        }
        System.out.println("Best cell " + bestCell);
        System.out.println("Best utility: " + bestUtility);
        return bestCell;
    }
}
