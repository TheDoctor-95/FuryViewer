package com.furyviewer.service;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class ImageService {

    public byte[] toImage(String url) throws Exception {
        Request request = new Request.Builder()
            .url(url)
            .build();
        OkHttpClient client =new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }

            return IOUtils.toByteArray(response.body().byteStream());
        }
    }
}
