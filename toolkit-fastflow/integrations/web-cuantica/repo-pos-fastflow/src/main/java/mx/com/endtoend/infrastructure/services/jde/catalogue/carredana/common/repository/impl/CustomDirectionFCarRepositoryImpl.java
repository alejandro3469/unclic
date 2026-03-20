package mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;

import mx.com.endtoend.domain.catalogue.dto.address.ColonyDto;
import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityCPDto;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.entities.QF0117;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.entities.QF0118;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.repository.CustomDirectionFCarRepository;

@Repository
@ConditionalOnProperty(name = "app.carredana.oracle.enabled", havingValue = "true", matchIfMissing = false)
public class CustomDirectionFCarRepositoryImpl implements CustomDirectionFCarRepository {

    private final Logger LOG = LoggerFactory.getLogger(CustomDirectionFCarRepositoryImpl.class);
    @Autowired
    @Qualifier("carredanaOracleEntityManagerFactory")
    EntityManager em;

    public CustomDirectionFCarRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public List<ColonyDto> getColonyList(String stateCode) {
        LOG.info("INIT getColonyList()");
        JPAQuery<ColonyDto> query = new JPAQuery<>(em);
        QF0117 F0117 = QF0117.f0117;
        QF0118 F0118 = QF0118.f0118;
        /*
        *Start evaluation of tables
        */
        query.from(F0117).join(F0118).on(F0117.id.a8addz.eq(F0118.a7addz));
        /*
         * Conditions to get list
         */
        query.where(F0117.a8adds.trim().eq(stateCode.trim()));

        query.select(Projections.constructor(ColonyDto.class,
                F0117.id.a8addz,
                F0118.a7add4,
                F0117.a8adds,
                F0117.id.a8cty1
        ));
        return query.fetch();
    }

    @Override
    public List<String> getMunicipalityNameListByStateCode(String stateCode) {
        LOG.info("INIT getMunicipalityNameListByStateCode()");
        JPAQuery<String> query = new JPAQuery<>(em);
        QF0117 F0117 = QF0117.f0117;
        QF0118 F0118 = QF0118.f0118;
        /*
         *Start evaluation of tables
         */
        query.from(F0117).join(F0118).on(F0117.id.a8addz.eq(F0118.a7addz));
        /*
         * Conditions to get list
         */
        query.where(F0117.a8adds.trim().eq(stateCode.trim()));
        query.select(F0118.a7cty1).distinct();
        return query.fetch();
    }

    @Override
    public List<MunicipalityCPDto> getMunicipalityAndCpList(String stateCode) {
    	LOG.info("INIT MunicipalityCPDto()");
        JPAQuery<MunicipalityCPDto> query = new JPAQuery<>(em);
        QF0117 F0117 = QF0117.f0117;
        QF0118 F0118 = QF0118.f0118;
        /*
        *Start evaluation of tables
        */
        query.from(F0117).join(F0118).on(F0117.id.a8addz.eq(F0118.a7addz));
        /*
         * Conditions to get list
         */
        query.where(F0117.a8adds.trim().eq(stateCode.trim()));
        query.groupBy(F0117.id.a8addz, F0118.a7cty1);
        query.select(Projections.constructor(MunicipalityCPDto.class,
        		F0118.a7cty1, 
        		F0117.id.a8addz));
    	
        return query.fetch();
    }
}
