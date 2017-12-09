package org.woehlke.tools.desktopcleanup.control;

import org.woehlke.tools.desktopcleanup.filter.FilterBildschirmfoto;
import org.woehlke.tools.desktopcleanup.filter.FilterOthers;
import org.woehlke.tools.desktopcleanup.filter.GenericFilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;
import static org.woehlke.tools.desktopcleanup.filter.FilterBildschirmfoto.DIR_BILDSCHIRMFOTO;
import static org.woehlke.tools.desktopcleanup.filter.FilterOthers.DIR_OTHER;


public class DesktopCleanupController implements Runnable {

    private final String srcDir;
    private final boolean workDir;
    private final String targetDir;

    public DesktopCleanupController(String srcDir,boolean workDir, String targetDir){
        this.srcDir = srcDir;
        this.workDir = workDir;
        this.targetDir = targetDir;
    }

    @Override
    public void run() {
        File srcDirectory = getSrcDirectory();
        File myTargetDirectory = getTargetDirectory();
        if(srcDirectory != null && myTargetDirectory != null) {
            if (workDir) {
                File myWorkDirectory = getWorkDirectory();
                if(myWorkDirectory != null){
                    run3(srcDirectory, myWorkDirectory, myTargetDirectory);
                } else {
                    run2(srcDirectory, myTargetDirectory);
                }
            } else {
                run2(srcDirectory, myTargetDirectory);
            }
        }
    }

    private void run2(File srcDirectory, File myTargetDirectory){
        moveAllBildschirmfoto(srcDirectory, myTargetDirectory);
        sortAllBildschirmfoto(myTargetDirectory,myTargetDirectory);
        moveAllOtherFiles(srcDirectory, myTargetDirectory);
        sortAllOtherFiles(myTargetDirectory,myTargetDirectory);
    }

    private void run3(File srcDirectory, File myWorkDirectory, File myTargetDirectory){
        moveAllBildschirmfoto(srcDirectory, myWorkDirectory);
        sortAllBildschirmfoto(myWorkDirectory,myTargetDirectory);
        moveAllOtherFiles(srcDirectory, myWorkDirectory);
        sortAllOtherFiles(myWorkDirectory,myTargetDirectory);
    }

    private File getSrcDirectory(){
        File srcDirFile = new File(srcDir);
        if(srcDirFile.exists() && srcDirFile.isDirectory() && srcDirFile.canWrite()){
            return srcDirFile;
        } else {
            if(!srcDirFile.exists()){
                FileNotFoundException f = new FileNotFoundException("srcDir does not exist"+srcDir);
                throw new RuntimeException("cannot get srcDir "+srcDir,f);
            }
            if(!srcDirFile.isDirectory()){
                throw new RuntimeException("srcDir is not an Directory "+srcDir);
            }
            if(!srcDirFile.canWrite()){
                throw new RuntimeException("srcDir is not writeable "+srcDir);
            }
            return null;
        }
    }

    private File getTargetDirectory(){
        File targetDirFile = new File(targetDir);
        targetDirFile.mkdirs();
        if(targetDirFile.exists() && targetDirFile.isDirectory() && targetDirFile.canWrite()){
            return targetDirFile;
        } else {
            if(!targetDirFile.exists()){
                FileNotFoundException f = new FileNotFoundException("targetDir does not exist"+targetDir);
                throw new RuntimeException("cannot get targetDir "+targetDir,f);
            }
            if(!targetDirFile.isDirectory()){
                throw new RuntimeException("targetDir is not an Directory "+targetDir);
            }
            if(!targetDirFile.canWrite()){
                throw new RuntimeException("targetDir is not writeable "+targetDir);
            }
            return null;
        }
    }

    private File getWorkDirectory(){
        File parentTargetDir = new File(targetDir);
        parentTargetDir.mkdirs();
        if(parentTargetDir.exists() && parentTargetDir.isDirectory() && parentTargetDir.canWrite()){
            String timeFormat = getDateTimeString();
            String nameWorkDirectory = parentTargetDir.getAbsolutePath() + File.separator + "cleanup-" + timeFormat;
            File myWorkDirectory = new File(nameWorkDirectory);
            myWorkDirectory.mkdirs();
            if(myWorkDirectory.exists() && myWorkDirectory.isDirectory() && myWorkDirectory.canWrite()){
                return myWorkDirectory;
            } else {
                if(!myWorkDirectory.exists()){
                    FileNotFoundException f = new FileNotFoundException("myWorkDirectory does not exist"+nameWorkDirectory);
                    throw new RuntimeException("cannot get myWorkDirectory "+nameWorkDirectory,f);
                }
                if(!myWorkDirectory.isDirectory()){
                    throw new RuntimeException("myWorkDirectory is not an Directory "+nameWorkDirectory);
                }
                if(!myWorkDirectory.canWrite()){
                    throw new RuntimeException("myWorkDirectory is not writeable "+nameWorkDirectory);
                }
                return null;
            }
        }
        return null;
    }
    private String getDateTimeString(){
        return LocalDateTime.now().format(ISO_LOCAL_DATE_TIME);
    }

    private String getDateString(long lastModified){
        long epochSecond = lastModified / 1000;
        long nanoOfSecondLong = (lastModified % 1000) * 1000;
        int nanoOfSecond = 0;
        if(nanoOfSecondLong < Integer.MAX_VALUE){
            nanoOfSecond = (int) nanoOfSecondLong;
        }
        return LocalDateTime.ofEpochSecond(epochSecond,nanoOfSecond,ZoneOffset.UTC).format(ISO_LOCAL_DATE);
    }

