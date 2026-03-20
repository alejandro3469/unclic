package mx.com.endtoend.infrastructure.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEventPublisher implements AuthenticationEventPublisher {

    private final Logger LOG = LoggerFactory.getLogger(CustomAuthenticationEventPublisher.class);
    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishAuthenticationSuccess(Authentication authentication) {
        LOG.info("Autenticación exitosa para: {}", authentication.getName());
        applicationEventPublisher.publishEvent(new AuthenticationSuccessEvent(authentication));
    }

    @Override
    public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        LOG.info("Fallo de autenticación para: {} -- Causa: {}",
                authentication.getName(), exception.getMessage());
        if (exception instanceof BadCredentialsException) {
            try {
                userDetailService.ChangeSessionStatus(authentication.getName());
                LOG.warn("Contraseña incorrecta para el usuario: {}", authentication.getName());
                applicationEventPublisher.publishEvent(
                        new AuthenticationFailureBadCredentialsEvent(authentication, exception)
                );
            } catch (Exception e) {
                LOG.error("Error al actualizar sesion por autentificacion erronea autenticación para: {} -- Causa: {}",
                        authentication.getName(), exception.getMessage());
            }
        }else{
            applicationEventPublisher.publishEvent(
                    new AbstractAuthenticationFailureEvent(authentication, exception) {}
            );
        }
    }
}
