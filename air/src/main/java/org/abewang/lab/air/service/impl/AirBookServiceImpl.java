package org.abewang.lab.air.service.impl;

import org.abewang.lab.air.domain.AirReservation;
import org.abewang.lab.air.domain.vo.AirBookReqVO;
import org.abewang.lab.air.domain.vo.AirBookRespVO;
import org.abewang.lab.air.service.AirBookService;
import org.abewang.lab.air.util.JsonUtil;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author Abe
 * @Date 2018/8/22.
 */
@Service
public class AirBookServiceImpl implements AirBookService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AirBookServiceImpl.class);

    private DefaultMQProducer producer = null;

    @Value("${rocketmq.namesvrAddr}")
    private String metaNamesvrAddr;

    @Value("${rocketmq.producer.groupName}")
    private String groupName;

    @Value("${rocketmq.topic}")
    private String topic;

    @PostConstruct
    public void initMQProducer() {
        producer = new DefaultMQProducer(groupName);
        producer.setNamesrvAddr(metaNamesvrAddr);
        // 消息发送失败重试次数
        producer.setRetryTimesWhenSendFailed(3);
        // 消息没有存储成功是否发送到另外一个broker
        producer.setRetryAnotherBrokerWhenNotStoreOK(true);
        try {
            producer.start();
        } catch (MQClientException e) {
            LOGGER.error("Fail to start air producer");
            throw new RuntimeException(e);
        }
    }

    @Override
    public AirBookRespVO airBook(AirBookReqVO airBookReqVO) {
        AirBookRespVO result = new AirBookRespVO();
        AirReservation airReservation = new AirReservation();
        airReservation.setPnr("AAAAAA");
        result.setAirReservation(airReservation);
        sendMsg(JsonUtil.toJson(airReservation));
        return result;
    }

    public void sendMsg(String msg) {
        Message bookingMsg = new Message(topic, "BOOKING_TAG", msg.getBytes());
        Message orderCenterMsg = new Message(topic, "ORDER_CENTER_TAG", msg.getBytes());
        try {
            SendResult bookingMsgResult = producer.send(bookingMsg);
            SendResult orderCenterMsgResult = producer.send(orderCenterMsg);
            System.out.printf("%s%n", bookingMsgResult);
            System.out.printf("%s%n", orderCenterMsgResult);
        } catch (Exception e) {
            LOGGER.error("Producer发送消息失败.", e);
        }
    }

    @PreDestroy
    public void shutdownProducer() {
        if (producer != null) {
            producer.shutdown();
        }
    }

}
