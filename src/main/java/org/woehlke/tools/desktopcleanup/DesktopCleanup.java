package org.woehlke.tools.desktopcleanup;

import org.woehlke.tools.desktopcleanup.control.DesktopCleanupController;

public class DesktopCleanup {

    //private static final String srcDir = "/Users/tw/Downloads";

    //private static final String targetDir = "/Users/tw/";

    private static final String srcDir = "/Volumes/WD_2TB_Elements/INBOX";

    private static final String targetDir = "/Volumes/WD_2TB_Elements/archiv";

    private static final boolean workDir = true;


    //private static final String targetDir = "/Users/tw/Desktop/TEST_SORT";

    public static void main(String args[]){
        DesktopCleanupController controller = new DesktopCleanupController(srcDir,workDir,targetDir);
        controller.run();
    }
}
