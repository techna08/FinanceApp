package com.ds.financewebservice.webApp.model.services;


import com.ds.financewebservice.AnalyticsDataModel;
import com.ds.financewebservice.AnalyticsServlet;
import com.ds.financewebservice.webApp.Path;
import com.ds.financewebservice.webApp.model.entity.AdditionalStat;
import com.ds.financewebservice.webApp.model.entity.CompanyStat;
import com.ds.financewebservice.webApp.model.entity.IdJsonObject;
import com.ds.financewebservice.webApp.model.exceptions.APIException;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
/**
 * Author: Mehak Sharma
 * AndrewId: mehaksha
 * Project 4
 */
public class ExternalAnalyticsAPIService {
    public Map<String, List<?>> getAnalytics() throws APIException {
        AnalyticsDataModel as= new AnalyticsDataModel();

        JSONArray fetchedJsonData = as.getAnalytics();

        List<JsonObject> jsonObjects = new ArrayList<>();

        for(int i=0 ;i< fetchedJsonData.length();i++){
            JsonObject j2=new Gson().fromJson(fetchedJsonData.getJSONObject(i).toString(), JsonObject.class);

            jsonObjects.add(j2);
        }

        return getAnalyticsMapFromJson(jsonObjects);
    }

    private Map<String, List<?>> getAnalyticsMapFromJson(List<JsonObject> jsonObjects) {
        Map<String, List<?>> analyticsMap = new HashMap<>();
        List<CompanyStat> companyStats = new ArrayList<>();
        List<AdditionalStat> additionalStats = new ArrayList<>();

        for(JsonObject jsonObject : jsonObjects) {
            if (jsonObject.get("company") != null) {
                companyStats.add(getCompanyStatFromJsonObject(jsonObject));
            } else {
                additionalStats.add(getAdditionalStatFromJsonObject(jsonObject));
            }
        }

        analyticsMap.put("companiesStat", companyStats);
        analyticsMap.put("additionalStat", additionalStats);
        return analyticsMap;
    }

    private CompanyStat getCompanyStatFromJsonObject(JsonObject jsonObject) {
        CompanyStat companyStat = new CompanyStat();

        for (Map.Entry<String, JsonElement> elementEntry : jsonObject.entrySet()) {
            String elementName = elementEntry.getKey();
            switch (elementName) {
                case "company": {
                    companyStat.setCompany(elementEntry.getValue().getAsString());
                    break;
                }

                case "_id": {
                    JsonElement elementValue = elementEntry.getValue();
                    if (elementValue.isJsonObject()) {
                        JsonObject elementValueObject = (JsonObject) elementValue;
                        String date = elementValueObject.get("date").getAsString();
                        long timestamp = elementValueObject.get("timestamp").getAsLong();
                        companyStat.set_id(new IdJsonObject(date, timestamp));
                    }

                    break;
                }

                default: {
                    companyStat.setStatName(elementName);
                    companyStat.setStatValue(elementEntry.getValue().getAsString());
                }
            }
        }

        return companyStat;
    }

    private AdditionalStat getAdditionalStatFromJsonObject(JsonObject jsonObject) {
        AdditionalStat additionalStat = new AdditionalStat();

        for (Map.Entry<String, JsonElement> elementEntry : jsonObject.entrySet()) {
            additionalStat.setOperationName(elementEntry.getKey());
            additionalStat.setOperationValue(elementEntry.getValue().getAsString());
        }

        return additionalStat;
    }
}
