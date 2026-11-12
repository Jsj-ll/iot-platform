package com.iot.platform.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.platform.document.DeviceData;
import com.iot.platform.repository.DeviceDataRepository;
import com.iot.platform.service.DeviceDataProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private DeviceDataRepository repository;

    @Autowired
    private DeviceDataProducer producer;

    @Autowired
    private ObjectMapper objectMapper;

    // 模拟设备上报数据（不直接存库，先发 Kafka）
    @PostMapping("/report")
    public String report(@RequestBody DeviceData data) {
        try {
            String json = objectMapper.writeValueAsString(data);
            producer.send(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "已入队"; // 接口瞬间返回
    }

    @GetMapping("/history/{deviceId}")
    public List<DeviceData> history(@PathVariable Long deviceId) {
        return repository.findByDeviceId(deviceId);
    }
}