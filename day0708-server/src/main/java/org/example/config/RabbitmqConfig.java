package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@Slf4j
public class RabbitmqConfig {
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * PublishSubscribeQueueA 队列名称
     * PublishSubscribeExchange 交换机名称
     * @return
     */
    @PostConstruct
    public void initRabbitMq(){
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                if(ack){
                    log.info("消息发送成功");
                }else{
                    log.error("消息发送失败");
                    log.error("失败原因="+cause);
                }
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                if(replyCode==200){
                    log.info("消息发送成功");
                }else{
                    log.error("消息发送失败");
                    rabbitTemplate.convertAndSend(exchange,routingKey,message);
                }
            }
        });
    }
    @Bean
    public Queue orderQueue() {
        return new Queue("orderQueue",true);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }
    @Bean
    public Binding orderQueueBindingDirectExchange() {
        return BindingBuilder.bind(orderQueue()).to(directExchange()).with("order");
    }
}
