
import jep.Jep;
import jep.JepException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Example {
    public static void main(String[] args) throws IOException, JepException, InterruptedException {

        // Path of folder of Python V2AServer library.
        File pythonLib = new File("..");

        // Temp folder where we'll store our MP3.
        File tempFolder = new File(pythonLib, "temp");

        // Make sure temp folder is empty.
        if (tempFolder.exists()) {
            FileUtils.deleteDirectory(tempFolder);
        }

        // Make sure the temp folder exists.
        if (!tempFolder.exists()) {
            tempFolder.mkdir();
        }

        // Path of sample data folder.
        File sampleData = new File(pythonLib, "sampledata");

        // Path of videos folder.
        File videos = new File(sampleData, "video");

        // Path of potato MP4 file.
        File potatoMP4 = new File(videos, "potato.mp4");

        // Path of potato MP3 file that we wish to see get converted.
        File potatoMP3 = new File(tempFolder, "potato.mp3");

        // Make sure a blank MP3 file exists.
        if(!potatoMP3.exists()) {
            potatoMP3.createNewFile();
        }

        Jep jep;

        try {
            jep = new Jep();
            System.out.println("Yay! jep works!");
        } catch (UnsatisfiedLinkError e) {
            System.out.println("You didn't set the PATH or LD_LIBRARY_PATH right.");
            System.out.println("jep can't find the DLL/shared lib to hook python!");
            throw e;
        }

        // Imports.
        jep.eval("import os");
        jep.eval("import sys");

        // Append parent dir to our path, since it's where the Python code is.
        jep.eval(String.format("sys.path.append('%s')", pythonLib.getPath()));

        // Import our library.
        jep.eval("import V2AServer");

        // Make a new v2aserver object.
        jep.eval("v2aserver = V2AServer.V2AServer()");

        // Set up some variables to paths for MP3 and MP4 files.
        jep.set("potatoMP4", potatoMP4.getCanonicalPath());
        jep.set("potatoMP3", potatoMP3.getCanonicalPath());

        // Convert MP4 to MP3.
        jep.eval("v2aserver.read_file(open(potatoMP4, 'rb'))");

        // Copy MP3 file into tempfolder.
        jep.eval("v2aserver.write_file(open(potatoMP3, 'wb'))");

        // MP3 file should exist and have some normal properties.
        assert (potatoMP3.exists());
        assert (potatoMP3.length() > 0);

        // Come and get it!
        System.out.printf("Come and get your piping hot Java/Python MP3 at %n'%s'%n before it explodes in 10 seconds!%n", potatoMP3.getCanonicalPath());

        Thread.sleep(10 * 1000);

        // Clean up.
        if (tempFolder.exists()) {
            FileUtils.deleteDirectory(tempFolder);
        }

    }
}
