package org.abewang.lab.air.consumer;

import org.abewang.lab.air.service.impl.AirBookServiceImpl;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Author Abe
 * @Date 2018/8/23.
 */
@Component
public class AirProducer {
    private final static Logger LOGGER = LoggerFactory.getLogger(AirBookServiceImpl.class);

    private DefaultMQProducer producer;

    @Value("${rocketmq.namesvrAddr}")
    private String metaNamesvrAddr;

    @Value("${rocketmq.producer.groupName}")
    private String groupName;

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

    public SendResult send(Message message) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        return producer.send(message);
    }

    @PreDestroy
    public void shutdownProducer() {
        if (producer != null) {
            producer.shutdown();
        }
    }
}
