package org.woehlke.tools.desktopcleanup.filter;

import org.woehlke.tools.desktopcleanup.control.MyFilenameFilter;

import java.io.File;

public class FilterBildschirmfoto implements MyFilenameFilter {

    /* Bildschirmfoto 2017-12-09 um 13.33.54.png */

    public final static String DIR_BILDSCHIRMFOTO = "Pictures"+ File.separator+"screenshot";

    @Override
    public boolean accept(File dir, String name) {
        boolean accepted = name.startsWith("Bildschirmfoto ");
        accepted = accepted && name.endsWith(".png");
        return accepted;
    }

    @Override
    public String getTargetSubDir() {
        return DIR_BILDSCHIRMFOTO;
    }
}
