package com.example.notifydemo.controller;

import com.example.notifydemo.dto.NotifyDTO;
import com.example.notifydemo.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by dequan.yu on 2020/3/12.
 */
@Slf4j
@RestController
@RequestMapping("/tc")
public class TcNotifyController {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build();

    private ExecutorService es = new ThreadPoolExecutor(8, 8,
            0, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024));

    @PostMapping("/sendNotify")
    public String sendNotify(@org.springframework.web.bind.annotation.RequestBody NotifyDTO notifyDTO) {
        log.info("[通知{}]同程——>程会玩...发送开始", notifyDTO.getNotifyId());
        String json = GsonUtils.toJson(notifyDTO);

        try {
            es.execute(() -> {
                try {
                    String url = "http://localhost:8080/chw/receiveNotify";
                    log.info("[通知{}]同程——>程会玩...【真的】发送开始", notifyDTO.getNotifyId());
                    String result = this.post(url, json);
                    int count = 1;
                    // 返回不成功并且重试次数小于5次
                    while (!"SUCCESS".equals(result) && count  < 6) {
                        long second = count * 60;
                        log.error("[通知{}]同程——>程会玩...result:{}，程会玩接收通知失败，延时{}s后再次发送通知", notifyDTO.getNotifyId(), result,
                                second);
                        Thread.sleep(second * 1000);
                        count++;
                        log.info("[通知{}]同程开始尝试第{}次发送", notifyDTO.getNotifyId(), count);
                        result = this.post(url, json);
                    }

                    if (!"SUCCESS".equals(result)) {
                        log.warn("[通知{}]以达到最大重发次数", notifyDTO.getNotifyId());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            log.warn(e.getMessage());
        }

        log.info("[通知{}]同程——>程会玩...发送结束", notifyDTO.getNotifyId());
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
        } catch (Exception e) {
            log.warn(e.getMessage());
            return "FAIL";
        }
    }
}
