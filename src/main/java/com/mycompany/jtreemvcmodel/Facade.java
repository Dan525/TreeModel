/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jtreemvcmodel;

import java.io.File;

/**
 *
 * @author daniil_pozdeev
 */
public interface Facade {
    
    public boolean isLeaf();

    public int getChildCount();

    public Facade getChild(int index);

    public int getIndexOfChild(Facade child);
    
    public Facade getParent();
    
    public File getFile();
}
