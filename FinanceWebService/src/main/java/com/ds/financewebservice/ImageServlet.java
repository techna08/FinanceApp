package com.ds.financewebservice;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Author: Aarushi Sinha
 * AndrewId: aarushis
 * Project 4
 * Image Endpoint used by the Android APP to fetch Image Data
 */
@WebServlet(name = "imageServlet", urlPatterns = {"/getImage"})
public class ImageServlet extends HttpServlet {

    private ImageDataModel idm;
    LogIntoMongo log;
    Logger logger;
    @Override
    public void init() {
        idm = new ImageDataModel();
        log=new LogIntoMongo();
        logger= LoggerFactory.getLogger(ImageServlet.class);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String company= request.getQueryString();
        log.enterData("The user has requested for Image Data for " +company ,System.currentTimeMillis());
        logger.info("The user has requested for Image Data for " +company);
        PrintWriter out = response.getWriter();
        if(!StringUtils.isAllUpperCase(company)){
            out.print("Please enter the Company Name in Uppercase Only");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.flush();
        }else{
            JSONObject j= idm.getImageUrl(company,log);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            //Handling all scenarios
            if(j==null)
                out.print("Company Not Found\n Are you entering the company's trading name correctly?\nExample: AAPL,SPOT,AMZN");

            else if(j.has("ERROR")){
                out.print(j.getString("ERROR"));

            }
            else{
                out.print(j.toString(4));
                log.enterData("Image Data request fulfilled for " + company,System.currentTimeMillis());
                logger.info("Image Data request fulfilled for " + company);
            }

            out.flush();
        }


    }
}
