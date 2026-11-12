package com.iot.platform.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
@Data
@Document(collection = "device_data")
public class DeviceData {
    @Id
    private String Id;
    private Long deviceId;
    private Double temperature;
    private LocalDateTime reportTime;
}
