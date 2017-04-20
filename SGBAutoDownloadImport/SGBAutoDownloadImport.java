/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Downloader;

import SourceCode.Configuration.Configuration;
import SourceCode.Server.utils.fileUtils;
import SourceCode.Server.utils.mail;
import SourceCode.Server.utils.systemUtils;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author bnson
 */
public class SGBAutoDownloadImport {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Properties pros = Configuration.getInstance().getProperty();
        String mailSubject = pros.getProperty("mail.smtp.subject").trim().replaceAll("[ ]+", "");
        
        ExecutorService executor = Executors.newFixedThreadPool(1000);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("doShutdownHook with Lambda");
            executor.shutdown();
        }));

        FolderDownloadImport fdi = new FolderDownloadImport(pros);

        Runnable runnable = () -> {
            // task to run goes here
            System.out.println("============================================");
            
            System.out.println("Check download...");
            fdi.getList().forEach((path) -> {
                System.out.println("--------------------------------------------");
                System.out.println("Download: " + path);
                Map dMap = fdi.doDownload(path);

                if (!"true".equals(dMap.get("status").toString())) {
                    mail.send(pros, 
                            mailSubject + " Error!", 
                            dMap.toString(),
                            true);
                } else {
                    System.out.println("Message: Download is succeed.");

                    PGFactory pgf = new PGFactory(pros);
                    int idDownload = pgf.callFunction_InsertTo_Download_Info(dMap);
                    //System.out.println("idDownload: " + idDownload);

                    switch (idDownload) {
                        case -1: 
                            //System.out.println("Error: File/Folder download is exists in the download table!");
                            //dMap.put("status", false);
                            //dMap.put("error", "Error: File/Folder download is exists in the download table!");
                            rename_WhenError(dMap);
                            setErrorInfor(dMap, false, "import", "Error: File/Folder download is exists in the download table!");
                            mail.send(pros,
                                    mailSubject + " Error!",
                                    dMap.toString(),
                                    true);
                            break;
                        case 0:
                            //System.out.println("Error: Can't insert to download table!");
                            //dMap.put("status", false);
                            //dMap.put("error", "Error: Can't insert data to download table!");
                            rename_WhenError(dMap);       
                            setErrorInfor(dMap, false, "import", "Error: Import file/folder to download table is failed!");
                            mail.send(pros,
                                    mailSubject + " Error!",
                                    dMap.toString(),
                                    true);
                            
                            System.out.println("Application will be exit!");
                            systemUtils.pressAnyKeyToContinue();
                            System.exit(0);             
                            break;
                        default:
                            System.out.println("Message: Import file/folder to download table is success!");
                            List<Map> importList = fdi.doImport(idDownload, dMap.get("path_unpacking").toString());
                            
                            if (importList == null) {
                                //System.out.println("Error: Folder download is empty!");
                                rename_WhenError(dMap);
                                setErrorInfor(dMap, false, "import", "Error: File/Folder download is empty!");
                                mail.send(pros,
                                        mailSubject + " Error!",
                                        dMap.toString(),
                                        true);

                            } else {
                                int statusImport = pgf.callFunction_InsertTo_Management(importList);
                                switch (statusImport) {
                                    case -1:
                                        rename_WhenError(dMap);
                                        setErrorInfor(dMap, false, "import", "Error: File/Folder download is empty!");
                                        mail.send(pros,
                                                mailSubject + " Error!",
                                                dMap.toString(),
                                                true);
                                        return;
                                    case 0:
                                        rename_WhenError(dMap);
                                        setErrorInfor(dMap, false, "import", "Error: Can't insert to management table!");
                                        mail.send(pros,
                                                mailSubject + " Error!",
                                                dMap.toString(),
                                                true);

                                        System.out.println("Application will be exit!");
                                        systemUtils.pressAnyKeyToContinue();
                                        System.exit(0);
                                        break;
                                    default:
                                        System.out.println("Message: Import File/Folder to management table is success!");
                                        break;
                                }                                
                                
                                mail.send(pros, mailSubject + " Successful!", dMap.toString(), true);
                                
                            }

                            break;
                            
                    }

                }
            });
        }; 
        
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        executor.execute((Runnable) service.scheduleAtFixedRate(runnable, 0,  Integer.parseInt(pros.getProperty("Download.Loop").trim()), TimeUnit.SECONDS));
        
    }
    
    private static void rename_WhenError(Map iMap) {
        fileUtils.rename(iMap.get("path_save").toString(), iMap.get("path_save").toString() + ".error");
        
        if (iMap.get("file_type").toString().toLowerCase().matches("(zip|rar)")) {
            fileUtils.rename(iMap.get("path_unpacking").toString(), iMap.get("path_unpacking").toString() + ".error");            
        } 
    }
    
    private static Map setErrorInfor(Map iMap, boolean status, String error, String note) {
        iMap.put("status", status);
        iMap.put("error", error);        
        iMap.put("note", note);
        System.out.println(note);
        return iMap;
    }

}
