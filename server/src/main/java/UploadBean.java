import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@ManagedBean
@SessionScoped
public class UploadBean {

    private Part file;
    private String fileName;
    public String upload() {
        if (file != null) {
            try {
                InputStream input = file.getInputStream();

                File f = new File("C://mnt//nfs_videos");
                System.out.println(f.getAbsolutePath());

                //fileName=file.getSubmittedFileName();
                if (!f.exists()) {
                    f.createNewFile();
                }
                FileOutputStream output = new FileOutputStream(f);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }
                input.close();
                output.close();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
            return "success";
        } else {
            return "failed";
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

    public void setFileName(String  fileName) {
        this.fileName = fileName;
    }
}
