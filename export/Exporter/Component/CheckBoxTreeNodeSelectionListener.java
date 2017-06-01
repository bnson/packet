/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Exporter.Component;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author bnson
 */
public class CheckBoxTreeNodeSelectionListener extends MouseAdapter {

    private final JTree tree;

    public CheckBoxTreeNodeSelectionListener(JTree tree) {
        this.tree = tree;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = tree.getRowForLocation(e.getX(), e.getY());
        TreePath path = tree.getPathForRow(row);
        //TreePath path = tree.getSelectionPath();

        if (path != null) {
            if (e.getClickCount() >= 1) {
                CheckBoxTreeNode node = (CheckBoxTreeNode)path.getLastPathComponent();
                CheckBoxTreeNode nodeParrent = (CheckBoxTreeNode)path.getParentPath().getLastPathComponent();
                
                if (node.isLeaf()) {
                    boolean isSelected = !(node.isSelected());
                    node.setSelected(isSelected);
                    
                    System.out.println("Select: " + nodeParrent.toString());
                    nodeParrent.setSelected(false);
                    
                    for (int i=0; i < nodeParrent.getChildCount(); i++) {
                        if (((CheckBoxTreeNode)nodeParrent.getChildAt(i)).isSelected()) {
                            nodeParrent.setSelected(true);
                            break;
                        }
                    }                                        
                    
//                    if (isSelected) {
//                        //tree.expandPath(path);
//                        ((CheckBoxTreeNode)node.getParent()).setSelected(true);
//                    } else {
//                        ((CheckBoxTreeNode)node.getParent()).setSelected(true);
////                        //tree.collapsePath(path);
////                        ((CheckBoxTreeNode)node.getParent()).setSelected(false);
////                        for (int i=0; i < ((CheckBoxTreeNode)node.getParent()).getChildCount(); i++) {
////                            if (((CheckBoxTreeNode)node.getParent().getChildAt(i)).selected) {
////                                ((CheckBoxTreeNode)node.getParent()).setSelected(true);
////                                break;
////                            }
////                        }                    
//                    }

//                for (int i=0; i < node.getChildCount(); i++) {
//                    ((CheckBoxTreeNode)node.getChildAt(i)).setSelected(isSelected);
//                }

                }
                
                ((DefaultTreeModel) tree.getModel()).nodeChanged(node);
                ((DefaultTreeModel) tree.getModel()).nodeChanged(nodeParrent);
            }

            if (row == 0) {
                tree.revalidate();
                tree.repaint();
            }
        }
    }

}
