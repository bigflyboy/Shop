package com.visionin.shop.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.server.AsyncHttpServer;
import com.koushikdutta.async.http.server.AsyncHttpServerRequest;
import com.koushikdutta.async.http.server.AsyncHttpServerResponse;
import com.koushikdutta.async.http.server.HttpServerRequestCallback;
import com.visionin.shop.R;

import java.util.ArrayList;
import java.util.List;

public class BigScreenActivity extends BaseActivity {

    private static final String TAG = "BigScreenActivity";

    private AsyncHttpServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_screen);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        server.stop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        server = new AsyncHttpServer();

        List<WebSocket> _sockets = new ArrayList<WebSocket>();

        server.get("/", new HttpServerRequestCallback() {
            @Override
            public void onRequest(AsyncHttpServerRequest request, AsyncHttpServerResponse response) {
                response.send("Hello!!!");
            }
        });

// listen on port 5000
        server.listen(5005);
// browsing http://localhost:5000 will return Hello!!!

    }
}
