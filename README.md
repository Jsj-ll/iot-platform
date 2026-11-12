\# IoT-Platform 物联网设备监控与数据采集平台



\## 📖 项目简介

本项目是一个基于 Spring Boot 3.x 构建的物联网后端服务平台，旨在解决海量设备接入、实时状态监控以及高频设备日志上报的问题。



\## 🛠️ 技术栈

\- \*\*核心框架\*\*: Spring Boot 3.3.5, Java 17

\- \*\*持久层框架\*\*: MyBatis-Plus

\- \*\*数据存储\*\*: MySQL (设备元数据), MongoDB (海量设备时序日志)

\- \*\*缓存中间件\*\*: Redis (高频设备状态缓存)

\- \*\*消息队列\*\*: Kafka (设备数据异步削峰与解耦)



\## ⚡ 核心架构亮点

1\. \*\*分层存储\*\*: 核心业务数据使用 MySQL 保证一致性；海量上报日志使用 MongoDB 解决写入瓶颈，实现冷热数据分离。

2\. \*\*缓存优化\*\*: 针对高频设备查询，采用 Cache-Aside 模式引入 Redis 缓存，并自定义 JSON 序列化解决乱码问题，大幅提升 QPS。

3\. \*\*异步削峰\*\*: 设备数据上报接口不直接落库，而是作为生产者将数据推入 Kafka。后端通过 `@KafkaListener` 异步消费落盘，有效保护底层数据库，实现系统高可用。



\## 🚀 快速开始

1\. 启动 MySQL, Redis, MongoDB, Kafka (参考 docker-compose 配置)。

2\. 修改 `application.properties` 中的连接信息。

3\. 运行 `IotPlatformApplication`。

