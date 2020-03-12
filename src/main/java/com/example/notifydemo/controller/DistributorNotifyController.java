package com.example.notifydemo.controller;

import com.example.notifydemo.dto.NotifyDTO;
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
@RequestMapping("/distributor")
public class DistributorNotifyController {
    @PostMapping("/receiveNotify")
    public String receiveNotify(@RequestBody NotifyDTO notifyDTO) {
        log.info("分销商接收通知开始");

        log.info("分销商接收通知结束");
        return "SUCCESS";
    }
}
