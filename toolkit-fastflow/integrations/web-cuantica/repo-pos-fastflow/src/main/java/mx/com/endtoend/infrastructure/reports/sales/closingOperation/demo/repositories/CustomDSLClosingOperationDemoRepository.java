package mx.com.endtoend.infrastructure.reports.sales.closingOperation.demo.repositories;

import javax.persistence.EntityManager;

import mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.repository.BaseCustomDSLClosingOperationRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
@Repository
public class CustomDSLClosingOperationDemoRepository extends BaseCustomDSLClosingOperationRepository {

    public CustomDSLClosingOperationDemoRepository(@Qualifier("demoDataEntityManagerFactory")
                                                        EntityManager em){
        super(CustomDSLClosingOperationDemoRepository.class, em);
    }
}
