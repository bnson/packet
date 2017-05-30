/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Exporter.Component;

import SourceCode.Server.Exporter.OBJ_Export;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author bnson
 */
public class TableExportModel extends AbstractTableModel {

    private final Object[] columnNames = {"Select", "File Path", "ID"};
    private List<Object[]> data = new ArrayList<>();

    public TableExportModel() {

    }
    
    public TableExportModel(List<OBJ_Export> data) {        
        data.forEach(tmp -> {Object[] objTmp = {tmp.getSelect(), tmp.getFilePath(),tmp.getId()};this.data.add(objTmp);});
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col].toString();
    }

    @Override
    public Object getValueAt(int row, int col) {
        return data.get(row)[col];
    }

    /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
     */
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
         * Don't need to implement this method unless your table's
         * editable.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col == 1) {
            return false;
        } else {
            return true;
        }
    }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
    @Override
        public void setValueAt(Object value, int row, int col) {
//            Object[] tmp = data.get(row);
//            tmp[col] = value;
//            data.set(row, tmp);            
            data.get(row)[col] = value;
            fireTableCellUpdated(row, col);
        }
}
