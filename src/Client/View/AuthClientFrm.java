/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client.View;

import Client.Control.ClientControl;
import Client.Control.ClientMessage;
import Entity.User;
import Server.Control.ServerControl;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrator
 */
public class AuthClientFrm extends javax.swing.JFrame {

    /**
     * Creates new form RunRMIServer
     */
    ClientControl clientControl = new ClientControl();
    ServerControl serverTCPControl = new ServerControl();

    public AuthClientFrm() {
        initComponents();
        serverTCPControl.start();
        loadData();
    }

    public void loadData() {
        try {
            tfMyIP.setText(serverTCPControl.getIPWilessAddress());
            tfMyPassword.setText(serverTCPControl.getPassword() + "");
            tfFriendIP.setText("");
            tfFriendPass.setText("");
        } catch (UnknownHostException ex) {
            Logger.getLogger(AuthClientFrm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(AuthClientFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tfMyPassword = new javax.swing.JTextField();
        tfMyIP = new javax.swing.JTextField();
        tfFriendIP = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        tfFriendPass = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(610, 330));
        getContentPane().setLayout(null);

        tfMyPassword.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tfMyPassword.setForeground(new java.awt.Color(51, 51, 255));
        getContentPane().add(tfMyPassword);
        tfMyPassword.setBounds(130, 240, 150, 30);

        tfMyIP.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tfMyIP.setForeground(new java.awt.Color(51, 51, 255));
        getContentPane().add(tfMyIP);
        tfMyIP.setBounds(120, 200, 160, 30);

        tfFriendIP.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tfFriendIP.setForeground(new java.awt.Color(51, 51, 255));
        tfFriendIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFriendIPActionPerformed(evt);
            }
        });
        getContentPane().add(tfFriendIP);
        tfFriendIP.setBounds(410, 120, 160, 30);

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("Kết nối");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(320, 240, 250, 40);

        tfFriendPass.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        tfFriendPass.setForeground(new java.awt.Color(51, 51, 255));
        tfFriendPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfFriendPassActionPerformed(evt);
            }
        });
        getContentPane().add(tfFriendPass);
        tfFriendPass.setBounds(440, 160, 130, 30);

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/background.png"))); // NOI18N
        getContentPane().add(jLabel8);
        jLabel8.setBounds(0, 0, 600, 300);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        try {
            String friendIP = tfFriendIP.getText();
            String password = tfFriendPass.getText();
            if (clientControl.openConnect(friendIP, password)) {
                JOptionPane.showMessageDialog(null, "Kết nối thành công");
                clientControl.SetScreen(clientControl.getOis(), clientControl.getOos());
                ClientMessage clientMessage = new ClientMessage(friendIP);
            } else {
                JOptionPane.showMessageDialog(null, "Kết nối thất bại");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AuthClientFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tfFriendIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFriendIPActionPerformed

    }//GEN-LAST:event_tfFriendIPActionPerformed

    private void tfFriendPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfFriendPassActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfFriendPassActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField tfFriendIP;
    private javax.swing.JTextField tfFriendPass;
    private javax.swing.JTextField tfMyIP;
    private javax.swing.JTextField tfMyPassword;
    // End of variables declaration//GEN-END:variables
}