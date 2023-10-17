package com.example.marvelapi.data.remote;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;


import java.net.URL;

import javax.xml.transform.Result;

import cafsoft.foundation.Data;

import cafsoft.foundation.HTTPURLResponse;
import cafsoft.foundation.URLComponents;
import cafsoft.foundation.URLQueryItem;
import cafsoft.foundation.URLSession;
import cafsoft.foundation.URLSessionDataTask;

public class MarvelApi {
    public void requestCharacterInfo(String name,
                                     TextCompletionHandler textCompletionHandler){
        var components = new URLComponents();
        components.setPort(-1);
        components.setScheme("https");
        components.setHost("gateway.marvel.com");
        components.setPath("/v1/public/characters");
       


        components.setQueryItems(new URLQueryItem[]{
                new URLQueryItem("hash","3ca3a5e309439ea38f05c3cf7fcfc3ea"),
                new URLQueryItem("ts","1500"),
                new URLQueryItem("apikey","2aebde68b0fc7f52b888a70791424a66"),
                new URLQueryItem("name",name),

        });

        // Generate the URL from the components
        URL url = components.getURL();

        // Get Default URLSession
        var session = URLSession.getShared();

        var task = createDataTask(url, session, (errorCode, data) -> {
            String text = null;
            if(data != null)
                text = data.toText();

            textCompletionHandler.run(errorCode, text);
        });

        task.resume();
    }

    public void requestImage(String stringURL,
                             ImageCompletionHandler completionHandler){
        var components = new URLComponents(stringURL);
        components.setScheme("https");

        var url = components.getURL();
        var session = URLSession.getShared();

        var task = createDataTask(url, session, (errorCode, data) -> {
            Bitmap bitmap = null;
            if (data != null)
                bitmap = BitmapFactory.decodeByteArray(data.toBytes(), 0, data.length());

            completionHandler.run(errorCode, bitmap);
        });

        task.resume();
    }


    public URLSessionDataTask createDataTask(URL url, URLSession session,
                                             DataCompletionHandler dataCompletionHandler){
        var task = session.dataTask(url, (data, response, error) -> {

            if (error != null){
                dataCompletionHandler.run(-1, null);
                return;
            }

            if (response instanceof HTTPURLResponse){
                var httpResponse = (HTTPURLResponse) response;

                dataCompletionHandler.run(httpResponse.getStatusCode(), data);
            }
        });

        return task;
    }

    public interface DataCompletionHandler {
        void run (int errorCode, Data data);
    }

    public interface TextCompletionHandler {
        void run(int errorCode, String text);
    }

    public interface ImageCompletionHandler {
        void run (int errorCode, Bitmap bitmap);
    }

}