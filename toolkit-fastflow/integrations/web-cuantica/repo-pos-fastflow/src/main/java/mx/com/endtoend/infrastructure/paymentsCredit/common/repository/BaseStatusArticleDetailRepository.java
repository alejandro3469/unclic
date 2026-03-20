package mx.com.endtoend.infrastructure.paymentsCredit.common.repository;

import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.StatusArticleDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import javax.persistence.MappedSuperclass;
import java.util.List;

@MappedSuperclass
@NoRepositoryBean
public interface BaseStatusArticleDetailRepository extends JpaRepository<StatusArticleDetailEntity, Long> {

    @Query("SELECT sad FROM StatusArticleDetailEntity sad WHERE sad.statusSaleDetailId=:id")
    List<StatusArticleDetailEntity> findAllByRequestId(@Param("id") Long id);

}
