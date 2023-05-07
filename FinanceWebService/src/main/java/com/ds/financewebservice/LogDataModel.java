package com.ds.financewebservice;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

/**
 * Author: Aarushi Sinha
 * AndrewId: aarushis
 * Project 4
 * To fetch logs for the dashboard
 */
public class LogDataModel {


    public JSONArray getLogs() {
        MongoClient mongoClient = null;
        try {

            ConnectionString connectionString = new ConnectionString("mongodb://Aaru_Mongo:Mongo1212@ac-uir8d9f-shard-00-00.mqfu6qw.mongodb.net:27017,ac-uir8d9f-shard-00-01.mqfu6qw.mongodb.net:27017,ac-uir8d9f-shard-00-02.mqfu6qw.mongodb.net:27017/test?w=majority&retryWrites=true&tls=true&authMechanism=SCRAM-SHA-1");
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .build();
            mongoClient = MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("Project4");
            MongoCollection<Document> gradesCollection = database.getCollection("Logs");
            FindIterable<Document> iterDoc = gradesCollection.find();
            Iterator it = iterDoc.iterator();
            JSONArray jArr = new JSONArray();
            //fetching the Document from MongoDB as is
            while (it.hasNext()) {
                Document d = (Document) it.next();
                jArr.put(d);
            }

            return jArr;
        } catch (Exception e) {
            System.out.println(e);
            mongoClient.close();
            return null;
        }
        finally{
            mongoClient.close();
        }

    }
}

//  while (it.hasNext()) {
//          Document d= (Document) it.next();
//          System.out.println(d.get("_id").toString());
//          JSONObject j= new JSONObject();
//          j.put("_id",d.get("_id").toString());
//          j.put("log",d.get("log").toString());
//          j.put("timestamp",d.get("timestamp").toString());
//          jArr.put(j);
//          }