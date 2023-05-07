package com.ds.financewebservice.webApp;

import com.ds.financewebservice.webApp.model.entity.Log;

import com.ds.financewebservice.webApp.model.exceptions.APIException;
import com.ds.financewebservice.webApp.model.services.ExternalLogsAPIService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * Author: Mehak Sharma
 * AndrewId: mehaksha
 * Project 4
 */
public class LogsCommand extends Command {
    private static final Logger log = LoggerFactory.getLogger(LogsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");

        List<Log> logList = null;
        try {
            //get logs from an external api
            logList = new ExternalLogsAPIService().getLogs();
            log.trace("Got from API: logList --> " + logList.toString());
        } catch (APIException e) {
            log.error("LogsCommand#execute api exception");

            request.setAttribute("errorMessage", e.getMessage());
            return Path.PAGE__ERROR_PAGE;
        }
        // TODO: add sorting, if its needed

        // put log list to the request
        request.setAttribute("logs", logList);
        log.trace("Set the request attribute: logList --> " + logList);

        log.debug("Command finished");
        return Path.PAGE__LOGS;
    }
}
