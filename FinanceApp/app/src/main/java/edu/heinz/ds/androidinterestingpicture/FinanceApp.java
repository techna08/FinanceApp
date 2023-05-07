package edu.heinz.ds.androidinterestingpicture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;

/**
 * Author: Aarushi Sinha
 * AndrewId: aarushis
 * Project 4
 * The main App
 */
public class FinanceApp extends AppCompatActivity {

    FinanceApp me = this;
    boolean proceed;
    boolean financeDataFound=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
         * The click listener will need a reference to this object, so that upon successfully finding a picture from Flickr, it
         * can callback to this object with the resulting picture Bitmap.  The "this" of the OnClick will be the OnClickListener, not
         * this InterestingPicture.
         */
        final FinanceApp ma = this;

        /*
         * Find the "submit" button, and add a listener to it
         */
        Button submitButton = (Button)findViewById(R.id.submit);


        // Add a listener to the send button
        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View viewParam) {
                financeDataFound=true;
                ImageView pictureView = (ImageView)findViewById(R.id.interestingPicture);
                pictureView.setImageResource(android.R.color.transparent);

                String searchTerm = ((EditText)findViewById(R.id.searchTerm)).getText().toString();
                    boolean isParamValid=checkValidity(searchTerm);
                    if(isParamValid){
                        System.out.println("searchTerm = " + searchTerm);
                        GetPicture gp = new GetPicture();
                        gp.search(searchTerm, me, ma); // Done asynchronously in another thread.  It calls ip.pictureReady() in this thread when complete.
                        GetContent gc=new GetContent();
                        gc.search(searchTerm,me,ma);
                        System.out.println("content searched");
                    }
                    else{
                        internetIssue("Input String Invalid! ");
                    }






            }
        });
    }

    private boolean checkValidity(String searchTerm) {
        if(StringUtils.isAllUpperCase(searchTerm)){
                return true;
        }else{
            return false;
        }
    }


    /*
     * This is called by the GetPicture object when the picture is ready.  This allows for passing back the Bitmap picture for updating the ImageView
     */
    public void pictureReady(Bitmap picture) {
        if(financeDataFound){
            ImageView pictureView = (ImageView)findViewById(R.id.interestingPicture);
            TextView searchView = (EditText)findViewById(R.id.searchTerm);
            pictureView.setImageBitmap(picture);
            pictureView.setVisibility(View.VISIBLE);
            searchView.setText("");
            pictureView.invalidate();
        }

    }
    /*
    * this is for when the content API responds.
     */
    public void contentReady(String info) {
        if(info.equals("ERROR")){
            internetIssue("Company does not exist in 3rd Party Database");
            financeDataFound=false;
        }else{
            TextView c= findViewById(R.id.feedback);
            c.setText(info);
            c.setTextColor(Color.BLACK);
        }


    }

    public void internetIssue(String error) {
        TextView c= findViewById(R.id.feedback);
        c.setText(error);
    }
}
