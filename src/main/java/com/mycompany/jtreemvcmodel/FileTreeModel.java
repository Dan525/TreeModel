/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jtreemvcmodel;

import javax.swing.event.*;
import javax.swing.tree.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author daniil_pozdeev
 */
public class FileTreeModel implements TreeModel {

    private final ArrayList<TreeModelListener> listeners = new ArrayList<>();
    private File root;

    FileTreeModel() {
        this.root = new File("C:\\NewFolders");
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public boolean isLeaf(Object node) {
        if (node.equals(new File("Мой компьютер"))) {
            return false;
        } else {
            return ((File) node).isFile();
        }
    }

    @Override
    public int getChildCount(Object parent) {
        if (parent.equals(new File("Мой компьютер"))) {
            return File.listRoots().length;
        } else {
            String[] children = ((File) parent).list();
            if (children == null) {
                return 0;
            }
            return children.length;
        }
    }

    @Override
    public File getChild(Object parent, int index) {
        if (parent.equals(new File("Мой компьютер"))) {
            return File.listRoots()[index];
        } else {
            String[] childrenNames = ((File) parent).list();
            if ((childrenNames == null) || (index >= childrenNames.length)) {
                return null;
            }
            return new File((File) parent, childrenNames[index]);
        }
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        File[] childrenFiles;
        if (parent.equals(new File("Мой компьютер"))) {
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

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    void fireAddFile(File file) {
        File[] path = getPathForEvent(file);
        int[] childIndices = getNewChildIndices(file);
        File[] children = getNewChildren(file);
        TreeModelEvent evt = new TreeModelEvent(this, path, childIndices, children);
        for (TreeModelListener l : listeners) {
            l.treeNodesInserted(evt);
        }
    }

    private int[] getNewChildIndices (File file) {
        String fileName = file.getName();
        String[] listParentFileNames = file.getParentFile().list();

        if (listParentFileNames != null) {
            for (int i = 0; i < listParentFileNames.length; i++) {
                if (listParentFileNames[i].equals(fileName)) {
                    return new int[]{i};
                }
            }
        }
        return null;
    }
    
    private File[] getNewChildren (File file) {
        File[] children = new File[1];
        String fileName = file.getName();
        String[] listParentFileNames = file.getParentFile().list();
        File[] listParentFiles = file.getParentFile().listFiles();

        if (listParentFileNames != null) {
            for (int i = 0; i < listParentFileNames.length; i++) {
                if (listParentFileNames[i].equals(fileName)) {
                    children[0] = listParentFiles[i];
                }
            }
        }
        return children;
    }

    private ArrayList<File> fillPathList(ArrayList<File> pathList, File child) {
        File parent;
        if (child.getParentFile() == null) {
            parent = root;
        } else {
            parent = child.getParentFile();
        }
        
        if (!parent.equals(this.getRoot())) {
            pathList.add(parent);
            fillPathList(pathList, parent);
        } else {
            pathList.add(parent);
        }
        return pathList;
    }

    private File[] getPathForEvent(File file) {
        ArrayList<File> pathList = fillPathList(new ArrayList<>(), file);
        File[] path = new File[pathList.size()];
        Collections.reverse(pathList);
        return pathList.toArray(path);
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }
}
