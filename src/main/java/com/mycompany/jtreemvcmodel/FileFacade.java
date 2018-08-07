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
public class FileFacade {
    
    private Object parent;
    private static FileFacade root;
    
    public FileFacade(Object parent, FileFacade root) {
        this.parent = parent;
        this.root = root;
    }
    
    public FileFacade(Object parent) {
        this.parent = parent;
    }
    
    public boolean isLeaf() {
        if (parent instanceof String) {
            return false;
        } else {
            return ((File) parent).isFile();
        }
    }

    public int getChildCount() {
        if (parent instanceof String) {
            return File.listRoots().length;
        } else {
            String[] children = ((File) parent).list();
        if (children == null) {
            return 0;
        }
        return children.length;
        }        
    }

    public Object getChild(int index) {
        if (parent instanceof String) {
            return File.listRoots()[index];
        } else {
            String[] childrenNames = ((File) parent).list();
            if ((childrenNames == null) || (index >= childrenNames.length)) {
                return null;
            }
        return new FileFacade(new File((File) parent, childrenNames[index]));
        }
    }

    public int getIndexOfChild(Object child) {
        File[] childrenFiles;
        if (parent instanceof String) {
            childrenFiles = File.listRoots();
            if (childrenFiles == null) {
                return -1;
            }
        } else {
            childrenFiles = ((File) parent).listFiles();
            if (childrenFiles == null) {
                return -1;
            }
        }        
        String childName = ((File) child).getName();
        for (int i = 0; i < childrenFiles.length; i++) {
            if (childName.equals(childrenFiles[i].getName())) {
                return i;
            }
        }
        return -1;
    }
    
    public FileFacade getParent() {
        if (parent instanceof File) {            
            return new FileFacade(((File) parent).getParentFile());
        } else {
            return root;
        }
    }
}
