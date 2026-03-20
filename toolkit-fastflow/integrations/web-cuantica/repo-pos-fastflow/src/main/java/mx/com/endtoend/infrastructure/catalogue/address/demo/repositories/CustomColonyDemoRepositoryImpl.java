package mx.com.endtoend.infrastructure.catalogue.address.demo.repositories;

import mx.com.endtoend.domain.catalogue.dto.address.GenericSearchDirectionDto;
import mx.com.endtoend.infrastructure.catalogue.address.entities.ColonyEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomColonyDemoRepositoryImpl implements CustomColonyDemoRepository {

    @PersistenceContext(unitName = "demoDataEntityManagerFactory")
    private EntityManager entityManager;

    @Override
    public List<ColonyEntity> findByParams(GenericSearchDirectionDto genericSearchDirectionDto) {
        // Para DEMO, retornamos una lista vacía por simplicidad
        // En una implementación real, aquí iría la lógica de búsqueda personalizada
        return new ArrayList<>();
    }
}
