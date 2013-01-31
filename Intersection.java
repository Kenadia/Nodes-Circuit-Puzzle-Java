/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package _nodespuzzle;

/**
 *
 * @author kenschiller
 */
public class Intersection extends Line {
    private Group g2_;
    private Intersection(Point p, Group g1, Group g2, boolean vertical) {
        super(p, g1, vertical);
        g2_ = g2;
    }
    public static Intersection convert(Line line, Group g2) {
        return new Intersection(line.getPoint(), line.getGroup(), g2, line.vertical_);
    }
    public Group getGroup2() {
        return g2_;
    }
    public char getChar() {
        return '+';
    }
    public Intersection clone() {
        Intersection clone = convert(super.clone(), g2_);
        return clone;
    }
}
