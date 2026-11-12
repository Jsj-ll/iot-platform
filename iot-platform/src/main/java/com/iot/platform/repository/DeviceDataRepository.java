package com.iot.platform.repository;

import com.iot.platform.document.DeviceData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DeviceDataRepository extends MongoRepository<DeviceData, String> {
    // 注意这里：参数必须是 Long，和设备类里的 deviceId 类型保持一致
    List<DeviceData> findByDeviceId(Long deviceId);
}