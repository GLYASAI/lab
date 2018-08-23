package org.abewang.lab.air.consumer;

import org.abewang.lab.air.domain.AirReservation;
import org.abewang.lab.air.service.BookingService;
import org.abewang.lab.air.service.OrderCenterService;
import org.abewang.lab.air.util.JsonUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

/**
 * @Author Abe
 * @Date 2018/8/22.
 */
@Component
public class AirConsumer {
    private final static Logger LOGGER = LoggerFactory.getLogger(AirConsumer.class);

    private DefaultMQPushConsumer consumer;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private OrderCenterService orderCenterService;

    @Value("${metaNamesvrAddr}")
    private String metaNamesvrAddr;

    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer("air-producer-group");
        consumer.setNamesrvAddr(metaNamesvrAddr);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe("AIR_TOPIC", "BOOKING_TAG || ORDER_CENTER_TAG");
        consume();
    }

    public void consume() throws MQClientException {
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, consumeConcurrentlyContext) -> {
            System.out.println(Thread.currentThread().getName() + " Receive new message: " + msgs + "%n");
            msgs.forEach(msg -> {
                try {
                    String messageBody = new String(msg.getBody(), RemotingHelper.DEFAULT_CHARSET);
                    System.out.println(messageBody);
                    System.out.println(msg.getTags());
                    if ("BOOKING_TAG".equals(msg.getTags())) {
                        saveBooking(messageBody);
                    } else if ("ORDER_CENTER_TAG".equals(msg.getTags())) {
                        sendMsgToOrderCenter(messageBody);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace(); // TODO
                }
            });
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // Launch the consumer instance.
        consumer.start();
    }

    private boolean sendMsgToOrderCenter(String messageBody) {
        return orderCenterService.sendBookingMsg(messageBody);
    }

    private boolean saveBooking(String message) {
        AirReservation airReservation = JsonUtil.toPojo(message, AirReservation.class);
        return bookingService.saveBooking(airReservation);
    }
}
