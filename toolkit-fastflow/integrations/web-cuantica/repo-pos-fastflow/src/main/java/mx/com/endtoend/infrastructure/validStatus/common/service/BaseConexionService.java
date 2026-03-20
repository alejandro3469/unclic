package mx.com.endtoend.infrastructure.validStatus.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseConexionService {

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    protected boolean validarConexion(Runnable conexionCheck, String origen) {
        try {
            conexionCheck.run();
            return true;
        } catch (Exception e) {
            LOG.error("Error en conexión a " + origen + ": " + e.getMessage());
            return false;
        }
    }
}
