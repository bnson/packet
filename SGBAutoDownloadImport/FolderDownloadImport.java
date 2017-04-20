/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Downloader;

import SourceCode.Server.utils.systemUtils;
import SourceCode.Server.utils.sevenZip;
import SourceCode.Server.utils.fileUtils;
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
public class FolderDownloadImport {

    private final String protocol;
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
    
    public FolderDownloadImport(Properties pros) {
        this.protocol = pros.getProperty("Download.Protocol").trim().toLowerCase();
        this.subDirectory = pros.getProperty("Download.SubDirectory").trim().toLowerCase();
        this.afterDone = pros.getProperty("Download.AfterDone").trim().toLowerCase();
        this.fromDirectory = pros.getProperty("Download.FromDirectory").trim().toLowerCase().replaceAll("[\\\\/]+$", "");
        this.toDirectory = pros.getProperty("Download.ToDirectory").trim().toLowerCase().replaceAll("[\\\\/]+$", "");
        this.toDirectory_Prefix = pros.getProperty("Download.ToDirectory.Prefix").trim().toLowerCase();
        this.filter_download = ".*\\.(" + pros.getProperty("Download.Filter").trim().toLowerCase().replaceAll("[;]+$", "").replace(";", "|") + ")$";
        this.filter_import = ".*\\.(" + pros.getProperty("Import.Filter").trim().toLowerCase().replaceAll("[;]+$", "").replace(";", "|") + ")$";
        this.classify_import = pros.getProperty("Import.Classify").trim();
        this.unpacking_path = pros.getProperty("Download.UnPacking.ToDirectory").trim().toLowerCase().replaceAll("[\\\\/]+$", "");
        this.unpacking_password = pros.getProperty("Download.UnPacking.Password").trim();
                
        this.pc_download = systemUtils.getHostName();
        
        if (!checkSetting()) {
            System.out.println("Properties Setting is error!");
            System.out.println("Application will be exit!");
            systemUtils.pressAnyKeyToContinue();
            System.exit(0);
        }
        
        sZip = new sevenZip();
    }

    private Boolean checkSetting() {
        if (protocol.isEmpty() || protocol == null || !protocol.equalsIgnoreCase("folder")) {
            return false;
        }
        if (subDirectory.isEmpty() || subDirectory == null) {
            return false;
        }
        if (afterDone.isEmpty() || afterDone == null) {
            return false;
        }
        if (filter_download.isEmpty() || filter_download == null) {
            return false;
        }
        if (fromDirectory.isEmpty() || fromDirectory == null || !fileUtils.isExists_Directory(fromDirectory)) {
            return false;
        }
        if (toDirectory.isEmpty() || toDirectory == null || !fileUtils.isExists_Directory(toDirectory)) {
            return false;
        }
        if (unpacking_path.isEmpty() || unpacking_path == null || !fileUtils.isExists_Directory(unpacking_path)) {
            return false;
        } 
        if (unpacking_password == null) {
            return false;
        }        
        
        if (fromDirectory.equalsIgnoreCase(toDirectory)) {
            return false;
        }
        return true;
    }

    private void setErrorInfor(boolean status, String error, String note) {
        this.status = status;
        this.error = error;
        this.note = note;
        System.out.println(note);
    }

    public List<String> getList() {
        List<String> listPath;

        if (this.filter_download.contains("folder")) {
            listPath = fileUtils.getList_PathDirectory(fromDirectory, false);
        } else {
            listPath = fileUtils.getList_PathFile(fromDirectory, subDirectory.equalsIgnoreCase("true"));
            listPath.removeIf(s -> !s.toLowerCase().matches(filter_download));
        }

        listPath.removeIf(s -> s.toLowerCase().endsWith(".error"));

        return listPath;
    }

    public Map doDownload(String pathDownload) {
        Map rs;

        if (this.filter_download.contains("folder")) {
            rs = download_Folder(pathDownload);
        } else {
            rs = download_FileCompress(pathDownload);
        }

        if ("false".equals(rs.get("status").toString())) {
            if ("download".equals(rs.get("error").toString())) {
                fileUtils.rename(rs.get("path_download").toString(), rs.get("path_download").toString() + ".error");
            }
            if ("unpacking".equals(rs.get("error").toString())) {
                fileUtils.rename(rs.get("path_download").toString(), rs.get("path_download").toString() + ".error");                
                fileUtils.rename(rs.get("path_save").toString(), rs.get("path_save").toString() + ".error");
                fileUtils.rename(rs.get("path_unpacking").toString(), rs.get("path_unpacking").toString() + ".error");
            }
        }        
        
        return rs;
    }
    
