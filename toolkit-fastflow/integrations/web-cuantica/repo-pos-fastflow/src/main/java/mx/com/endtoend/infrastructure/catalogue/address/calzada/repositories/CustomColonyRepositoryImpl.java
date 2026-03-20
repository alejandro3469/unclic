package mx.com.endtoend.infrastructure.catalogue.address.calzada.repositories;

import mx.com.endtoend.domain.catalogue.dto.address.GenericSearchDirectionDto;
import mx.com.endtoend.infrastructure.catalogue.address.entities.ColonyEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomColonyRepositoryImpl implements CustomColonyRepository {

    private final Logger LOG = LoggerFactory.getLogger(CustomColonyRepositoryImpl.class);
    @Autowired
    @Qualifier("calzadaDataEntityManagerFactory")
    EntityManager em;

    public CustomColonyRepositoryImpl(EntityManager entityManager) {
        this.em = entityManager;
    }

    @Override
    public List<ColonyEntity> findByParams(GenericSearchDirectionDto genericSearchDirectionDto) {
        LOG.info("INIT findByParams()");

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ColonyEntity> query = cb.createQuery(ColonyEntity.class);

        Root<ColonyEntity> colonyEntity = query.from(ColonyEntity.class);
        List<Predicate> conditionsPredicate = new ArrayList<Predicate>();

        /*
         * Start evaluation of search params
         */
        if (genericSearchDirectionDto.getCp() != null) {
            if (!genericSearchDirectionDto.getCp().isEmpty()) {
                LOG.info("FIND BY CP: " + genericSearchDirectionDto.getCp());
                conditionsPredicate
                        .add(cb.equal(cb.trim(colonyEntity.get("cp")), genericSearchDirectionDto.getCp()));
            }
        }
        if (genericSearchDirectionDto.getStateCode() != null) {
            if(!genericSearchDirectionDto.getStateCode().isEmpty()){
                LOG.info("FIND BY STATE CODE: " + genericSearchDirectionDto.getStateCode());
                conditionsPredicate
                        .add(cb.equal(cb.trim(colonyEntity.get("stateCode")), genericSearchDirectionDto.getStateCode()));
            }
        }
        query.where(conditionsPredicate.toArray(new Predicate[0]));
        return em.createQuery(query).getResultList();
    }
}
