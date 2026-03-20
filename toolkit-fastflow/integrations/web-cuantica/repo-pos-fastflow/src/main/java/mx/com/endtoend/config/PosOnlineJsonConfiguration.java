package mx.com.endtoend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import mx.com.smartbussiness.generic.converters.JsonConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.math.BigDecimal;

/**
 * Configuración JSON para POS-Online
 * Integra las utilidades de serialización del Generic-Model
 * 
 * @author SmartBussiness Team
 * @version 1.0.0
 * @since 2025-09-29
 */
@Configuration
public class PosOnlineJsonConfiguration {

    /**
     * Configuración principal de ObjectMapper para POS-Online
     * Integra serializadores personalizados para BigDecimal con precisión SAT
     * 
     * @return ObjectMapper configurado para cálculos decimales precisos
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        // Usar configuración SAT del Generic-Model
        return JsonConfiguration.createSATCompliantObjectMapper();
    }
}
