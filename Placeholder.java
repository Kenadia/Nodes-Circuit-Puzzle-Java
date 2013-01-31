/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package _nodespuzzle;

/**
 *
 * @author kenschiller
 */
public class Placeholder extends Grouped {
    private Point origin_;
    private boolean vertical_;
    public Placeholder(Point p, Group g, Point origin, boolean vertical) {
        super(p, g);
        origin_ = origin;
        vertical_ = vertical;
    }
    public Point getOrigin() {
        return origin_;
    }
    public boolean isVertical() {
        return vertical_;
    }
    public char getChar() {
        //return '•';
        return vertical_? '#' : '=';
    }
    public Placeholder clone() {
        Placeholder clone = new Placeholder(getPoint(), getGroup(), origin_, vertical_);
        return clone;
    }
    /*public boolean equals(Item item) {
        if(!super.equals(item))
            return false;
        if(!(item instanceof Placeholder))
            return false;
        Placeholder ph = ((Placeholder) item);
        if(!ph.origin_.getPoint().equals(origin_.getPoint()))
            return false;
        if(ph.vertical_ != vertical_)
            return false;
        return true;
    }*/
}
