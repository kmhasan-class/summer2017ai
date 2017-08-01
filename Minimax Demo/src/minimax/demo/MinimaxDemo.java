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
public class MinimaxDemo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        State state = Reader.getState("board.in");
        System.out.println(state);
        System.out.println(state.getUtility());
        //MoveGenerator moveGenerator = new MoveGenerator();
        //System.out.println(moveGenerator.doMinimax(state));
    }
    
}