    private void moveAllBildschirmfoto( File srcDirectory , File myTargetDirectory){
        System.out.println(myTargetDirectory.getAbsolutePath());
        FilenameFilter filter = new FilterBildschirmfoto();
        File[] filesBildschirmfotoSrc = srcDirectory.listFiles(filter);
        for(File myBildschirmfoto:filesBildschirmfotoSrc){
            String filenameTarget = myTargetDirectory.getAbsolutePath() + File.separator + myBildschirmfoto.getName();
            File fileTarget = new File(filenameTarget);
            myBildschirmfoto.renameTo(fileTarget);
            System.out.println("moveAllBildschirmfoto: "+fileTarget.getAbsolutePath());
        }
    }

    private void sortAllBildschirmfoto(File myTargetDirectory, File myWorkDirectory){
        FilenameFilter filter = new FilterBildschirmfoto();
        File[] filesBildschirmfotoSrc = myTargetDirectory.listFiles(filter);
        for(File myBildschirmfoto:filesBildschirmfotoSrc){
            String datumString = myBildschirmfoto.getName().split(" ")[1];
            String daturmDirectoryName = myWorkDirectory.getAbsolutePath()+ File.separator + DIR_BILDSCHIRMFOTO  + File.separator + datumString;
            File datumDirectory = new File(daturmDirectoryName);
            datumDirectory.mkdirs();
            String filename = myBildschirmfoto.getName().replace(" ","_");
            String movedFileName = daturmDirectoryName + File.separator + filename;
            File movedFile = new File(movedFileName);
            myBildschirmfoto.renameTo(movedFile);
            System.out.println("sortAllBildschirmfoto: "+movedFile.getAbsolutePath());
        }
    }

    private void moveAllOtherFiles(File srcDirectory, File myTargetDirectory) {
        System.out.println(myTargetDirectory.getAbsolutePath());
        FileFilter filter = new FilterOthers();
        File[] filesBildschirmfotoSrc = srcDirectory.listFiles(filter);
        for(File myOtherFile:filesBildschirmfotoSrc){
            long lastModified = myOtherFile.lastModified();
            String filenameTarget = myTargetDirectory.getAbsolutePath()+ File.separator + myOtherFile.getName();
            File fileTarget = new File(filenameTarget);
            myOtherFile.renameTo(fileTarget);
            myOtherFile.setLastModified(lastModified);
            fileTarget.setLastModified(lastModified);
            System.out.println("moveAllOtherFiles: "+fileTarget.getAbsolutePath());
        }
    }

    private void sortAllOtherFiles(File myTargetDirectory, File myWorkDirectory) {
        System.out.println(myWorkDirectory.getAbsolutePath());
        FilterBildschirmfoto filterBildschirmfoto = new FilterBildschirmfoto();
        File[] filesBildschirmfotoSrc = myTargetDirectory.listFiles(filterBildschirmfoto);
        for (File myOtherFile : filesBildschirmfotoSrc) {
            sortOneFile(myOtherFile, DIR_BILDSCHIRMFOTO ,myWorkDirectory);
        }
        for(TargetSubDirs enumTargetSubDirs:TargetSubDirs.values()) {
            MyFilenameFilter myFilenameFilter = new GenericFilter(enumTargetSubDirs);
            filesBildschirmfotoSrc = myTargetDirectory.listFiles(myFilenameFilter);
            for (File myOtherFile : filesBildschirmfotoSrc) {
                sortOneFile(myOtherFile, enumTargetSubDirs.getDirName(),myWorkDirectory);
            }
        }
        FilterOthers filterOthers = new FilterOthers();
        filesBildschirmfotoSrc = myTargetDirectory.listFiles(filterOthers);
        for (File myOtherFile : filesBildschirmfotoSrc) {
            sortOneFile(myOtherFile, DIR_OTHER , myWorkDirectory);
        }
    }

    private void sortOneFile(File myOtherFile, String enumTargetSubDir, File myWorkDirectory) {
        long lastModified = myOtherFile.lastModified();
        String datumsString = getDateString(lastModified);
        String directoryFileName = myWorkDirectory.getAbsolutePath() + File.separator + enumTargetSubDir + File.separator + datumsString;
        File directoryFile = new File(directoryFileName);
        directoryFile.mkdirs();
        String name = myOtherFile.getName();
        name = name.replace(" ", "_");
        name = name.replace("ä", "ae");
        name = name.replace("ü", "ue");
        name = name.replace("ö", "ue");
        name = name.replace("Ä", "Ae");
        name = name.replace("Ü", "Ue");
        name = name.replace("Ö", "Oe");
        name = name.replace("ß", "sz");
        String targetFileName = directoryFileName + File.separator + name;
        File targetFile = new File(targetFileName);
        if (targetFile.exists()) {
            String endings[] = name.split("\\.");
            int length = endings.length;
            length = length > 0 ? length - 1 : length;
            targetFileName = targetFileName + "-" + getDateTimeString() + "." + endings[length];
            targetFile = new File(targetFileName);
        }
        myOtherFile.renameTo(targetFile);
        targetFile.setLastModified(lastModified);
        myOtherFile.setLastModified(lastModified);
        System.out.println("sortAllOtherFiles: "+targetFile.getAbsolutePath());
    }

}
