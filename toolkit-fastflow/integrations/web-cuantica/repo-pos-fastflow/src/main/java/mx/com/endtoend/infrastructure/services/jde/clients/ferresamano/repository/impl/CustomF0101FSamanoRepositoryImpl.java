package mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQuery;

import mx.com.endtoend.domain.clients.dto.FiltersClientDto;
import mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.entities.F0101;
import mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.entities.QF0101;
import mx.com.endtoend.infrastructure.services.jde.clients.ferresamano.repository.CustomF0101FSamanoRepository;

@Repository
@ConditionalOnProperty(name = "app.ferresamano.oracle.enabled", havingValue = "true", matchIfMissing = false)
public class CustomF0101FSamanoRepositoryImpl implements CustomF0101FSamanoRepository {

	private final Logger LOG = LoggerFactory.getLogger(CustomF0101FSamanoRepositoryImpl.class);
	
	@Autowired
	@Qualifier("ferresamanoOracleEntityManagerFactory")
	private EntityManager entityManager;
	
	public List<F0101> getClient(HashMap<String,Object> conditions){
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<F0101> query = cb.createQuery(F0101.class);
		Root<F0101> root = query.from(F0101.class);
		
		List<Predicate> predicates = new ArrayList<>();
		conditions.forEach((field,value) -> {
			switch (field) {
				case "noClient":
					predicates.add(cb.equal(cb.trim(root.get(field)), (Long) value));
					break;
				case "rfc":
					Expression<String> exp1 = cb.concat(root.<String>get("rfc"), " ");
					Predicate rfc = cb.like(exp1, "%".concat((String) value).concat("%"));
					
					predicates.add(cb.or(rfc));
					break;
				case "name":
					Expression<String> exp2 = cb.concat(root.<String>get("name"), " ");
					Predicate name = cb.like(exp2, "%".concat((String) value).concat("%"));
					
					predicates.add(cb.or(name));
			}
		});

		query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<F0101> findByParams(FiltersClientDto filtersClientDto) {
		LOG.info("INIT findByParams()");
		JPAQuery<F0101> query = new JPAQuery<>(entityManager);
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