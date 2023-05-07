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
 * Servlet class for explosing analytics endpoint
 */
@WebServlet(name = "analyticsServlet", urlPatterns = {"/getAnalytics"})
public class AnalyticsServlet extends HttpServlet {

    AnalyticsDataModel adm;


    @Override
    public void init(){
        adm= new AnalyticsDataModel();


    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        PrintWriter out = response.getWriter();
        JSONArray j=adm.getAnalytics();
        //When Some issue occurred
        if (j == null) {
            out.print("Issue Occured");
            out.flush();
        }else {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            out.print(j.toString(4));
            out.flush();

        }


    }
}
