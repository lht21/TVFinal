/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.Control;

import Client.View.ChatClient;
import Entity.MyFile;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class ClientFileHandler extends Thread {

    ChatClient chatClient;
    Socket sk;
    File filepath;
    MyFile File;
    public ClientFileHandler(ChatClient chatClient, File file) {
        try {
            this.chatClient = chatClient;
            sk = new Socket(this.chatClient.getHost(), 7777);
            this.filepath = file;
            byte[] contents = Files.readAllBytes(file.toPath());
            this.File = new MyFile(file, contents);
        } catch (IOException ex) {
            Logger.getLogger(ClientFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            ObjectOutputStream oos = null;
            oos = new ObjectOutputStream(sk.getOutputStream());
            oos.writeObject(this.File);
        } catch (IOException ex) {
            Logger.getLogger(ClientFileHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
