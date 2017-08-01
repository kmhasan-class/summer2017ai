/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minimax.demo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kmhasan
 */
public class Reader {
    public static State getState(String filename) {
        int dimension;
        String line;
        Player currentPlayer;
        
        
        try (RandomAccessFile input = new RandomAccessFile(filename, "r")) {
            dimension = Integer.parseInt(input.readLine());
            currentPlayer = Player.valueOf(input.readLine());
            
            State state = new State(dimension, currentPlayer);
            
            for (int i = 0; i < dimension; i++) {
                line = input.readLine();
                state.setRow(i, line);
            }
            
            return state;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
