/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Exporter;

import SourceCode.Configuration.Configuration;
import SourceCode.Server.Exporter.Component.TableExportModel;
import SourceCode.Server.Exporter.Component.TableHeaderRenderer;
import SourceCode.Server.utils.fileUtils;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author bnson
 */
public final class GUI_SGBExport extends javax.swing.JFrame {

    /**
     * Creates new form SGBExport
     */
    public GUI_SGBExport() {
        initComponents();
        loadProjectList();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cbSelectAll = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btExport = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        lProject = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbExport = new javax.swing.JTable();

        cbSelectAll.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cbSelectAllMousePressed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton4.setText("Remove");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btExport.setText("Export");
        btExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExportActionPerformed(evt);
            }
        });

        jButton3.setText("Auto Export");

        jButton2.setText("Refersh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btExport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(102, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btExport)
                    .addComponent(jButton3)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lProject.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lProject.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lProjectValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(lProject);

        jScrollPane1.setViewportView(tbExport);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 316, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(13, 13, 13))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:        
        GUI_AddNewProject guiANP = new GUI_AddNewProject(this);
        guiANP.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbSelectAllMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cbSelectAllMousePressed
        // TODO add your handling code here:
        for (int i = 0; i < tbExport.getModel().getRowCount(); i++) {
            tbExport.getModel().setValueAt(!cbSelectAll.isSelected(), i, 0);
        }
    }//GEN-LAST:event_cbSelectAllMousePressed

    private void lProjectValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lProjectValueChanged
        // TODO add your handling code here:
        if (!evt.getValueIsAdjusting()) {
            loadExportList(lProject.getSelectedValue());
        }
    }//GEN-LAST:event_lProjectValueChanged

    private void btExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExportActionPerformed
        // TODO add your handling code here:
        // Get ID need export.
        runExport();
    }//GEN-LAST:event_btExportActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        loadExportList(currProject.getId());
    }//GEN-LAST:event_jButton2ActionPerformed
    private void runExport() {
        String id;
        try {

            for (int i = 0; i < this.tbExport.getRowCount(); i++) {
                if (this.tbExport.getValueAt(i, 0).toString().equalsIgnoreCase("true")) {
                    id = this.tbExport.getValueAt(i, 2).toString();
                    id = id.replaceAll(",$", "");

                    if (!id.isEmpty()) {

                        String dataExport = pgsql.getExportData(currProject.getQueryExportData().replace("?", id));
                        System.out.println("-----------\n" + dataExport);

                        //File xmlSource = new File("D:\\tmp\\test\\export\\mrp_data.xml");
                        InputSource xmlSource = new InputSource(new StringReader(dataExport));
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document document = builder.parse(xmlSource);
                        Source source = new DOMSource(document);
                        //-----------------------------
                        for (String exportTemplate : currProject.getExportTemplate()) {

                            System.out.println("exportTemplate: " + exportTemplate);
                            File stylesheet = new File(exportTemplate);
                            StreamSource stylesource = new StreamSource(stylesheet);
                            Transformer transformer = TransformerFactory.newInstance().newTransformer(stylesource);

                            Result outputTarget = new StreamResult(System.out);
                            //Result outputTarget = new StreamResult(new File("D:\\tmp\\test\\export\\001\\.tmp"));

                            transformer.transform(source, outputTarget);
                            System.out.println("Done.");
                        }
                        //-----------------------------                
                        if (pgsql.runExportFinish(currProject.getQueryExportFinish().replace("?", id))) {
                            System.out.println("Message: Export is success!");
                        } else {
                            System.out.println("Error: Export is fail!");
                        }

                        loadExportList(currProject.getId());
                    }

                }
            }

        } catch (SAXException | ParserConfigurationException | IOException | TransformerException ex) {
            Logger.getLogger(GUI_SGBExport.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI_SGBExport.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new GUI_SGBExport().setVisible(true);
        });

    }

    public void loadProjectList() {
        try {

            if (fileUtils.createDirectory("Projects")) {
                System.out.println("Message: Initialize [Project] folder is success.");
                String sPL_DataInitialize = "<sgb>\n</sgb>";
                if (fileUtils.wirteNewFile(pathFileProjectList, sPL_DataInitialize)) {
                    System.out.println("Message: Initialize [projectList.xml] is success.");
                    this.listProject = new ArrayList<>();
                    DefaultListModel listModel = new DefaultListModel();

                    File xmlFile = new File(pathFileProjectList);
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(xmlFile);
                    doc.getDocumentElement().normalize();
                    System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

                    NodeList nlProject = doc.getElementsByTagName("project");
                    for (int i = 0; i < nlProject.getLength(); i++) {
                        Node nProject = nlProject.item(i);
                        System.out.println("Current Element :" + nProject.getNodeName());
                        if (nProject.getNodeType() == Node.ELEMENT_NODE) {
                            Element eProject = (Element) nProject;

                            List<String> exportTemplate = new ArrayList<>();
                            NodeList ntExportTemplate = eProject.getElementsByTagName("exportTemplate");
                            for (int j = 0; j < ntExportTemplate.getLength(); j++) {
                                Node nExportTemplate = ntExportTemplate.item(j);
                                System.out.println("Current Element :" + nExportTemplate.getNodeName());
                                if (nExportTemplate.getNodeType() == Node.ELEMENT_NODE) {
                                    Element eTemplate = (Element) nExportTemplate;
                                    NodeList nlTemplate = eTemplate.getElementsByTagName("template");
                                    for (int k = 0; k < nlTemplate.getLength(); k++) {
                                        Node nTemplate = nlTemplate.item(k);
                                        System.out.println("Template :" + nTemplate.getFirstChild().getTextContent());
                                        exportTemplate.add(nTemplate.getFirstChild().getTextContent());
                                    }
                                }
                            }

                            OBJ_Project tmpOBJProject = new OBJ_Project(
                                    eProject.getAttribute("id"),
                                    eProject.getElementsByTagName("host").item(0).getTextContent(),
                                    eProject.getElementsByTagName("port").item(0).getTextContent(),
                                    eProject.getElementsByTagName("database").item(0).getTextContent(),
                                    eProject.getElementsByTagName("schema").item(0).getTextContent(),
                                    eProject.getElementsByTagName("username").item(0).getTextContent(),
                                    eProject.getElementsByTagName("password").item(0).getTextContent(),
                                    eProject.getElementsByTagName("queryExportList").item(0).getTextContent(),
                                    eProject.getElementsByTagName("queryExportData").item(0).getTextContent(),
                                    eProject.getElementsByTagName("queryExportFinish").item(0).getTextContent(),
                                    exportTemplate
                            );

                            listModel.addElement(tmpOBJProject.getId());
                            listProject.add(tmpOBJProject);
                        }
                    }

                    lProject.setModel(listModel);

                }
            }

        } catch (IOException | ParserConfigurationException | SAXException ex) {
            System.out.println("Error Message: " + ex.getMessage());
        }
    }

    public void loadExportList(String idProject) {

        listProject.stream().filter((tmp) -> (tmp.getId().equals(idProject))).forEachOrdered((tmp) -> {
            currProject = tmp;
        });

        if (currProject != null) {
            pgsql = new PGSQL(currProject.getHost(), currProject.getPort(), currProject.getDatabase(), currProject.getSchema(), currProject.getUsername(), currProject.getPassword());

            TableExportModel model = new TableExportModel(pgsql.getExportList(currProject.getQueryExportList()));
            tbExport.setModel(model);
            tbExport.getColumnModel().getColumn(0).setHeaderRenderer(new TableHeaderRenderer(cbSelectAll));
            tbExport.getColumnModel().getColumn(0).setWidth(50);
            tbExport.getColumnModel().getColumn(0).setPreferredWidth(50);
        }

    }
    private OBJ_Project currProject = null;
    private List<OBJ_Project> listProject;
    private final static Properties pros = Configuration.getInstance().getProperty();
    private PGSQL pgsql;
    private final String pathFileProjectList = "Projects" + File.separator + "projectList.xml";
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btExport;
    private javax.swing.JCheckBox cbSelectAll;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> lProject;
    private javax.swing.JTable tbExport;
    // End of variables declaration//GEN-END:variables
}