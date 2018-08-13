/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jtreemvcmodel;

import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import static javax.swing.event.TableModelEvent.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author daniil_pozdeev
 */
public class FileTableModel implements TableModel {

    private final ArrayList<TableModelListener> listeners = new ArrayList<>();
    private final FileTreeModel treeModel;
    private Facade parent;
    
    public FileTableModel(FileTreeModel treeModel) {
        this.treeModel = treeModel;
    }
    
    @Override
    public int getRowCount() {
        if (parent == null) {
            return treeModel.getChildCount(treeModel.getRoot());
        } else {
            return treeModel.getChildCount(parent);
        }
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return "Имя";
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            if (parent == null) {
                return treeModel.getRoot().getClass();
            } else {
                return parent.getClass();
            }
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0) {
            if (parent == null) {
                return treeModel.getChild(treeModel.getRoot(), rowIndex);
            } else {
                return treeModel.getChild(parent, rowIndex);
            }
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        
    }
    
    public void fireNewSelectedDir(Facade file) {
        parent = file;
        TableModelEvent evt = new TableModelEvent(this);
        for (TableModelListener l : listeners) {
            l.tableChanged(evt);
        }
    }
    
    public void fireAddFile(Facade file) {
        int changedRow = treeModel.getIndexOfChild(file.getParent(), file);
        TableModelEvent evt = new TableModelEvent(this, changedRow, changedRow, ALL_COLUMNS, INSERT);
        for (TableModelListener l : listeners) {
            l.tableChanged(evt);
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }
    
}
