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

    private double getMaxValue(State currentState) {
        double bestUtility = Double.NEGATIVE_INFINITY;
        System.out.println("MAX " + currentState);
        if (currentState.isTerminal()) {
            System.exit(0);
            return currentState.getUtility();
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
        System.out.println("MIN " + currentState);
        if (currentState.isTerminal()) {
            System.exit(0);
            return currentState.getUtility();
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
