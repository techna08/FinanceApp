package com.ds.financewebservice;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author: Aarushi Sinha
 * AndrewId: aarushis
 * Project 4
 * Endpoint exposed to get Logs
 */
@WebServlet(name = "logServlet", urlPatterns = {"/getLogs"})
public class LogServlet extends HttpServlet {

    LogDataModel ldm;



    @Override
    public void init(){
        ldm=new LogDataModel();

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        JSONArray j=ldm.getLogs();
        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if(j==null){

            out.print("Issue Occured");
            out.flush();
        }else{
            out.print(j.toString(4));
            out.flush();

        }


    }

}
