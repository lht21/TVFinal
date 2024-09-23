/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Control;

import Entity.MyImage;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Administrator
 */
public class ThreadCaptureScreen extends Thread {

    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    private ObjectOutputStream oos;
    private ByteArrayOutputStream baos;

    public ThreadCaptureScreen(ObjectOutputStream oos) {
        this.oos = oos;
    }

    @Override
    public void run() {
        int count = 0;
        while (true) {
            try {
                capture(count++);
                Thread.sleep(10);
            } catch (AWTException ex) {
                Logger.getLogger(ThreadCaptureScreen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ThreadCaptureScreen.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadCaptureScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void capture(int count) throws AWTException, IOException {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage capture = new Robot().createScreenCapture(screenRect);
        byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(capture, "jpg", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        MyImage image = new MyImage(imageInByte, count + "");
        System.out.println("đang chụp màn hình" + image.getMessage());
        byteArrayOutputStream.close();
        oos.writeObject(image);
    }
}
