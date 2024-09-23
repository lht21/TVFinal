package Client.Control;

import Client.View.RemoteFrm;
import Entity.MyImage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.Remote;

public class MaskScreen
        extends Thread {

    RemoteFrm rf;
    private ObjectOutputStream oos;

    public MaskScreen(ObjectOutputStream oos, RemoteFrm rf) {
        this.rf = rf;
        this.rf.show();
        this.oos = oos;
    }

     public void run() {
        ClientEvtHandler handler = new ClientEvtHandler(this.oos, this.rf);
        handler.start();
        }
    }
