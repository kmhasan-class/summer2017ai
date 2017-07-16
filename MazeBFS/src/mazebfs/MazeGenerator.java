/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazebfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kmhasan
 */
public class MazeGenerator {

    private char maze[][];

    // generateMaze("maze.in", 5000, 5000)
    public void generateMaze(String filename, int rows, int cols, double freeProbability) {
        maze = new char[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (Math.random() < freeProbability) {
                    maze[r][c] = '.';
                } else {
                    maze[r][c] = '#';
                }
            }
        }

        int srow, scol, erow, ecol;
        srow = (int) (Math.random() * rows);
        scol = (int) (Math.random() * cols);
        do {
            erow = (int) (Math.random() * rows);
            ecol = (int) (Math.random() * cols);
        } while (srow == erow && scol == ecol);
        
        maze[srow][scol] = 'S';
        maze[erow][ecol] = 'E';

        try {
            RandomAccessFile output = new RandomAccessFile(filename, "rw");
            output.setLength(0);
            output.writeBytes(rows + " " + cols + "\n");
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    output.writeBytes(maze[r][c] + "");
                }
                output.writeBytes("\n");
            }
            output.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MazeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MazeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public MazeGenerator() {
    }

}
