package com.mycompany.jtreemvcmodel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;

/**
 * Created by Daniil on 04.08.2018.
 */
public class FileSelectionListener implements TreeSelectionListener {

    private String filePath;
    private File parent;

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        JTree tree = (JTree) e.getSource();
        parent = (File) tree.getLastSelectedPathComponent();
        filePath = null;
        if (parent != null) {
            filePath = parent.getPath();
        }        
    }

    public String getFilePath() {
        return filePath;
    }

    public File getParent() {
        return parent;
    }
}
