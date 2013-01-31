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
public class Node extends Grouped {
    private Point hLinked_;
    private Point vLinked_;
    private boolean hKnown_;
    private boolean vKnown_;
    private boolean east_;
    private boolean south_;
    //
    private ArrayList<Point> openH_;
    private ArrayList<Point> openV_;
    public Node(Point p, Group g) {
        super(p, g);
        hLinked_ = null;
        vLinked_ = null;
        hKnown_ = false;
        vKnown_ = false;
        openH_ = new ArrayList<Point>();
        openV_ = new ArrayList<Point>();
    }
    public static Node convert(Placeholder p) {
        Node n = new Node(p.getPoint(), p.getGroup());
        //Node n2 = p.getOrigin();
        //to ensure the correct steps are taken, linking is left to checkColinear()
        /*if(p.isVertical())
            n.vLinkTo(n2);
        else
            n.hLinkTo(n2);*/
        return n;
    }
    public boolean isComplete() {
        return hLinked_ != null && vLinked_ != null;
    }
    public Point getHLinked() {
        return hLinked_;
    }
    public Point getVLinked() {
        return vLinked_;
    }
    public boolean hKnown() {
        return hKnown_;
    }
    public boolean vKnown() {
        return vKnown_;
    }
    public boolean isEast() {
        return east_;
    }
    public boolean isSouth() {
        return south_;
    }
    public ArrayList<Point> getOpenH() {
        return openH_;
    }
    public ArrayList<Point> getOpenV() {
        return openV_;
    }
    public int countOpenH() {
        return openH_.size();
    }
    public int countOpenV() {
        return openV_.size();
    }
    public int countOpenTotal() {
        return openH_.size() + openV_.size();
    }
    public void hLinkTo(Node hLinked) {
        hLinked_ = hLinked.getPoint();
        hLinked.hLinked_ = getPoint();
        if(hLinked.getX() > getPoint().x) {
            setEast();
            hLinked.setWest();
        }
        else {
            setWest();
            hLinked.setEast();
        }
    }
    public void vLinkTo(Node vLinked) {
        vLinked_ = vLinked.getPoint();
        vLinked.vLinked_ = getPoint();
        if(vLinked.getY() > getPoint().y) {
            setSouth();
            vLinked.setNorth();
        }
        else {
            setNorth();
            vLinked.setSouth();
        }
    }
    public void setWest() {
        hKnown_ = true;
        east_ = false;
    }
    public void setEast() {
        hKnown_ = true;
        east_ = true;
    }
    public void setNorth() {
        vKnown_ = true;
        south_ = false;
    }
    public void setSouth() {
        vKnown_ = true;
        south_ = true;
    }
    public void addOpenH(ArrayList<Item> a) {
        for(Item i : a) {
            openH_.add(i.getPoint());
        }
    }
    public void addOpenV(ArrayList<Item> a) {
        for(Item i : a) {
            openV_.add(i.getPoint());
        }
    }
    public void clearOpenH() {
        openH_.clear();
    }
    public void clearOpenV() {
        openV_.clear();
    }
    public char getChar() {
        switch(getGroup()) {
            case x:
                return isComplete()? 'X' : 'x';
            case o:
                return isComplete()? 'O' : 'o';
            case t:
                return isComplete()? '∆' : 'A';
            default:
                return '?';
        }
    }
    public Node clone() {
        Node clone = new Node(getPoint(), getGroup());
        clone.hLinked_ = hLinked_;
        clone.vLinked_ = vLinked_;
        clone.hKnown_ = hKnown_;
        clone.vKnown_ = vKnown_;
        clone.east_ = east_;
        clone.south_ = south_;
        for(Point p : openH_) {
            clone.openH_.add(p);
        }
        for(Point p : openV_) {
            clone.openV_.add(p);
        }
        return clone;
    }
    /*public boolean equals(Item item) {
        if(!super.equals(item))
            return false;
        if(!(item instanceof Node))
            return false;
        Node n2 = ((Node) item);
        if(!(n2.hLinked_.getPoint().equals(hLinked_.getPoint()))
                && n2.vLinked_.getPoint().equals(vLinked_.getPoint()))
            return false;
        if(!(n2.hKnown_ == hKnown_ && n2.vKnown_ == vKnown_
                && n2.east_ == east_ && n2.south_ == south_))
            return false;
        int countH = countOpenH();
        int countV = countOpenV();
        ArrayList<Item> openH2 = n2.getOpenH();
        ArrayList<Item> openV2 = n2.getOpenV();
        if(!(n2.countOpenH() == countH && n2.countOpenV() == countV))
            return false;
        for(int i = 0; i < countH; i++) {
            if(!openH_.get(i).getPoint().equals(openH2.get(i).getPoint()))
                return false;
        }
        for(int i = 0; i < countV; i++) {
            if(!openV_.get(i).getPoint().equals(openV2.get(i).getPoint()))
                return false;
        }
        return true;
    }*/
}
