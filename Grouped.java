/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package _nodespuzzle;

/**
 *
 * @author kenschiller
 */
public abstract class Grouped extends Item {
    private Group g_;
    public Grouped(Point p, Group g) {
        super(p);
        g_ = g;
    }
    public Group getGroup() {
        return g_;
    }
    /*public boolean equals(Item item) {
        if(super.equals(item))
            return true;
        if(!(item instanceof Grouped))
            return false;
        if(((Grouped) item).g_ != g_)
            return false;
        return true;
    }*/
}
