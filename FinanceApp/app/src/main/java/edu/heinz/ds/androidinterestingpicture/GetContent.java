package edu.heinz.ds.androidinterestingpicture;

import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Author: Aarushi Sinha
 * AndrewId: aarushis
 * Project 4
 * Source: Gets finance Info from 3rd Party API
 */
public class GetContent {
    FinanceApp ip = null;   // for callback
    String searchTerm = null;
    String info=null;
    boolean proceed=true;
    String errorMessage="";

    // search( )
    // Parameters:
    // String searchTerm: the thing to search for
    // Activity activity: the UI thread activity
    // InterestingPicture ip: the callback method's class; here, it will be ip.pictureReady( )
    public void search(String searchTerm, Activity activity, FinanceApp ip) {
        this.ip = ip;
        this.searchTerm = searchTerm;
        new GetContent.BackgroundTask(activity).execute();
    }



    // class BackgroundTask
    // Implements a background thread for a long running task that should not be
    //    performed on the UI thread. It creates a new Thread object, then calls doInBackground() to
    //    actually do the work. When done, it calls onPostExecute(), which runs
    //    on the UI thread to update some UI widget (***never*** update a UI
    //    widget from some other thread!)
    //
    // Adapted from one of the answers in
    // https://stackoverflow.com/questions/58767733/the-asynctask-api-is-deprecated-in-android-11-what-are-the-alternatives
    // Modified by Barrett
    //
    // Ideally, this class would be abstract and parameterized.
    // The class would be something like:
    //      private abstract class BackgroundTask<InValue, OutValue>
    // with two generic placeholders for the actual input value and output value.
    // It would be instantiated for this program as
    //      private class MyBackgroundTask extends BackgroundTask<String, Bitmap>
    // where the parameters are the String url and the Bitmap image.
    //    (Some other changes would be needed, so I kept it simple.)
    //    The first parameter is what the BackgroundTask looks up on Flickr and the latter
    //    is the image returned to the UI thread.
    // In addition, the methods doInBackground() and onPostExecute( ) could be
    //    absttract methods; would need to finesse the input and ouptut values.
    // The call to activity.runOnUiThread( ) is an Android Activity method that
    //    somehow "knows" to use the UI thread, even if it appears to create a
    //    new Runnable.

    private class BackgroundTask {

        private Activity activity; // The UI thread

        public BackgroundTask(Activity activity) {
            this.activity = activity;
        }

        private void startBackground() {
            new Thread(new Runnable() {
                public void run() {

                    try {
                        doInBackground();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // This is magic: activity should be set to MainActivity.this
                    //    then this method uses the UI thread
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            onPostExecute();
                        }
                    });
                }
            }).start();
        }

        private void execute(){
            // There could be more setup here, which is why
            //    startBackground is not called directly
            startBackground();
        }

        // doInBackground( ) implements whatever you need to do on
        //    the background thread.
        // Implement this method to suit your needs
        private void doInBackground() throws IOException {

                info=getInfo(searchTerm);


        }
        /**
         * SOURCE:https://stackoverflow.com/questions/9570237/android-check-internet-connection
         * @return
         */
        public boolean isInternetAvailable() {
            try {

                InetAddress ipAddr = InetAddress.getByName("google.com");

                return !ipAddr.equals("");

            } catch (Exception e) {
                System.out.println(e);
                return false;
            }
        }
        public String getInfo(String searchTerm) throws IOException {
            String response="";
            String urlString="https://dry-garden-76213.herokuapp.com/getFinance?" + searchTerm;
            URL url2= new URL(urlString);
            HttpURLConnection urlConnection2 = (HttpURLConnection) url2.openConnection();
            try {

                InputStream in = new BufferedInputStream(urlConnection2.getInputStream());
                BufferedReader streamReader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                StringBuilder responseStrBuilder = new StringBuilder();

                String inputStr="";
                while ((inputStr = streamReader.readLine()) != null)
                    responseStrBuilder.append(inputStr);

                if(responseStrBuilder.toString().contains("Company Not Found")){
                    //When company not found
                    response="ERROR";
                }else{
                    JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());
                    response+= "\n COMPANY --> " + searchTerm +
                            "\nPeriod: " + jsonObject.getString("period") + "\nRevenue: " + jsonObject.getString("revenue") +
                            "\nFillingDate: " + jsonObject.getString("fillingDate") + "\nCalendarYear: " + jsonObject.getString("calendarYear") +
                            "\nOperatingExpenses: " + jsonObject.getString("operatingExpenses") + "\nResearchAndDevelopmentExpenses: " + jsonObject.getString("researchAndDevelopmentExpenses") +
                            "\nGrossProfit: " + jsonObject.getString("grossProfit") + "\n" ;

                }



            } catch(UnknownHostException e){
                proceed=false;
               errorMessage = "\nConnection Issue! \nCheck your Internet";
            }
            catch(FileNotFoundException e){
                    proceed=false;
                    errorMessage= "\nBad URL. \nContact Administrator";
                } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection2.disconnect();
            }
            return response;
        }

        // onPostExecute( ) will run on the UI thread after the background
        //    thread completes.
        // Implement this method to suit your needs
        public void onPostExecute() {
            if(proceed)
            ip.contentReady(info);
            else
            ip.internetIssue(errorMessage);
        }

    }

}
