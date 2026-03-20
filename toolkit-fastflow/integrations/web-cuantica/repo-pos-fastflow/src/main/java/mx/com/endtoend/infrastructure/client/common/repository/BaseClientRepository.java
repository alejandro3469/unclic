package mx.com.endtoend.infrastructure.client.common.repository;

import mx.com.endtoend.infrastructure.client.common.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface BaseClientRepository extends JpaRepository<ClientEntity, Serializable> {

    List<ClientEntity> getClient(HashMap<String,Object> conditions);

    ClientEntity findTopByOrderByNoClientDesc();

    ClientEntity findById(Long id);

    ClientEntity findByRfcAndNoClient(Long noClient,String rfc);

    boolean existsByNameAndFatherSurnameAndMotherSurnameAndRfc(String name, String fatherSurname, String motherSurname, String rfc);

    boolean existsByBusinessNameAndRfc(String businessName,String rfc);

    ClientEntity findByRfcAndBusinessName(String rfc, String businessName);

    @Query("SELECT c FROM ClientEntity c WHERE c.noClient=:clientNumber")
    Optional<ClientEntity> findByClientNumber(@Param("clientNumber") Long clientNumber);

}
