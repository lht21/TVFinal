package Client.Control;

import Client.View.RemoteFrm;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientEvtHandler
  extends Thread
{
  private ObjectOutputStream oos;
  private RemoteFrm rf = new RemoteFrm();
  public ClientEvtHandler(ObjectOutputStream oos, RemoteFrm rf)
  { 
    this.oos = oos;
    this.rf = rf;
    this.rf.MouseMotionListener(new MouseMotion());
    this.rf.MouseActionListener(new MouseAction());
    this.rf.MouseWheelListener(new MouseWheel());
    this.rf.KeyActionListener(new KeyAction());
  }
  
  class MouseWheel
    implements MouseWheelListener
  {
    MouseWheel() {}
    
    @Override
    public void mouseWheelMoved(MouseWheelEvent e)
    {
      try
      {
        ClientEvtHandler.this.oos.writeObject(e);
      }
      catch (Exception ex) {}
    }
  }
  
  class KeyAction
    implements KeyListener
  {
    KeyAction() {}
    
    public void keyTyped(KeyEvent e) {}
    
    public void keyPressed(KeyEvent e)
    {
      try
      {
        ClientEvtHandler.this.oos.writeObject(e);
      }
      catch (Exception ex) {}
    }
    
    public void keyReleased(KeyEvent e)
    {
      try
      {
        ClientEvtHandler.this.oos.writeObject(e);
      }
      catch (Exception ex) {}
    }
  }
  
  class MouseMotion
    implements MouseMotionListener
  {
    MouseMotion() {}
    
    public void mouseDragged(MouseEvent e) {}
    
    public void mouseMoved(MouseEvent e)
    {
      try
      {
        ClientEvtHandler.this.oos.writeObject(e);
      }
      catch (Exception ex) {}
    }
  }
  
  class MouseAction
    implements MouseListener
  {
    MouseAction() {}
    
    public void mousePressed(MouseEvent e)
    {
      try
      {
        ClientEvtHandler.this.oos.writeObject(e);
      }
      catch (Exception ex) {}
    }
    
    public void mouseReleased(MouseEvent e)
    {
      try
      {
        ClientEvtHandler.this.oos.writeObject(e);
      }
      catch (Exception ex)
      {
        Logger.getLogger(ThreadScreen.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    public void mouseEntered(MouseEvent e) {}
    
    public void mouseExited(MouseEvent e) {}
    
    public void mouseClicked(MouseEvent e)
    {
      throw new UnsupportedOperationException("Not supported yet.");
    }
  }
}
