/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.Control;

import Entity.User;
import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class ClientControl {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ClientControl() {
    }

    public boolean openConnect(String host, String password) throws IOException, ClassNotFoundException {
        this.socket = new Socket(host, 9999);
        this.oos = new ObjectOutputStream(socket.getOutputStream());
        this.ois = new ObjectInputStream(socket.getInputStream());
        User user = new User(password, "login");
        oos.writeObject(user);
        Object o = ois.readObject();
        if (o instanceof User) {
            user = (User) o;
            System.out.println(user.getMessage());
            return user.getMessage().equals("loginOK");
        }
        return false;
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public ObjectOutputStream getOos() {
        return oos;
    }

    public void SetScreen(ObjectInputStream ois, ObjectOutputStream oos) {
        ThreadScreen screen = new ThreadScreen(ois, oos);
        screen.start();
        MaskScreen mscr = new MaskScreen(oos, screen.getRf());
        mscr.start();
    }
}
