package com.ds.financewebservice;

import com.mongodb.*;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;

/**
 * Author: Aarushi Sinha
 * AndrewId: aarushis
 * Project 4
 * Class to get Finance Info
 */
public class FinanceDataModel {
    Logger logger= LoggerFactory.getLogger(FinanceDataModel.class);
    public JSONObject getFinanceData(String company, LogIntoMongo log) {
        try{
            log.enterData("Working to fulfill Finance Request for " +company, System.currentTimeMillis());
            logger.info("Working to fulfill Finance Request for " +company);
            String url="https://financialmodelingprep.com/api/v3/income-statement/" + company +"?limit=120&apikey=<APIKEY>";
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            long start=System.currentTimeMillis();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            long end=System.currentTimeMillis();
            log.enterData("Response TIme for Finance API: " + (end - start),System.currentTimeMillis());
            logger.info("Response TIme for Finance API: " + (end - start));
            addFinanceApiResponseTime(end-start,log);
            addMostSearched(company,log);
            JSONArray jArr= new JSONArray(response.body());
            JSONObject responseObj=new JSONObject();
            responseObj.put("fillingDate",jArr.getJSONObject(0).get("fillingDate"));
            responseObj.put("calendarYear",jArr.getJSONObject(0).get("calendarYear"));
            responseObj.put("period",jArr.getJSONObject(0).get("period"));
            responseObj.put("revenue",jArr.getJSONObject(0).get("revenue"));
            responseObj.put("grossProfit",jArr.getJSONObject(0).get("grossProfit"));
            responseObj.put("researchAndDevelopmentExpenses",jArr.getJSONObject(0).get("researchAndDevelopmentExpenses"));
            responseObj.put("operatingExpenses",jArr.getJSONObject(0).get("operatingExpenses"));
            updateHighestNumbers(responseObj,company,log);
            log.enterData("Fulfilled Finance Request for " +company, System.currentTimeMillis());
            logger.info("Fulfilled Finance Request for " +company);
            return responseObj;
        }catch(Exception e)
        {
            System.out.println("Exception in Finance Data API--> " +e);
        }
        return null;
    }

