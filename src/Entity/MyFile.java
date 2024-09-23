/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.File;
import java.io.Serializable;

/**
 *
 * @author Administrator
 */
public class MyFile implements Serializable {

    private File fileName;
    private byte[] content;

    public MyFile() {
    }

    public MyFile(File fileName, byte[] content) {
        this.fileName = fileName;
        this.content = content;
    }

    public File getFileName() {
        return fileName;
    }

    public void setFileName(File fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    
    

}
