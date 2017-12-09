package org.woehlke.tools.desktopcleanup.filter;

import org.woehlke.tools.desktopcleanup.control.MyFilenameFilter;
import org.woehlke.tools.desktopcleanup.control.TargetSubDirs;

import java.io.File;

public class GenericFilter extends MyFilter implements MyFilenameFilter {

    public GenericFilter(TargetSubDirs targetSubDir){
        super(targetSubDir);
    }

    @Override
    public boolean accept(File dir, String name) {
        return super.acceptFileEndings(dir,name);
    }
}
