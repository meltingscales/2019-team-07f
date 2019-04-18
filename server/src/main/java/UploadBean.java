import com.teamteem.config.UploadConfig;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@ManagedBean
@SessionScoped
public class UploadBean {

    /**
     * File stream from the form.
     */
    private Part file;

    /**
     * Filename from the form.
     */
    private String fileName;

    private File videos_folder = UploadConfig.videosFolder;

    public void upload() throws IOException, Exception {

        if (!videos_folder.exists()) {
            throw new Exception("Videos folder does not exist!");
        }

        if(!fileName.endsWith("mp4")){ //TODO actually show error in form instead of creating a stack trace
            throw new IllegalArgumentException("Only MP4 files can be uploaded!");
        }

        if (file != null) {
            InputStream input = file.getInputStream();

            File videos_file = new File(videos_folder, fileName);

            System.out.println(videos_folder.getAbsolutePath());

            if (!videos_file.exists()) {
                videos_file.createNewFile();
            }

            FileOutputStream output = new FileOutputStream(videos_file);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            input.close();
            output.close();

        } else {
            throw new NullPointerException(String.format("`file` is null somehow! This %s's variables are not being automatically filled in!", UploadBean.class.getSimpleName()));
        }
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
