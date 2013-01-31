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
public class Generator {
    //public static final int ZERO_LIMIT = 5;
    public static final int SOLUTION_THRESHOLD = 32;
    private static final boolean level2Search = true;
    public Generator() {
    }
    public static ArrayList<DataAndPoint> [] getMapsCounts(ArrayList<Item [] []> mapList) {
        ArrayList<DataAndPoint> emptyPoints = new ArrayList<DataAndPoint>();
        ArrayList<DataAndPoint> xPoints = new ArrayList<DataAndPoint>();
        ArrayList<DataAndPoint> oPoints = new ArrayList<DataAndPoint>();
        ArrayList<DataAndPoint> tPoints = new ArrayList<DataAndPoint>();
        int w = mapList.get(0).length;
        int h = mapList.get(0)[0].length;
        for(int m = 0; m < w; m++) {
            for(int n = 0; n < h; n++) {
                int emptyCount = 0;
                int xCount = 0;
                int oCount = 0;
                int tCount = 0;
                for(Item [] [] aMap: mapList) {
                    Item anItem = aMap[m][n];
                    if(anItem instanceof Empty)
                        emptyCount++;
                    else if(anItem instanceof Node) {
                        Node aNode = (Node) anItem;
                        switch(aNode.getGroup()) {
                            case x:
                                xCount++;
                                break;
                            case o:
                                oCount++;
                                break;
                            case t:
                                tCount++;
                                break;
                        }
                    }
                }
                if(emptyCount > 0) {
                    int j = 0;
                    for(; j < emptyPoints.size(); j++) {
                        if(emptyPoints.get(j).a > emptyCount)
                            break;
                    }
                    emptyPoints.add(j, new DataAndPoint(emptyCount, m, n));
                }
                if(xCount > 0) {
                    int j = 0;
                    for(; j < xPoints.size(); j++) {
                        if(xPoints.get(j).a > xCount)
                            break;
                    }
                    xPoints.add(j, new DataAndPoint(xCount, m, n));
                }
                if(oCount > 0) {
                    int j = 0;
                    for(; j < oPoints.size(); j++) {
                        if(oPoints.get(j).a > oCount)
                            break;
                    }
                    oPoints.add(j, new DataAndPoint(oCount, m, n));
                }
                if(tCount > 0) {
                    int j = 0;
                    for(; j < tPoints.size(); j++) {
                        if(tPoints.get(j).a > tCount)
                            break;
                    }
                    tPoints.add(j, new DataAndPoint(tCount, m, n));
                }
            }
        }
        ArrayList [] mapsCounts = {emptyPoints, xPoints, oPoints, tPoints};
        return mapsCounts;
    }
    public static void println(String s) {
        System.out.println(s);
    }
    public static void println(int a) {
        System.out.println(a);
    }
    public static void printMap(boolean doit, Item [] [] map) {
        if(doit) {
            Solver.printMap(map);
        }
    }
    public static void printMapPair(boolean doit, Item [] [] map1, Item [] [] map2) {
        if(doit) {
            Solver.printMapPair(map1, map2);
        }
    }
    public static void generate(Scanner sc, boolean type2Input, boolean dontAddRandom, int puzzleCount, int minSize, int maxSize) {
        final boolean BATCH_MODE = true;
        final int MIN_DIFFICULTY = 8;
        Solver solver = new Solver(null);
        ArrayList<Item [] []> totalMostDifficult = new ArrayList<Item [] []>();
        ArrayList<MapAndData> totalMostDifficultSolved = new ArrayList<MapAndData>();
        int totalHighestDifficulty = 0;
        Item [] [] templateMap = null;
        if(sc != null) {
            if(type2Input)
                templateMap = solver.loadMapWithScanner2(sc);
            else
                templateMap = solver.loadMapWithScanner(sc);
            println("\nGenerating from template:");
            printMap(false, templateMap);
        }
        for(int i = 0; i < puzzleCount; i++) {
            if(!BATCH_MODE) println("~~Stage 1");
            int w, h, limit;
            Item [] [] map, prevMap;
            int itemCount = 0;
            int triangleCount = 0;
            if(templateMap == null) { //blank map
                w = (int) (Math.random() * (maxSize - minSize + 1)) + minSize;
                h = (int) (Math.random() * (maxSize - minSize + 1)) + minSize;
                limit = (w * h) / 4;
                map = Solver.emptyMap(w, h);
                if(BATCH_MODE) { // Batch Template Definition
                    map[1][2] = new Node(new Point(1, 2), Group.x);
                    map[3][2] = new Wall(new Point(3, 2));
                    map[3][3] = new Node(new Point(3, 3), Group.o);
                    map[4][4] = new Node(new Point(4, 4), Group.x);
                    map[4][5] = new Wall(new Point(4, 5));
                    map[6][5] = new Node(new Point(6, 5), Group.o);
                    itemCount+= 6;
                }
                prevMap = null;
            } else { // from template
                w = templateMap.length;
                h = templateMap[0].length;
                limit = (w * h) / 5;
                for(int j = 0; j < w; j++)
                    for(int k = 0; k < h; k++)
                        if(!(templateMap[j][k] instanceof Empty))
                            itemCount++;
                prevMap = null;
                map = Solver.cloneMap(templateMap);
            }
            //
            int solutionCount = -1;
            boolean resetCheck = false;
            //int zeroCount = 0;
            int randomX = -1;
            int randomY = -1;
            ArrayList<Item [] []> solutions = null;
            if(dontAddRandom) {
                solver.loadMap(Solver.cloneMap(map));
                solutions = solver.solve(false);
            }
            else {
                do {
                    if(solutionCount == 0) {
                        solutionCount = -1;
                        //Solver.printMap(map);
                        if(resetCheck) {
                            //initial items made impossible puzzle – need to reset
                            //System.out.println("RESET");
                            if(templateMap == null)
                                map = Solver.emptyMap(w, h);
                            else
                                map = Solver.cloneMap(templateMap);
                            prevMap = Solver.cloneMap(map);
                            itemCount = 0;
                            resetCheck = false;
                        }
                        else {
                            map = Solver.cloneMap(prevMap);
                            if(itemCount == limit) {
                                //System.out.println("resetcheck");
                                resetCheck = true;
                            }
                            if(prevMap[randomX][randomY] instanceof Empty)
                                itemCount--;
                        }
                    }
                    else {
                        prevMap = Solver.cloneMap(map);
                        resetCheck = false;
                    }
                    randomX = (int) (Math.random() * w);
                    randomY = (int) (Math.random() * h);
                    Point p = new Point(randomX, randomY);
                    
                    /*
                    int random = (int) (Math.random() * 12);
                    Item randomItem;
                    if(random < 1) {
                        randomItem = new Node(p, Group.t);
                    }
                    else if(random < 3) {
                        randomItem = new Node(p, Group.o);
                    }
                    else if(random < 6) {
                        randomItem = new Node(p, Group.x);
                    }
                    else {
                        if(itemCount == 0)
                            randomItem = new Node(p, Group.x);
                        else
                            randomItem = new Wall(p);
                    }*/
                    
                    //Edit: fewer walls, triangles are less common but are very rarely single
                    Item randomItem;
                    boolean atEdge = randomX == 0 || randomY == 0 || randomX == w - 1 || randomY == h - 1;
                    if(atEdge && Math.random() < 0.5) {
                        randomItem = new Wall(p);
                    } else {
                        int random = (int) (Math.random() * 15);
                        if(random < 1 || triangleCount == 1) {
                            randomItem = new Node(p, Group.t);
                            triangleCount++;
                        }
                        else if(random < 4) {
                            randomItem = new Node(p, Group.o);
                        }
                        else if(random < 13) {
                            randomItem = new Node(p, Group.x);
                        }
                        else {
                            if(itemCount == 0)
                                randomItem = new Node(p, Group.x);
                            else
                                randomItem = new Wall(p);
                        }
                    }
                    
                    if(map[randomX][randomY] instanceof Empty)
                        itemCount++;
                    map[randomX][randomY] = randomItem;
                    solver.loadMap(Solver.cloneMap(map));
                    printMap(false, map);
                    if(itemCount < limit) {
                        if(!solver.spread()) {
                            solutionCount = 0;
                        }
                    }
                    else {
                        //System.out.println("checkpoint A");
                        try {
                            solutions = solver.solve(false);
                            solutionCount = solutions.size();
                            if(!BATCH_MODE) println(solutionCount);
                        }
                        catch(java.lang.OutOfMemoryError e) {
                            println("ran out of memory trying to solve this?");
                            //solver.printMap();
                        }
                        //System.out.println("checkpoint B");
                    }
                } while(solutionCount > SOLUTION_THRESHOLD || solutionCount <= 0);
            }
            if(!BATCH_MODE) println("~~Stage 2");
            ArrayList<Item [] []> generatedPuzzles = new ArrayList<Item [] []>();
            ArrayList<DataAndPoint> [] mapCounts = getMapsCounts(solutions);
            //DEBUG DISPLAY
            //
            /*println("DEBUG:\nSOLUTION MAPS:");
            for(Item [] [] sol : solutions) {
                printMap(false, sol);
            }
            for(int ii = 0; ii < 4; ii++) {
                for(int yy = 0; yy < h; yy++) {
                    for(int xx = 0; xx < w; xx++) {
                        boolean printed = false;
                        for(int jj = 0; jj < mapCounts[ii].size(); jj++) {
                            DataAndPoint dd = mapCounts[ii].get(jj);
                            if(dd.x == xx && dd.y == yy) {
                                System.out.print(dd.a);
                                mapCounts[ii].get(jj); //or remove
                                printed = true;
                            }
                        }
                        if(!printed)
                            System.out.print(" ");
                    }
                    println();
                }
                println();
            }*/
            //
            //
            for(int j = 0; j < 10; j++) {
                int listNum = (int) (Math.random() * 4);
                if(mapCounts[listNum].size() > 0) {
                    DataAndPoint d = mapCounts[listNum].remove(0);
                    //System.out.println("try to generate solutions with: " + listNum + " – " + d.a + " " + d.x + ", " + d.y);
                    if(!(map[d.x][d.y] instanceof Node)) {
                        Item [] [] map2 = Solver.cloneMap(map);
                        Point p = new Point(d.x, d.y);
                        Item newItem = null;
                        switch(listNum) {
                            case 0:
                                newItem = new Wall(p);
                                break;
                            case 1:
                                newItem = new Node(p, Group.x);
                                break;
                            case 2:
                                newItem = new Node(p, Group.o);
                                break;
                            case 3:
                                newItem = new Node(p, Group.t);
                                break;
                        }
                        map2[d.x][d.y] = newItem;
                        //Solver.printMap(map2);
                        if(d.a == 1) {
                            generatedPuzzles.add(map2);
                            if(!BATCH_MODE) println("puzzle generated (type 1)");
                        }
                        else if(level2Search) {
                            solver.loadMap(Solver.cloneMap(map2));
                            ArrayList<Item [] []> solutions2 = solver.solve(false);
                            //System.out.println("new solutions: ");
                            //for(Item [] [] soll : solutions2) {
                            //    printMap(false, soll);
                            //}
                            if(solutions2.size() == 0) {
                                println("error... no solutions. d = " + d.a + " " + d.x + ", " + d.y);
                                printMap(false, map);
                                printMap(false, map2);
                            }
                            else {
                                ArrayList<DataAndPoint> [] mapCounts2 = getMapsCounts(solutions2);
                                for(int k = 0; k < 4; k++) {
                                    for(DataAndPoint d2 : mapCounts2[k]) {
                                        if(d2.a == 1) {
                                            Item [] [] map2Plus = Solver.cloneMap(map2);
                                            Point p2 = new Point(d2.x, d2.y);
                                            Item newItem2 = null;
                                            switch(k) {
                                                case 0:
                                                    newItem2 = new Wall(p2);
                                                    break;
                                                case 1:
                                                    newItem2 = new Node(p2, Group.x);
                                                    break;
                                                case 2:
                                                    newItem2 = new Node(p2, Group.o);
                                                    break;
                                                case 3:
                                                    newItem2 = new Node(p2, Group.t);
                                                    break;
                                            }
                                            map2Plus[d2.x][d2.y] = newItem2;
                                            generatedPuzzles.add(map2Plus);
                                            if(!BATCH_MODE) println("puzzle generated (type 2)");
                                        }
                                        else break;
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    //System.out.println("(empty list)");
                }
            }
            if(generatedPuzzles.size() == 0) {
                println("Failed to Generate Puzzles");
                println("map: ");
                printMap(false, map);
            }
            else {
                int originalCount = generatedPuzzles.size();
                println("\n" + originalCount + " Puzzles Generated Successfully");
                for(int j = 0; j < generatedPuzzles.size(); j++) {
                    Item [] [] puzzle = generatedPuzzles.get(j);
                    solver.loadMap(Solver.cloneMap(puzzle));
                    Data difficultyEstimate = solver.solveAndGetDifficulty();
                    Item [] [] solvedPuzzle = solver.getMap();
                    if(!BATCH_MODE) printMapPair(true, puzzle, solvedPuzzle);
                    for(int k = j + 1; k < generatedPuzzles.size(); k++) {
                        if(Solver.equalMaps(puzzle, generatedPuzzles.get(k)))
                            generatedPuzzles.remove(k);
                    }
                    if(!BATCH_MODE) println("^ puzzle " + j + ": difficulty " + difficultyEstimate.a + ", depth " + difficultyEstimate.b);
                    if(difficultyEstimate.a >= MIN_DIFFICULTY/*totalHighestDifficulty*/) {
                        if(difficultyEstimate.a > totalHighestDifficulty) totalHighestDifficulty = difficultyEstimate.a;
                        totalMostDifficult.add(puzzle);
                        totalMostDifficultSolved.add(new MapAndData(solvedPuzzle, difficultyEstimate.a, difficultyEstimate.b));
                    }
                }
                int finalCount = generatedPuzzles.size();
                println("\n" + finalCount + " unique and " + (originalCount - finalCount) + " duplicates");
            }
        }
        if(BATCH_MODE) {
            System.out.println("\nMost Difficult:");
            int i = 0;
            while(totalMostDifficult.size() > 0) {
                Solver.printMapPair(totalMostDifficult.remove(0), totalMostDifficultSolved.get(0).map);
                Data d = totalMostDifficultSolved.remove(0).d;
                int difficulty = d.a;
                int depth = d.b;
                System.out.println("difficulty: " + difficulty + " (depth " + depth + ")");
            }
            System.out.println("\nMax Difficulty: " + totalHighestDifficulty);
        }
    }
}
