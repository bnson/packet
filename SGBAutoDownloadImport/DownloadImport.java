/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Downloader;

import SourceCode.Server.utils.fileUtils;
import SourceCode.Server.utils.sevenZip;
import SourceCode.Server.utils.systemUtils;
import SourceCode.Server.utils.timeUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

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

    private final sevenZip sZip;
    private final PGSQL pgf;
    
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
        pgf = new PGSQL(pros);        
    }

    public List<String> getListDownload() {
        List<String> listPathDownload = new ArrayList();
        boolean isDownloadFolder = this.filter_download.contains("folder");
        
        switch (this.protocol) {
            case "local":
                if (isDownloadFolder) {
                    listPathDownload = LOCAL.getListFolders(this.fromDirectory, Boolean.parseBoolean(this.subDirectory));
                } else {
                    listPathDownload = LOCAL.getListFiles(this.fromDirectory, Boolean.parseBoolean(this.subDirectory));
                }                
                break;            
            case "ftp":
                if (isDownloadFolder) {
                    listPathDownload = FTP.getListFolders(this.host, Integer.parseInt(this.port), this.userName, this.passWord, Integer.parseInt(this.timeOut), this.fromDirectory, Boolean.parseBoolean(this.subDirectory));
                } else {
                    listPathDownload = FTP.getListFiles(this.host, Integer.parseInt(this.port), this.userName, this.passWord, Integer.parseInt(this.timeOut), this.fromDirectory, Boolean.parseBoolean(this.subDirectory));
                }                
                break;
            case "sftp":
                if (isDownloadFolder) {
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
    
    public Map doImport(Map data) {
        if ("zip;rar".contains((String)data.get("file_type"))) {
            System.out.println("--: " + (String)data.get("path_save"));
            path_unpacking = unpacking_path
                    + File.separator
                    + fileUtils.getName_Pattern(fileUtils.getParent_Pattern((String)data.get("path_save")))
                    + File.separator
                    + ((String)data.get("file_name")).replaceAll(Pattern.quote("." + (String)data.get("file_type")) + "$", "");
            System.out.println("path_unpacking: " + path_unpacking);
            if (sZip.extractFiles((String)data.get("path_save"), path_unpacking, this.unpacking_password)) {
                this.total_files = Integer.toString(fileUtils.getNumberOfFilesInDirectory(path_unpacking));
            } else {
                data.put("status", false);
                data.put("note", "Error: Can't not extrac file [" + (String)data.get("path_save") + "]");
            }            
        } else {
            path_unpacking = data.get("path_save").toString();
        }
        
        data.put("path_unpacking", path_unpacking);
        data.put("total_files", total_files);

        int idDownload = pgf.callFunction_InsertTo_Download_Info(data);
        System.out.println("idDownload: " + idDownload);        
        switch (idDownload) {
            case -1:
                data.put("status", false);
                data.put("note", "Error: File/Folder [" + (String)data.get("path_save") + "]  the download table!");                
                break;
            case 0:
                data.put("status", false);
                data.put("note", "Error: Import file/folder [" + (String)data.get("path_save") + "] is exists in the download table!");                
                break;
            default:
                List<Map> importList = getListFilesToImport(idDownload, data.get("path_unpacking").toString());
                
                if (importList == null) {
                    data.put("status", false);
                    data.put("note", "Error: File/Folder [" + (String)data.get("path_save") + "] is empty!");                                    
                } else {
                    int statusImport = pgf.callFunction_InsertTo_Management(importList);
                    switch (statusImport) {
                        case -1:
                            data.put("status", false);
                            data.put("note", "Error: Import file/folder [" + (String)data.get("path_save") + "] to management table is failed!");
                            break;
                        case 0:
                            data.put("status", false);
                            data.put("note", "Error: Import file/folder [" + (String)data.get("path_save") + "] to management table is failed!");                            
                            break;
                        default:
                            data.put("note", "Message: Import file/folder [" + (String)data.get("path_save") + "] to management table is success!");
                    }
                }
        }
        
        
        return data;
    }
    
    private List<Map> getListFilesToImport(int idDownload, String pathFolder) {
        List<Map> rs = new ArrayList<>();
        
        List<String> listFiles = fileUtils.getList_PathFile(pathFolder, true);
        
        if (listFiles == null || listFiles.isEmpty()) {
            return null;
        }
        
        listFiles.removeIf(s -> !s.toLowerCase().matches(filter_import));
        listFiles.removeIf(s -> s.toLowerCase().endsWith(".download.done"));
        listFiles.removeIf(s -> s.toLowerCase().endsWith(".download.error"));

        if (listFiles.isEmpty()) {
            return null;
        }        
        
        listFiles.stream().map((path) -> {
            Map iMap = new HashMap();
            iMap.put("download_id", Integer.toString(idDownload));
            iMap.put("form_id", this.classify_import);
            iMap.put("filepath", "/" + path.replaceFirst("^" + Pattern.quote(toDirectory_Prefix), "")
                                        .replace("\\", "/")
                                        .replaceFirst(fileUtils.getName_Pattern(path) + "$", "")
                                        .replaceFirst("/$", ""));
            iMap.put("filename", fileUtils.getName_Pattern(path));
            return iMap;            
        }).forEachOrdered((iMap) -> {
            rs.add(iMap);
        });
  
        return rs;        
    }    
    
    
    public Map doDownload(String pathDownload) {
        Map rsMap = null;
        switch (this.protocol) {
            case "local":
                rsMap = LOCAL.download(this.toDirectory, pathDownload);
                break;              
            case "ftp":
                rsMap = FTP.download(this.host, Integer.parseInt(this.port), this.userName, this.passWord, Integer.parseInt(this.timeOut), this.toDirectory, pathDownload);
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
                rs = delete(remoteFile);
                break;
            default:
                System.out.println("Error: Invalid method process file atfer finish download!");                     
        }
        
        return rs;
    }
    
    public boolean delete(String remoteFile) {
        boolean rs = false;
        switch (this.protocol) {
            case "local":
                rs = LOCAL.delete(remoteFile);
                break;             
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
            case "local":
                rs = LOCAL.rename(oldName, newName);
                break;            
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