    public List<Map> doImport(int idDownload, String pathImport) {
        List<Map> rs = new ArrayList<>();
        
        List<String> listFiles = fileUtils.getList_PathFile(pathImport, true);
        
        if (listFiles == null || listFiles.isEmpty()) {
            return null;
        }
        
        listFiles.removeIf(s -> !s.toLowerCase().matches(filter_import));
        listFiles.removeIf(s -> s.toLowerCase().endsWith(".error"));

        if (listFiles.isEmpty()) {
            return null;
        }        
        
        if (listFiles.isEmpty()) {

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
    

    public Map download_Folder(String pathFolder) {
        String dateNow = timeUtils.getDate_Format_001();
        Map rs = new HashMap();
        
        path_download = pathFolder;
        path_save = toDirectory + File.separator + dateNow + File.separator;
        path_unpacking = "";
        file_name = "";
        file_type = "folder";
        file_size = "0";
        total_files = "0";
        start_download = timeUtils.getDateTimeNow_Format_001();
        end_download = "";
        status = true;
        note = "";

        File fDownload = new File(path_download);
        
        //Kiểm tra sự tồn tại của folder download.
        if (!fDownload.exists()) {
            setErrorInfor(false, "download", "Error: Folder download isn't exists!");
        }
        //Kiểm tra folder download là folder.
        if (!fDownload.isDirectory() && status) {
            setErrorInfor(false, "download", "Error: Folder download isn't directory!");
        }
        
        //Folder download ok.
        if (status) {
            file_name = fDownload.getName();
            path_save = path_save + file_name;
            file_size = Long.toString(fileUtils.getSize_Directory_ApacheCommonsIO(fDownload.getAbsolutePath()));
            total_files = Integer.toString(fileUtils.getNumberOfFilesInDirectory(fDownload.getAbsolutePath()));
            
            //Kiểm tra tồn tại của folder save.
            if (fileUtils.isExists_Directory(path_save)) {
                setErrorInfor(false, "download", "Error: Folder save is exists!");
            }            
            
        }

        //Folder download is ok.
        //Folder save is ok.
        //Start download.
        if (status) {
            if ("move".equalsIgnoreCase(afterDone)) {
                if (!fileUtils.move_Directory_ApacheCommonsIO(path_download, path_save)) {
                    setErrorInfor(false, "download","Error: Can't move directory!");
                }
            }
        }

        path_unpacking = path_save;
        end_download = timeUtils.getDateTimeNow_Format_001();
        rs.put("pc_download", pc_download);
        rs.put("path_download", path_download);
        rs.put("path_save", path_save);
        rs.put("path_unpacking", path_unpacking);
        rs.put("file_name", file_name);
        rs.put("file_type", file_type);
        rs.put("file_size", file_size);
        rs.put("total_files", total_files);
        rs.put("start_download", start_download);
        rs.put("end_download", end_download);
        rs.put("status", status);
        rs.put("note", note);

        return rs;

    }

    public Map download_FileCompress(String pathFile) {
        String dateNow = timeUtils.getDate_Format_001();
        Map rs = new HashMap();

        path_download  = pathFile;
        path_save      = this.toDirectory + File.separator + dateNow + File.separator;
        path_unpacking = this.unpacking_path + File.separator + dateNow + File.separator;
        file_name      = "";
        file_type      = fileUtils.getExtension(path_download);
        file_size      = "0";
        total_files    = "0";
        start_download = timeUtils.getDateTimeNow_Format_001();
        end_download   = "";
        status         = true;
        note           = "";

        File fDownload = new File(path_download);

        //Kiểm tra tồn tại của tập tin download.
        if (!fDownload.exists()) {
            setErrorInfor(false, "download", "Error: File download isn't exists!");
        }
        //Kiểm tra tập tin download là tập tin.
        if (!fDownload.isFile() && status) {
            setErrorInfor(false, "download", "Error: File download isn't file!");
        }
        
        //File Download is ok.
        if (status) {
            file_name = fDownload.getName();
            path_save = path_save + fDownload.getParent().replace(this.fromDirectory, "").replaceAll("^[\\\\/]+", "").replaceAll("[\\\\/]+$", "");
            path_save = path_save.replaceAll("[\\\\/]+$", "");
            if (fileUtils.createDirectory(path_save)) {
                path_save = path_save + File.separator + file_name;
                file_size = Long.toString(fDownload.length());

                //Kiểm tra tồn tại của tập tin save.
                if (fileUtils.isExists_File(path_save)) {
                    setErrorInfor(false, "download", "Error: File download is exists in import folder!");
                }                
                
            } else {
                setErrorInfor(false, "download", "Error: Can't create new folder date in import folder!");
            }

        }

        //File download is ok.
        //Create folder date is ok.
        //File save is ok.
        //Start download & unpacking.
        if (status) {
            if ("move".equalsIgnoreCase(afterDone)) {
                if (!fileUtils.move_File_ApacheCommonsIO(path_download, path_save)) {
                    setErrorInfor(false, "download", "Error: Can't move file/folder form download folder to import folder!");
                } else {
                    path_unpacking = path_unpacking + fDownload.getParent().replace(this.fromDirectory, "").replaceAll("^[\\\\/]+", "").replaceAll("[\\\\/]+$", "");
                    path_unpacking = path_unpacking.replaceAll("[\\\\/]+$", "");                                                
                    path_unpacking = path_unpacking + File.separator + file_name.replaceFirst(Pattern.quote("." + file_type) + "$", "");
                    if (!sZip.extractFiles(path_save, path_unpacking, this.unpacking_password)) {
                        setErrorInfor(false, "unpacking", "Error: Can't unpacking compressed file!");
                    }
                }
            }
        }
        
        total_files = Integer.toString(fileUtils.getNumberOfFilesInDirectory(path_unpacking));
        end_download = timeUtils.getDateTimeNow_Format_001();
        rs.put("pc_download", pc_download);
        rs.put("path_download", path_download);
        rs.put("path_save", path_save);
        rs.put("path_unpacking", path_unpacking);
        rs.put("file_name", file_name);
        rs.put("file_type", file_type);
        rs.put("file_size", file_size);
        rs.put("total_files", total_files);
        rs.put("start_download", start_download);
        rs.put("end_download", end_download);
        rs.put("status", Boolean.toString(status));
        rs.put("error", error);
        rs.put("note", note);

        return rs;

    }


    
}
    
