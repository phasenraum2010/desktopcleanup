package org.woehlke.tools.desktopcleanup.control;

import java.io.File;

public enum TargetSubDirs {

    AUDIO("Music","mp3","MP3","m4b","m4a","pls"),
    VIDEO("Movies","mp4","MP4","mov","MOV","m4v","flv"),
    PDF("Documents"+ File.separator+"pdf","pdf","PDF"),
    ISO("Documents"+ File.separator+"iso","iso"),
    TXT("Documents"+ File.separator+"txt","txt","TXT"),
    CODE("Documents"+ File.separator+"code","swift","yml","sh","properties","roo","log","tcl","sql","php","htm","html","php","java","xml","pom","class","jar","csv","less","css","js"),
    ARCHIVE("Documents"+ File.separator+"archive","zip","ZIP","tar.gz","gz","tgz","rar","bz2"),
    DMG("Documents"+ File.separator+"installer","dmg","exe"),
    CALENDAR("Documents"+ File.separator+"calendar","ics"),
    OFFICE("Documents"+ File.separator+"office","docx","doc","DOC","xlsx","xls","XLS","pptx","ppt","PPT","odt","ODT","dvi"),
    FONTS("Documents"+ File.separator+"fonts","ttf","woff","eot"),
    PICTURE("Pictures"+ File.separator+"foto","jpg","JPG","gif","GIF","jpeg","JPEG","png","PNG","xcf");

    private String dirName;
    private String fileEndings[];

    public String getDirName() {
        return dirName;
    }

    public String[] getFileEndings() {
        return fileEndings;
    }

    TargetSubDirs(String dirName, String... fileEndings){
        this.dirName = dirName;
        this.fileEndings=fileEndings;
    }
}
