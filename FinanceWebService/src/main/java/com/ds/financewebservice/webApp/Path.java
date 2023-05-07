package com.ds.financewebservice.webApp;

/**
 * Path holder (jsp pages, controller commands, external api).
 *
*/
/**
 * Author: Mehak Sharma
 * AndrewId: mehaksha
 * Project 4
 */
 public final class Path {
    public static final String LOGS_API_URL = "https://floating-ridge-23010.herokuapp.com/getLogs";
    public static final String ANALYTICS_API_URL = "https://floating-ridge-23010.herokuapp.com/getAnalytics";

    // pages
    public static final String PAGE__DASHBOARD = "/WEB-INF/jsp/dashboard.jsp";
    public static final String PAGE__LOGS = "/WEB-INF/jsp/logs.jsp";
    public static final String PAGE__ANALYTICS = "/WEB-INF/jsp/analytics.jsp";
    public static final String PAGE__ERROR_PAGE = "/WEB-INF/jsp/error_page.jsp";
   public static final String PAGE__INTERNAL_ERROR_PAGE = "/WEB-INF/jsp/error/500.jsp";
   public static final String PAGE__NOT_FOUND_ERROR_PAGE = "/WEB-INF/jsp/error/404.jsp";

    // commands
    public static final String COMMAND__DASHBOARD__LOGS = "/dashboard?command=logs";
   public static final String COMMAND__DASHBOARD__ANALYTICS = "/dashboard?command=analytics";
}
