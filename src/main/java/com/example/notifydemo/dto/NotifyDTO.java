package com.example.notifydemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by dequan.yu on 2020/3/12.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotifyDTO {
    private Integer notifyId;
    private String content;
}
