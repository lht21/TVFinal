/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class MyImage implements Serializable{

    private byte[] myImage;
    private String message;

    public MyImage() {
    }

    public MyImage(byte[] myImage, String message) {
        this.myImage = myImage;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMyImage(byte[] myImage) {
        this.myImage = myImage;
    }

    public String getMessage() {
        return message;
    }

    public byte[] getMyImage() {
        return myImage;
    }

}
