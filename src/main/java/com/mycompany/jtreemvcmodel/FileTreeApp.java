package com.mycompany.jtreemvcmodel;

import javax.swing.*;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

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
        FileTreeModel model = new FileTreeModel();

        //Дерево        
        Theme theme = null;
        JTree tree = new JTreeFactory().setTheme(theme);
        
        //JTree tree = new JTree();
        tree.setModel(model);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        //tree.setRootVisible(false);

        //Add Selection Listener to Tree
        FileSelectionListener fl = new FileSelectionListener();
        tree.addTreeSelectionListener(fl);

        //Разметка окна
        Container container = this.getContentPane();
        container.setLayout(new BorderLayout());

        //Полоса прокрутки        
        JScrollPane scrollPane = new JScrollPane(tree);
        
        //Panel
        JPanel panel = new JPanel();
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
        container.add(scrollPane, BorderLayout.CENTER);
        container.add(panel, BorderLayout.SOUTH);

        this.setSize(600,600);
    }
}
