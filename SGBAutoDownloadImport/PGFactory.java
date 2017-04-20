/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Downloader;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author saigonbpo
 */
public class PGFactory {

    private final String url;
    private final String username;
    private final String password;
    private final String schema;

    public PGFactory(Properties props) {
        this.url = props.getProperty("PostgreSQL.Url");
        this.username = props.getProperty("PostgreSQL.Username");
        this.password = props.getProperty("PostgreSQL.Password");
        this.schema = props.getProperty("PostgreSQL.Schema");

    }

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
            System.out.println("Message: Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
   
    public int callFunction_InsertTo_Download_Info(Map iMap) {
        int result = 0;
        String[] arg_001 = new String[6];
        arg_001[0] = iMap.get("file_name").toString();
        arg_001[1] = iMap.get("file_size").toString();
        arg_001[2] = iMap.get("total_files").toString();
        arg_001[3] = iMap.get("path_download").toString();
        arg_001[4] = iMap.get("path_save").toString();
        arg_001[5] = "0";
        //System.out.println("InsertTo_Download_Info: " + Arrays.toString(arg_001));
        
        Connection conn = connect();
        
        if (conn != null) {
            try {
                CallableStatement properCase = conn.prepareCall("{ ? = call " + schema + ".sp_download_insert_new(?) }");
                properCase.registerOutParameter(1, Types.INTEGER);
                properCase.setArray(2, conn.createArrayOf("VARCHAR", arg_001));
                properCase.execute();
                result = (int) properCase.getInt(1);
                conn.commit();
                System.out.println("Message: Commit succeed.");
            } catch (SQLException ex) {
                try {
                    conn.rollback();
                    System.out.println("Message: Rollback.");
                    System.out.println("Error: " + ex.getMessage());
                    Logger.getLogger(PGFactory.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex1) {
                    System.out.println("Error: " + ex1.getMessage());
                    Logger.getLogger(PGFactory.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    conn.close();
                    System.out.println("Message: Connection is close.");
                } catch (SQLException ex) {
                    System.out.println("Error: " + ex.getMessage());
                    Logger.getLogger(PGFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return result;
    }
    
    public int callFunction_InsertTo_Management(List<Map> listMap) {
        int result = 0;
        Connection conn = connect();
        
        if (conn != null) {
            try {
                for (Map iMap : listMap) {
                    String[] arg_001 = new String[4];
                    arg_001[0] = iMap.get("download_id").toString();
                    arg_001[1] = iMap.get("form_id").toString();
                    arg_001[2] = iMap.get("filepath").toString();
                    arg_001[3] = iMap.get("filename").toString();
                    System.out.println("Message: Import " + Arrays.toString(arg_001));

                    CallableStatement properCase = conn.prepareCall("{ ? = call " + schema + ".sp_management_insert_new(?) }");
                    properCase.registerOutParameter(1, Types.INTEGER);
                    properCase.setArray(2, conn.createArrayOf("VARCHAR", arg_001));
                    properCase.execute();
                    result = (int) properCase.getInt(1);
                }
                conn.commit();
                System.out.println("Message: Commit succeed.");
            } catch (SQLException ex) {
                try {
                    conn.rollback();
                    System.out.println("Message: Rollback.");
                    System.out.println("Error: " + ex.getMessage());
                    Logger.getLogger(PGFactory.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex1) {
                    System.out.println("Error: " + ex1.getMessage());
                    Logger.getLogger(PGFactory.class.getName()).log(Level.SEVERE, null, ex1);
                }
            } finally {
                try {
                    conn.close();
                    System.out.println("Message: Connection is close.");
                } catch (SQLException ex) {
                    System.out.println("Error: " + ex.getMessage());
                    Logger.getLogger(PGFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        }      

        return result;
    }

//    public int callFunction_InsertTo_Management(Map iMap) {
//        int result = 0;
//        String[] arg_001 = new String[4];
//        arg_001[0] = iMap.get("download_id").toString();
//        arg_001[1] = iMap.get("form_id").toString();
//        arg_001[2] = iMap.get("filepath").toString();
//        arg_001[3] = iMap.get("filename").toString();
//
//        System.out.println("Message: Import " + Arrays.toString(arg_001));
//        
//        Connection conn = null;
//        try {
//            conn = connect();
//            CallableStatement properCase = conn.prepareCall("{ ? = call " + schema + ".sp_management_insert_new(?) }");
//            properCase.registerOutParameter(1, Types.INTEGER);
//            properCase.setArray(2, conn.createArrayOf("VARCHAR", arg_001));
//            properCase.execute();
//            result = (int) properCase.getInt(1);
//        } catch (SQLException ex) {
//            System.out.println("Error: " + ex.getMessage());
//            Logger.getLogger(PGFactory.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                if (conn != null) {
//                    conn.commit();
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println("Error: " + ex.getMessage());
//                Logger.getLogger(PGFactory.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        return result;
//    }    
 
}