    /**
     * To Update Highest Numbers
     * @param responseObj
     * @param company
     * @param log
     */
    private void updateHighestNumbers(JSONObject responseObj,String company,LogIntoMongo log) {
        log.enterData("Updating Highest Numbers for " +company, System.currentTimeMillis());
        logger.info("Updating Highest Numbers for " +company);
        MongoClient mongoClient = null;
        try{
            ConnectionString connectionString = new ConnectionString(<YourCOnnectionString>);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .build();
             mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("Project4");
            MongoCollection<Document> highestNumbers = database.getCollection("HighestNumbers");
            FindIterable<Document> iterDoc = highestNumbers.find();
            Iterator it = iterDoc.iterator();
            while(it.hasNext()){
                Document d= (Document) it.next();
                if(d.keySet().contains("highestRevenue")){
                    log.enterData("Current highest Revenue " +d.get("highestRevenue"), System.currentTimeMillis());
                    if(responseObj.getLong("revenue") > Long.parseLong((String) d.get("highestRevenue"))){
                        log.enterData("Updated Revenue for " +company, System.currentTimeMillis());
                        logger.info("Updated Revenue for " +company);
                        Document dNew= new Document();
                        dNew.append("highestRevenue",String.valueOf(responseObj.getLong("revenue"))).append("company",company);
                        BasicDBObject updateObject = new BasicDBObject();
                        updateObject.put("$set", dNew);
                        highestNumbers.updateOne(new BasicDBObject("_id", new ObjectId(d.get("_id").toString())),updateObject);
                    }

                }else if(d.keySet().contains("highestProfit")){
                    log.enterData("Current highest Profit " + d.get("highestProfit"), System.currentTimeMillis());

                    if(responseObj.getLong("grossProfit") > Long.parseLong((String) d.get("highestProfit"))){
                        log.enterData("Updated Profit for " +company, System.currentTimeMillis());
                        logger.info("Updated Profit for " +company);
                        Document dNew= new Document();
                        dNew.append("highestProfit",String.valueOf(responseObj.getLong("grossProfit"))).append("company",company);
                        BasicDBObject updateObject = new BasicDBObject();
                        updateObject.put("$set", dNew);
                        highestNumbers.updateOne(new BasicDBObject("_id", new ObjectId(d.get("_id").toString())),updateObject);
                    }

                }
                else if(d.keySet().contains("highestOperatingExpenses")){
                    log.enterData("Current highest Operating Expenses " + d.get("highestOperatingExpenses"), System.currentTimeMillis());
                    if(responseObj.getLong("operatingExpenses") > Long.parseLong((String) d.get("highestOperatingExpenses"))){
                        log.enterData("Updated Operating Expenses for " +company, System.currentTimeMillis());
                        logger.info("Updated Operating Expenses for " +company);
                        Document dNew= new Document();
                        dNew.append("highestOperatingExpenses",String.valueOf(responseObj.getLong("operatingExpenses"))).append("company",company);
                        BasicDBObject updateObject = new BasicDBObject();
                        updateObject.put("$set", dNew);
                        highestNumbers.updateOne(new BasicDBObject("_id", new ObjectId(d.get("_id").toString())),updateObject);
                    }

                }
                else if(d.keySet().contains("highestRDExpenses")){
                    log.enterData("Current highest R&D Expenses " + d.get("highestRDExpenses"), System.currentTimeMillis());
                    if(responseObj.getLong("researchAndDevelopmentExpenses") > Long.parseLong((String) d.get("highestRDExpenses"))){
                        log.enterData("Updated R&D Expenses for " +company, System.currentTimeMillis());
                        logger.info("Updated R&D Expenses for " +company);
                        Document dNew= new Document();
                        dNew.append("highestRDExpenses",String.valueOf(responseObj.getLong("researchAndDevelopmentExpenses"))).append("company",company);
                        BasicDBObject updateObject = new BasicDBObject();
                        updateObject.put("$set", dNew);
                        highestNumbers.updateOne(new BasicDBObject("_id", new ObjectId(d.get("_id").toString())),updateObject);
                    }

                }
            }

            log.enterData("Highest Numbers Request fulfilled for  " +company, System.currentTimeMillis());
            logger.info("Highest Numbers Request fulfilled for  " +company);
        }catch(Exception e){
            mongoClient.close();
        }finally{
            mongoClient.close();
        }
       

    }

    /**
     * Adding company to Most Searched
     * @param company
     * @param log
     */
    private void addMostSearched(String company,LogIntoMongo log) {
        log.enterData("Adding to searched list for  " +company, System.currentTimeMillis());
        logger.info("Adding to searched list for  " +company);
        MongoClient mongoClient = null;
        try{
            ConnectionString connectionString = new ConnectionString(<Yourconnectionstring>);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .build();
             mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("Project4");
            MongoCollection<Document> mostSearched = database.getCollection("MostSearched");
            Document companyDoc = new Document("_id", new ObjectId());
            companyDoc.append("company",company);
            mostSearched.insertOne(companyDoc);
            log.enterData("Inserted into Most searched list for  " +company, System.currentTimeMillis());
            logger.info("Inserted into Most searched list for  " +company);
        }catch(Exception e){
            mongoClient.close();
        }finally{
            mongoClient.close();
        }
        
    }

    /**
     * Recalculating the Average Response Time
     * @param l
     * @param log
     */
    private void addFinanceApiResponseTime(long l,LogIntoMongo log) {
        log.enterData("Calculating new Average for Finance API Response Time  " , System.currentTimeMillis());
        logger.info("Calculating new Average for Finance API Response Time  " );
        MongoClient mongoClient=null;
        try{
            ConnectionString connectionString = new ConnectionString(<Yourconnectionstring>);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .build();
            mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("Project4");
            MongoCollection<Document> responseTimeFinance = database.getCollection("ResponseTimeFinance");
            FindIterable<Document> iterDoc = responseTimeFinance.find();
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

            responseTimeFinance.updateOne( new BasicDBObject("_id", new ObjectId(d.get("_id").toString())),updateObject);
            log.enterData("Updated new Average for Finance API Response Time to  "+ time , System.currentTimeMillis());
            logger.info("Updated new Average for Finance API Response Time to  "+ time );
        }catch(Exception e){
            mongoClient.close();
        }finally{
            mongoClient.close();
        }

    }
}
