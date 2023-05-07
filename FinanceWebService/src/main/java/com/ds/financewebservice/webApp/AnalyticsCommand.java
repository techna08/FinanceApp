package com.ds.financewebservice.webApp;


import com.ds.financewebservice.webApp.model.entity.AdditionalStat;
import com.ds.financewebservice.webApp.model.entity.CompanyStat;
import com.ds.financewebservice.webApp.model.exceptions.APIException;
import com.ds.financewebservice.webApp.model.services.ExternalAnalyticsAPIService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Author: Mehak Sharma
 * AndrewId: mehaksha
 * Project 4
 */
public class AnalyticsCommand extends Command {
    private static final Logger log = LoggerFactory.getLogger(LogsCommand.class);

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        log.debug("Command starts");

        Map<String, ?> analyticsMap = null;
        try {
            //get logs from an external api
            analyticsMap = new ExternalAnalyticsAPIService().getAnalytics();
            log.trace("Got from API: analyticsMap --> " + analyticsMap.toString());
        } catch (APIException e) {
            log.error("AnalyticsCommand#execute api exception");

            request.setAttribute("errorMessage", e.getMessage());
            return Path.PAGE__ERROR_PAGE;
        }
        // TODO: add sorting, if its needed

        List<CompanyStat> companiesStatsList = (List<CompanyStat>) analyticsMap.get("companiesStat");
        // put companiesStat list to the request
        request.setAttribute("companiesStat", companiesStatsList);
        log.trace("Set the request attribute: companiesStat --> " + companiesStatsList.toString());

        List<AdditionalStat> additionalStatList = (List<AdditionalStat>) analyticsMap.get("additionalStat");
        // put additionalStat list to the request
        request.setAttribute("additionalStat", additionalStatList);
        log.trace("Set the request attribute: additionalStat --> " + additionalStatList.toString());

        log.debug("Command finished");
        return Path.PAGE__ANALYTICS;
    }
}
