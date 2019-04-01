package com.teamteem;

import com.teamteem.util.VideoConverter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TestServlet extends HttpServlet {

     @Override
     public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{

         String[] dummy = new String[1];
         System.out.println("-------------------BEGIN");
            VideoConverter.main(dummy);
            System.out.println("------END!");


     }
}
