/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Control;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Wind
 */
public class ServerEvtHandler extends Thread {

    private Robot robot;
    /**
     */
    private ObjectInputStream ois;
    private Dimension d;
    private Toolkit toolkit;
    private Dimension screenSize;

    public ServerEvtHandler(ObjectInputStream ois) {
        this.ois = ois;
        this.d = new Dimension(1280, 720);

        this.toolkit = Toolkit.getDefaultToolkit();
        this.screenSize = this.toolkit.getScreenSize();
        try {
            this.robot = new Robot();
        } catch (AWTException ex) {
            Logger.getLogger(ServerEvtHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Dimension Mousepoint(int x, int y) {
        if (this.d.getWidth() <= 0.0D) {
            return new Dimension(x, y);
        }
        double tx = x / this.d.getWidth();
        double ty = y / this.d.getHeight();
        Dimension dnew = new Dimension((int) (tx * this.screenSize.getWidth()), (int) (ty * this.screenSize.getHeight()));
        return dnew;
    }

    public void run() {
        for (;;) {
            try {
                Object o = this.ois.readObject();
                if ((o instanceof MouseEvent)) {
                    MouseEvent e = (MouseEvent) o;
                    int x = e.getX();
                    int y = e.getY();

                    Dimension md = Mousepoint(x, y);
                    this.robot.mouseMove((int) md.getWidth(), (int) md.getHeight());

                    int buttonMask = 0;
                    int buttons = e.getButton();
                    if (buttons == 1) {
                        buttonMask = InputEvent.getMaskForButton(1);
                    } else if (buttons == 2) {
                        buttonMask = InputEvent.getMaskForButton(2);
                    } else if (buttons == 3) {
                        buttonMask = InputEvent.getMaskForButton(3);
                    }
                    switch (e.getID()) {
                        case 501:
                            this.robot.mousePress(buttonMask);
                            break;
                        case 502:
                            this.robot.mouseRelease(buttonMask);
                            break;
                        case 507:
                            this.robot.mouseWheel(((MouseWheelEvent) e).getUnitsToScroll());
                    }
                } else if ((o instanceof KeyEvent)) {
                    try {
                        KeyEvent k = (KeyEvent) o;
                        switch (k.getID()) {
                            case 401:
                                this.robot.keyPress(k.getKeyCode());
                                break;
                            case 402:
                                this.robot.keyRelease(k.getKeyCode());
                        }
                    } catch (Exception e) {
                    }
                }
            } catch (ClassNotFoundException ex) {
            } catch (IOException ex) {
                Logger.getLogger(ServerEvtHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
