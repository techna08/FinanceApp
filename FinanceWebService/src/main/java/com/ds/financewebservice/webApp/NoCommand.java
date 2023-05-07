package com.ds.financewebservice.webApp;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * No command.
 */
/**
 * Author: Mehak Sharma
 * AndrewId: mehaksha
 * Project 4
 */
public class NoCommand extends Command {

    private static final long serialVersionUID = -2785976616686657267L;

    private static final Logger log = LoggerFactory.getLogger(NoCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");

        String errorMessage = "No such command";
        request.setAttribute("errorMessage", errorMessage);
        log.error("Set the request attribute: errorMessage --> " + errorMessage);

        log.debug("Command finished");
        return Path.PAGE__NOT_FOUND_ERROR_PAGE;
    }

}

