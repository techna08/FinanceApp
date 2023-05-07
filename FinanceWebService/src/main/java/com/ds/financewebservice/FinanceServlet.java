package com.ds.financewebservice;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Author: Aarushi Sinha
 * AndrewId: aarushis
 * Project 4
 * The Endpoint that the Andprid APP uses to get finance data
 */
@WebServlet(name = "financeServlet", urlPatterns = {"/getFinance"})
public class FinanceServlet extends HttpServlet {
    private FinanceDataModel fdm;
    LogIntoMongo log;
    Logger logger;

    @Override
    public void init() {
        fdm = new FinanceDataModel();
        log=new LogIntoMongo();
        logger= LoggerFactory.getLogger(FinanceServlet.class);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String company= request.getQueryString();
        log.enterData("The user has requested for Financial Data for " +company ,System.currentTimeMillis());
        logger.info("The user has requested for Financial Data for " +company);
        PrintWriter out = response.getWriter();
        //Validating
        if(!StringUtils.isAllUpperCase(company)){
            out.print("Please enter the Company Name in Uppercase Only");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.flush();

        }else{
            JSONObject j= fdm.getFinanceData(company,log);
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            if(j==null)
                out.print("Company Not Found\n Are you entering the company's trading name correctly?\nExample: AAPL,SPOT,AMZN");
            else{
                out.print(j.toString(4));
                log.enterData("Financial Data request fulfilled for " + company,System.currentTimeMillis());
                logger.info("Financial Data request fulfilled for " + company);
            }

            out.flush();
        }


    }

    public void destroy() {
    }
}