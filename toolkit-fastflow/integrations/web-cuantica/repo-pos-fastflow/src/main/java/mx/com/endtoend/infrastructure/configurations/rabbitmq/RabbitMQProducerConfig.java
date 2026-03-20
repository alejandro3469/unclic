package mx.com.endtoend.infrastructure.configurations.rabbitmq;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de RabbitMQ para el Producer (pos-online)
 * 
 * Esta configuración establece el uso de JSON para la serialización de mensajes
 * en lugar de la serialización Java nativa, lo que resuelve problemas de
 * NoClassDefFoundError y mejora la compatibilidad entre versiones.
 * 
 * @author Sistema POS Online
 * @version 1.0
 * @since 2025-10-11
 */
@Configuration
@ConditionalOnBean(ConnectionFactory.class)
public class RabbitMQProducerConfig {
    
    /**
     * Configura el convertidor de mensajes para usar JSON
     * 
     * @return Jackson2JsonMessageConverter configurado para serialización JSON
     */
    @Bean("producerJsonMessageConverter")
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    /**
     * Configura el RabbitTemplate para usar el convertidor JSON
     * 
     * @param connectionFactory Factory de conexiones RabbitMQ
     * @return RabbitTemplate configurado con serialización JSON
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }
}
