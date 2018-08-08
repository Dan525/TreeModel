/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jtreemvcmodel;

import java.util.ArrayList;

/**
 *
 * @author daniil_pozdeev
 */
public interface Facade {
    
    public boolean isLeaf();

    public int getChildCount();

    public FileFacade getChild(int index);

    public int getIndexOfChild(FileFacade child);
    
    public boolean equals(Object other);
    
    public String toString();
}
