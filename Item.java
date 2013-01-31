/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package _nodespuzzle;

/**
 *
 * @author kenschiller
 */
public abstract class Item {
    private Point p_;
    public Item(Point p) {
        p_ = p;
    }
    protected Point getPoint() {
        return p_;
    }
    public int getX() {
        return p_.x;
    }
    public int getY() {
        return p_.y;
    }
    public static Item newItem(char c, Point p) {
        switch(c) {
            case 'x':
            case 'X':
                return new Node(p, Group.x);
            case 'o':
            case 'O':
                return new Node(p, Group.o);
            case 't':
            case 'T':
            case 'A':
            case '∆':
                return new Node(p, Group.t);
            case ' ':
                return new Empty(p);
            case '/':
                return new Wall(p);
            default:
                return null;
        }
    }
    public abstract Item clone();
    public abstract char getChar();
    /*public boolean equals(Item item) {
        return this == item;
        //don't check point cuz it shouldn't matter
    }*/
    public String toString() {
        return "" + getChar();
    }
}
