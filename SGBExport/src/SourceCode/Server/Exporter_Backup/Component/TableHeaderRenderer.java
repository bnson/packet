/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Exporter.Component;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author bnson
 */
public class TableHeaderRenderer implements TableCellRenderer {

    private JTable table = null;
    private TableHeaderRendererMouseEventReposter reporter = null;
    private final JComponent editor;

    public TableHeaderRenderer(JComponent editor) {
        this.editor = editor;
        //this.editor.setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        if (table != null && this.table != table) {
            this.table = table;
            final JTableHeader header = table.getTableHeader();   
            if (header != null) {   
                this.editor.setForeground(header.getForeground());   
                this.editor.setBackground(header.getBackground());   
                this.editor.setFont(header.getFont());
                reporter = new TableHeaderRendererMouseEventReposter(header, col, this.editor);
                header.addMouseListener(reporter);
            }
        }

        if (reporter != null) reporter.setColumn(col);

        return this.editor;
    }


}