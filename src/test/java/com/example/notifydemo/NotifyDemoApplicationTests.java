package com.example.notifydemo;

import com.example.notifydemo.dto.NotifyDTO;
import com.example.notifydemo.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@Slf4j
@SpringBootTest
class NotifyDemoApplicationTests {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    @Test
    void contextLoads() {
    }

    @Test
    public void sendPost() {
        String url = "http://localhost:8080/tc/sendNotify";
        NotifyDTO notifyDTO;
        try {
            for (int i = 0; i < 100; i++) {
                Thread.sleep(2000);
                notifyDTO = new NotifyDTO(i, "支付成功");
                log.info("发送请求" + (i + 1));
                this.post(url, GsonUtils.toJson(notifyDTO));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
