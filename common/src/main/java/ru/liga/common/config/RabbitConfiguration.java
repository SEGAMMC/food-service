package ru.liga.common.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@EnableRabbit
@Configuration
@PropertySource("classpath:application.yaml")
public class RabbitConfiguration {

    @Value("${rabbitMQ.host}")
    private String host;

    @Value("${rabbitMQ.port}")
    private int port;

    @Value("${rabbitMQ.username}")
    private String username;

    @Value("${rabbitMQ.password}")
    private String password;

    /**
     * Создание подключения к RabbitMQ.
     * Для создания соединения необходимо
     * host, Username, password к RabbitMQ
     *
     * @return возвращает соединение типа ConnectionFactory
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setPort(port);
        cachingConnectionFactory.setUsername(username);
        cachingConnectionFactory.setPassword(password);
        return cachingConnectionFactory;
    }

    /**
     * Занимается обслуживанием очередей, обменниками, сообщениями
     */
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    /**
     * Основной класс для отправки сообщения, так же имеет
     * гибкие настройки, такие как явное указание типа конвертации.
     *
     * @return возвращает соединение типа ConnectionFactory
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return new RabbitTemplate(connectionFactory());
    }
}