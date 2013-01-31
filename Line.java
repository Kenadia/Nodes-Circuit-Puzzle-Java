/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package _nodespuzzle;

/**
 *
 * @author kenschiller
 */
public class Line extends Item {
    protected Group g_;
    protected boolean vertical_;
    public Line(Point p, Group g, boolean vertical) {
        super(p);
        g_ = g;
        vertical_ = vertical;
    }
    public static Line convert(Placeholder p) {
        return new Line(p.getPoint(), p.getGroup(), p.isVertical());
    }
    public Group getGroup() {
        return g_;
    }
    public boolean isVertical() {
        return vertical_;
    }
    public char getChar() {
        if(vertical_)
            return '|';
        else
            return '–';
    }
    public Line clone() {
        Line clone = new Line(getPoint(), g_, vertical_);
        return clone;
    }
    /*public boolean equals(Item item) {
        if(!(item instanceof Line))
            return false;
        if(g_ != ((Line) item).g_)
            return false;
        if(vertical_ != ((Line) item).vertical_)
            return false;
        return true;
    }*/
}
