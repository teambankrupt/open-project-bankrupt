package com.example.common.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public final class NetworkUtil {

    public static void postData(String url, String body, String authorization) throws IOException {
        new Thread(() -> {

            CloseableHttpClient client = HttpClients.createDefault();
            String newUrl = url.replace(" ", "%20");
            HttpPost httpPost = new HttpPost(newUrl);

            if (body != null && !body.isEmpty()) {
                StringEntity entity = new StringEntity(body, "UTF-8");
                httpPost.setEntity(entity);
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setHeader("Authorization", authorization);
            }
            try {
                CloseableHttpResponse response = client.execute(httpPost);
                client.close();
                response.close();
            } catch (IOException ignored) {

            }

        }).start();
//        HttpEntity e = response.getEntity();
//        InputStream is = e.getContent();
    }

}
