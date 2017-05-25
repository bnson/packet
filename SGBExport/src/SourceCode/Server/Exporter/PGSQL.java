/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Exporter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saigonbpo
 */
public class PGSQL {

    private final String host;
    private final String port;
    private final String database;
    private final String schema;
    private final String username;
    private final String password;

    public PGSQL(Properties props) {
        this.host = props.getProperty("PostgreSQL.Host");
        this.port = props.getProperty("PostgreSQL.Port");
        this.database = props.getProperty("PostgreSQL.Data");
        this.schema = props.getProperty("PostgreSQL.Schema");
        this.username = props.getProperty("PostgreSQL.Username");
        this.password = props.getProperty("PostgreSQL.Password");
    }
    
    public PGSQL(String host, String port, String database, String schema, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.schema = schema;
        this.username = username;
        this.password = password;     
    }

    public List<OBJ_Export> getExportList(String query) {
        List<OBJ_Export> exportList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                OBJ_Export objExport = new OBJ_Export();
                objExport.setSelect(false);
                objExport.setFilePath(rs.getString(2));
                objExport.setId(rs.getString(1));
                exportList.add(objExport);
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PGSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return exportList;
    }
    
    public String getExportData(String query) {
        String rsString = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = connect();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                rsString = rs.getString(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PGSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
        
        return rsString;
    }
    
    public boolean runExportFinish(String query) {
        
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = connect();
            conn.setAutoCommit(false);
            
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
            
            conn.commit();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PGSQL.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }        
        
        return true;
    }
    
    public boolean checkConnect() {
        boolean rs = true;
        Connection conn = connect();
        try {
            if (conn != null) {
                rs = true;
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(PGSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

    public Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:postgresql://" + this.host + ":" + this.port + "/" + this.database + "?currentSchema=" + this.schema;
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
            System.out.println("Message: Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
