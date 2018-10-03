package seedu.address.commons.util;

import com.oracle.tools.packager.UnsupportedPlatformException;
import com.sun.javafx.PlatformUtil;
//@author Jeffry

/**
 * An utility class that handles most of the low-level interaction with the ImageMagick executable.
 */
public class ImageMagickUtil {
    /**
     *
     * @return path an string to the location of the ImageMagick executable for a supported platform.
     */
    public static String getImageMagickPath() /*throws UnsupportedPlatformException*/ {
        if(PlatformUtil.isLinux()){
            return "convert";
        }else if(PlatformUtil.isMac()){
            return "convert";
        } else if (PlatformUtil.isWindows()) {
            return "convert";
        }else{
            //TODO: make a new exception that allows us to specify an error message.
            /*throw new UnsupportedPlatformException();*/
            return "cry deeply";
        }
    }
}
