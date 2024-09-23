/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Control;

import Entity.MyFile;
import Entity.User;
import Server.View.ChatServer;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.io.OutputStream;

/**
 *
 * @author Administrator
 */
public class ServerMessage extends Thread {

    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ChatServer chatServer;
    ServerSocket ss;
    Socket sk;

    public ServerMessage() {
        try {
            ss = new ServerSocket(8888);
            while (true) {
                sk = ss.accept();
                this.oos = new ObjectOutputStream(sk.getOutputStream());
                this.ois = new ObjectInputStream(sk.getInputStream());
                chatServer = new ChatServer(ois, oos);
                chatServer.setVisible(true);
                this.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        int kiemtra = 0;
        while (true) {
            try {
                Object o = ois.readObject();
                if (o instanceof String) {
                    String message = (String) o;
                    chatServer.getTextArea().append("\nBạn: " + message);
                }
                if (o instanceof User) {
                    User req = (User) o;
                    if (req.getPassword().equals("yeucau") && req.getMessage().equals("guifile")) {
                        //gọi luồng chờ nhận file
                        int check = JOptionPane.showConfirmDialog(null, "Bạn nhận được yêu cầu gửi file");
                        if (check == 0) {
                            //khoi dong luong nhan file
                            req.setPassword("ok");
                            oos.writeObject(req);
                            kiemtra = 1;
                        } else {
                            req.setPassword("no");
                            oos.writeObject(req);
                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(ServerMessage.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (kiemtra == 1) {
                SvFileHandler trf = new SvFileHandler();
                kiemtra = 0;
            }
        }
    }

}
