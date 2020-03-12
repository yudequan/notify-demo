package com.example.notifydemo.controller;

import com.example.notifydemo.dto.NotifyDTO;
import com.example.notifydemo.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dequan.yu on 2020/3/12.
 */
@Slf4j
@RestController
@RequestMapping("/chw")
public class ChwNotifyController {

    @PostMapping("/receiveNotify")
    public String receiveNotify(@RequestBody NotifyDTO notifyDTO) {
        log.info("程会玩接收通知开始");
        log.info("程会玩接收到同程的通知：{}", GsonUtils.toJson(notifyDTO));
        // 异常场景
        try {
            int result = 1 / 0;
        } catch (Exception e) {
            log.error("程会玩处理通知失败");
            return "FAIL";
        }

        log.info("程会玩接收通知结束");
        return "SUCCESS";
    }
}
