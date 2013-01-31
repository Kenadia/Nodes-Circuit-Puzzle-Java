/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package _nodespuzzle;

/**
 *
 * @author kenschiller
 */
public class MapAndData {
    Item [] [] map;
    Data d;
    public MapAndData(Item [] [] _map, Data _d) {
        map = _map;
        d = _d;
    }
    public MapAndData(Item [] [] _map, int _a, int _b) {
        map = _map;
        d = new Data(_a, _b);
    }
}
