/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package _nodespuzzle;

import java.util.*;
import java.awt.*;

/**
 *
 * @author kenschiller
 */
public class PlayerPuzzle {
    Item [] [] map_;
    int w_;
    int h_;
    Node [] [] addedNodes_;
    int x_;
    int y_;
    int squareSize_;
    public PlayerPuzzle(Item [] [] map) {
        map_ = map;
        w_ = map.length;
        h_ = map[0].length;
        addedNodes_ = new Node [w_] [h_];
        calculateDisplayParameters();
    }
    public void calculateDisplayParameters() {
        int squareSize = 40;
        if(w_ * 40 + 40 > PlayerPanel.PANEL_WIDTH) {
            squareSize = (PlayerPanel.PANEL_WIDTH - 40) / w_;
        }
        if(h_ * 40 + 40 > PlayerPanel.PANEL_HEIGHT) {
            int newMax = (PlayerPanel.PANEL_HEIGHT - 40) / h_;
            if(squareSize > newMax) {
                squareSize = newMax;
            }
        }
        squareSize_ = squareSize;
        x_ = (PlayerPanel.PANEL_WIDTH - w_ * squareSize_) / 2;
        y_ = (PlayerPanel.PANEL_HEIGHT - h_ * squareSize_) / 2;
    }
    public void addNode(Group g, int x, int y) {
        int squareX = (x - x_) / squareSize_;
        int squareY = (y - y_) / squareSize_;
        if(!(squareX < 0 || squareX >= w_ || squareY < 0 || squareY >= h_)) {
            if(g == null) {
                addedNodes_[squareX][squareY] = null;
            }
            else {
                addedNodes_[squareX][squareY] = new Node(new Point(squareX, squareY), g);
            }
        }
    }
    public void show(Graphics g) {
        g.setFont(new Font("Lucida Grande", Font.PLAIN, squareSize_));
        //draw base map
        g.setColor(Color.black);
        int x = x_;
        int y;
        for(int i = 0; i < w_; i++) {
            y = y_;
            for(int j = 0; j < h_; j++) {
                g.drawRect(x, y, squareSize_, squareSize_);
                char c = map_[i][j].getChar();
                if(c == 'A') {
                    c = '∆';
                }
                if(c == '/') {
                    g.fillRect(x, y, squareSize_, squareSize_);
                }
                else {
                    g.drawString("" + c, x + 8, y + squareSize_ - 8);
                }
                y += squareSize_;
            }
            x += squareSize_;
        }
        //draw added nodes
        g.setColor(Color.blue);
        x = x_;
        for(int i = 0; i < w_; i++) {
            y = y_;
            for(int j = 0; j < h_; j++) {
                if(addedNodes_[i][j] != null) {
                    char c = addedNodes_[i][j].getChar();
                    if(c == 'A') {
                        c = '∆';
                    }
                    g.drawString("" + c, x + 8, y + squareSize_ - 8);
                }
                y += squareSize_;
            }
            x += squareSize_;
        }
        //draw connecting lines vertically
        Group lastGroup;
        Group foundGroup = null;
        int lastI = -1;
        int lastJ = -1;
        for(int i = 0; i < w_; i++) {
            lastGroup = null;
            for(int j = 0; j < h_; j++) {
                foundGroup = null;
                if(addedNodes_[i][j] != null) {
                    foundGroup = addedNodes_[i][j].getGroup();
                }
                else if(map_[i][j] instanceof Node) {
                    foundGroup = ((Node) map_[i][j]).getGroup();
                }
                else if(map_[i][j] instanceof Wall) {
                    lastGroup = null;
                }
                if(lastGroup != null) {
                    if(foundGroup == lastGroup) {
                        int x1 = (int) (x_ + (lastI + 0.5) * squareSize_);
                        int y1 = (int) (y_ + (lastJ + 0.5) * squareSize_);
                        int x2 = (int) (x_ + (i + 0.5) * squareSize_);
                        int y2 = (int) (y_ + (j + 0.5) * squareSize_);
                        g.drawLine(x1, y1, x2, y2);
                    }
                }
                if(foundGroup != null) {
                    lastGroup = foundGroup;
                    lastI = i;
                    lastJ = j;
                }
            }
        }
        //draw connecting lines horizontally
        for(int j = 0; j < h_; j++) {
            lastGroup = null;
            for(int i = 0; i < w_; i++) {
                foundGroup = null;
                if(addedNodes_[i][j] != null) {
                    foundGroup = addedNodes_[i][j].getGroup();
                }
                else if(map_[i][j] instanceof Node) {
                    foundGroup = ((Node) map_[i][j]).getGroup();
                }
                else if(map_[i][j] instanceof Wall) {
                    lastGroup = null;
                }
                if(lastGroup != null) {
                    if(foundGroup == lastGroup) {
                        int x1 = (int) (x_ + (lastI + 0.5) * squareSize_);
                        int y1 = (int) (y_ + (lastJ + 0.5) * squareSize_);
                        int x2 = (int) (x_ + (i + 0.5) * squareSize_);
                        int y2 = (int) (y_ + (j + 0.5) * squareSize_);
                        g.drawLine(x1, y1, x2, y2);
                    }
                }
                if(foundGroup != null) {
                    lastGroup = foundGroup;
                    lastI = i;
                    lastJ = j;
                }
            }
        }
    }
}
