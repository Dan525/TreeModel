package com.mycompany.jtreemvcmodel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.io.File;

/**
 * Created by Daniil on 04.08.2018.
 */
public class FileTreeApp extends JFrame {

    public FileTreeApp() {

        //Window
        super("Файловая система");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //Модель дерева
        FileTreeModel model = new FileTreeModel();        

        //Дерево
        JTree tree = new JTree();
        tree.setModel(model);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        //Add Selection Listener to Tree
        FileSelectionListener fl = new FileSelectionListener();
        tree.addTreeSelectionListener(fl);

        //Разметка окна
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        //Полоса прокрутки        
        JScrollPane scrollpane = new JScrollPane(tree);        
        
        //Panel
        JPanel panel = new JPanel();
        //panel.setLayout(new GridLayout(1,3,2,2));
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Имя файла:"));

        //Text        
        JTextField text = new JTextField(30);
        panel.add(text);

        //Кнопка        
        JButton button = new JButton("Создать файл");
        ButtonNewFileListener bl = new ButtonNewFileListener(fl, model, text);
        button.addActionListener(bl);
        panel.add(button);
        
        //Add to container
        container.add(scrollpane, BorderLayout.CENTER);
        container.add(panel, BorderLayout.SOUTH);
        
    }
}
