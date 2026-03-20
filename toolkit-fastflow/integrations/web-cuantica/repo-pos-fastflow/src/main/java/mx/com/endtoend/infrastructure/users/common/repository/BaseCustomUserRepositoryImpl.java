package mx.com.endtoend.infrastructure.users.common.repository;

import mx.com.endtoend.domain.users.dto.GenericSerchParamsUserDto;
import mx.com.endtoend.infrastructure.branch.common.entities.BranchEntity;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.company.common.entities.CompanyEntity;
import mx.com.endtoend.infrastructure.users.common.entities.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@MappedSuperclass
public class BaseCustomUserRepositoryImpl implements CustomUserRepository {

    @Qualifier("centralDataEntityManagerFactory")
    private final EntityManager em;
    private final Logger LOG;

    public BaseCustomUserRepositoryImpl(EntityManager entityManager, Class<?> loggerClass) {
        em = entityManager;
        LOG = LoggerFactory.getLogger(loggerClass);
    }

    @Override
    public List<UserEntity> findUserLitsByParams(GenericSerchParamsUserDto genericSerchParamsUserDto) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);

        Root<UserEntity> userEntity = query.from(UserEntity.class);
        Join<UserEntity, BranchEntity> joinBranchEntity = userEntity.join("branch", JoinType.INNER);
        Join<BranchEntity, CompanyEntity> joinCompanyEntity = joinBranchEntity.join("company", JoinType.INNER);

        List<Predicate> conditionsPredicate = new ArrayList<Predicate>();

        /**
         * Start evaluation of serch parameters
         */

        LOG.info("INIT EVALUATIO PARAMS");

        if (genericSerchParamsUserDto.getIsEnable()) {
            LOG.info("FIND BY USERS ENABLE");
            conditionsPredicate.add(cb.equal(userEntity.get("enabled"), true));

        } else {
            LOG.info("FIND BY USERS DISABLE");
            conditionsPredicate.add(cb.equal(userEntity.get("enabled"), false));
        }

        if (genericSerchParamsUserDto.getUserNumber() != null) {
            LOG.info("FIND BY USER-NUMBER");
            conditionsPredicate.add(cb.equal(userEntity.get("userNumber"), genericSerchParamsUserDto.getUserNumber()));
        }

        if (genericSerchParamsUserDto.getBranchCode() != null) {
            if (!genericSerchParamsUserDto.getBranchCode().isEmpty()) {
                LOG.info("FIND BY BRANCHE-CODE");
                conditionsPredicate
                        .add(cb.equal(joinBranchEntity.get("code"), genericSerchParamsUserDto.getBranchCode()));
            }
        }

        if (genericSerchParamsUserDto.getIsAllBranches()) {
            LOG.info("FIND BY COMPANY-CODE");
            conditionsPredicate.add(cb.equal(joinCompanyEntity.get("code"),
                    CompanyCodes.valueOf(genericSerchParamsUserDto.getCompanyCode())));
        }

        if (genericSerchParamsUserDto.getName() != null) {
            if (!genericSerchParamsUserDto.getName().isEmpty()) {

                LOG.info("FIND BY NAME IN ALL FIELDS - NAME, FIRST_SURNAME AND SECOND_SURNAME");

                Predicate name = cb.like(userEntity.get("name"), "%" + genericSerchParamsUserDto.getName() + "%");

                Predicate firstSurname = cb.like(userEntity.get("firstSurname"),
                        "%" + genericSerchParamsUserDto.getName() + "%");

                Predicate secondSurname = cb.like(userEntity.get("secondSurname"),
                        "%" + genericSerchParamsUserDto.getName() + "%");

                conditionsPredicate.add(cb.or(name, firstSurname, secondSurname));

            }
        }

        query.where(conditionsPredicate.toArray(new Predicate[0])).orderBy(cb.desc(userEntity.get("userNumber")));
        return em.createQuery(query).getResultList();
    }

}
