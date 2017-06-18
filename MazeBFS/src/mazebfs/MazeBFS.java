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
import java.util.logging.Level;
import java.util.logging.Logger;

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
                for (int c = 0; c < cols; c++)
                    maze[r][c] = line.charAt(c);
            }

            // Homework:
            // Look up trails on The Java Tutorial by Oracle
            // 1. Study the "Collections Framework"
            // 2. "Generics"
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MazeBFS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MazeBFS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
