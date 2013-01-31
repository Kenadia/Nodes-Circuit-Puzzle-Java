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
public class Empty extends Item {
    ArrayList<Group> restricted_;
    public Empty(Point p) {
        super(p);
        restricted_ = new ArrayList<Group>();
    }
    public boolean restricts(Group g) {
        return restricted_.contains(g);
    }
    public void restrictGroup(Group g) {
        if(!restricted_.contains(g)) {
            restricted_.add(g);
        }
    }
    public char getChar() {
        if(restricted_.size() > 0) {
            if(restricted_.size() == 1) {
                Group g = restricted_.get(0);
                switch(g) {
                    case x:
                        return '.';
                    case o:
                        return ',';
                    case t:
                        return '\'';
                    default:
                        return '?';
                }
            }
            else if (restricted_.size() == 2) {
                if(!restricted_.contains(Group.x))
                    return '^';
                else if(!restricted_.contains(Group.o))
                    return '*';
                else if(!restricted_.contains(Group.t))
                    return ';';
                else
                    return '?';
            }
            else {
                return '\\';
            }
        }
        else {
            return ' ';
        }
    }
    public Empty clone() {
        Empty clone = new Empty(getPoint());
        for(Group g : restricted_) {
            clone.restricted_.add(g);
        }
        return clone;
    }
    /*public boolean equals(Item item) {
        if(super.equals(item))
            return true;
        if(!(item instanceof Empty))
        return false;
        ArrayList<Group> restricted2 = ((Empty) item).restricted_;
        if(restricted_.size() != restricted2.size())
            return false;
        for(int i = 0; i < restricted_.size(); i++) {
            if(restricted2.get(i) != restricted_.get(i))
                return false;
        }
        return true;
    }*/
}
