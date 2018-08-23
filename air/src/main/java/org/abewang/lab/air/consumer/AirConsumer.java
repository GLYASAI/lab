package org.abewang.lab.air.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.abewang.lab.air.domain.AirReservation;
import org.abewang.lab.air.service.BookingService;
import org.abewang.lab.air.service.OrderCenterService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @Author Abe
 * @Date 2018/8/22.
 */
@Component
public class AirConsumer {
    private final static Logger LOGGER = LoggerFactory.getLogger(AirConsumer.class);

    private DefaultMQPushConsumer consumer;

    @Value("${rocketmq.namesvrAddr}")
    private String namesvrAddr;

    @Value("${rocketmq.consumer.groupName}")
    private String groupName;

    @Value("${rocketmq.topic}")
    private String topic;

    @Value("${rocketmq.tags}")
    private String tags;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private BookingService bookingService;

    @Autowired
    private OrderCenterService orderCenterService;

    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(namesvrAddr);
        /*
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(topic, tags);
        consume();
    }

    public void consume() throws MQClientException {
        consumer.registerMessageListener((List<MessageExt> msgs, ConsumeConcurrentlyContext context) -> {
            boolean result = true;
            for (MessageExt msg : msgs) {
                try {
                    if (msg.getReconsumeTimes() == 3) {  // 消息已经重试了3次，如果不需要再次消费，则返回成功
                        LOGGER.warn("消息*" + msg.toString() + "* 已经重试了3次, 忽略该消息.");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    String messageBody = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                    LOGGER.debug("Message body: {}", messageBody);
                    LOGGER.debug("Tags: {}", msg.getTags());
                    if ("BOOKING_TAG".equals(msg.getTags())) {
                        result = saveBooking(messageBody);
                    } else if ("ORDER_CENTER_TAG".equals(msg.getTags())) {
                        result = sendMsgToOrderCenter(messageBody);
                    }
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error("消息解析失败, 直接返回CONSUME_SUCCESS", e);
                }
            }
            return result ? ConsumeConcurrentlyStatus.CONSUME_SUCCESS : ConsumeConcurrentlyStatus.RECONSUME_LATER;
        });
        // Launch the consumer instance.
        consumer.start();
    }

    private boolean sendMsgToOrderCenter(String messageBody) {
        return orderCenterService.sendBookingMsg(messageBody);
    }

    private boolean saveBooking(String message) {
        AirReservation airReservation = null;
        try {
            airReservation = mapper.readValue(message, AirReservation.class);
        } catch (IOException e) {
            LOGGER.error("*" + message + "* 无法转换成org.abewang.lab.air.domain.AirReservation, 忽略这条消息.", e);
            return true;
        }
        return bookingService.saveBooking(airReservation);
    }
}
