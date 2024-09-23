/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server.Control;

import Entity.User;
import java.awt.Dimension;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.System.out;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class ServerControl extends Thread {

    private ServerSocket serverSocket;
    private Socket socket;
    private int serverPort = 9999;
    private int password;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    @Override
    public void run() {
        try {
            password = generatePassword();
            serverSocket = new ServerSocket(serverPort);
            while (true) {
                System.out.println("Chờ đợi là hạnh phúc");
                socket = serverSocket.accept();
                System.out.println("CÓ CLIENT KẾT NỐI TỚI");
                this.ois = new ObjectInputStream(socket.getInputStream());
                this.oos = new ObjectOutputStream(socket.getOutputStream());
                if (checklogin(ois, oos)) {
                    process(ois, oos);
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ServerControl() {
    }

    public boolean checklogin(ObjectInputStream ois, ObjectOutputStream oos) throws IOException, ClassNotFoundException {

        Object obj = ois.readObject();
        if (obj instanceof User) {
            User user = (User) obj;
            System.out.println("SERVER USER MESSAGE:" + user.getMessage());
            System.out.println("SERVER USER PASS:" + user.getPassword());
            System.out.println("SERVER PASS:" + password);
            if (user.getMessage().equals("login") && user.getPassword().equals(password + "")) {
                //login thành công
                System.out.println("SERVER: đúng");
                user.setMessage("loginOK");
                // bắt đầu chụp màn hình
                oos.writeObject(user);
                return true;
            } else {
                //login thất bại gửi thông điệp xuống client
                System.out.println("SERVER: sai");
                user.setMessage("loginNO");
                oos.writeObject(user);
                return false;
            }
        }
        return false;
    }

    public void process(ObjectInputStream ois, ObjectOutputStream oos) {
        System.out.println("We've reached here!");
        ServerEvtHandler handler = new ServerEvtHandler(ois);
        handler.start();
        ThreadCaptureScreen myCapture = new ThreadCaptureScreen(oos);
        myCapture.start();
        ServerMessage serverReceiveMessage = new ServerMessage();

    }

    public int generatePassword() {
        Random random = new Random();
        int number = random.nextInt(9000) + 1000;
        return number;
    }

    public int getPassword() {
        return password;
    }

    public String getIPWilessAddress() throws UnknownHostException, SocketException {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        String ipAddress = inetAddress.getHostAddress().toString();
                        return ipAddress;
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "Not connected";
    }
}
