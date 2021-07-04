package com.nj.learn.nio;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 自定义客户端
 * 通过OkHttpClient实现
 */
public class TestOkHttpClient {

    public static void main(String[] args) {
        String url = "http://localhost:8801";

        TestOkHttpClient testOkHttpClient = new TestOkHttpClient();
        try {
            String result = testOkHttpClient.run(url);
            System.out.println("访问成功，访问结果："+ result);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("访问出错");
        }

    }

    /**
     * get访问
     * @param url
     * @return
     * @throws IOException
     */
    String run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
