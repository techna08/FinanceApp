package com.ds.financewebservice;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * Author: Aarushi Sinha
 * AndrewId: aarushis
 * Project 4
 * Class for running analytics on the app
 */
public class AnalyticsDataModel {
    public JSONArray getAnalytics() {

        MongoClient mongoClient = null;
        try{
            // making MongoDB Connection
            ConnectionString connectionString = new ConnectionString("mongodb://Aaru_Mongo:Mongo1212@ac-uir8d9f-shard-00-00.mqfu6qw.mongodb.net:27017,ac-uir8d9f-shard-00-01.mqfu6qw.mongodb.net:27017,ac-uir8d9f-shard-00-02.mqfu6qw.mongodb.net:27017/test?w=majority&retryWrites=true&tls=true&authMechanism=SCRAM-SHA-1");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .build();
            mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("Project4");
            MongoCollection<Document> highestNumbers = database.getCollection("HighestNumbers");
            MongoCollection<Document> mostSearched = database.getCollection("MostSearched");
            MongoCollection<Document> responseTimeFinance = database.getCollection("ResponseTimeFinance");
            MongoCollection<Document> responseTimeImage = database.getCollection("ResponseTimeImage");

            //Highest Numbers
            FindIterable<Document> iterDoc = highestNumbers.find();
            Iterator it = iterDoc.iterator();
            JSONArray jArr= new JSONArray();
            while (it.hasNext()) {
                Document d= (Document) it.next();
                jArr.put(d);
            }



            //Most Searched
            FindIterable<Document> iterDoc2 = mostSearched.find();
            Iterator it2 = iterDoc2.iterator();
            List<String> companyList= new ArrayList<>();
            while (it2.hasNext()) {
                Document d= (Document) it2.next();
                companyList.add(d.getString("company"));
            }
            String maxOccurredElement = companyList.stream()
                    .reduce(BinaryOperator.maxBy((o1, o2) -> Collections.frequency(companyList, o1) -
                            Collections.frequency(companyList, o2))).orElse(null);
            JSONObject j=new JSONObject();
            j.put("mostSearchedCompany",maxOccurredElement);
            jArr.put(j);


            //AverageResponseTimeFinance
            FindIterable<Document> iterDoc3 = responseTimeFinance.find();
            Iterator it3 = iterDoc3.iterator();

            Document d2= (Document) it3.next();

            JSONObject j1=new JSONObject();
            j1.put("averageResponseTimeFinance",d2.get("responseTime").toString());
            jArr.put(j1);



            //AverageResponseTimeImage
            FindIterable<Document> iterDoc4 = responseTimeImage.find();
            Iterator it4 = iterDoc4.iterator();

            Document d= (Document) it4.next();
            JSONObject j2=new JSONObject();
            j2.put("averageResponseTimeImage",d.get("responseTime").toString());
            jArr.put(j2);


            return jArr;
        }catch(Exception e){
            System.out.println(e);
            mongoClient.close();
            return null;
        }finally {
            mongoClient.close();
        }

    }
}
