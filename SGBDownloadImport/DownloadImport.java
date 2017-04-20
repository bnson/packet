/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Downloader;

import SourceCode.Server.utils.sevenZip;
import SourceCode.Server.utils.systemUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author bnson
 */
public class  DownloadImport {

    private final String protocol;

    private final String host;
    private final String port;
    private final String timeOut;
    private final String userName;
    private final String passWord;

    private final String subDirectory;
    private final String afterDone;
    private final String fromDirectory;
    private final String toDirectory;
    private final String toDirectory_Prefix;
    private final String filter_download;
    private final String filter_import;
    private final String classify_import;
    private final String pc_download;
    private final String unpacking_path;
    private final String unpacking_password;

    private String path_download;
    private String path_save;
    private String path_unpacking;
    private String file_name;
    private String file_type;
    private String file_size;
    private String total_files;
    private String start_download;
    private String end_download;
    private boolean status;
    private String error;
    private String note;

    private sevenZip sZip;

    public DownloadImport(Properties pros) {
        this.protocol = pros.getProperty("Download.Protocol").trim().toLowerCase();

        this.host = pros.getProperty("Download.Host").trim();
        this.port = pros.getProperty("Download.Port").trim();
        this.userName = pros.getProperty("Download.Username").trim();
        this.passWord = pros.getProperty("Download.Password").trim();
        this.timeOut = pros.getProperty("Download.TimeOut").trim();
        this.fromDirectory = pros.getProperty("Download.FromDirectory").trim().replaceAll("[\\\\/]+$", "");
        this.toDirectory = pros.getProperty("Download.ToDirectory").trim().replaceAll("[\\\\/]+$", "");        
        this.subDirectory = pros.getProperty("Download.SubDirectory").trim().toLowerCase();
        this.afterDone = pros.getProperty("Download.AfterDone").trim().toLowerCase();

        this.toDirectory_Prefix = pros.getProperty("Download.ToDirectory.Prefix").trim().toLowerCase();
        this.filter_download = ".*\\.(" + pros.getProperty("Download.Filter").trim().toLowerCase().replaceAll("[;]+$", "").replace(";", "|") + ")$";
        this.filter_import = ".*\\.(" + pros.getProperty("Import.Filter").trim().toLowerCase().replaceAll("[;]+$", "").replace(";", "|") + ")$";
        this.classify_import = pros.getProperty("Import.Classify").trim();
        this.unpacking_path = pros.getProperty("Download.UnPacking.ToDirectory").trim().toLowerCase().replaceAll("[\\\\/]+$", "");
        this.unpacking_password = pros.getProperty("Download.UnPacking.Password").trim();

        this.pc_download = systemUtils.getHostName();
        sZip = new sevenZip();
    }

    public List<String> getListDownload() {
        List<String> listPathDownload = new ArrayList();
        //System.out.println("this.fromDirectory: " + this.fromDirectory);
        switch (this.protocol) {
            case "local":
                
                
                break;            
            case "ftp":
                if (this.filter_download.contains("folder")) {
                    listPathDownload = FTP.getListFolders(this.host, Integer.parseInt(this.port), this.userName, this.passWord, Integer.parseInt(this.timeOut), this.fromDirectory, Boolean.parseBoolean(this.subDirectory));
                } else {
                    listPathDownload = FTP.getListFiles(this.host, Integer.parseInt(this.port), this.userName, this.passWord, Integer.parseInt(this.timeOut), this.fromDirectory, Boolean.parseBoolean(this.subDirectory));
                }                
                
                break;
            case "sftp":
                if (this.filter_download.contains("folder")) {
                    listPathDownload = SFTP.getListFolders(this.host, Integer.parseInt(this.port), this.userName, this.passWord, Integer.parseInt(this.timeOut), this.fromDirectory, Boolean.parseBoolean(this.subDirectory));
                } else {
                    listPathDownload = SFTP.getListFiles(this.host, Integer.parseInt(this.port), this.userName, this.passWord, Integer.parseInt(this.timeOut), this.fromDirectory, Boolean.parseBoolean(this.subDirectory));
                }
                
                break;
            default:
                System.out.println("Error: Invalid download protocal!");

        }

        if (!this.filter_download.contains("folder")) {
            listPathDownload.removeIf(s -> !s.toLowerCase().matches(filter_download));
        }
        
        listPathDownload.removeIf(s -> s.toLowerCase().endsWith(".download.done"));
        listPathDownload.removeIf(s -> s.toLowerCase().endsWith(".download.error"));
        
        return listPathDownload;
    }
    
    public Map download(String pathDownload) {
        Map rsMap = null;
        switch (this.protocol) {
            case "local":
                break;              
            case "ftp":
                break;
            case "sftp":
                rsMap = SFTP.download(this.host, Integer.parseInt(this.port), this.userName, this.passWord, Integer.parseInt(this.timeOut), this.toDirectory, pathDownload);
                break;
            default:
                System.out.println("Error: Invalid download protocal!");            
        }
        return rsMap;
    }
    
    public boolean afterCompleteDownload(String remoteFile) {
        boolean rs = false;
        
        switch (this.afterDone) {
            case "rename":
                rs = rename(remoteFile, remoteFile + ".download.done");
                break;
            case "move":
                rs = deleteFile(remoteFile);
                break;
            default:
                System.out.println("Error: Invalid method process file atfer finish download!");                     
        }
        
        return rs;
    }
    
    public boolean importDownloadInfo() {
        boolean rs = false;
        
        return rs;
    }
    
    public boolean deleteFile(String remoteFile) {
        boolean rs = false;
        switch (this.protocol) {
            case "ftp":
                rs = FTP.delete(this.host, Integer.parseInt(this.port), this.userName, this.passWord, Integer.parseInt(this.timeOut), remoteFile);
                break;
            case "sftp":
                rs = SFTP.delete(this.host, Integer.parseInt(this.port), this.userName, this.passWord, Integer.parseInt(this.timeOut), remoteFile);
                break;
            default:
                System.out.println("Error: Invalid download protocal!");            
        }
        return rs;
        
    }    
    
    
    public boolean rename(String oldName, String newName) {
        boolean rs = false;
        switch (this.protocol) {
            case "ftp":
                rs = FTP.rename(this.host, Integer.parseInt(this.port), this.userName, this.passWord, Integer.parseInt(this.timeOut), oldName, newName);
                break;
            case "sftp":
                rs = SFTP.rename(this.host, Integer.parseInt(this.port), this.userName, this.passWord, Integer.parseInt(this.timeOut), oldName, newName);
                break;
            default:
                System.out.println("Error: Invalid download protocal!");            
        }
        return rs;
        
    }

}
