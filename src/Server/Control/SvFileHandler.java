/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Control;

import Entity.MyFile;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class SvFileHandler extends Thread {

    ServerSocket ss;
    Socket sk;
    File file;
    public SvFileHandler() {
        try {
            ss = new ServerSocket(7777);
            System.out.println("===========Chờ nhận file");
            sk = ss.accept();
             JFileChooser jFileChooser = new JFileChooser();
                 jFileChooser.showSaveDialog(null);
                this.file = jFileChooser.getSelectedFile();
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(SvFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run(){
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(sk.getInputStream());
            Object o;
            try {
                o = ois.readObject();
                if (o instanceof MyFile){
                   MyFile File = (MyFile) o;
                   byte[] content = File.getContent();
                   Files.write(this.file.toPath(), content);
            }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(SvFileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (IOException ex) {
            Logger.getLogger(SvFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ois.close();
            } catch (IOException ex) {
                Logger.getLogger(SvFileHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
           
    }
}
