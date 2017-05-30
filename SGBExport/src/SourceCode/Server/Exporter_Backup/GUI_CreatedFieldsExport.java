/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Exporter;

import SourceCode.Server.Exporter.Component.CheckBoxTreeCellRenderer;
import SourceCode.Server.Exporter.Component.CheckBoxTreeNode;
import SourceCode.Server.Exporter.Component.CheckBoxTreeNodeSelectionListener;
import SourceCode.Server.utils.fileUtils;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author bnson
 */
public class GUI_CreatedFieldsExport extends javax.swing.JFrame {

    /**
     * Creates new form GUI_CreatedExportTemplate
     * @param guiANP
     * @param projectName
     * @param listTable
     * @param pathFileFieldsNeedExport
     */
    public GUI_CreatedFieldsExport(GUI_AddNewProject guiANP, String projectName, List<OBJ_Table> listTable, String pathFileFieldsNeedExport) {
        this.projectName = projectName;
        this.listTable = listTable;
        this.pathFileFieldsNeedExport = pathFileFieldsNeedExport;
        this.guiANP = guiANP;
        initComponents();
        load();
    }

    public final void load() {
        this.modelFieldsExport = (DefaultTreeModel) treeProject.getModel();
        this.root = new CheckBoxTreeNode(projectName);
        modelFieldsExport.setRoot(root);

        int iTable = 0;
        int iColumn;
        for (OBJ_Table tmpTable : listTable) {
            DefaultMutableTreeNode table = new CheckBoxTreeNode(tmpTable.getTable());
            modelFieldsExport.insertNodeInto(table, root, iTable);
            iTable++;

            iColumn = 0;
            for (String tmpString : tmpTable.getColumn()) {
                //System.out.println("tmpString: " + tmpString);
                modelFieldsExport.insertNodeInto(new CheckBoxTreeNode(tmpString), table, iColumn);
                iColumn++;
            }
        }

        modelFieldsExport.reload();
        treeProject.setCellRenderer(new CheckBoxTreeCellRenderer());
        treeProject.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        treeProject.putClientProperty("JTree.lineStyle", "Angled");
        //treeProject.addMouseListener(new CheckBoxTreeNodeSelectionListener(treeProject));

        treeProject.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = treeProject.getRowForLocation(e.getX(), e.getY());
                TreePath path = treeProject.getPathForRow(row);
                //TreePath path = tree.getSelectionPath();

                if (path != null) {
                    if (e.getClickCount() >= 1) {
                        CheckBoxTreeNode node = (CheckBoxTreeNode) path.getLastPathComponent();
                        CheckBoxTreeNode nodeParrent = (CheckBoxTreeNode) path.getParentPath().getLastPathComponent();

                        if (node.isLeaf()) {
                            boolean isSelected = !(node.isSelected());
                            node.setSelected(isSelected);

                            System.out.println("Select: " + nodeParrent.toString());
                            nodeParrent.setSelected(false);

                            for (int i = 0; i < nodeParrent.getChildCount(); i++) {
                                if (((CheckBoxTreeNode) nodeParrent.getChildAt(i)).isSelected()) {
                                    nodeParrent.setSelected(true);
                                    break;
                                }
                            }
                            //--------------------
                            String sFieldsNeedExport = taFieldsNeedExport.getText();
                            String sSelect = "<xs:element name=\"" + nodeParrent.toString() + "." + node.toString() + "\" type=\"xs:string\"/>";
                            System.out.println("sSelect: " + sSelect);                            
                            if (isSelected) {
                                if (!sFieldsNeedExport.contains(sSelect)) {
                                    sFieldsNeedExport = sFieldsNeedExport + "\n" + sSelect;
                                }
                            } else {
                                if (sFieldsNeedExport.contains(sSelect)) {
                                    sFieldsNeedExport = sFieldsNeedExport.replace(sSelect, "");                                    
                                }
                            }
                            sFieldsNeedExport = sFieldsNeedExport.replaceAll("(?m)^[ \t]*\r?\n", "");
                            taFieldsNeedExport.setText(sFieldsNeedExport);
                            
                        }

                        ((DefaultTreeModel) treeProject.getModel()).nodeChanged(node);
                        ((DefaultTreeModel) treeProject.getModel()).nodeChanged(nodeParrent);
                    }

                    if (row == 0) {
                        treeProject.revalidate();
                        treeProject.repaint();
                    }
                }
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        treeProject = new javax.swing.JTree();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        taFieldsNeedExport = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        treeProject.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane1.setViewportView(treeProject);

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        taFieldsNeedExport.setColumns(20);
        taFieldsNeedExport.setRows(5);
        jScrollPane2.setViewportView(taFieldsNeedExport);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton1)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String templateExport = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                            "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:vc=\"http://www.w3.org/2007/XMLSchema-versioning\" elementFormDefault=\"qualified\" attributeFormDefault=\"unqualified\" vc:minVersion=\"1.1\">\n" +
                            "<xs:element name=\"table\">\n" +
                            "<xs:complexType>\n" +
                            "<xs:sequence>\n" +
                            "<xs:element name=\"row\">\n" +
                            "<xs:complexType>\n" +
                            "<xs:sequence>\n" +
                            "***\n" +
                            "</xs:sequence>\n" +
                            "</xs:complexType>\n" +
                            "</xs:element>\n" +
                            "</xs:sequence>\n" +
                            "</xs:complexType>\n" +
                            "</xs:element>\n" +
                            "</xs:schema>";
        templateExport = templateExport.replace("***", taFieldsNeedExport.getText());
        System.out.println("templateExport:" + templateExport);
        fileUtils.wirteFile(this.pathFileFieldsNeedExport, templateExport);
        this.guiANP.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        this.guiANP.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI_CreatedExportTemplate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI_CreatedExportTemplate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI_CreatedExportTemplate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_CreatedExportTemplate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new GUI_CreatedFieldsExport(null, null, null, null).setVisible(true);
        });
    }    
    
    private final String projectName;
    private DefaultTreeModel modelFieldsExport;
    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode selectNode;
    private final List<OBJ_Table> listTable;
    private final String pathFileFieldsNeedExport;
    private final GUI_AddNewProject guiANP;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea taFieldsNeedExport;
    private javax.swing.JTree treeProject;
    // End of variables declaration//GEN-END:variables
}
