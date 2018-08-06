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
    private File[] path;
    private int[] childIndices = new int[1];
    private File[] children = new File[1];


    FileTreeModel(File root) {
        this.root = root;
    }

    @Override
    public Object getRoot() {
        return root;
    }

    @Override
    public boolean isLeaf(Object node) {
        return ((File) node).isFile();
    }

    @Override
    public int getChildCount(Object parent) {
        String[] children = ((File) parent).list();
        if (children == null) {
            return 0;
        }
        return children.length;
    }

    @Override
    public Object getChild(Object parent, int index) {
        if (parent == null) {
            return File.listRoots()[index];
        } else {
        String[] children = ((File) parent).list();
        if ((children == null) || (index >= children.length)) {
            return null;
        }
        return new File((File) parent, children[index]);
        }
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        String[] children = ((File) parent).list();
        if (children == null) {
            return -1;
        }
        String childName = ((File) child).getName();
        for (int i = 0; i < children.length; i++) {
            if (childName.equals(children[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    void fireAddFile(File file) {
        getPathForEvent(file);
        getNewChildrenAndIndices(file);
        TreeModelEvent evt = new TreeModelEvent(this, path, childIndices, children);
        for (TreeModelListener l : listeners) {
            l.treeNodesInserted(evt);
        }
    }

    private void getNewChildrenAndIndices (File file) {
        String fileName = file.getName();
        String[] listParentFileNames = file.getParentFile().list();
        File[] listParentFiles = file.getParentFile().listFiles();

        if (listParentFileNames != null) {
            for (int i = 0; i < listParentFileNames.length; i++) {
                if (listParentFileNames[i].equals(fileName)) {
                    childIndices[0] = i;
                    if (listParentFiles != null)
                        children[0] = listParentFiles[i];
                }
            }
        }
    }

    private ArrayList<File> fillPathList(ArrayList<File> pathList, File child) {
        File parent = child.getParentFile();
        if (!parent.equals(this.getRoot())) {
            pathList.add(parent);
            fillPathList(pathList, parent);
        } else {
            pathList.add(parent);
        }
        return pathList;
    }

    private void getPathForEvent(File file) {
        ArrayList<File> pathList = fillPathList(new ArrayList<>(), file);
        path = new File[pathList.size()];
        Collections.reverse(pathList);
        path = pathList.toArray(path);
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
