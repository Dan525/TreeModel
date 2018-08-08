package com.mycompany.jtreemvcmodel;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import java.io.File;

/**
 *
 * @author daniil_pozdeev
 */
public class FileSelectionListener implements TreeSelectionListener {

    private String filePath;

    @Override
    public void valueChanged(TreeSelectionEvent e) {
        JTree tree = (JTree) e.getSource();
        Facade parent = (Facade) tree.getLastSelectedPathComponent();
        filePath = null;
        if (parent != null && (parent instanceof FileFacade)) {
            filePath = ((FileFacade) parent).getFile().getPath();
        }        
    }

    String getFilePath() {
        return filePath;
    }
}
