package seedu.address.commons.util;

<<<<<<< HEAD
//import com.oracle.tools.packager.UnsupportedPlatformException;
//gitimport com.sun.javafx.PlatformUtil;
//@author Jeffry
=======

//import com.oracle.tools.packager.UnsupportedPlatformException;
//import com.sun.javafx.PlatformUtil;
//@@author j-lum
>>>>>>> 6af3a1442e46f36312d26fdfec2a2eb823de766b

/**
 * An utility class that handles most of the low-level interaction with the ImageMagick executable.
 */
public class ImageMagickUtil {
    /**
     * @return path an string to the location of the ImageMagick executable for a supported platform.
     */
    public static String getImageMagickPath() /*throws UnsupportedPlatformException*/ {
        /*if(PlatformUtil.isLinux()){
            return "convert";
        }else if(PlatformUtil.isMac()){
            return "convert";
        } else if (PlatformUtil.isWindows()) {
            return "convert";
        }else{
            //TODO: make a new exception that allows us to specify an error message.
            *//*throw new UnsupportedPlatformException();*//*
            return "cry deeply";
        }*/
        return "";
    }
}
