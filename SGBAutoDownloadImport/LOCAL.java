/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Downloader;

import SourceCode.Server.utils.fileUtils;
import SourceCode.Server.utils.systemUtils;
import SourceCode.Server.utils.timeUtils;
import com.enterprisedt.net.ftp.FTPException;
import com.enterprisedt.net.ftp.FTPFile;
import com.enterprisedt.net.ftp.pro.ProFTPClient;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bnson
 */
public class LOCAL {

    public static List<String> getListFolders(String directoryWork, boolean subDirectory) {
        List<String> listPathFolders;
        listPathFolders = fileUtils.getList_PathDirectory(directoryWork, subDirectory);
        return listPathFolders;
    }    
    
    public static List<String> getListFiles(String directoryWork, boolean subDirectory) {
        List<String> listPathFolders;
        listPathFolders = fileUtils.getList_PathFile(directoryWork, subDirectory);
        return listPathFolders;
    }   
    
    public static boolean rename(String oldName, String newName) {    
        return fileUtils.rename(oldName, newName);
    }
    
    public static boolean delete(String path) {    
        return fileUtils.delete(path);
    }    
    
    public static Map download(String pathDirectorySave, String pathFileDownload) {
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
   
        File fDownload = new File(path_download);
        
        if (fDownload.exists()) {
            file_size = fDownload.length();
            if (fileUtils.createDirectory(fileUtils.getParent_Pattern(path_save))) {
                fileUtils.copy(path_download, path_save);
            }
        } else {
            status = false;
            note = "Error: Not exists [" + path_download + "]";            
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
        
    
}
