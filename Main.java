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
public class Main {

    //Name: something about circuit?

    /**
     * @param args the command line arguments
     */
    public static void println(String s) {
        System.out.println(s);
    }
    public static void main(String[] args) {
        //kinda glitchy: Scanner sc = new Scanner("9,5,/,2,3,/,8,3,x,1,4,x,9,2,o,3,2,o,6,1,o,7,4,o,4,5,q");

        //CG 1 in progress: Scanner sc = new Scanner("8,8,/,2,2,/,3,4,/,8,5,/,7,5,/,8,6,x,2,8,x,5,3,x,6,4,x,7,6,o,6,5,t,3,8,q");

        /*
        Scanner sc = new Scanner(System.in);
//        sc.useDelimiter(",|\n");

        Solver solver = new Solver(null);

        solver.loadMapWithScanner2(sc);


//        Solver solver = new Solver(sc);

        Item [] [] startMap = Solver.cloneMap(solver.getMap());
        solver.solve(true);
        Solver.printMapPair(startMap, solver.getMap());
        return;
         */
        
        
//        Scanner sc = null;

        //boolean 1: true if map input is by full map paste
        //boolean 2: true to avoid adding random items
        //Scanner sc = new Scanner(System.in);
        Generator.generate(null, true, false, 100, 8, 8);
        // arg0: sc for template input, null for blank or template (batch mode)
        // arg1: true for graphical template paste, false for old style
        // arg2: false for random items, true to disable
        // arg3: number of puzzle seeds
        // arg4: min side length if not template
        // arg5: max side length if not template
    }

}
