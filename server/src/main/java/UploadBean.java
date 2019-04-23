import com.teamteem.config.UploadConfig;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@ManagedBean(name = "uploadBean")
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

    /**
     * File extension of the file.
     */
    private String fileExt = "mp4";

    private File videos_folder = UploadConfig.videosFolder;

    public void upload() throws IOException, Exception {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

        if (session.getAttribute("user") != null) {
            // TODO Then upload to a specific folder...
        } else {
            //TODO for some reason, 'user' is null. Session not being set correctly?
            throw new Exception("You are not logged in and thus cannot upload videos!");
        }

        if (!videos_folder.exists()) {
            throw new Exception("Videos folder does not exist!");
        }

        if (!file.getSubmittedFileName().endsWith(fileExt)) { //TODO actually show error in form instead of creating a stack trace
            throw new IllegalArgumentException(String.format("Only %s files can be uploaded!", fileExt));
        }

        if (file != null) {
            InputStream input = file.getInputStream();

            File videos_file = new File(videos_folder, String.format("%s.%s", fileName, fileExt));

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
