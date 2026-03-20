package mx.com.endtoend.infrastructure.orderConfigurations.common.repository;

import mx.com.endtoend.infrastructure.orderConfigurations.common.entities.OrderConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BaseOrderConfigurationRepository extends JpaRepository<OrderConfigurationEntity, Long> {

    @Query("SELECT oc FROM OrderConfigurationEntity oc JOIN oc.saleType st WHERE oc.id=:id")
    OrderConfigurationEntity findByOrerConfigurationId(@Param("id") Long id);

    @Query("SELECT oc FROM OrderConfigurationEntity oc JOIN oc.saleType st WHERE st.code=:orderCode")
    OrderConfigurationEntity findByOrerCode(@Param("orderCode") String orderCode);

    @Query("SELECT oc FROM OrderConfigurationEntity oc JOIN oc.saleType st WHERE st.code=:orderCode")
    Optional<OrderConfigurationEntity> findByOrderCode(@Param("orderCode") String orderCode);

    @Query("SELECT oc FROM OrderConfigurationEntity oc JOIN oc.saleType st WHERE st.code=:orderCode AND oc.id!=:id")
    Optional<OrderConfigurationEntity> findByOrderCodeAndIdNot(@Param("orderCode") String orderCode,
                                                               @Param("id") Long id);

    @Query("SELECT oc FROM OrderConfigurationEntity oc ")
    List<OrderConfigurationEntity> findOrderConfigurationList();


}
