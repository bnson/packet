/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Exporter.Component;

import java.util.EventObject;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeNode;

/**
 *
 * @author bnson
 */
public class LeafCellEditor extends DefaultTreeCellEditor {

    public LeafCellEditor(JTree tree, DefaultTreeCellRenderer renderer, TreeCellEditor editor) {
        super(tree, renderer, editor);
    }

    @Override
    public boolean isCellEditable(EventObject event) {
        boolean returnValue = super.isCellEditable(event);
        if (returnValue) {
            Object node = tree.getLastSelectedPathComponent();
            if ((node != null) && (node instanceof TreeNode)) {
                TreeNode treeNode = (TreeNode) node;
                returnValue = treeNode.isLeaf();
            }
        }
        return returnValue;
    }
    
}
