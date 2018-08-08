/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jtreemvcmodel;

import java.io.File;

/**
 *
 * @author daniil_pozdeev
 */
public class FileFacade implements Facade {
    
    private File file;
    
    FileFacade(File file) {
        this.file = file;
    }

    @Override
    public boolean isLeaf() {
        return file.isFile();
    }

    @Override
    public int getChildCount() {
        String[] children = file.list();
            if (children == null) {
                return 0;
            }
            return children.length;
    }

    @Override
    public FileFacade getChild(int index) {
        String[] childrenNames = file.list();
            if ((childrenNames == null) || (index >= childrenNames.length)) {
                return null;
            }
            return new FileFacade(new File(file, childrenNames[index]));
    }

    @Override
    public int getIndexOfChild(FileFacade child) {
        File[] childrenFiles = file.listFiles();
            if (childrenFiles == null) {
                return -1;
            }
        String childName = child.getFile().getName();
        for (int i = 0; i < childrenFiles.length; i++) {
            if (childName.equals(childrenFiles[i].getName())) {
                return i;
            }
        }
        return -1;
    }
    
    public File getFile() {
        return file;
    }
    
    Facade getParent() {
        File parent = this.getFile().getParentFile();
        if (parent == null)
            return new RootFacade();
        return new FileFacade(parent);
    }
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof FileFacade))
            return false;
        File otherFile = ((FileFacade)other).getFile();
        File thisFile = this.getFile();
        return (otherFile.equals(thisFile));
    }
    
    @Override
    public String toString() {
        File[] disks = File.listRoots();
        for (File disk : disks) {
            if (file.equals(disk))
                return file.toString();
        }
        return file.getName();
    }
}
