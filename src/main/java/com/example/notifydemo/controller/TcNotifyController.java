package com.example.notifydemo.controller;

import com.example.notifydemo.dto.NotifyDTO;
import com.example.notifydemo.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by dequan.yu on 2020/3/12.
 */
@Slf4j
@RestController
@RequestMapping("/tc")
public class TcNotifyController {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    @PostMapping("/sendNotify")
    public String sendNotify() {
        log.info("同程——>程会玩...开始");
        NotifyDTO notifyDTO = new NotifyDTO("支付成功");
        String json = GsonUtils.toJson(notifyDTO);
        try {
            String url = "http://localhost:8080/chw/receiveNotify";
            String result = this.post(url, json);
            int count = 1;
            while (!"SUCCESS".equals(result)) {
                long second = count * 10;
                log.error("程会玩接收通知失败，延时{}s后再次发送通知", second);
                Thread.sleep(second * 1000);
                count++;
                log.info("同程开始尝试第{}次发送", count);
                result = this.post(url, json);
            }
            log.info("同程——>程会玩...result:{}", result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("同程——>程会玩...结束");
        return "SUCCESS";
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
