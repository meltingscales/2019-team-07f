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

    private Part file;

    private File videos_folder = new File("/mnt/nfs_videos/");

    private String fileName;

    public void upload() throws IOException, Exception {

        if (!videos_folder.exists()) {
            throw new Exception("Videos folder does not exist!");
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
