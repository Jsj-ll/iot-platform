package com.iot.platform.controller;

import com.iot.platform.entity.Device;
import com.iot.platform.mapper.DeviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 1. 查询所有设备（暂时不缓存）
    @GetMapping("/list")
    public List<Device> list() {
        return deviceMapper.selectList(null);
    }

    // 2. 新增设备
    @PostMapping("/add")
    public String add(@RequestBody Device device) {
        deviceMapper.insert(device);
        return "添加成功";
    }

    // 3. 根据 ID 查询设备（演示 Redis 缓存）
    @GetMapping("/get/{id}")
    public Device getById(@PathVariable Long id) {
        String key = "device:info:" + id;

        // 第一步：先查 Redis
        Device cachedDevice = (Device) redisTemplate.opsForValue().get(key);
        if (cachedDevice != null) {
            System.out.println(">>>>>> 命中缓存，直接返回！");
            return cachedDevice;
        }

        // 第二步：缓存没有，查数据库
        System.out.println(">>>>>> 缓存未命中，查询数据库...");
        Device device = deviceMapper.selectById(id);

        // 第三步：查到数据，写入缓存，设置 10 分钟过期
        if (device != null) {
            redisTemplate.opsForValue().set(key, device, 10, TimeUnit.MINUTES);
        }
        return device;
    }
}