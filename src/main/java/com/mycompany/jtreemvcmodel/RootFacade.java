/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jtreemvcmodel;

import java.io.File;
import java.util.ArrayList;


public class RootFacade implements Facade {
    
    @Override
    public boolean isLeaf() {
        return false;
    }

    @Override
    public int getChildCount() {
        return File.listRoots().length;
    }

    @Override
    public FileFacade getChild(int index) {
        return new FileFacade(File.listRoots()[index]);
    }

    @Override
    public int getIndexOfChild(FileFacade child) {
        File[] childrenFiles = File.listRoots();
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
    
    @Override
    public boolean equals(Object other) {
        return (other instanceof RootFacade);
    }
    
    @Override
    public String toString() {
        return "Мой компьютер";
    }
}