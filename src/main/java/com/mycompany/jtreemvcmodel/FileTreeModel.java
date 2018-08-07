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
    private FileFacade root;    

    FileTreeModel() {
        this.root = new FileFacade("Мой компьютер");
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public boolean isLeaf(Object node) {
        return ((FileFacade) node).isLeaf();
    }

    @Override
    public int getChildCount(Object parent) {        
        return ((FileFacade) parent).getChildCount();
    }

    @Override
    public Object getChild(Object parent, int index) {        
        return ((FileFacade) parent).getChild(index);
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        return ((FileFacade) parent).getIndexOfChild(child);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    void fireAddFile(File file) {
        FileFacade fileFacade = new FileFacade(file);
        FileFacade[] path = getPathForEvent(fileFacade);
        int[] childIndices = getNewChildIndices(file);
        FileFacade[] children = getNewChildren(fileFacade);
        TreeModelEvent evt = new TreeModelEvent(this, path, childIndices, children);
        for (TreeModelListener l : listeners) {
            l.treeNodesInserted(evt);
        }
    }

    private int[] getNewChildIndices (File file) {
        int[] childIndices = new int[1];
        String fileName = file.getName();
        String[] listParentFileNames = file.getParentFile().list();

        if (listParentFileNames != null) {
            for (int i = 0; i < listParentFileNames.length; i++) {
                if (listParentFileNames[i].equals(fileName)) {
                    childIndices[0] = i;
                }
            }
        }
        return childIndices;
    }
    
    private FileFacade[] getNewChildren (File file) {
        FileFacade[] children = new FileFacade[1];
        String fileName = file.getName();
        String[] listParentFileNames = file.getParentFile().list();
        File[] listParentFiles = file.getParentFile().listFiles();

        if (listParentFileNames != null) {
            for (int i = 0; i < listParentFileNames.length; i++) {
                if (listParentFileNames[i].equals(fileName)) {
                    children[0] = new FileFacade(listParentFiles[i]);
                }
            }
        }
        return children;
    }

    private ArrayList<FileFacade> fillPathList(ArrayList<FileFacade> pathList, FileFacade child) {
        FileFacade parent = child.getParent();
        if (child.getParentFile() == null) {
            pa            
        } else {
            File parent = child.getParentFile();
        }
        
        if (!parent.equals(this.getRoot())) {
            pathList.add(parent);
            fillPathList(pathList, parent);
        } else {
            pathList.add(parent);
        }
        return pathList;
    }

    private FileFacade[] getPathForEvent(FileFacade fileFacade) {
        ArrayList<FileFacade> pathList = fillPathList(new ArrayList<>(), fileFacade);
        FileFacade[] path = new FileFacade[pathList.size()];
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
