/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Downloader;

import SourceCode.Server.utils.fileUtils;
import SourceCode.Server.utils.stringUtils;
import SourceCode.Server.utils.systemUtils;
import SourceCode.Server.utils.timeUtils;
import com.enterprisedt.net.ftp.pro.ProFTPClient;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.ssh.SSHFTPClient;
import com.enterprisedt.util.license.License;
import java.io.File;
import java.io.FileFilter;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bnson
 */
public class FTP {

    private static ProFTPClient initializedFTP(String host, int port, String user, String pass, int timeOut) {
        License.setLicenseDetails("GHPFarEastLtd", stringUtils.decrypt("5N4qDSUywq6/7PVcplDub1EPsKE0mjPi", "This is a fairly long phrase used to encrypt", "DES", "DES", "UTF8"));
        //GHPFarEastLtd//348-7352-2340-1248        
        ProFTPClient ftp = new ProFTPClient();
        try {
            // setting server address and credentials          
            ftp.setRemoteHost(host);
            ftp.setRemotePort(port);
            ftp.setTimeout(timeOut);    
            ftp.connect();
            ftp.login(user, pass);
            //ftp.user(user);
            //ftp.password(pass);       
            
        } catch (FTPException | IOException ex) {
            Logger.getLogger(FTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ftp;
    }

    public static List<String> getListFolders(String host, int port, String user, String pass, int timeOut, String directoryWork, boolean subDirectory) {
        List<String> listPathFolders = new ArrayList();

        ProFTPClient ftp = initializedFTP(host, port, user, pass, timeOut);
        try {
            if (ftp.connected()) {
                //System.out.println("Message: Connect to [" + host + ":" + port + "]");
                FTPFile[] files = ftp.dirDetails(directoryWork);
                for (FTPFile file : files) {
                    if (file.isDir()) {
                        if (!".".equals(file.getName()) && !"..".equals(file.getName())) {
                            listPathFolders.add((file.getPath() + "/" + file.getName()).replaceAll("[/]+", "/"));
                            //System.out.println("File: " + (file.getPath() + "/" + file.getName()).replaceAll("[/]+", "/"));
                            
                        }
                    }
                }
            }

            ftp.quit();

        } catch (FTPException | ParseException | IOException e) {
            System.out.println("Error: " + e.getMessage());
            Logger.getLogger(FTP.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

        return listPathFolders;

    }

    public static List<String> getListFiles(String host, int port, String user, String pass, int timeOut, String directoryWork, boolean subDirectory) {
        List<String> listPathFiles = new ArrayList();

        ProFTPClient ftp = initializedFTP(host, port, user, pass, timeOut);
        try {
            if (ftp.connected()) {
                //System.out.println("Message: Connect to [" + host + ":" + port + "]");

                FTPFile[] files = ftp.dirDetails(directoryWork);
                for (FTPFile file : files) {
                    if (file.isFile()) {
                        listPathFiles.add((file.getPath() + "/" + file.getName()).replaceAll("[/]+", "/"));
                        //System.out.println("File: " + (file.getPath() + "/" + file.getName()).replaceAll("[/]+", "/"));
                    } else if (subDirectory) {
                        if (!".".equals(file.getName()) && !"..".equals(file.getName())) {
                            //System.out.println("Folder: " + (file.getPath() + "/" + file.getName()).replaceAll("[/]+", "/"));
                            listPathFiles = getListFiles(host, port, user, pass, timeOut, (file.getPath() + "/" + file.getName()).replaceAll("[/]+", "/"), subDirectory);

                        }
                    }

                }

            }

            ftp.quit();

        } catch (FTPException | ParseException | IOException e) {
            System.out.println("Error: " + e.getMessage());
            Logger.getLogger(FTP.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }

        return listPathFiles;
    }


    public static Map download(String host, int port, String user, String pass, int timeOut, String pathDirectorySave, String pathFileDownload) {
        Map rs = new HashMap();
        String dateNow = timeUtils.getDate_Format_001();
        
        boolean status = true;
        String note = "";             
        String pc_download = systemUtils.getHostName();
        String path_download = pathFileDownload;
        String path_save = pathDirectorySave + File.separator + dateNow + File.separator + fileUtils.getName_Pattern(pathFileDownload);
        String file_name = fileUtils.getName_Pattern(path_save);
        String file_type = fileUtils.getExtension(path_save);
        long file_size = 0;
        String start_download = timeUtils.getDateTimeNow_Format_001();
        String end_download = "";
   
        ProFTPClient ftp = initializedFTP(host, port, user, pass, timeOut);
        try {
            if (ftp.connected()) {
                System.out.println("Message: Connect to [" + host + ":" + port + "]");
                System.out.println("pathFileDownload: " + path_download);
                System.out.println("pathDirectorySave: " + path_save);                

                //if (ftp.exists(path_download)) {
                if (ftp.existsDirectory(path_download) || ftp.existsFile(path_download)) {
                    FTPFile ftpFile = ftp.fileDetails(path_download);
                    file_size = ftpFile.size();
                    fileUtils.createDirectory(fileUtils.getParent_Pattern(path_save));
                    
                    //Download
                    if (ftpFile.isFile()) {
                        ftp.get(path_save, path_download);
                    }
                    else if (ftpFile.isDir()) {        
                        ftp.mget(path_save, path_download, "*", true);                        
                    }
                    
                } else {
                    status = false;
                    note = "Error: Not exists [" + path_download + "]";
                }

            }
            
            ftp.quit();

        } catch (FTPException | ParseException | IOException ex) {
            status = false;
            note = "Error: " + ex.getMessage();
            System.out.println("Error: " + ex.getMessage());
            Logger.getLogger(SFTP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        rs.put("status", status);
        rs.put("note", note);
        rs.put("pc_download", pc_download);
        rs.put("path_download", path_download);
        rs.put("path_save", path_save);
        rs.put("file_name", file_name);
        rs.put("file_type", file_type);
        rs.put("file_size", file_size);
        rs.put("start_download", start_download);
        rs.put("end_download", end_download);  

        return rs;
    }    
    


    public static boolean rename(String host, int port, String user, String pass, int timeOut, String oldName, String newName) {

        ProFTPClient ftp = initializedFTP(host, port, user, pass, timeOut);
        try {
            

            if (ftp.connected()) {
                ftp.rename(oldName, newName);
            }

            ftp.quit();

        } catch (FTPException | IOException e) {
            System.out.println("Error: " + e.getMessage());
            Logger.getLogger(FTP.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }

        return true;
    }

    public static boolean delete(String host, int port, String user, String pass, int timeOut, String remoteFile) {

        ProFTPClient sftp = initializedFTP(host, port, user, pass, timeOut);
        try {

            if (sftp.connected()) {
                sftp.delete(remoteFile);
            }

            sftp.quit();

        } catch (FTPException | IOException e) {
            System.out.println("Error: " + e.getMessage());
            Logger.getLogger(FTP.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }

        return true;
    }

}
