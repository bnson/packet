/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Downloader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author bnson
 */
public class FTPDownload {

    String type;
    String host;
    String port;
    String user;
    String pass;
    String remoteDirectory;        
    String localDirectory;        
    String[] filter;
    String suDirectory;    

    public FTPDownload(Properties pros) {
        this.type = pros.getProperty("Download.Type").trim();
        this.host = pros.getProperty("Download.Host").trim();
        this.port = pros.getProperty("Download.Port").trim();
        this.user = pros.getProperty("Download.Username").trim();
        this.pass = pros.getProperty("Download.Password").trim();
        this.remoteDirectory = pros.getProperty("Download.RemoteDirector").trim().replaceAll("[\\/]+$", "");        
        this.localDirectory = pros.getProperty("Download.LocalDirectory").trim().replaceAll("[\\/]+$", "");        
        this.filter = pros.getProperty("Download.Filter").trim().split(";");
        this.suDirectory = pros.getProperty("Download.SubDirectory").trim();   
        
        if (!checkSetting()) {
            System.out.println("FTP Setting is erorr!");
            System.out.println("Application will be exit!");
            pressAnyKeyToContinue();
            System.exit(0);
        }
        
    }
    
    private Boolean checkSetting() {
        if (type.isEmpty() || type == null) return false;
        if (host.isEmpty() || host == null) return false;
        if (port.isEmpty() || port == null) return false;
        if (user.isEmpty() || user == null) return false;
        if (pass.isEmpty() || pass == null) return false;
        if (remoteDirectory.isEmpty() || remoteDirectory == null) return false;        
        if (localDirectory.isEmpty() || localDirectory == null) return false;        
        if (filter.length == 0 || filter == null) return false;
        if (suDirectory.isEmpty() || suDirectory == null) return false;  
        
        return true;
    }
    
    private void pressAnyKeyToContinue() {
        System.out.println("Press any key to exit...");
        try {
            System.in.read();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }    
    
    private void connect
    
    private List<String> getListFilesInDirectory(String pathDirectory) {
        List<String> listFiles = new ArrayList<>();

        FTPClient ftp = null;
        try {
            if (type.equalsIgnoreCase(type)) {
                
                ftp = new FTPClient();
                ftp.connect(host,Integer.parseInt(port));

                if (!ftp.login(user, pass)) {
                    ftp.logout();
                }

                int reply = ftp.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftp.disconnect();
                }
                
                ftp.enterLocalPassiveMode();
                ftp.changeWorkingDirectory(remoteDirectory);
                System.out.println("Remote system is " + ftp.getSystemType());
                System.out.println("Current directory is " + ftp.printWorkingDirectory());

                FTPFile[] ftpFiles = ftp.listFiles();                               
                if (ftpFiles != null && ftpFiles.length > 0) {
                    for (FTPFile file : ftpFiles) {
                        if (!file.isFile()) {
                            continue;
                        }
                        System.out.println("File is " + file.getName());
                    }
                }                

            } else {
                System.out.println("Error: This not FTP Server!");
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (ftp != null) {
                    ftp.logout();
                    ftp.disconnect();
                }        
                
            } catch (IOException ex) {
                Logger.getLogger(FTPDownload.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
        
        return listFiles;
    }
    
    
    public static String download(Properties pros) {
        String rs = "";

        long startTime = System.currentTimeMillis();
        String type = pros.getProperty("Download.Type").trim();
        String host = pros.getProperty("Download.Host").trim();
        String port = pros.getProperty("Download.Port").trim();
        String user = pros.getProperty("Download.Username").trim();
        String pass = pros.getProperty("Download.Password").trim();
        
        String remoteDirectory = pros.getProperty("Download.RemoteDirector").trim().replaceAll("[\\/]+$", "");        
        String localDirectory = pros.getProperty("Download.LocalDirectory").trim().replaceAll("[\\/]+$", "");

        
        String[] filter = pros.getProperty("Download.Filter").trim().split(";");

        FTPClient ftp = null;
        try {
            if (type.equalsIgnoreCase(type)) {
                //-- ftp://user:password@host:port/path
                
                ftp = new FTPClient();
                ftp.connect(host,Integer.parseInt(port));

                if (!ftp.login(user, pass)) {
                    ftp.logout();
                }

                int reply = ftp.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftp.disconnect();
                }
                
                ftp.enterLocalPassiveMode();
                ftp.changeWorkingDirectory(remoteDirectory);
                System.out.println("Remote system is " + ftp.getSystemType());
                System.out.println("Current directory is " + ftp.printWorkingDirectory());

//                FTPFileFilter ftpFileFilter = new FTPFileFilter() {
//                    @Override
//                    public boolean accept(FTPFile ftpFile) {
//                        if (ftpFile.isFile()) {
//                            for (String tmp : filter) {
//                                return ftpFile.getName().endsWith(tmp);
//                            }
//                        }
//                        return false;
//                    }
//                };                
                
                
                FTPFile[] ftpFiles = ftp.listFiles();                               
                if (ftpFiles != null && ftpFiles.length > 0) {
                    for (FTPFile file : ftpFiles) {
                        if (!file.isFile()) {
                            continue;
                        }
                        System.out.println("File is " + file.getName());
                    }
                }                
                

            } else {
                System.out.println("Error: This not FTP Server!");
            }
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        } finally {
            try {
                if (ftp != null) {
                    ftp.logout();
                    ftp.disconnect();
                }        
                
            } catch (IOException ex) {
                Logger.getLogger(FTPDownload.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
   
        return rs;
    }
    
    
}
