package com.example.sunny.xkcd_app;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class GetUrlByString extends AsyncTask<String, Void, Integer>  {

    // Pretty hacky method that returns the same comic as https://relevantxkcd.appspot.com with the same search string.
    // Needs more testing, bugs likely.
    // Still no problems encountered.
    Integer getComicURL(String searchString) throws Exception {
        // Make a URL to the web page
        String urlString = "https://relevantxkcd.appspot.com/process?action=xkcd&query=" + searchString;
        urlString = urlString.replaceAll(" ", "%20");

        URL url = new URL(urlString);

        // Get the input stream through URL Connection
        URLConnection con = url.openConnection();
        InputStream is = null;
        is = con.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String searchResults;

        while((searchResults = br.readLine()) != null) {

            // Finds the first line containing a comic nr and splitts it from the rest of the string.
            if(searchResults.contains("/wiki/")) { // first line containing /wiki/ also has the comicNR as the head of the String separated by " "
                String[] stringArr = searchResults.split(" ");
                return Integer.parseInt(stringArr[0]);
                // This number is later used to get the comic and display it
            }


        }


return null;
    }

        @Override
    protected Integer doInBackground(String... strings) {


            try {
                return getComicURL(strings[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
}
