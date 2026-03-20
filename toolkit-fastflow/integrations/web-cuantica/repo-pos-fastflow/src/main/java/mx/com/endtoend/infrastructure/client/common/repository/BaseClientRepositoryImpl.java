package mx.com.endtoend.infrastructure.client.common.repository;

import mx.com.endtoend.infrastructure.client.common.entities.ClientEntity;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.MappedSuperclass;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NoRepositoryBean
@MappedSuperclass
public class BaseClientRepositoryImpl {
    private final EntityManager entityManager;

    public BaseClientRepositoryImpl(EntityManager _entityManager) {
        entityManager = _entityManager;
    }

    public List<ClientEntity> getClient(HashMap<String,Object> conditions){

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEntity> query = cb.createQuery(ClientEntity.class);
        Root<ClientEntity> root = query.from(ClientEntity.class);

        List<Predicate> predicates = new ArrayList<>();
        conditions.forEach((field,value) -> {
            switch (field) {
                case "noClient":
                    predicates.add(cb.equal(cb.trim(root.get(field)), (Long) value));
                    break;
                case "rfc":
                    Expression<String> value1 = cb.concat(root.<String>get("rfc"), " ");
                    Predicate rfc = cb.like(value1, "%".concat((String) value).concat("%"));
                    predicates.add(cb.or(rfc));
                    break;
                case "name":
                    String value2 = "%".concat((String) value).concat("%");
                    Predicate completeName = cb.like(cb.function("CONCAT_WS", String.class,
                            cb.literal(" "),
                            root.get("name"),
                            root.get("fatherSurname"),
                            root.get("motherSurname")) , value2);
                    Predicate name = cb.like(root.get("name"), value2);
                    Predicate motherSurname = cb.like(root.get("motherSurname"), value2);
                    Predicate fatherSurname = cb.like(root.get("fatherSurname"), value2);
                    Predicate businessName = cb.like(root.get("businessName"), value2);
                    predicates.add(cb.or(name, motherSurname, fatherSurname, businessName, completeName));


            }
        });

        query.select(root).where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(query).getResultList();
    }

}
