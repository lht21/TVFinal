package Client.Control;

import Client.View.RemoteFrm;
import Entity.MyImage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.Remote;

public class ThreadScreen
        extends Thread {

    RemoteFrm rf;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public ThreadScreen(ObjectInputStream ois, ObjectOutputStream oos) {
        System.out.println("Chay lần đầu tiên");
        this.rf = new RemoteFrm();
        this.rf.show();
        this.ois = ois;
        this.oos = oos;
    }

    public RemoteFrm getRf() {
        return rf;
    }

    public void run() {
        int count = 0;
//        System.out.println("Chay ham run");
        while (true) {
//            System.out.println("Chay den day");
            try {
                Object o = this.ois.readObject();
                if (o instanceof MyImage) {
                    MyImage image = (MyImage) o;
//                    System.out.println("Nhận " + image.getMessage());
                    this.rf.setScreen(image.getMyImage());
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
