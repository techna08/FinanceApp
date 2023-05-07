package com.ds.financewebservice;

import com.ds.financewebservice.SerpAPI.GoogleSearch;

import com.mongodb.*;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: Aarushi Sinha
 * AndrewId: aarushis
 * Project 4
 * Data Model to process th Image Response from Google Image Search
 */
public class ImageDataModel {
    Logger logger= LoggerFactory.getLogger(ImageDataModel.class);
    public JSONObject getImageUrl(String company,LogIntoMongo log) {
        try{
            log.enterData("Working to fulfill Image Request for " +company, System.currentTimeMillis());
            logger.info("Working to fulfill Image Request for " +company);
            Map<String, String> parameter = new HashMap<>();

            parameter.put("q", company + " shares logo");
            parameter.put("tbm", "isch");
            parameter.put("ijn", "0");
            parameter.put("api_key", <Your API Key>);
            long start=System.currentTimeMillis();
            log.enterData("Search Parameter for image API is " + parameter ,System.currentTimeMillis());
            logger.info("Search Parameter for image API is " + parameter);
            GoogleSearch search = new GoogleSearch(parameter);
            long end=System.currentTimeMillis();
            log.enterData("Response TIme for Image API: " + (end - start),System.currentTimeMillis());
            logger.info("Response TIme for Image API: " + (end - start));
            addImageApiResponseTime(end-start, log);
            JsonObject results = search.getJson();
            var images_results = results.get("images_results");
            log.enterData("The image API responded for " + parameter ,System.currentTimeMillis());
            logger.info("The image API responded for " + parameter);
            JSONArray j = new JSONArray(images_results.toString());
            log.enterData("Fulfilled Image Request for " +company, System.currentTimeMillis());
            logger.info("Fulfilled Image Request for " +company);
            return j.getJSONObject(1);
        }catch(Exception e){
            System.out.println("Exception is -> " +e);
            JSONObject j=new JSONObject();
            return j.put("ERROR","Third-party API unavailable");
        }


    }

    /**
     * Source: Updating average Image API Response Time
     * @param l
     * @param log
     */
    private void addImageApiResponseTime(long l, LogIntoMongo log) {
        log.enterData("Calculating new Average for Image API Response Time  " , System.currentTimeMillis());
        logger.info("Calculating new Average for Image API Response Time  " );
        ConnectionString connectionString = new ConnectionString(<Your connection string>);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .serverApi(ServerApi.builder()
                        .version(ServerApiVersion.V1)
                        .build())
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("Project4");
        MongoCollection<Document> responseTimeImage = database.getCollection("ResponseTimeImage");
        FindIterable<Document> iterDoc = responseTimeImage.find();
        Iterator it = iterDoc.iterator();
        Document d= (Document) it.next();
        int count= (int) d.get("count");
        long time= (long) d.get("responseTime");

        time=((time*count) + l)/(count+1);
        count++;
        Document dNew= new Document();
        dNew.append("responseTime",time).append("count",count);

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", dNew);

        responseTimeImage.updateOne( new BasicDBObject("_id", new ObjectId(d.get("_id").toString())),updateObject);
        log.enterData("Updated new Average for Image API Response Time  " , System.currentTimeMillis());
        logger.info("Updated new Average for Image API Response Time  " );

    }
}
