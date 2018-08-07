/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jtreemvcmodel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author daniil_pozdeev
 */
public class DarkTheme implements Theme {

    @Override
    public void setup(JTree tree) {
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        
        renderer.setFont(new Font("TreeDark", Font.BOLD | Font.ITALIC, 14));

        Color backgroundSelection = renderer.getBackgroundSelectionColor();
        renderer.setBackgroundSelectionColor(Color.blue);
        renderer.setBackgroundNonSelectionColor(backgroundSelection);

        Color textSelection = renderer.getTextSelectionColor();
        renderer.setTextSelectionColor(Color.green);
        renderer.setTextNonSelectionColor(textSelection);
    }
    
}
