/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jtreemvcmodel;

import javax.swing.JTree;

/**
 *
 * @author daniil_pozdeev
 */
public class JTreeFactory {
    
    public JTree setTheme(Theme theme) {
        JTree tree = new JTree();
        theme.setup(tree);
        return tree;
    }
    
    
}
