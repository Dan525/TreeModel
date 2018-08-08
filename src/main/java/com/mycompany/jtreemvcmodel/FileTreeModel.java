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
    private RootFacade root;

    FileTreeModel() {
        this.root = new RootFacade();
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public boolean isLeaf(Object node) {
        return ((Facade) node).isLeaf();
    }

    @Override
    public int getChildCount(Object parent) {
        return ((Facade) parent).getChildCount();
    }

    @Override
    public FileFacade getChild(Object parent, int index) {
        return ((Facade) parent).getChild(index);
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return ((Facade) parent).getIndexOfChild((FileFacade) child);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    void fireAddFile(FileFacade file) {
        Facade[] path = getPathForEvent(file);
        int[] childIndices = getNewChildIndices(file);
        Facade[] children = getNewChildren(file);
        TreeModelEvent evt = new TreeModelEvent(this, path, childIndices, children);
        for (TreeModelListener l : listeners) {
            l.treeNodesInserted(evt);
        }
    }

    private int[] getNewChildIndices (FileFacade file) {
        int[] childIndices = new int[1];
        File child = file.getFile();
        String fileName = child.getName();
        String[] listParentFileNames = child.getParentFile().list();

        if (listParentFileNames != null) {
            for (int i = 0; i < listParentFileNames.length; i++) {
                if (listParentFileNames[i].equals(fileName)) {
                    childIndices[0] = i;
                }
            }
        }
        return childIndices;
    }
    
    private Facade[] getNewChildren (FileFacade file) {
        Facade[] children = new Facade[1];
        children[0] = file;
//        File child = file.getFile();
//        String fileName = child.getName();
//        String[] listParentFileNames = child.getParentFile().list();
//        File[] listParentFiles = child.getParentFile().listFiles();
//
//        if (listParentFileNames != null) {
//            for (int i = 0; i < listParentFileNames.length; i++) {
//                if (listParentFileNames[i].equals(fileName)) {
//                    children[0] = new FileFacade(listParentFiles[i]);
//                }
//            }
//        }
        return children;
    }

    private ArrayList<Facade> fillPathList(ArrayList<Facade> pathList, FileFacade child) {
        
        Facade parent = child.getParent();
        
        if (!parent.equals(this.getRoot())) {
            pathList.add(parent);
            fillPathList(pathList, (FileFacade) parent);
        } else {
            pathList.add(parent);
        }
        return pathList;
    }

    private Facade[] getPathForEvent(FileFacade file) {
        ArrayList<Facade> pathList = fillPathList(new ArrayList<>(), file);
        Facade[] path = new Facade[pathList.size()];
        Collections.reverse(pathList);
        path = pathList.toArray(path);
        return path;
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
