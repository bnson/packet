/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Exporter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author bnson
 */
public class OBJ_Table {

    private String table;
    private List<String> column;    
    
    public OBJ_Table() {
        
    }
    
    public OBJ_Table(String table, List<String> column) {
        this.table = table;
        this.column = column;
    }    
    public OBJ_Table(String table, String column) {
        this.table = table;
        this.column = new ArrayList<>(Arrays.asList(column.split(";")));
    }      
    
    
    public int getColumnCount() { return column.size(); }
    
    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public List<String> getColumn() {
        return column;
    }

    public void setColumn(List<String> column) {
        this.column = column;
    }
    

    

    
}
