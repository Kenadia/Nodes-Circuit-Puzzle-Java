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
public class Solver {
    ArrayList<Node> nodeList_;
    ArrayList<Node> [] nodesByGroup_;
    private Item [] [] map_;
    public static final int GROUP_COUNT = 3;
    public Solver(Scanner sc) {
        nodeList_ = new ArrayList<Node>();
        ArrayList<Node> xList = new ArrayList<Node>();
        ArrayList<Node> oList = new ArrayList<Node>();
        ArrayList<Node> tList = new ArrayList<Node>();
        ArrayList [] nodesByType = {xList, oList, tList};
        nodesByGroup_ = nodesByType;
        if(sc != null) {
            loadMapWithScanner(sc);
        }
    }
    public Item [] [] getMap() {
        return map_;
    }
    public static void errorPrint(String s) {
        //System.out.println(s);
    }
    public static void infoPrint(boolean print) {
        if(print)
            System.out.println();
    }
    public static void infoPrint(String s, boolean print) {
        if(print)
            System.out.println(s);
    }
    public void printMap() {
        printMap(map_);
    }
    public static void printMap(Item [] [] map) {
        System.out.println();
        int w = map.length;
        int h = map[0].length;
        for(int i = 0; i < w + 2; i++) {
            System.out.print('–');
        }
        System.out.println();
        for(int j = 0; j < h; j++) {
            System.out.print('|');
            for(int i = 0; i < w; i++) {
                System.out.print(map[i][j]);
            }
            System.out.println('|');
        }
        for(int i = 0; i < w + 2; i++) {
            System.out.print('–');
        }
        System.out.println();
        System.out.println();
    }
    public static void printMapPair(Item [] [] map1, Item [] [] map2) {
        System.out.println();
        int w = map1.length;
        int h = map1[0].length;
        for(int i = 0; i < w + 2; i++) {
            System.out.print('–');
        }
        System.out.print("  ");
        for(int i = 0; i < w + 2; i++) {
            System.out.print('–');
        }
        System.out.println();
        for(int j = 0; j < h; j++) {
            System.out.print('|');
            for(int i = 0; i < w; i++) {
                System.out.print(map1[i][j]);
            }
            System.out.print("|  |");
            for(int i = 0; i < w; i++) {
                System.out.print(map2[i][j]);
            }
            System.out.println('|');
        }
        for(int i = 0; i < w + 2; i++) {
            System.out.print('–');
        }
        System.out.print("  ");
        for(int i = 0; i < w + 2; i++) {
            System.out.print('–');
        }
        System.out.println();
        System.out.println();
    }
    public static boolean equalMaps(Item [] [] m1, Item [] [] m2) {
        if(m1.length != m2.length)
            return false;
        if(m1[0].length != m2[0].length)
            return false;
        int w = m1.length;
        int h = m1[0].length;
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                if(m1[i][j].getChar() != m2[i][j].getChar())
                    return false;
        return true;
    }
    public static Item [] [] emptyMap(int w, int h) {
        Item [] [] map = new Item [w] [h];
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                map[i][j] = new Empty(new Point(i, j));
        return map;
    }
    public static Item [] [] cloneMap(Item [] [] map) {
        int w = map.length;
        int h = map[0].length;
        Item [] [] newMap = new Item [w] [h];
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                newMap[i][j] = map[i][j].clone();
        return newMap;
    }
    public void loadMap(Item [] [] map) {
        nodeList_.clear();
        for(int i = 0; i < GROUP_COUNT; i++) {
            nodesByGroup_[i].clear();
        }
        int w = map.length;
        int h = map[0].length;
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                if(map[i][j] instanceof Node)
                    addNode((Node) map[i][j]);
        map_ = map;
    }
    private ArrayList<Node> getListForGroup(Group g) {
        switch(g) {
            case x:
                return nodesByGroup_[0];
            case o:
                return nodesByGroup_[1];
            case t:
                return nodesByGroup_[2];
            default:
                return null;
        }
    }
    public Item [] [] loadMapWithScanner(Scanner sc) {
        System.out.println("Grid Dimensions (X, Y): ");
        System.out.print(">");
        int w = Integer.parseInt(sc.next().trim());
        int h = Integer.parseInt(sc.next().trim());
        Item [] [] map = emptyMap(w, h);
        System.out.println("Grid Input (H = help): ");
        boolean exit = false;
        while(exit == false) {
            System.out.print(">");
            String s = sc.next();
            if(s.length() > 0) {
                char c = s.charAt(0);
                switch(c) {
                    case 'h':
                    case 'H':
                        System.out.println("'Q': done\n'X': x node\n'O': o node\n" +
                                "'T': triangle node\n' 'empty space\n'/' closed space");
                        break;
                    case 'q':
                    case 'Q':
                        exit = true;
                        break;
                    case 'x':
                    case 'X':
                    case 'o':
                    case 'O':
                    case 't':
                    case 'T':
                    case ' ':
                    case '/':
                        System.out.println(" –coordinates (X, Y): ");
                        System.out.print(" –");
                        int x = Integer.parseInt(sc.next().trim()) - 1;
                        int y = Integer.parseInt(sc.next().trim()) - 1;
                        if(x >= 0 && x < w && y >= 0 && y < h) {
                            Item newItem = Item.newItem(c, new Point(x, y));
                            map[x][y] = newItem;
                            if(newItem instanceof Node) {
                                addNode((Node) newItem);
                            }
                        }
                        else {
                            System.out.println("error: outside bounds");
                        }
                        break;
                    default:
                        System.out.println("error: unrecognized command");
                        break;
                }
            }
        }
        map_ = map;
        return map;
    }
    public Item [] [] loadMapWithScanner2(Scanner sc) {
        System.out.println("Input map display");
        String s = sc.nextLine();
        int a = s.length();
        int k;
        for(k = 0; k < a; k++) {
            if(s.charAt(k) == ' ') {
                break;
            }
        }
        int w = k - 2;
        ArrayList<Item []> lines = new ArrayList<Item []>();
        for(int j = 0; j < 999; j++) {
            Item [] newLine = new Item [w];
            String line = sc.nextLine();
            if(line.charAt(0) != '|') {
                break;
            }
            for(int i = 0; i < w; i++) {
                char c = line.charAt(i + 1);
                if(c != ' ') {
                    Item newItem = Item.newItem(c, new Point(i, j));
                    newLine[i] = newItem;
                    if(newItem instanceof Node) {
                        addNode((Node) newItem);
                    }
                }
            }
            lines.add(newLine);
        }
        int h = lines.size();
        Item [] [] map = emptyMap(w, h);
        for(int i = 0; i < w; i++) {
            for(int j = 0; j < h; j++) {
                if(lines.get(j)[i] != null) {
                    map[i][j] = lines.get(j)[i];
                }
            }
        }
        map_ = map;
        return map;
    }
    public Data solveAndGetDifficulty() {
        Data d = new Data();
        solve(false, d);
        return d;
    }
    public ArrayList<Item [] []> solve(boolean printMessages) {
        return solve(printMessages, null);
    }
    public ArrayList<Item [] []> solve() {
        return solve(false, null);
    }
    public ArrayList<Item [] []> solve(boolean printMessages, Data difficulty) {
        //spread test: 6,5,/,2,1,/,6,3,x,3,2,t,4,1,t,5,1,t,4,5,t,5,5,o,2,2,o,3,3,q
        //solveable test: 6,4,/,4,2,/,6,4,o,3,1,o,5,3,o,6,3,x,1,2,x,2,4,q
        //
        //unsolved
        //puzzle 1: 7,7,x,1,1,/,4,2,/,4,6,o,2,3,o,6,5,x,7,7,/,6,1,/,6,2,/,6,3,/,7,1,/,7,2,/,7,3,/,1,5,/,1,6,/,1,7,/,2,5,/,2,6,/,2,7,q
        //puzzle 2: 5,5,o,1,1,o,5,5,x,1,5,x,5,1,x,2,2,x,4,4,q
        //puzzle 3: 6,6,/,1,1,/,1,2,/,2,1,/,5,1,/,6,1,/,6,2,o,3,1,o,2,2,x,1,3,o,3,4,o,2,5,x,6,5,x,5,6,q
        //puzzle 4: 7,7,x,1,1,x,7,7,o,7,1,o,2,2,o,6,5,/,3,3,/,5,3,/,3,5,/,5,5,/,2,6,q
        //puzzle 5: 6,5,x,1,1,x,6,5,/,6,1,o,3,1,o,5,2,o,1,4,q
        //puzzle 6-ish: 9,8,o,1,1,o,2,2,o,3,7,x,4,5,x,6,4,t,7,6,t,9,1,/,2,7,/,8,2,/,5,2,/,5,3,/,5,4,/,5,5,/,5,6,/,5,7,q
        //puzzle 6: 9,8,o,1,1,o,2,2,o,3,7,x,6,3,x,6,4,x,7,3,x,9,1,x,8,8,t,6,7,t,9,7,/,2,7,/,8,2,/,5,2,/,5,3,/,5,4,/,5,5,/,5,6,/,5,7,q
        //puzzle X: 9,9,x,2,2,t,3,3,o,4,4,x,5,5,t,6,6,o,7,7,x,8,8,/,1,1,/,1,2,/,2,1,/,6,1,/,7,1,/,7,2,/,7,3,/,8,1,/,8,2,/,8,3,/,9,1,/
        //continued: 9,2,/,9,3,/,9,4,/,1,6,/,1,7,/,1,8,/,1,9,/,2,7,/,2,8,/,2,9,/,3,8,/,3,9,/,4,9,/,8,9,/,9,8,/,9,9,q
        int w = map_.length;
        int h = map_[0].length;
        int puzzleNodeCount = 0;
            for(int m = 0; m < w; m++)
                for(int n = 0; n < h; n++)
                    if(map_[m][n] instanceof Node)
                        puzzleNodeCount++;
        if(difficulty == null)
            difficulty = new Data();
        infoPrint(printMessages);
        if(printMessages) printMap(map_);
        infoPrint("COMPUTING...", printMessages);
        Item [] [] prevMap = {null};
        int a = 0;
        while(!equalMaps(map_, prevMap)) {
            prevMap = cloneMap(map_);
            checkColinear();
            spread();
            infoPrint("" + a, printMessages);
            a++; //final value of i represents the number of steps to result
        }
        //difficulty estimation version 1
        /*if(a > 1)
            difficulty.a++;
        if(a > 4)
            difficulty.a++;
        if(a > 9)
            difficulty.a++;*/
        if(printMessages) printMap(map_);
        //BRANCHING WITH CURRENT MAP
        infoPrint("BRANCHING...", printMessages);
        //the data portion a represents the depth
        ArrayList<MapAndData> solutionMaps = new ArrayList<MapAndData>();
        ArrayList<MapAndData> potentialMaps = new ArrayList<MapAndData>();
        //data: a = depth, b = steps
        potentialMaps.add(new MapAndData(map_, 0, a));
        while(potentialMaps.size() > 0) {
            //for(int i = 0; i < potentialMaps.size(); /*conditional increment*/) {
            MapAndData testMap = potentialMaps.get(potentialMaps.size() - 1);
            loadMap(testMap.map);
            int depth = testMap.d.a;
            int steps = testMap.d.b;
            Node branchNode = null; //node with the least options > 0
            int lowestFreedom = 1000;
            for(Node n : nodeList_) { //find node for branching
                int openHCount, openVCount;
                if(n.getHLinked() != null) {
                    n.clearOpenH();
                    openHCount = 0;
                }
                else {
                    openHCount = n.countOpenH();
                }
                if(n.getVLinked() != null) {
                    n.clearOpenV();
                    openVCount = 0;
                }
                else {
                    openVCount = n.countOpenV();
                }
                int countForComparison = 0;
                if(openHCount <= openVCount && openHCount != 0) {
                    countForComparison = openHCount;
                }
                else if(openVCount != 0) {
                    countForComparison = openVCount;
                }
                else if(openHCount != 0) {
                    countForComparison = openHCount;
                }
                if(countForComparison != 0 && countForComparison < lowestFreedom) {
                    branchNode = n;
                    lowestFreedom = countForComparison;
                }
                if(branchNode == null && n.countOpenTotal() != 0) {
                    errorPrint("error 1");
                    return null;
                }
            }
            potentialMaps.remove(testMap);
            if(branchNode == null) { //the map has been solved
                solutionMaps.add(testMap);
            }
            else {
                ArrayList<Integer> stepsList = new ArrayList<Integer>();
                ArrayList<Item [] []> validCases = branch(branchNode, stepsList);
                for(int i = 0; i < validCases.size(); i++) {
                    Item [] [] newPotentialMap = validCases.get(i);
                    potentialMaps.add(new MapAndData(newPotentialMap, depth + 1, steps + stepsList.get(i)));
                }
            }
            //}
        }
        /*//solutions
        infoPrint();
        for(int i = 0; i < solutionMaps.size(); i++) {
            infoPrint("SOLUTION " + i);
            printMap(solutionMaps.get(i));
        }*/
        //filter out incorrect solutions that have sub-loops
        ArrayList<MapAndData> solutionMaps2 = new ArrayList<MapAndData>();
        for(MapAndData mad : solutionMaps) {
            Item [] [] testMap = mad.map;
            loadMap(testMap);
            boolean validSolution = containsValidLoops() == 2;
            if(validSolution) {
                solutionMaps2.add(new MapAndData(testMap, mad.d));
            }
        }
        //filter out solutions that are duplicates, if there are no more than 25
        if(solutionMaps2.size() <= 25) {
            for(int i = 0; i < solutionMaps2.size() - 1; i++) {
                for(int j = i + 1; j < solutionMaps2.size();) {
                    if(equalMaps(solutionMaps2.get(i).map, solutionMaps2.get(j).map)) {
                        solutionMaps2.remove(j);
                    }
                    else {
                        j++;
                    }
                }
            }
        }
        //final solutions
        if(printMessages) {
            infoPrint(printMessages);
            for(int i = 0; i < solutionMaps2.size(); i++) {
                infoPrint("SOLUTION " + i, printMessages);
                Item [] [] solutionMap = solutionMaps2.get(i).map;
                printMap(solutionMap);
                int solutionNodeCount = 0;
                for(int m = 0; m < w; m++)
                    for(int n = 0; n < h; n++)
                        if(solutionMap[m][n] instanceof Node)
                            solutionNodeCount++;
                Data d = solutionMaps2.get(i).d;
                int mapDepth = d.a;
                int mapSteps = d.b;
                int nodeScore = solutionNodeCount / 2 - puzzleNodeCount;
                double mapDifficulty = mapDepth / 2.0 + mapSteps / 5.0 + nodeScore / 2.0;
                System.out.println("difficulty = " + mapDifficulty + " depth = " + mapDepth + " steps = " + mapSteps + " node score = " + nodeScore);
            }
        }
        ArrayList<Item [] []> returnSolutions = new ArrayList<Item [] []>();
        for(MapAndData mad : solutionMaps2) {
            returnSolutions.add(mad.map);
        }
        if(solutionMaps2.size() == 0) {
            infoPrint("no solution", printMessages);
        }
        else {
            //difficulty estimation type 1
            //difficulty.a += (solutionMaps2.get(0).a + 1) / 2;
            //difficulty.b = solutionMaps2.get(0).a;
            //difficulty estimation type 3
            //printMap(solutionMaps2.get(0).map);
            loadMap(solutionMaps2.get(0).map);
            Data d = solutionMaps2.get(0).d;
            difficulty.a = (int) (d.a / 2.0 + d.b / 5.0 + ((nodeList_.size() / 2) - puzzleNodeCount) / 2.0); //difficulty
            difficulty.b = d.a; //depth
            //System.out.println("depth score: " + d.a / 2.0);
            //System.out.println("steps score: " + d.b / 5.0);
            //System.out.println("node score: " + ((nodeList_.size() / 2) - puzzleNodeCount) / 2.0);
        }
        return returnSolutions;
    }
    private Node addNode(Node n) {
        nodeList_.add(n);
        switch(n.getGroup()) {
            case x:
                nodesByGroup_[0].add(n);
                break;
            case o:
                nodesByGroup_[1].add(n);
                break;
            case t:
                nodesByGroup_[2].add(n);
                break;
        }
        return n;
    }
    private Node addNode(Point p, Group g) {
        Node newNode = new Node(p, g);
        return addNode(newNode);
    }
    private Node addNode(Placeholder placeholder) {
        Node newNode = Node.convert(placeholder);
        return addNode(newNode);
    }
    private Placeholder addPlaceholder(Point p, Group g, Point origin, boolean vertical) {
        if(p.x == 6 && p.y == 1 && origin.x == 5 && origin.y == 0 && g == Group.o)
            printMap(map_);
        Placeholder newPlaceholder = new Placeholder(p, g, origin, vertical);
        return newPlaceholder;
    }
    private DoublePlaceholder makeDoublePlaceholder(Placeholder ph, Point n) {
        DoublePlaceholder newDoublePlaceholder = DoublePlaceholder.convert(ph, n);
        return newDoublePlaceholder;
    }
    private Line addLine(Point p, Group g, boolean vertical) {
        Line newLine = new Line(p, g, vertical);
        return newLine;
    }
    private Intersection makeIntersection(Line line, Group g) {
        Intersection newIntersection = Intersection.convert(line, g);
        return newIntersection;
    }
    private ArrayList<Item [] []> branch(Node node, ArrayList<Integer> stepsList) {
        //stepsList starts empty and is populated by branch(…)
        ArrayList<Item [] []> validCases = new ArrayList<Item [] []>();
        int countH = node.countOpenH();
        int countV = node.countOpenV();
        boolean vertical = (countV < countH && countV != 0) || countH == 0;
        int freedom = vertical? countV : countH;
        ArrayList<Point> opens;
        if(vertical)
            opens = node.getOpenV();
        else
            opens = node.getOpenH();
        Item [] [] [] cases = new Item [freedom] [map_.length] [map_[0].length];
        for(int i = 0; i < freedom; i++) {
            cases[i] = cloneMap(map_);
        }
        for(int i = 0; i < freedom; i++) {
            //println("case " + i);
            Item [] [] caseMap = cases[i];
            loadMap(caseMap);
            int x = opens.get(0).x;
            int y = opens.remove(0).y; //remove(0) replaces get(i) to clear list
            caseMap[x][y] = addNode(new Point(x, y), node.getGroup());
            //
            Item [] [] prevMap = {null};
            int j = 0;
            boolean failed = false;
            while(!equalMaps(caseMap, prevMap)) {
                prevMap = cloneMap(caseMap);
                if(!checkColinear()) {
                    failed = true;
                    break;
                }
                if(!spread()) {
                    failed = true;
                    break;
                }
                //System.out.println(j); //number of steps for solution
                j++;
            }
            if(!failed) {
                validCases.add(caseMap);
                stepsList.add(j);
            }
        }
        return validCases;
    }
    private int containsValidLoops() {
        //0: invalid loops, 1: intederminate, 2: complete and valid
        ArrayList<Node> checkNodes = new ArrayList<Node>();
        for(Node n : nodeList_) {
            checkNodes.add(n);
        }
        int result = 2;
        for(int i = 0; result > 0 && i < checkNodes.size(); i++) {
            Node n = checkNodes.get(i);
            int loopedNodes = 0;
            int fullLoop = getListForGroup(n.getGroup()).size();
            Node loopedNode = n;
            do {
                Point p = loopedNode.getHLinked();
                if(p == null) {
                    result = 1;
                    break;
                }
                if(!(map_[p.x][p.y] instanceof Node)) {
                    result = 0;
                    break;
                }
                loopedNode = (Node) map_[p.x][p.y];
                checkNodes.remove(loopedNode);
                p = loopedNode.getVLinked();
                if(p == null) {
                    result = 1;
                    break;
                }
                if(!(map_[p.x][p.y] instanceof Node)) {
                    result = 0;
                    break;
                }
                loopedNode = (Node) map_[p.x][p.y];
                checkNodes.remove(loopedNode);
                loopedNodes+= 2;
                if(loopedNodes > fullLoop) {
                    result = 0;
                    break;
                }
            } while(loopedNode != n);
            if(result == 2 && loopedNodes != fullLoop) {
                result = 0;
            }
        }
        return result;
    }
    private boolean spreadTestBlockingSpace(Item checkSpace, Group g, boolean vertical) {
        //check if a space disallows a connection to a node past this point
        //THIS METHOD PRESUMES THAT COLINEAR CHECKING WAS RUN PREVIOUSLY
        if(checkSpace instanceof Wall)
            return true;
        if(checkSpace instanceof Node)
            if(((Node) checkSpace).getGroup() != g) //false if colinear nodes exist
                return true;
        if(checkSpace instanceof Placeholder)
            if(((Placeholder) checkSpace).getGroup() != g)
                if(((Placeholder) checkSpace).isVertical() == vertical)
                    return true;
        if(checkSpace instanceof Line)
            if(((Line) checkSpace).getGroup() != g
            && ((Line) checkSpace).isVertical() == vertical)
                return true;
        return false;
    }
    private boolean spreadTestOpenSpace(Point origin, Item checkSpace, Group g, boolean vertical) {
        //check if a space allows a new node
        //THIS METHOD PRESUMES THAT COLINEAR CHECKING WAS RUN PREVIOUSLY
        //AND THAT spreadTestBlockingSpace RETURNED FALSE
        if(checkSpace instanceof Empty) {
            if(!spreadTest(checkSpace, g, !vertical, true)) { //LEVEL 2 SPREAD CHECKING
                ((Empty) checkSpace).restrictGroup(g);
                return false;
            }
            if(!((Empty) checkSpace).restricts(g)) {
                return true;
            }
        }
        if(checkSpace instanceof Placeholder)
            if(((Placeholder) checkSpace).getGroup() == g) {
                if(((Placeholder) checkSpace).isVertical() != vertical)
                    return true;
                else { //colinear node exists or placeholder is from source
                    if(((Placeholder) checkSpace).getOrigin().equals(origin)) {
                        if(!spreadTest(checkSpace, g, !vertical, true)) { //LEVEL 2 SPREAD CHECKING
                            int x = checkSpace.getX();
                            int y = checkSpace.getY();
                            map_[x][y] = new Empty(new Point(x, y));
                            ((Empty) map_[x][y]).restrictGroup(g);
                            return false;
                        }
                        else {
                            return true;
                        }
                    }
                }
            }
        return false;
    }
    private boolean spreadTestOpenSpaceAlt(Item checkSpace, Group g, boolean vertical, boolean recurse) {
        if(checkSpace instanceof Empty) {
            if(recurse) {
                if(!spreadTest(checkSpace, g, !vertical, false)) { //LEVEL 2 SPREAD CHECKING
                    ((Empty) checkSpace).restrictGroup(g);
                    return false;
                }
            }
            if(!((Empty) checkSpace).restricts(g)) {
                return true;
            }
        }
        if(checkSpace instanceof Placeholder)
            if(((Placeholder) checkSpace).getGroup() == g) {
                if(((Placeholder) checkSpace).isVertical() != vertical)
                    return true;
                /*else if(((Placeholder) checkSpace).getOrigin() == origin)
                    return true;*/
            }
        return false;
    }
    private boolean spreadTestColinearNode(Point origin, Item checkSpace, Group g, boolean vertical) {
        //check if a space indicates that a colinear node (same group) exists
        //PRESUMES THAT spreadTestBlockingSpace AND spreadTestOpenSpace RETURNED FALSE
        if(checkSpace instanceof Node) {
            if(((Node) checkSpace).getGroup() == g)
                return true;
        }
        if(checkSpace instanceof Placeholder) {
            if(((Placeholder) checkSpace).isVertical() == vertical) //must be same group
                if(!((Placeholder) checkSpace).getOrigin().equals(origin))
                    return true;
        }
        return false;
    }
    private boolean spreadTestColinearNodeAlt(Item checkSpace, Group g, boolean vertical) {
        //check if a space indicates that a colinear node (same group) exists
        //PRESUMES THAT spreadTestBlockingSpace AND spreadTestOpenSpace RETURNED FALSE
        if(checkSpace instanceof Node) {
            if(((Node) checkSpace).getGroup() == g)
                return true;
        }
        if(checkSpace instanceof Placeholder) {
            if(((Placeholder) checkSpace).isVertical() == vertical) //must be same group
                //origin can't be source node because source node does not exist
                //during recursive spread testing
                return true;
        }
        return false;
    }
    public boolean spread() {
        //checks row and column of a node for necessary extensions
        //returns false if no solution exists
        //THIS METHOD PRESUMES THAT COLINEAR CHECKING WAS RUN PREVIOUSLY
        ArrayList<Node> iterationList_ = (ArrayList<Node>) nodeList_.clone();
        for(Node n : iterationList_) {
            boolean success = spread(n);
            if(!success)
                return false;
        }
        return true;
    }
    private boolean spread(Node n) {
        int w = map_.length;
        int h = map_[0].length;
        int x = n.getX();
        int y = n.getY();
        Group g = n.getGroup();
        boolean colinearNodeExists = false;
        if(n.getHLinked() == null) { //check horizontal spread
            //check for spaces that could contain a new node in the row
            n.clearOpenH();
            ArrayList<Item> openWest = new ArrayList<Item>(); //open = empty or placholder of same group
            ArrayList<Item> openEast = new ArrayList<Item>();
            for(int i = x - 1; i >= 0; i--) { //check west
                Item checkSpace = map_[i][y];
                if(spreadTestBlockingSpace(checkSpace, g, false)) {
                    break;
                }
                else if(spreadTestOpenSpace(n.getPoint(), checkSpace, g, false)) {
                    openWest.add(checkSpace);
                }
                else if(spreadTestColinearNode(n.getPoint(), checkSpace, g, false)) {
                    colinearNodeExists = true;
                    break;
                }
            }
            for(int i = x + 1; i < w; i++) { //check east
                Item checkSpace = map_[i][y];
                if(spreadTestBlockingSpace(checkSpace, g, false)) {
                    break;
                }
                else if(spreadTestOpenSpace(n.getPoint(), checkSpace, g, false)) {
                    openEast.add(checkSpace);
                }
                else if(spreadTestColinearNode(n.getPoint(), checkSpace, g, false)) {
                    colinearNodeExists = true;
                    break;
                }
            }
            if(!colinearNodeExists) {
                //do things based on availability of open spaces
                Grouped extension = null; //null or placeholder or node
                int extendX = -1;
                int extendY = -1;
                int openWestCount = openWest.size();
                int openEastCount = openEast.size();
                Item forNewNode = null;
                Item forNewPlaceholder = null;
                if(openWestCount == 0) {
                    if(openEastCount == 0) {
                        errorPrint("INVALID SOLUTION: no room for horizontal spread");
                        return false;
                    }
                    else if(openEastCount == 1) {
                        forNewNode = openEast.get(0);
                    }
                    else {
                        forNewPlaceholder = openEast.get(0);
                    }
                }
                else if(openEastCount == 0) {
                    if(openWestCount == 1) {
                        forNewNode = openWest.get(0);
                    }
                    else {
                        forNewPlaceholder = openWest.get(0);
                    }
                }
                //
                if(forNewNode != null) {
                    extendX = forNewNode.getX();
                    extendY = forNewNode.getY();
                    if(forNewNode instanceof Empty) {
                        extension = addNode(new Point(extendX, extendY), g);
                    }
                    else { //must be a placeholder
                        Placeholder placeholder = ((Placeholder) forNewNode);
                        extension = addNode(placeholder);
                    }
                }
                else if(forNewPlaceholder != null) {
                    extendX = forNewPlaceholder.getX();
                    extendY = forNewPlaceholder.getY();
                    if(forNewPlaceholder instanceof Empty) {
                        extension = addPlaceholder(new Point(extendX, extendY), g, n.getPoint(), false);
                    }
                    else { //must be a placeholder
                        Placeholder placeholder = ((Placeholder) forNewPlaceholder);
                        if(placeholder.isVertical())
                            extension = makeDoublePlaceholder(placeholder, n.getPoint());
                    }
                }
                //
                if(extension != null) {
                    map_[extendX][extendY] = extension;
                    boolean east = extendX > x;
                    for(int i = (east? x + 1 : x - 1); (east? i < extendX : i > extendX); i = (east? i + 1 : i - 1)) {
                        Item checkSpace = map_[i][y];
                        if(checkSpace instanceof Empty) {
                            map_[i][y] = addLine(new Point(i, y), g, false);
                        }
                        else if(checkSpace instanceof Line) {
                            Line line = ((Line) checkSpace);
                            if(line.isVertical()) {
                                map_[i][y] = makeIntersection(line, g);
                            }
                        }
                        else if(checkSpace instanceof Placeholder) {
                            //placeholder cross-through
                            Placeholder placeholder = ((Placeholder) checkSpace);
                            if(placeholder.isVertical()) {
                                Group g2 = placeholder.getGroup();
                                Point o2 = placeholder.getOrigin();
                                int k = y;
                                if(o2.y > y)
                                    k--;
                                else
                                    k++;
                                if(k < 0 || k >= h) {
                                    errorPrint("INVALID SOLUTION: no room for crossing placeholder");
                                    return false;
                                }
                                Line line = Line.convert(placeholder);
                                map_[i][y] = makeIntersection(line, g);
                                Item space2 = map_[i][k];
                                if(spreadTestBlockingSpace(space2, g2, true)) {
                                    errorPrint("INVALID SOLUTION: no room for crossing placeholder");
                                    return false;
                                }
                                else if(!(space2 instanceof Node)) {
                                    map_[i][k] = addPlaceholder(new Point(i, k), g2, o2, true);
                                }
                            }
                        }
                    }
                    //for placement of node over a placeholder,
                    //connecting two nodes, restrict colinear empty spaces
                    /*if(extension instanceof Node) {
                        if(((Node) extension).isComplete()) {
                            for(int i = 0; i < w; i++) {
                                if(map_[i][extendY] instanceof Empty) {
                                    ((Empty) map_[i][extendY]).restrictGroup(g);
                                }
                            }
                            for(int i = 0; i < h; i++) {
                                if(map_[extendX][i] instanceof Empty) {
                                    ((Empty) map_[extendX][i]).restrictGroup(g);
                                }
                            }
                        }
                    }*/
                }
            }
            //update node's list of open spaces
            n.addOpenH(openWest);
            n.addOpenH(openEast);
        }
        if(n.getVLinked() == null) { //check vertical spread
            //MODIFIED CODE FROM HORIZONTAL SPREAD BLOCK ABOVE
            //
            //check for spaces that could contain a new node in the row
            n.clearOpenV();
            ArrayList<Item> openNorth = new ArrayList<Item>(); //open = empty or placholder of same group
            ArrayList<Item> openSouth = new ArrayList<Item>();
            for(int i = y - 1; i >= 0; i--) { //check north
                Item checkSpace = map_[x][i];
                if(spreadTestBlockingSpace(checkSpace, g, true)) {
                    break;
                }
                else if(spreadTestOpenSpace(n.getPoint(), checkSpace, g, true)) {
                    openNorth.add(checkSpace);
                }
                else if(spreadTestColinearNode(n.getPoint(), checkSpace, g, true)) {
                    colinearNodeExists = true;
                    break;
                }
            }
            for(int i = y + 1; i < h; i++) { //check south
                Item checkSpace = map_[x][i];
                if(spreadTestBlockingSpace(checkSpace, g, true)) {
                    break;
                }
                else if(spreadTestOpenSpace(n.getPoint(), checkSpace, g, true)) {
                    openSouth.add(checkSpace);
                }
                else if(spreadTestColinearNode(n.getPoint(), checkSpace, g, true)) {
                    colinearNodeExists = true;
                    break;
                }
            }
            if(!colinearNodeExists) {
                //do things based on availability of open spaces
                Grouped extension = null; //null or placeholder or node
                int extendX = -1;
                int extendY = -1;
                int openNorthCount = openNorth.size();
                int openSouthCount = openSouth.size();
                Item forNewNode = null;
                Item forNewPlaceholder = null;
                if(openNorthCount == 0) {
                    if(openSouthCount == 0) {
                        errorPrint("INVALID SOLUTION: no room for vertical spread");
                        return false;
                    }
                    else if(openSouthCount == 1) {
                        forNewNode = openSouth.get(0);
                    }
                    else {
                        forNewPlaceholder = openSouth.get(0);
                    }
                }
                else if(openSouthCount == 0) {
                    if(openNorthCount == 1) {
                        forNewNode = openNorth.get(0);
                    }
                    else {
                        forNewPlaceholder = openNorth.get(0);
                    }
                }
                //
                if(forNewNode != null) {
                    extendX = forNewNode.getX();
                    extendY = forNewNode.getY();
                    if(forNewNode instanceof Empty) {
                        extension = addNode(new Point(extendX, extendY), g);
                    }
                    else { //must be a placeholder
                        Placeholder placeholder = ((Placeholder) forNewNode);
                        extension = addNode(placeholder);
                    }
                }
                else if(forNewPlaceholder != null) {
                    extendX = forNewPlaceholder.getX();
                    extendY = forNewPlaceholder.getY();
                    if(forNewPlaceholder instanceof Empty) {
                        extension = addPlaceholder(new Point(extendX, extendY), g, n.getPoint(), true);
                    }
                    else { //must be a placeholder of same group
                        Placeholder placeholder = ((Placeholder) forNewPlaceholder);
                        if(!placeholder.isVertical())
                            extension = makeDoublePlaceholder(placeholder, n.getPoint());
                    }
                }
                //
                if(extension != null) {
                    map_[extendX][extendY] = extension;
                    boolean south = extendY > y;
                    for(int i = (south? y + 1 : y - 1); (south? i < extendY : i > extendY); i = (south? i + 1 : i - 1)) {
                        Item checkSpace = map_[x][i];
                        if(checkSpace instanceof Empty) {
                            map_[x][i] = addLine(new Point(x, i), g, true);
                        }
                        else if(checkSpace instanceof Line) {
                            Line line = ((Line) checkSpace);
                            if(!line.isVertical()) {
                                map_[x][i] = makeIntersection(line, g);
                            }
                        }
                        else if(checkSpace instanceof Placeholder) {
                            //placeholder cross-through
                            Placeholder placeholder = ((Placeholder) checkSpace);
                            if(!placeholder.isVertical()) {
                                Group g2 = placeholder.getGroup();
                                Point o2 = placeholder.getOrigin();
                                int k = x;
                                if(o2.x > x)
                                    k--;
                                else
                                    k++;
                                if(k < 0 || k >= w) {
                                    errorPrint("INVALID SOLUTION: no room for crossing placeholder");
                                    return false;
                                }
                                Line line = Line.convert(placeholder);
                                map_[x][i] = makeIntersection(line, g);
                                Item space2 = map_[k][i];
                                if(spreadTestBlockingSpace(space2, g2, false)) {
                                    errorPrint("INVALID SOLUTION: no room for crossing placeholder");
                                    return false;
                                }
                                else if(!(space2 instanceof Node)) {
                                    map_[k][i] = addPlaceholder(new Point(k, i), g2, o2, false);
                                }
                            }
                        }
                    }
                    //for placement of node over a placeholder,
                    //connecting two nodes, restrict colinear empty spaces
                    /*if(extension instanceof Node) {
                        if(((Node) extension).isComplete()) {
                            for(int i = 0; i < w; i++) {
                                if(map_[i][extendY] instanceof Empty) {
                                    ((Empty) map_[i][extendY]).restrictGroup(g);
                                }
                            }
                            for(int i = 0; i < h; i++) {
                                if(map_[extendX][i] instanceof Empty) {
                                    ((Empty) map_[extendX][i]).restrictGroup(g);
                                }
                            }
                        }
                    }*/
                }
            }
            //update node's list of open spaces
            n.addOpenV(openNorth);
            n.addOpenV(openSouth);
            //
            //END
        }
        return true;
    }
    private boolean spreadTest(Item n, Group g, boolean vertical, boolean recurse) {
        //MODIFIED DUPLICATE OF spreadTest(Node n)
        //returns true if a node at n can possibly connect in the row/column
        int w = map_.length;
        int h = map_[0].length;
        int x = n.getX();
        int y = n.getY();
        boolean colinearNodeExists = false;
        if(!vertical) { //check horizontal spread
            //check for spaces that could contain a new node in the row
            ArrayList<Item> openWest = new ArrayList<Item>(); //open = empty or placholder of same group
            ArrayList<Item> openEast = new ArrayList<Item>();
            for(int i = x - 1; i >= 0; i--) { //check west
                Item checkSpace = map_[i][y];
                if(spreadTestBlockingSpace(checkSpace, g, false)) {
                    break;
                }
                else if(spreadTestOpenSpaceAlt(checkSpace, g, false, recurse)) {
                    openWest.add(checkSpace);
                }
                else if(spreadTestColinearNodeAlt(checkSpace, g, false)) {
                    colinearNodeExists = true;
                    break;
                }
            }
            for(int i = x + 1; i < w; i++) { //check east
                Item checkSpace = map_[i][y];
                if(spreadTestBlockingSpace(checkSpace, g, false)) {
                    break;
                }
                else if(spreadTestOpenSpaceAlt(checkSpace, g, false, recurse)) {
                    openEast.add(checkSpace);
                }
                else if(spreadTestColinearNodeAlt(checkSpace, g, false)) {
                    colinearNodeExists = true;
                    break;
                }
            }
            if(!colinearNodeExists) {
                //do things based on availability of open spaces
                int openWestCount = openWest.size();
                int openEastCount = openEast.size();
                if(openWestCount == 0) {
                    if(openEastCount == 0) {
                        //println("INVALID SOLUTION: no room for horizontal spread (test)");
                        return false;
                    }
                }
            }
        }
        else { //check vertical spread
            //MODIFIED CODE FROM HORIZONTAL SPREAD BLOCK ABOVE
            //
            //check for spaces that could contain a new node in the row
            ArrayList<Item> openNorth = new ArrayList<Item>(); //open = empty or placholder of same group
            ArrayList<Item> openSouth = new ArrayList<Item>();
            for(int i = y - 1; i >= 0; i--) { //check north
                Item checkSpace = map_[x][i];
                if(spreadTestBlockingSpace(checkSpace, g, true)) {
                    break;
                }
                else if(spreadTestOpenSpaceAlt(checkSpace, g, true, recurse)) {
                    openNorth.add(checkSpace);
                }
                else if(spreadTestColinearNodeAlt(checkSpace, g, true)) {
                    colinearNodeExists = true;
                    break;
                }
            }
            for(int i = y + 1; i < h; i++) { //check east
                Item checkSpace = map_[x][i];
                if(spreadTestBlockingSpace(checkSpace, g, true)) {
                    break;
                }
                else if(spreadTestOpenSpaceAlt(checkSpace, g, true, recurse)) {
                    openSouth.add(checkSpace);
                }
                else if(spreadTestColinearNodeAlt(checkSpace, g, true)) {
                    colinearNodeExists = true;
                    break;
                }
            }
            if(!colinearNodeExists) {
                //do things based on availability of open spaces
                int openNorthCount = openNorth.size();
                int openSouthCount = openSouth.size();
                if(openNorthCount == 0) {
                    if(openSouthCount == 0) {
                        //println("INVALID SOLUTION: no room for vertical spread (test)");
                        return false;
                    }
                }
            }
            //
            //END
        }
        return true;
    }
    private boolean checkColinear() {
        int w = map_.length;
        int h = map_[0].length;
        for(ArrayList<Node> list : nodesByGroup_)
            for(int i = 0; i < list.size() - 1; i++) {
                Node n = list.get(i);
                if(!n.isComplete()) {
                    for(int j = i + 1; j < list.size(); j++) {
                        Node n2 = list.get(j);
                        int x1 = n.getX();
                        int y1 = n.getY();
                        int x2 = n2.getX();
                        int y2 = n2.getY();
                        Group g = n.getGroup();
                        if(x1 == x2) {
                            if(n.getVLinked() == null) {
                                int yMin = (y1 < y2? y1 : y2) + 1;
                                int yMax = (y1 > y2? y1 : y2) - 1;
                                for(int k = 0; k < h; k++) {
                                    Item checkSpace = map_[x1][k];
                                    if(k >= yMin && k <= yMax) {
                                        if(checkSpace instanceof Empty) {
                                            map_[x1][k] = addLine(new Point(x1, k), g, true);
                                        }
                                        else if(checkSpace instanceof Line) {
                                            Line line = (Line) checkSpace;
                                            if(!line.isVertical()) {
                                                map_[x1][k] = Intersection.convert(line, g);
                                            }
                                            else if(line.getGroup() != g) {
                                                errorPrint("INVALID SOLUTION (line of wrong group exists between two matching nodes)");
                                                return false;
                                            }
                                        }
                                        else if(checkSpace instanceof Placeholder) {
                                            Placeholder placeholder = ((Placeholder) checkSpace);
                                            if(placeholder.isVertical()) {
                                                if(placeholder.getGroup() == g) {
                                                    map_[x1][k] = Line.convert(placeholder);
                                                }
                                                else {
                                                    errorPrint("INVALID SOLUTION (placeholder of wrong group)");
                                                    return false;
                                                }
                                            }
                                            else if(placeholder instanceof DoublePlaceholder) {
                                                if(placeholder.getGroup() != g) {
                                                    errorPrint("INVALID SOLUTION (double placeholder of wrong group)");
                                                    return false;
                                                }
                                            }
                                            if(!placeholder.isVertical() || placeholder instanceof DoublePlaceholder) {
                                                //placeholder cross-through
                                                Group g2 = placeholder.getGroup();
                                                Point o2;
                                                if(placeholder instanceof DoublePlaceholder && placeholder.isVertical())
                                                    o2 = ((DoublePlaceholder) placeholder).getOrigin2();
                                                else
                                                    o2 = placeholder.getOrigin();
                                                int m = x1;
                                                if(o2.x > x1)
                                                    m--;
                                                else
                                                    m++;
                                                if(m < 0 || m >= w) {
                                                    errorPrint("INVALID SOLUTION: no room for crossing placeholder");
                                                    return false;
                                                }
                                                Line line = Line.convert(placeholder);
                                                map_[x1][k] = makeIntersection(line, g);
                                                Item space2 = map_[m][k];
                                                if(spreadTestBlockingSpace(space2, g2, false)) {
                                                    errorPrint("INVALID SOLUTION: no room for crossing placeholder");
                                                    return false;
                                                }
                                                else if(!(space2 instanceof Node)) {
                                                    map_[m][k] = addPlaceholder(new Point(m, k), g2, o2, false);
                                                }
                                            }
                                        }
                                        else {
                                            errorPrint("INVALID SOLUTION (object exists between two matching nodes)");
                                            return false;
                                        }
                                    }
                                    else {
                                        if(checkSpace instanceof Empty) {
                                            ((Empty) map_[x1][k]).restrictGroup(g);
                                        }
                                        else if(checkSpace instanceof Placeholder) {
                                            Placeholder placeholder = ((Placeholder) checkSpace);
                                            if(placeholder.getGroup() == g) {
                                                //placeholder cross-through
                                                Point o2;
                                                if(placeholder instanceof DoublePlaceholder && placeholder.isVertical())
                                                    o2 = ((DoublePlaceholder) placeholder).getOrigin2();
                                                else
                                                    o2 = placeholder.getOrigin();
                                                int m = x1;
                                                if(o2.x > x1)
                                                    m--;
                                                else
                                                    m++;
                                                if(m < 0 || m >= w) {
                                                    errorPrint("INVALID SOLUTION: no room for crossing placeholder");
                                                    return false;
                                                }
                                                Line line = Line.convert(placeholder);
                                                map_[x1][k] = makeIntersection(line, g);
                                                Item space2 = map_[m][k];
                                                if(spreadTestBlockingSpace(space2, g, false)) {
                                                    errorPrint("INVALID SOLUTION: no room for crossing placeholder");
                                                    return false;
                                                }
                                                else if(!(space2 instanceof Node)) {
                                                    map_[m][k] = addPlaceholder(new Point(m, k), g, o2, false);
                                                }
                                            }
                                        }
                                    }
                                }
                                n.vLinkTo(n2);
                            }
                            else if(n.getVLinked() != n2.getPoint()) {
                                errorPrint("INVALID SOLUTION (three matching nodes on same line)");
                                return false;
                            }
                        }
                        else if(y1 == y2) {
                            if(n.getHLinked() == null) {
                                int xMin = (x1 < x2? x1 : x2) + 1;
                                int xMax = (x1 > x2? x1 : x2) - 1;
                                for(int k = 0; k < w; k++) {
                                    Item checkSpace = map_[k][y1];
                                    if(k >= xMin && k <= xMax) {
                                        if(checkSpace instanceof Empty) {
                                            map_[k][y1] = addLine(new Point(k, y1), g, false);
                                        }
                                        else if(checkSpace instanceof Line) {
                                            Line line = (Line) checkSpace;
                                            if(line.isVertical()) {
                                                map_[k][y1] = Intersection.convert(line, g);
                                            }
                                            else if(line.getGroup() != g) {
                                                errorPrint("INVALID SOLUTION (line of wrong group exists between two matching nodes)");
                                                return false;
                                            }
                                        }
                                        else if(checkSpace instanceof Placeholder) {
                                            Placeholder placeholder = ((Placeholder) checkSpace);
                                            if(!placeholder.isVertical()) {
                                                if(placeholder.getGroup() == g) {
                                                    map_[k][y1] = Line.convert(placeholder);
                                                }
                                                else {
                                                    errorPrint("INVALID SOLUTION (placeholder of wrong group)");
                                                    return false;
                                                }
                                            }
                                            else if(placeholder instanceof DoublePlaceholder) {
                                                if(placeholder.getGroup() != g) {
                                                    errorPrint("INVALID SOLUTION (double placeholder of wrong group)");
                                                    return false;
                                                }
                                            }
                                            if(placeholder.isVertical() || placeholder instanceof DoublePlaceholder) {
                                                //placeholder cross-through
                                                Group g2 = placeholder.getGroup();
                                                Point o2;
                                                if(placeholder instanceof DoublePlaceholder && !placeholder.isVertical())
                                                    o2 = ((DoublePlaceholder) placeholder).getOrigin2();
                                                else
                                                    o2 = placeholder.getOrigin();
                                                int m = y1;
                                                if(o2.y > y1)
                                                    m--;
                                                else
                                                    m++;
                                                if(m < 0 || m >= h) {
                                                    errorPrint("INVALID SOLUTION: no room for crossing placeholder");
                                                    return false;
                                                }
                                                Line line = Line.convert(placeholder);
                                                map_[k][y1] = makeIntersection(line, g);
                                                Item space2 = map_[k][m];
                                                if(spreadTestBlockingSpace(space2, g2, true)) {
                                                    errorPrint("INVALID SOLUTION: no room for crossing placeholder");
                                                    return false;
                                                }
                                                else if(!(space2 instanceof Node)) {
                                                    map_[k][m] = addPlaceholder(new Point(k, m), g2, o2, true);
                                                }
                                            }
                                        }
                                        else {
                                            errorPrint("INVALID SOLUTION (object exists between two matching nodes)");
                                            return false;
                                        }
                                    }
                                    else {
                                        if(checkSpace instanceof Empty) {
                                            ((Empty) map_[k][y1]).restrictGroup(g);
                                        }
                                        else if(checkSpace instanceof Placeholder) {
                                            Placeholder placeholder = ((Placeholder) checkSpace);
                                            if(placeholder.getGroup() == g) {
                                                //placeholder cross-through
                                                Point o2;
                                                if(placeholder instanceof DoublePlaceholder && !placeholder.isVertical())
                                                    o2 = ((DoublePlaceholder) placeholder).getOrigin2();
                                                else
                                                    o2 = placeholder.getOrigin();
                                                int m = y1;
                                                if(o2.y > y1)
                                                    m--;
                                                else
                                                    m++;
                                                if(m < 0 || m >= h) {
                                                    errorPrint("INVALID SOLUTION: no room for crossing placeholder");
                                                    return false;
                                                }
                                                Line line = Line.convert(placeholder);
                                                map_[k][y1] = makeIntersection(line, g);
                                                Item space2 = map_[k][m];
                                                if(spreadTestBlockingSpace(space2, g, true)) {
                                                    errorPrint("INVALID SOLUTION: no room for crossing placeholder");
                                                    return false;
                                                }
                                                else if(!(space2 instanceof Node)) {
                                                    map_[k][y1] = addPlaceholder(new Point(k, m), g, o2, true);
                                                }
                                            }
                                        }
                                    }
                                }
                                n.hLinkTo(n2);
                            }
                            else if(n.getHLinked() != n2.getPoint()) {
                                errorPrint("INVALID SOLUTION (three matching nodes on same line)");
                                return false;
                            }
                        }
                    }
                }
            }
        if(containsValidLoops() == 0) {
            return false;
        }
        return true;
    }
    // Below is unused.
    private boolean checkForEdges() {
        int w = map_.length;
        int h = map_[0].length;
        for(Node n : nodeList_) {
            int x = n.getX();
            int y = n.getY();
            Group g = n.getGroup();
            if(!n.hKnown()) {
                boolean westBlocked = x == 0
                        || map_[x - 1][y] instanceof Node
                        || map_[x - 1][y] instanceof Wall;
                boolean eastBlocked = x == w - 1
                        || map_[x + 1][y] instanceof Node
                        || map_[x + 1][y] instanceof Wall;
                if(westBlocked) {
                    if(eastBlocked) {
                        errorPrint("INVALID SOLUTION (node lacks room for horizontal connection)");
                        return false;
                    }
                    else {
                        if(!placeholderCheckLoop(n, x, y, g, 1)) {
                            return false;
                        }
                    }
                }
                else if(eastBlocked) {
                    if(!placeholderCheckLoop(n, x, y, g, 0)) {
                        return false;
                    }
                }
            }
            if(!n.vKnown()) {
                boolean northBlocked = x == 0
                        || map_[x][y - 1] instanceof Node
                        || map_[x][y - 1] instanceof Wall;
                boolean southBlocked = x == h - 1
                        || map_[x][y + 1] instanceof Node
                        || map_[x][y + 1] instanceof Wall;
                if(northBlocked) {
                    if(southBlocked) {
                        errorPrint("INVALID SOLUTION (node lacks room for vertical connection)");
                        return false;
                    }
                    else {
                        if(!placeholderCheckLoop(n, x, y, g, 3)) {
                            return false;
                        }
                    }
                }
                else if(southBlocked) {
                    if(!placeholderCheckLoop(n, x, y, g, 2)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    private boolean placeholderCheckLoop(Node n, int x, int y, Group g, int direction) {
        /*//0 = west, 1 = east, 2 = north, 3 = south
        int w = map_.length;
        int h = map_[0].length;
        int k = 0;
        boolean vertical = false;
        switch(direction) {
            case 0:
                k = x - 1;
                vertical = false;
                break;
            case 1:
                k = x + 1;
                vertical = false;
                break;
            case 2:
                k = y - 1;
                vertical = true;
                break;
            case 3:
                k = y + 1;
                vertical = true;
                break;
        }
        boolean hitWall = false;
        while(true) { //EXITS AT BREAK OR RETURN FALSE
            if(k == 0 || (vertical? k == h - 1 : k == w - 1)) {
                println("INVALID SOLUTION (node lacks room for horizontal connection)");
                return false;
            }
            Item checkSpace = vertical? map_[x][k] : map_[k][y];
            if(checkSpace instanceof Empty) {
                n.setEast();
                if(vertical)
                    map_[x][k] = addPlaceholder(g, true);
                else
                    map_[k][y] = addPlaceholder(g, false);
                break; //BREAK
            }
            else if(checkSpace instanceof Placeholder) {
                Placeholder placeholder = ((Placeholder) checkSpace);
                if(placeholder.isVertical() != vertical) {
                    if(vertical)
                        map_[x][k] = new Intersection(g, placeholder.getGroup(), true);
                    else
                        map_[k][y] = new Intersection(g, placeholder.getGroup(), false);
                }
                else {
                    hitWall = true;
                    break;
                }
            }
            else if(checkSpace instanceof Line) {
                Line line = ((Line) checkSpace);
                if(line.isVertical() != vertical) {
                    if(vertical)
                        map_[x][k] = Intersection.convert(line, g);
                    else
                        map_[k][y] = Intersection.convert(line, g);
                }
                else if(line.getGroup() != g) {
                    println("ERORR! dangling line in edge check");
                    return false;
                }
            }
            else if(checkSpace instanceof Node) {
                if(((Node) checkSpace).getGroup() != g) {
                    hitWall = true;
                    break;
                }
                else {
                    return true;
                }
            }
            else if(checkSpace instanceof Wall) {
                hitWall = true;
                break;
            }
            if((direction & 1) > 0)
                k++;
            else
                k--;
        }
        if(hitWall) {
            if((direction & 1) > 0)
                k--;
            else
                k++;
            if(vertical? k == y : k == x) {
                println("INVALID SOLUTION (node lacks room for horizontal connection)");
                return false;
            }
            else {
                if(vertical)
                    map_[x][k] = addNode(g, new Point(x, k));
                else
                    map_[k][y] = addNode(g, new Point(k, y));
            }
        }*/
        return true;
    }
    private boolean checkForEdges(Item [] [] map) {
        /*int w = map.length;
        int h = map[0].length;
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++) {
                if(map[i][j] instanceof Node) {
                    Node n = (Node) map[i][j];
                    if(!n.hKnown()) {
                        boolean westEmpty = i > 0 && map[i - 1][j] instanceof Empty;
                        boolean eastEmpty = i < w - 1 && map[i + 1][j] instanceof Empty;
                        if(!westEmpty) {
                            if(!eastEmpty) {
                                println("NO SOLUTION");
                                return false;
                            }
                            else {
                                n.setEast();
                                map[i + 1][j] = new Placeholder(n.getGroup());
                            }
                        }
                        else if(!eastEmpty) {
                            n.setWest();
                            map[i - 1][j] = new Placeholder(n.getGroup());
                        }
                    }
                    if(!n.vKnown()) {
                        boolean northEmpty = j > 0 && map[i][j - 1] instanceof Empty;
                        boolean southEmpty = j < h - 1 && map[i][j + 1] instanceof Empty;
                        if(!northEmpty) {
                            if(!southEmpty) {
                                println("NO SOLUTION");
                                return false;
                            }
                            else {
                                n.setSouth();
                                map[i][j + 1] = new Placeholder(n.getGroup());
                            }
                        }
                        else if(!southEmpty) {
                            n.setNorth();
                            map[i][j - 1] = new Placeholder(n.getGroup());
                        }
                    }
                }
            }*/
        return true;
    }
}
