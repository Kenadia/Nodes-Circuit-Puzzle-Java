/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package _nodespuzzle;

import java.util.*;

/**
 *
 * @author kenschiller
 */
public class Main2 {
    
    //Input: Map display
    //Output: Map with solution and estimated difficulty

    /**
     * @param args the command line arguments
     */
    public static void println(String s) {
        System.out.println(s);
    }
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
//        sc.useDelimiter(",|\n");

        Solver solver = new Solver(null);

        solver.loadMapWithScanner2(sc);


//        Solver solver = new Solver(sc);

        Item [] [] startMap = Solver.cloneMap(solver.getMap());
        solver.solve(true);
        Solver.printMapPair(startMap, solver.getMap());
        return;

    }

}
