/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.Control;

import Client.View.ChatClient;
import Entity.MyFile;
import Entity.User;
import Server.View.ChatServer;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class ClientMessage extends Thread {
    
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ChatClient chatClient;
    
    public ClientMessage(String friendIP) {
        try {
            Socket sk = new Socket(friendIP, 8888);
            this.oos = new ObjectOutputStream(sk.getOutputStream());
            this.ois = new ObjectInputStream(sk.getInputStream());
            chatClient = new ChatClient(ois, oos);
            chatClient.setHost(friendIP);
            chatClient.setVisible(true);
            this.start();
        } catch (IOException ex) {
            Logger.getLogger(ClientMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                Object o = ois.readObject();
                if (o instanceof String) {
                    String message = (String) o;
                    chatClient.getTextArea().append("\nBạn: " + message);
                }
                if (o instanceof User) {
                    User myUser = (User) o;
                    if (myUser.getPassword().equals("ok") && myUser.getMessage().equals("guifile")) {
                        JOptionPane.showMessageDialog(null, "bạn đã được quyền gửi file.\nClick vào đính kèm 1 lần nữa để gửi");
                        chatClient.setOkGuiFile(true);
                    } else if (myUser.getPassword().equals("no") && myUser.getMessage().equals("guifile")) {
                        JOptionPane.showMessageDialog(null, "Bên kia không cho gửi file");
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
