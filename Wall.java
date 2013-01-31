/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package _nodespuzzle;

/**
 *
 * @author kenschiller
 */
public class Wall extends Item {
    public Wall(Point p) {
        super(p);
    }
    public char getChar() {
        return '/';
    }
    public Wall clone() {
        Wall clone = new Wall(getPoint());
        return clone;
    }
    /*public boolean equals(Item item) {
        if(super.equals(item))
            return true;
        if(!(item instanceof Wall))
            return false;
        return true;
    }*/
}
