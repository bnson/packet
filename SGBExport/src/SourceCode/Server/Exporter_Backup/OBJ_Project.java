/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Exporter;

import java.util.List;

/**
 *
 * @author bnson
 */
public class OBJ_Project {

    private String id;
    private String host;
    private String port;
    private String database;
    private String schema;
    private String username;
    private String password;
    private String queryExportList;
    private String queryExportData;
    private String queryExportFinish;    
    private List<String> exportTemplate;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }    
    
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getQueryExportList() {
        return queryExportList;
    }
    
    public void setQueryExportList(String queryGetExport) {
        this.queryExportList = queryGetExport;
    }
    
    public String getQueryExportData() {
        return queryExportData;
    }

    public void setQueryExportData(String queryGetExportData) {
        this.queryExportData = queryGetExportData;
    }    

    public String getQueryExportFinish() {
        return queryExportFinish;
    }

    public void setQueryExportFinish(String queryWhenFinishExport) {
        this.queryExportFinish = queryWhenFinishExport;
    }
    
    public List<String> getExportTemplate() {
        return exportTemplate;
    }

    public void setExportTemplate(List<String> exportTemplate) {
        this.exportTemplate = exportTemplate;
    }    
    
    //--------------------------------------------------------
    public OBJ_Project() {
        
    }
    
    public OBJ_Project(String id,String host, String port, String database, String schema, String username, String password, String queryGetExport, String queryExportData, String queryWhenFinishExport, List<String> exportTemplate) {
        this.id = id;
        this.host = host;
        this.port = port;
        this.database = database;
        this.schema = schema;
        this.username = username;
        this.password = password;
        this.queryExportList = queryGetExport;
        this.queryExportData = queryExportData;
        this.queryExportFinish = queryWhenFinishExport;
        this.exportTemplate = exportTemplate;
    }    
   
        
}
