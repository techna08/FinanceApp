package com.ds.financewebservice;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * Author: Aarushi Sinha
 * AndrewId: aarushis
 * Project 4
 * This class logs the data into Mongo DB
 */
public class LogIntoMongo {
    MongoCollection<Document> logs;

    public LogIntoMongo(){
        MongoClient mongoClient = null;
        try {
            ConnectionString connectionString = new ConnectionString(<Your connection string>);
            MongoClientSettings settings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .serverApi(ServerApi.builder()
                            .version(ServerApiVersion.V1)
                            .build())
                    .build();
            mongoClient= MongoClients.create(settings);
            MongoDatabase database = mongoClient.getDatabase("Project4");
            logs = database.getCollection("Logs");
        }catch(Exception e){
            mongoClient.close();
        }
    }

    public void enterData(String s, long time) {
        //logging in a specific format
        Document doc = new Document();
        doc.append("log",s).append("timestamp",time);
        logs.insertOne(doc);
    }
}
