/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author bnson
 */
public class fileUtils {

    public static boolean isExists(String path) {
        File f = new File(path);
        return (f.exists());
    }    
    
    public static boolean isExists_Directory(String pathDirectory) {
        File f = new File(pathDirectory);
        return (f.exists() && f.isDirectory());
    }

    public static boolean isExists_File(String pathFile) {
        File f = new File(pathFile);
        return (f.exists() && f.isFile());
    }
    
    public static boolean createDirectory(String pathDirectory) {
        File file = new File(pathDirectory);
        if (file.isDirectory() && file.exists()) {
            return true;
        } 
        return file.mkdirs();
    }
    
    public static String createFile(String pathFile) {
        File file = new File (pathFile);
        try {
            if (file.isFile() && file.exists()) {
                return file.getAbsolutePath();
            }
            if (!file.createNewFile()) {
                return "";
            }
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        
        return file.getAbsolutePath();
    }
    
    public static boolean wirteFile(String pathFile, String data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathFile))) {
            bw.write(data);
            bw.close();
            //System.out.println("Done");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    public static boolean wirteNewFile(String pathFile, String data) {
        try {
            File file = new File (pathFile);
            if (file.isFile() && file.exists()) {
                return true;
            }
            
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                bw.write(data);
                //System.out.println("Done");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
        return true;
    }    
    
    public static int getNumberOfFilesInDirectory(String pathDirectory) {
            List<String> tmpListFiles = getList_PathFile(pathDirectory, true);
            if (tmpListFiles == null || tmpListFiles.isEmpty()) {
                return 0;
            }            
            tmpListFiles.removeIf(s -> s.toLowerCase().endsWith(".db"));    
            return tmpListFiles.size();
            
    }

    public static List<String> getList_PathDirectory(String pathDirectory, boolean subDirectory) {
        List<String> rs = null;

        if (isExists_Directory(pathDirectory)) {
            rs = new ArrayList<>();

            File directory = new File(pathDirectory);
            for (File file : directory.listFiles()) {
                if (file.isDirectory()) {
                    rs.add(file.getAbsolutePath());
                    if (subDirectory) {
                        rs.addAll(getList_PathDirectory(file.getAbsolutePath(), subDirectory));
                    }
                }
            }
            
        }
        
        return rs;
    }
    
    public static List<String> getList_PathFile(String pathDirectory, boolean subDirectory) {
        List<String> rs = null;
        
        if (isExists_Directory(pathDirectory)) {
            rs = new ArrayList<>();
            
            File directory = new File(pathDirectory);
            for (File file : directory.listFiles()) {
                if (file.isFile()) {
                    rs.add(file.getAbsolutePath());
                } else if (subDirectory) {
                    rs.addAll(getList_PathFile(file.getAbsolutePath(), subDirectory));
                }
            }
        }

        return rs;
    }   
    
    public static boolean rename(String source, String dest) {
        return new File(source).renameTo(new File(dest));
    }
    
    public static long getSize_Directory_ApacheCommonsIO(String pathDirectory) {
        long size = FileUtils.sizeOfDirectory(FileUtils.getFile(pathDirectory));
        return size;
    }

    public static String getName_Pattern(String pathFile) {
        Matcher mt = Pattern.compile("[^/\\\\]+$").matcher(pathFile);
        if (mt.find()) { return mt.group(); } 
        return null;        
    } 
    
    public static String getParent_Pattern(String pathFile) {
        Matcher mt = Pattern.compile("[\\w\\W]+[/\\\\]").matcher(pathFile);
        if (mt.find()) { return mt.group().replaceAll("[/\\\\]$", ""); } 
        return null;         
    }
    
    public static boolean move_Directory_ApacheCommonsIO(String source, String dest) {
        try {
            FileUtils.moveDirectory(FileUtils.getFile(source), FileUtils.getFile(dest));
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }   
    
    public static boolean move_File_ApacheCommonsIO(String source, String dest) {
        try {
            FileUtils.moveFile(FileUtils.getFile(source), FileUtils.getFile(dest));
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }    
    
    public static String getExtension(String pathFile) {
        String extension = FilenameUtils.getExtension(pathFile);
        if (extension.isEmpty()) {
            extension = "unknown";
        }
        return extension;
    }      

    public static boolean delete(String source) {
        File delFile = new File(source);
        return FileUtils.deleteQuietly(delFile);
    }
    
    public static boolean copy(String source, String dest) {
        try {
            File sFile = new File(source);
            File dFile = new File(dest);
            if (sFile.isFile()) {
                FileUtils.copyFile(sFile, dFile);
            } else if (sFile.isDirectory()) {
                FileUtils.copyDirectory(sFile, dFile);
            }
        } catch (IOException ex) {
            Logger.getLogger(fileUtils.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    
}
