package com.iot.platform.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.platform.document.DeviceData;
import com.iot.platform.repository.DeviceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeviceDataConsumer {
    @Autowired
    private DeviceDataRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "device-data-topic", groupId = "iot-group")
    public void consume(String message) {
        try {
            DeviceData data = objectMapper.readValue(message, DeviceData.class);
            data.setReportTime(LocalDateTime.now());
            repository.save(data);
            System.out.println(">>>>>> 消费了一条数据：" + data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}