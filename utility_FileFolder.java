/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bnson.comrobot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author bnson
 */
public class utility_FileFolder {

    public utility_FileFolder() {

    }

    public void createFolder(String folderPath) {
        Path path = Paths.get(folderPath);
        //if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                //fail to create directory

            }
        }
    }

    public String readFileToString(String filename) {
        String records ="";
        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    records += line;
                    System.out.println("line: " + line);
                }
            }
            return records;
        } catch (IOException e) {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            return null;
        }
    }    
    
    
    public List<String> readFile(String filename) {
        List<String> records = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);
                System.out.println("line: " + line);
            }
            reader.close();
            return records;
        } catch (Exception e) {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return null;
        }
    }

    public void wirteFileAppend(String dataStr, String folderPath, String fileName) {
        BufferedWriter fbw = null;
        OutputStreamWriter writer = null;
        try {
            String linkFile = folderPath + "\\" + fileName.replaceAll("[\\\\/*?\"<>|:]", "");
            System.out.println("wirteFile: " + linkFile);

            writer = new OutputStreamWriter(new FileOutputStream(linkFile, true), "UTF-8");

            fbw = new BufferedWriter(writer);
            fbw.write(dataStr);
            fbw.newLine();

        } catch (IOException ex) {
            Logger.getLogger(utility_FileFolder.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fbw != null) {
                    fbw.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(utility_FileFolder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }    
    
    public void wirteFile(String dataStr, String folderPath, String fileName) {
        BufferedWriter fbw = null;
        OutputStreamWriter writer = null;
        try {
            String linkFile = folderPath + "\\" + fileName.replaceAll("[\\\\/*?\"<>|:]", "") + ".txt";
            System.out.println("wirteFile: " + linkFile);

            writer = new OutputStreamWriter(new FileOutputStream(linkFile, false), "UTF-8");

            fbw = new BufferedWriter(writer);
            fbw.write(dataStr);
            fbw.newLine();

        } catch (IOException ex) {
            Logger.getLogger(utility_FileFolder.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (fbw != null) {
                    fbw.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(utility_FileFolder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public ArrayList<String> getNameOfFilesInFolder(String pathFolder) {

        File folder = new File(pathFolder);
        List<File> listOfFiles = Arrays.asList(folder.listFiles());
        ArrayList<String> rs = new ArrayList();

        //adaptor for comparing files
        Collections.sort(listOfFiles, new Comparator<File>() {
            private final Comparator<String> NATURAL_SORT = new WindowsExplorerComparator();

            @Override
            @SuppressWarnings("empty-statement")
            public int compare(File o1, File o2) {;
                return NATURAL_SORT.compare(o1.getName(), o2.getName());
            }
        });

        listOfFiles.stream().filter((listOfFile) -> (listOfFile.isFile())).forEachOrdered((listOfFile) -> {
            rs.add(listOfFile.getName());
            //System.out.println("File: " + listOfFile.getName());
        });

        return rs;
    }

    public ArrayList<String> getPathOfFilesInFolder(String pathFolder) {

        File folder = new File(pathFolder);
        List<File> listOfFiles = Arrays.asList(folder.listFiles());
        ArrayList<String> rs = new ArrayList();

        //adaptor for comparing files
        Collections.sort(listOfFiles, new Comparator<File>() {
            private final Comparator<String> NATURAL_SORT = new WindowsExplorerComparator();

            @Override
            @SuppressWarnings("empty-statement")
            public int compare(File o1, File o2) {;
                return NATURAL_SORT.compare(o1.getName(), o2.getName());
            }
        });

        listOfFiles.stream().filter((listOfFile) -> (listOfFile.isFile())).forEachOrdered((listOfFile) -> {
            rs.add(listOfFile.getAbsolutePath());
            System.out.println("File " + listOfFile.getAbsolutePath());
        });

        return rs;
    }

}
