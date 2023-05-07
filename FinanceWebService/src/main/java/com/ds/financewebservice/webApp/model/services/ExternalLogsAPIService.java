package com.ds.financewebservice.webApp.model.services;

import com.ds.financewebservice.LogDataModel;
import com.ds.financewebservice.webApp.Path;
import com.ds.financewebservice.webApp.model.entity.Log;
import com.ds.financewebservice.webApp.model.exceptions.APIException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;

import java.util.List;
/**
 * Author: Mehak Sharma
 * AndrewId: mehaksha
 * Project 4
 */
public class ExternalLogsAPIService  {

    public List<Log> getLogs() throws APIException {
        LogDataModel ld= new LogDataModel();
        JSONArray fetchedJsonData = ld.getLogs();

        return toEntityList(fetchedJsonData.toString());
    }

    private List<Log> toEntityList(String jsonLogs) {
        // convert JSON array to list of logs
        return new Gson().fromJson(jsonLogs, new TypeToken<List<Log>>() {}.getType());
    }
}
