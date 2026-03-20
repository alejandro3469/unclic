package mx.com.endtoend.infrastructure.client.common.repository;

import com.querydsl.jpa.impl.JPAQuery;
import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.infrastructure.client.common.entities.ClientEntity;
import mx.com.endtoend.infrastructure.client.common.entities.QClientEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public class BaseCustomDslClientRepository extends QuerydslRepositorySupport {

    private final Logger LOG;
    private final EntityManager em;


    public BaseCustomDslClientRepository(Class<?> loggerClass, EntityManager _em) {
        super(ClientEntity.class);
        LOG = LoggerFactory.getLogger(loggerClass);
        this.em = _em;
    }

    public List<ClientEntity> findClientListByParams(FiltersClientDto filtersClientDto) {

        JPAQuery<ClientEntity> query = new JPAQuery<>(em);

        QClientEntity clientEntity = QClientEntity.clientEntity;

        /**
         * Start evaluation of table
         */

        query.from(clientEntity);

        /**
         * Start evaluation of search parameters
         */

        if (filtersClientDto.getName() != null ? !filtersClientDto.getName().isEmpty() : false) {
            LOG.info("FIND BY BUSINESS-NAME " + filtersClientDto.getName());
            String fullName = filtersClientDto.getName().replace(' ', '%');
            LOG.info("FIND BY BUSINESS-NAME " + fullName);
            query.where(clientEntity.businessName
                    .like("%" + fullName + "%")
                    .or(clientEntity.name.concat(clientEntity.fatherSurname).concat(clientEntity.motherSurname)
                            .like("%" + fullName + "%"))
                    .or(clientEntity.name
                            .like("%" + fullName + "%"))
                    .or(clientEntity.fatherSurname
                            .like("%" + fullName + "%"))
                    .or(clientEntity.motherSurname
                            .like("%" + fullName + "%")));
        }
        if (filtersClientDto.getNoClient() != null) {
            LOG.info("FIND BY CLIENT NUMBER " + filtersClientDto.getNoClient());
            query.where(clientEntity.noClient.eq(filtersClientDto.getNoClient()));
        }
        if (filtersClientDto.getRfc() != null ? !filtersClientDto.getRfc().isEmpty() : false) {
            LOG.info("FIND BY RFC " + filtersClientDto.getRfc());
            query.where(clientEntity.rfc.like("%" + filtersClientDto.getRfc() + "%"));
        }
        return query.fetch();
    }
}