package com.example.sunny.xkcd_app;

import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class GetWebAPI extends AsyncTask<Integer, Void, String> {


    String getComic(int index) throws Exception{
        // Make a URL to the xkcd json api where index will be the comicNR
        // On success the method returns an URL in String form to the comic image.

        URL url = new URL("https://xkcd.com/" + index + "/info.0.json");

        URLConnection con = url.openConnection();
        InputStream is = null;
        is = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String jsonString =  br.readLine();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject.getString("img");
        }catch(JSONException e){

        }
        return null;

    }

    // Uses the JSON API to return the number of the most resent comic
    String getLatestComicNr() throws Exception{

        URL url = new URL("https://xkcd.com/info.0.json");
        URLConnection con = url.openConnection();
        InputStream is = null;
        is = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String jsonString =  br.readLine();

        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            return jsonObject.getString("num");
        }catch(JSONException e){

        }
        return null;

    }



    @Override
    protected String doInBackground(Integer... indexes) {


        try {
            if(indexes[0] == -1){
                return getLatestComicNr();
            }else {
                return getComic(indexes[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
