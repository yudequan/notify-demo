package com.example.notifydemo.controller;

import com.example.notifydemo.dto.NotifyDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
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
        log.info("[通知{}]程会玩接收开始", notifyDTO.getNotifyId());
        // log.info("程会玩接收到同程的通知：{}", GsonUtils.toJson(notifyDTO));
        // 异常场景
        try {
            int random = RandomUtils.nextInt(0, 2);
            log.info("[通知{}]random: {}", notifyDTO.getNotifyId(), random);
            int result = 1 / random;
        } catch (Exception e) {
            log.error("[通知{}]程会玩处理失败", notifyDTO.getNotifyId());
            return "FAIL";
        }

        log.info("[通知{}]程会玩接收结束", notifyDTO.getNotifyId());
        return "SUCCESS";
    }
}
