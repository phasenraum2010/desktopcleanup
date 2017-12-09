package org.woehlke.tools.desktopcleanup;

import org.woehlke.tools.desktopcleanup.control.DesktopCleanupController;

public class DesktopCleanup {

    private static final String srcDir = "/Users/tw/Downloads";

    private static final boolean workDir = true;
    private static final String targetDir = "/Users/tw/";

    //private static final String targetDir = "/Users/tw/Desktop/TEST_SORT";

    public static void main(String args[]){
        DesktopCleanupController controller = new DesktopCleanupController(srcDir,workDir,targetDir);
        controller.run();
    }
}
