/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Implementation.sourcecode.application.AutoExport;

import Implementation.sourcecode.business.BLLCommunication;
import Implementation.sourcecode.configuration.Configuration;
import Implementation.sourcecode.utilities.IdeasUtilities;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author ideas
 */
public class ExecuteExport {
    public static void main(String[] args) throws Exception{        
        try {            
            PropertyConfigurator.configure(Configuration.LOG4J_IDEAS_FILE_MONITOR);
            ExportProcess exportProcess = new ExportProcess();
            final BLLCommunication bllCom = new BLLCommunication();
            int maxPool = Integer.parseInt(Configuration.getInstance().getProperty("threadPool", "10"));
            final String projectName = Configuration.getInstance().getProperty("schemas");
            final int getCardLoopTime = Integer.parseInt(Configuration.getInstance().getProperty("getCardLoopTime", "100"));
            final String IP = IdeasUtilities.getHostAddress();
            final String SQLReset = "DELETE FROM " + projectName + ".assign WHERE username Like '" + IP + "%' AND userid = -1 AND step = -7;";
            //======ShutdownHook=========================================
            Runtime.getRuntime().addShutdownHook(new Thread() {
                @Override
                public void run() {
                    try {
                        bllCom.updateSQL(SQLReset, projectName);                    
                    } catch (Exception e) {
                        IdeasUtilities.logError(e);
                    }

                }
            });
            boolean isSleeping = true;
            boolean isHaveCard;
            int totalCardProcessing = 0;
            bllCom.updateSQL(SQLReset, projectName);
            
            while(true){
                if (exportProcess.poolCounter < maxPool) {
                    isHaveCard = exportProcess.doExport();
                    if(isHaveCard){
                        isSleeping = true;
                    }
                }     
                if (exportProcess.poolCounter > 0) {
                    if(totalCardProcessing != exportProcess.poolCounter){
                        totalCardProcessing = exportProcess.poolCounter;
                        IdeasUtilities.logInfo("Total Card Processing: " + Math.min(maxPool, totalCardProcessing));
                        IdeasUtilities.logInfo("Free Pool: " + Math.max(0, maxPool - totalCardProcessing));
                        IdeasUtilities.logInfo("Waiting Card: " + Math.max(0, totalCardProcessing - maxPool));
                    }
                } else {
                    totalCardProcessing = 0;
                    if (isSleeping) {
                        IdeasUtilities.logInfo("Sleeping...");
                        isSleeping = false;
                    }
                }
                TimeUnit.MILLISECONDS.sleep(getCardLoopTime);                               
            }
        } catch (InterruptedException ex) {
            IdeasUtilities.logError(ex);
        }
    }
}
