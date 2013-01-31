/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package _nodespuzzle;

/**
 *
 * @author kenschiller
 */
public class DoublePlaceholder extends Placeholder {
    private Point origin2_;
    private DoublePlaceholder(Point p, Group g, Point o, Point o2, boolean vertical) {
        super(p, g, o, vertical);
        origin2_ = o2;
    }
    public static DoublePlaceholder convert(Placeholder ph, Point o2) {
        return new DoublePlaceholder(ph.getPoint(), ph.getGroup(), ph.getOrigin(), o2, ph.isVertical());
    }
    public Point getOrigin2() {
        return origin2_;
    }
    public char getChar() {
        return '@';
    }
    public DoublePlaceholder clone() {
        DoublePlaceholder clone = convert(super.clone(), origin2_);
        return clone;
    }
}
