package com.mycompany.jtreemvcmodel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 *
 * @author daniil_pozdeev
 */
public class ButtonNewFileListener implements ActionListener {

    private FileSelectionListener fl;
    private FileTreeModel model;
    private JTextField text;    

    ButtonNewFileListener(FileSelectionListener fl, FileTreeModel model, JTextField text) {
        this.fl = fl;
        this.model = model;
        this.text = text;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (fl.getFilePath() != null) {            
            File file = new File(fl.getFilePath(), text.getText());            
            boolean isCreated = file.mkdir();  
            
            if (isCreated) {                
                model.fireAddFile(file);
                text.setText("");
                text.setBackground(Color.WHITE);
            } else {
                text.setBackground(Color.YELLOW);
            }
        }
    }
}
