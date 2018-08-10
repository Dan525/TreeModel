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
    private final Facade root;

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
    public Facade getChild(Object parent, int index) {
        return ((Facade) parent).getChild(index);
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return ((Facade) parent).getIndexOfChild((Facade) child);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    void fireAddFile(Facade file) {
        Facade[] path = getPathForEvent(file);
        int[] childIndices = getNewChildIndices(file);
        Facade[] children = new Facade[]{file};
        TreeModelEvent evt = new TreeModelEvent(this, path, childIndices, children);
        for (TreeModelListener l : listeners) {
            l.treeNodesInserted(evt);
        }
    }

    private int[] getNewChildIndices (Facade file) {
        File child = file.getFile();
        String fileName = child.getName();
        String[] listParentFileNames = child.getParentFile().list();
        for (int i = 0; i < listParentFileNames.length; i++) {
            if (listParentFileNames[i].equals(fileName)) {
                return new int[]{i};
            }
        }
        return null;
    }

    private ArrayList<Facade> fillPathList(Facade child) {
        ArrayList<Facade> pathList = new ArrayList<>();
        while (child.getParent() != null) {
            Facade parent = child.getParent();
            pathList.add(parent);
            child = parent;
        }
        return pathList;
    }

    private Facade[] getPathForEvent(Facade file) {
        ArrayList<Facade> pathList = fillPathList(file);
        Facade[] path = new Facade[pathList.size()];
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
