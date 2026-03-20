package mx.com.endtoend.infrastructure.services.jde.clients.calzada.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F0101;
import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.QF0101;
import mx.com.endtoend.infrastructure.services.jde.clients.calzada.repository.CustomF0101Repository;

@Repository
@ConditionalOnProperty(name = "app.calzadaOracle.enabled", havingValue = "true", matchIfMissing = false)
public class CustomF0101RepositoryImpl implements CustomF0101Repository {

    private final Logger LOG = LoggerFactory.getLogger(CustomF0101RepositoryImpl.class);
    @Autowired
    @Qualifier("calzadaOracleEntityManagerFactory")
    EntityManager em;

    public CustomF0101RepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public List<F0101> findByParams(FiltersClientDto filtersClientDto) {
        LOG.info("INIT findByParams()");
        JPAQuery<F0101> query = new JPAQuery<>(em);
        QF0101 F0101 = QF0101.f0101;
        query.from(F0101);

        if (filtersClientDto.getNoClient() != null){
            LOG.info("FIND BY CLIENT NUMBER: " + filtersClientDto.getNoClient());
            query.where(F0101.noClient.eq(filtersClientDto.getNoClient()));
        }
        if(filtersClientDto.getRfc() != null){
            if (!filtersClientDto.getRfc().isEmpty()){
                LOG.info("FIND BY RFC: " + filtersClientDto.getRfc());
                query.where(F0101.rfc.like("%" + filtersClientDto.getRfc() + "%" ));
            }
        }
        if(filtersClientDto.getName() != null){
            if (!filtersClientDto.getName().isEmpty()){
                LOG.info("FIND BY NAME: " + filtersClientDto.getName());
                query.where(F0101.name.like("%" + filtersClientDto.getName() + "%"));
            }
        }
        LOG.info("FIND BY ABAT1: C ");
        query.where(F0101.abat1.trim().eq("C"));
        return query.fetch();
    }
}