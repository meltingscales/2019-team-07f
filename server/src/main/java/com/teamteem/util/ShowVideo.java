package com.teamteem.util;

import java.io.*;
import javax.faces.bean.ManagedBean;
import javax.servlet.*;
import javax.servlet.http.*;

@ManagedBean (name = "showVideo")

public class ShowVideo extends HttpServlet {


    public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws IOException
    {
        response.setContentType("video/mp4");
        ServletOutputStream out = response.getOutputStream();
        FileInputStream fin = new FileInputStream("/mnt/nfs_videos/");

        byte [] buf = new byte[4096];
        int read;
        while((read = fin.read(buf)) != -1)
        {
            out.write(buf, 0, read);
        }

        fin.close();
        out.flush();
        out.close();
    }
}