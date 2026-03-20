package mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0101;

@Component
@ConditionalOnProperty(name = "app.carredana.oracle.enabled", havingValue = "true", matchIfMissing = false)
public class F0101FCarRepositoryImpl {

	@Autowired
	@Qualifier("carredanaOracleEntityManagerFactory")
	private EntityManager entityManager;

	public List<F0101> getClient(HashMap<String, Object> conditions) {

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<F0101> query = cb.createQuery(F0101.class);
		Root<F0101> root = query.from(F0101.class);

		List<Predicate> predicates = new ArrayList<>();
		conditions.forEach((field, value) -> {
			switch (field) {
			case "noClient":
				predicates.add(cb.equal(cb.trim(root.get(field)), (Long) value));
				break;
			case "rfc":
				Expression<String> exp1 = cb.concat(root.<String>get("rfc"), " ");
				Predicate rfc = cb.like(exp1, "%".concat((String) value).concat("%"));

				predicates.add(cb.or(rfc));
				break;
			case "abat1":
				predicates.add(cb.equal(cb.trim(root.get(field)), (String) value));
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
}
