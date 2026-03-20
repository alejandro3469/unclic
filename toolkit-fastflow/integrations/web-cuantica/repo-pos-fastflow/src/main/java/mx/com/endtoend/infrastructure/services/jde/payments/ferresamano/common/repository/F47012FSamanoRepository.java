package mx.com.endtoend.infrastructure.services.jde.payments.ferresamano.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.jde.payments.ferresamano.common.entities.F47012;
import mx.com.endtoend.infrastructure.services.jde.payments.ferresamano.common.entities.F47012Id;

import java.util.List;

@Repository
public interface F47012FSamanoRepository extends JpaRepository<F47012, F47012Id> {

    @Query("SELECT f FROM F47012 f WHERE f.szkcoo=:kcoo AND f.szdoco=:doco AND f.szdcto=:dcto AND f.szitm=:itm")
    List<F47012> findAllByKooAndDocoAndDctoAndItm(@Param("kcoo") String kcoo, @Param("doco") Long doco,
            @Param("dcto") String dcto, @Param("itm") Long itm);

}

