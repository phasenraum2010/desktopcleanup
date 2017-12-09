package org.woehlke.tools.desktopcleanup.filter;

import org.woehlke.tools.desktopcleanup.control.FilenameFilterTargetSubDir;
import org.woehlke.tools.desktopcleanup.control.TargetSubDirs;

import java.io.File;

public abstract class MyFilter implements FilenameFilterTargetSubDir {

    private final TargetSubDirs targetSubDir;

    protected MyFilter(final TargetSubDirs targetSubDir){
        this.targetSubDir = targetSubDir;
    }

    @Override
    public String getTargetSubDir() {
        return targetSubDir.getDirName();
    }

    protected boolean acceptFileEndings(File dir, String name) {
        File pathname = new File(dir.getAbsolutePath()+File.separator+name);
        if(pathname.isDirectory()){
            return false;
        } else if (!pathname.isFile()){
            return false;
        } else if (pathname.isHidden()){
            return false;
        } else if(!pathname.canWrite()){
            return false;
        } else {
            String fileEndings[] = targetSubDir.getFileEndings();
            if(fileEndings.length>0){
                for(String fileEnding:fileEndings){
                    if(name.endsWith(fileEnding)){
                        return true;
                    }
                }
                return false;
            } else {
                return true;
            }
        }
    }
}
