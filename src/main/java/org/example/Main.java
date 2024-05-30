package org.example;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private JFrame mainFrame;

    public static void main(String[] args) {
            Main main = new Main();
            main.createAndShowGUI();
    }

    private void createAndShowGUI() {
        mainFrame = new JFrame("laba3");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(200, 200);
        mainFrame.setLocationRelativeTo(null);

        JButton chooseFileButton = new JButton("Choose File");
        chooseFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleChooseFileButtonClick();
            }
        });

        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(chooseFileButton, BorderLayout.CENTER);
        mainFrame.setVisible(true);
    }

    private void handleChooseFileButtonClick() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(mainFrame);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                Manager manager = new Manager();
                HashMap<String, Reactor> reactors = manager.readCommonClass(selectedFile.getAbsolutePath());
                displayReactorData(reactors);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainFrame, "Error: Please choose a correct file");
            }
        }
    }

    private void displayReactorData(HashMap<String, Reactor> reactors) {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode();
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);

        for (Map.Entry<String, Reactor> entry : reactors.entrySet()) {
            DefaultMutableTreeNode reactorNode = new DefaultMutableTreeNode(entry.getKey());

            reactorNode.add(new DefaultMutableTreeNode("Burnup: " + entry.getValue().burnup));
            reactorNode.add(new DefaultMutableTreeNode("Class: " + entry.getValue().reactorClass));
            reactorNode.add(new DefaultMutableTreeNode("Electrical Capacity: " + entry.getValue().electricalCapacity));
            reactorNode.add(new DefaultMutableTreeNode("First Load: " + entry.getValue().firstLoad));
            reactorNode.add(new DefaultMutableTreeNode("KPD: " + entry.getValue().kpd));
            reactorNode.add(new DefaultMutableTreeNode("Life Time: " + entry.getValue().lifeTime));
            reactorNode.add(new DefaultMutableTreeNode("Terminal Capacity: " + entry.getValue().terminalCapacity));
            reactorNode.add(new DefaultMutableTreeNode("File Type: " + entry.getValue().fileType));

            treeModel.insertNodeInto(reactorNode, rootNode, treeModel.getChildCount(rootNode));
        }

        JTree tree = new JTree(treeModel);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tree);

        JFrame treeFrame = new JFrame();
        treeFrame.add(scrollPane);
        treeFrame.setSize(400, 400);
        treeFrame.setLocationRelativeTo(null);
        treeFrame.setVisible(true);
    }
}
