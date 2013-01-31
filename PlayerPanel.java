/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package _nodespuzzle;

import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author kenschiller
 */
public class PlayerPanel extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {
    private java.util.Timer refreshTimer_;
    private RegularProcess refreshProcess_;
    private boolean paused_;
    public static final int PANEL_WIDTH = 512;
    public static final int PANEL_HEIGHT = 512;
    public static final int FPS = 30;
    //
    private ArrayList<PlayerPuzzle> openPuzzles_;
    private int currentPuzzle_;
    private int mouseX_;
    private int mouseY_;
    private int frameCount_;
    public PlayerPanel() {
        paused_ = true;
        refreshTimer_ = new java.util.Timer();
        refreshProcess_ = new RegularProcess(this);
        refreshTimer_.schedule(refreshProcess_, 0, 1000 / FPS);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        //initialize member variables
        openPuzzles_ = new ArrayList<PlayerPuzzle>();
        currentPuzzle_ = 0;
        mouseX_ = -1;
        mouseY_ = -1;
        frameCount_ = 0;
        start();
        //
        paused_ = false;
    }
    public void start() {
        Item [] [] newMap;
        PlayerPuzzle newPuzzle;
        Scanner sc = new Scanner(System.in);
        Solver solver = new Solver(null);
        newMap = solver.loadMapWithScanner2(sc);
        newPuzzle = new PlayerPuzzle(newMap);
        openPuzzles_.add(newPuzzle);
    }
    public void addNode(Group g) {
        if(!(mouseX_ == -1 || mouseY_ == -1 || openPuzzles_.size() == 0)) {
            openPuzzles_.get(currentPuzzle_).addNode(g, mouseX_, mouseY_);
        }
    }
    public void run() {
        if(paused_) {
            repaint();
            return;
        }
        frameCount_++;
        repaint();
    }
    public void paintComponent(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        if(paused_) {
            g.setColor(Color.black);
            g.setFont(new Font("Monaco", Font.PLAIN, 10));
            g.drawString("paused", 100, 100);
        }
        else {
            if(openPuzzles_.size() != 0) {
                openPuzzles_.get(currentPuzzle_).show(g);
            }
            g.setColor(Color.black);
            g.setFont(new Font("Monaco", Font.PLAIN, 10));
            g.drawString("" + frameCount_ / FPS, 0, 10);
        }
    }
    public void mouseClicked(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {
        mouseX_ = -1;
        mouseY_ = -1;
    }
    public void mouseMoved(MouseEvent e) {
        mouseX_ = e.getX();
        mouseY_ = e.getY();
    }
    public void mousePressed(MouseEvent e) {
        if(paused_) return;
    }
    public void mouseReleased(MouseEvent e) {
        if(paused_) return;
    }
    public void mouseDragged(MouseEvent e) {}
    public void keyPressed(KeyEvent e) {
        key(e.getKeyCode(), true);
    }
    public void keyReleased(KeyEvent e) {
        key(e.getKeyCode(), false);
    }
    public void keyTyped(KeyEvent e) {}
    public void key(int keyCode, boolean keyDown) {
        if(paused_) {
            switch(keyCode) {
                case KeyEvent.VK_P:
                    if(keyDown) {
                        paused_ = false;
                    }
                    return;
            }
            return;
        }
        switch(keyCode) {
            case KeyEvent.VK_P:
                if(keyDown) {
                    paused_ = true;
                }
                break;
            case KeyEvent.VK_X:
                addNode(Group.x);
                break;
            case KeyEvent.VK_O:
                addNode(Group.o);
                break;
            case KeyEvent.VK_T:
                addNode(Group.t);
                break;
            case KeyEvent.VK_BACK_SPACE:
                addNode(null);
                break;
            default:
                break;
        }
    }
}
