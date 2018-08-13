package com.mycompany.jtreemvcmodel;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 *
 * @author daniil_pozdeev
 */
class FileTreeApp extends JFrame {

    FileTreeApp() {

        //Window
        super("Файловая система");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        //Модель дерева
        FileTreeModel treeModel = new FileTreeModel();

        //Дерево
        JTree tree = new JTreeFactory().createTree();
        tree.setModel(treeModel);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        //tree.setRootVisible(false);    

        //Таблица
        FileTableModel tableModel = new FileTableModel(treeModel);
        JTable table = new JTable(tableModel);
        
        //Add Selection Listener to Tree
        FileSelectionListener fl = new FileSelectionListener(tableModel);
        tree.addTreeSelectionListener(fl);   

        //Разметка окна
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        //Полоса прокрутки        
        JScrollPane treeScrollPane = new JScrollPane(tree);
        treeScrollPane.setPreferredSize(new Dimension(400,600));
        JScrollPane tableScrollPane = new JScrollPane(table);
        
        //Panel
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Имя папки:"));

        //Text        
        JTextField text = new JTextField(30);
        panel.add(text);

        //Кнопка        
        JButton button = new JButton("Создать папку");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fl.getFilePath() != null) {
                    File file = new File(fl.getFilePath(), text.getText());
                    boolean isCreated = file.mkdir();

                    if (isCreated) {
                        treeModel.fireAddFile(new FileFacade(file), fl.getTreePath());
                        tableModel.fireAddFile(new FileFacade(file));
                        text.setText("");
                        text.setBackground(Color.WHITE);
                    } else {
                        text.setBackground(Color.YELLOW);
                    }
                }
            }
        });
        panel.add(button);
        
        //Add to container
        container.add(treeScrollPane, BorderLayout.WEST);
        container.add(panel, BorderLayout.SOUTH);
        container.add(tableScrollPane, BorderLayout.CENTER);

        this.setSize(1000,600);
    }
}
