/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SourceCode.Server.Downloader;

import SourceCode.Configuration.Configuration;
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
public class SGBAutoDownloadImport_Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Properties pros = Configuration.getInstance().getProperty();

        ExecutorService executorMain = Executors.newFixedThreadPool(1);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("doShutdownHook with Lambda");
            executorMain.shutdown();
        }));

        Runnable runnableMain = () -> {
            System.out.println("==========================================");
            DownloadImport di = new DownloadImport(pros);
            List<String> listPathDownload = di.getListDownload();

            if (listPathDownload != null) {
                if (!listPathDownload.isEmpty()) {
                    ExecutorService executorDownload = Executors.newFixedThreadPool(Integer.parseInt(pros.getProperty("Download.Thread").trim()));
                    listPathDownload.stream().forEach((file) -> {
                        Runnable runnableDownload = () -> {
                            Map fileDownload = di.doDownload(file);
                            if ((boolean)fileDownload.get("status")) {
                                if (di.afterCompleteDownload(file)) {
                                    if ((boolean)di.doImport(fileDownload).get("status")) {
                                        
                                    } else {
                                        System.out.println("Error: Import not success!");
                                    }
                                } else {
                                    System.out.println("Error: Can't process(move/rename) after finish download!");
                                }
                            } else {
                                System.out.println("Error: Can't download [" + file + "]");
                            }

                        };
                        executorDownload.execute(runnableDownload);
                    });
                    //-- Waiting finish all Thread in Pool.
                    executorDownload.shutdown();
                    while (!executorDownload.isTerminated()) {
                    }
                    //System.out.println("Finished all threads-----");

                } else {
                    System.out.println("Message: Not file/folder to download.");
                }

            } else {
                System.out.println("Error: Can't get list files to download.");
            }

        };

        ScheduledExecutorService serviceMain = Executors.newSingleThreadScheduledExecutor();
        executorMain.execute((Runnable) serviceMain.scheduleAtFixedRate(runnableMain, 0, Integer.parseInt(pros.getProperty("Download.Loop").trim()), TimeUnit.SECONDS));

    }
}
