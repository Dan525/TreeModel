package com.mycompany.jtreemvcmodel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

/**
 *
 * @author daniil_pozdeev
 */
public class FileSelectionListener implements TreeSelectionListener {

    private String filePath;
    private TreePath treePath;

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        JTree tree = (JTree) e.getSource();
        Facade parent = (Facade) tree.getLastSelectedPathComponent();
        treePath = tree.getSelectionPath();
        filePath = null;
        if (parent != null && (parent instanceof FileFacade)) {
            filePath = parent.getFile().getPath();
        }        
    }

    String getFilePath() {
        return filePath;
    }

    TreePath getTreePath() {
        return treePath;
    }
}
