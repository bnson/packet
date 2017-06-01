/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Exporter.Component;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author bnson
 */
public final class CheckBoxTreeNode extends DefaultMutableTreeNode {

    private boolean selected;    
    
    public CheckBoxTreeNode() {
        this(null);
    }    
    
    public CheckBoxTreeNode(Object userObject) {
        this(userObject, true, false);
    }    
    
    public CheckBoxTreeNode(Object userObject, boolean allowsChildren, boolean selected) {
        super(userObject, allowsChildren);
        this.selected = selected;
    }    

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    
}
