package com.example.webservice.commons.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class NetworkUtil {
    public static String getClientIP() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

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

    public static boolean sendSms(String phoneNumber, String message) {
        String phone = phoneNumber.startsWith("88") ? phoneNumber : "88" + phoneNumber;
        String url = "http://brandsms.mimsms.com/smsapi?api_key=C20028605c20e34fb09258.11734548&type=text&contacts=" + phone +
                "&senderid=8801847169884&msg=" + message;
        try {
            NetworkUtil.postData(url, null, null);
            return true;
        } catch (IOException e) {
            System.out.println("Could not send SMS. " + e.getMessage());
            return false;
        }
    }
}
