package com.teamteem.dao;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@ManagedBean
@ViewScoped
public class Upload {

    private Part uploadedFile;
    private String folder = "C:/temp-upload";

    public Part getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(Part uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public String Uploaded() {
        if (uploadedFile != null) {
            try (InputStream input = uploadedFile.getInputStream()) {
                Files.copy(input, new File(folder, uploadedFile.getSubmittedFileName()).toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "success";
        } else {
            return "failed";
        }
    }
}
