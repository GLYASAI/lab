package org.abewang.lab.air.service.impl;

import org.abewang.lab.air.consumer.AirProducer;
import org.abewang.lab.air.domain.AirReservation;
import org.abewang.lab.air.domain.vo.AirBookReqVO;
import org.abewang.lab.air.domain.vo.AirBookRespVO;
import org.abewang.lab.air.service.AirBookService;
import org.abewang.lab.air.util.JsonUtil;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Author Abe
 * @Date 2018/8/22.
 */
@Service
public class AirBookServiceImpl implements AirBookService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AirBookServiceImpl.class);

    @Value("${rocketmq.topic}")
    private String topic;

    @Autowired
    private AirProducer producer;

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
            LOGGER.debug("bookingMsgResult: {}", bookingMsgResult);
            LOGGER.debug("orderCenterMsgResult: {}", orderCenterMsgResult);
        } catch (Exception e) {
            LOGGER.error("Producer发送消息失败.", e);
        }
    }
}
