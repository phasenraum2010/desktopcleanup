package org.woehlke.tools.desktopcleanup.filter;

import org.woehlke.tools.desktopcleanup.control.FilenameFilterTargetSubDir;

import java.io.File;
import java.io.FileFilter;

public class FilterOthers implements FileFilter,FilenameFilterTargetSubDir {

    public final static String DIR_OTHER = "Documents"+ File.separator+"other";

    @Override
    public boolean accept(File pathname) {
        if(pathname.isDirectory()){
            return false;
        } else if (!pathname.isFile()){
            return false;
        } else if (pathname.isHidden()){
            return false;
        } else {
            return pathname.canWrite();
        }
    }

    @Override
    public String getTargetSubDir() {
        return DIR_OTHER;
    }

}
