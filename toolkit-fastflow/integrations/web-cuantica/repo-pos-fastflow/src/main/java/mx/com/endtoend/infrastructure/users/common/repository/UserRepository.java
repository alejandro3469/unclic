package mx.com.endtoend.infrastructure.users.common.repository;


import mx.com.endtoend.infrastructure.users.common.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 
 * @author ddcasas
 *
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>, CustomUserRepository {
    //CONSULTA PARA VALIDACION DE CONEXION A BD
    @Query(value = "SELECT * FROM users LIMIT 1", nativeQuery = true)
    UserEntity validConnection();

    // CONSULTAS PARA LOGING Y DATOS DEL TOKEN
    @Query("SELECT u FROM UserEntity u JOIN u.branch b WHERE u.email=:email")
    Optional<UserEntity> findByEmail(@Param("email") String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u set u.sessionActive =:active WHERE u.id =:id")
    int enableSessionById(@Param("id") Long id, @Param("active") boolean active);

    // END - CONSULTAS PARA LOGING Y DATOS DEL TOKEN


    @Query("SELECT u FROM UserEntity u JOIN u.branch b WHERE u.id =:id")
    Optional<UserEntity> findById(@Param("id") Long id);

    // Consultas de validacion en creción/actualiación

    @Query("SELECT u FROM UserEntity u JOIN u.branch b WHERE u.userNumber =:userNumber")
    Optional<UserEntity> findByUserNumber(@Param("userNumber") Long userNumber);

    @Query("SELECT u FROM UserEntity u JOIN u.branch b WHERE u.userNumber =:userNumber AND b.code =:branchCode")
    Optional<UserEntity> findByUserNumberAndBranchCode(@Param("userNumber") Long userNumber,
                                                       @Param("branchCode") String branchCode);

    @Query("SELECT u FROM UserEntity u JOIN u.branch b WHERE u.userNumber =:userNumber AND b.code =:branchCode AND u.id!=:id")
    Optional<UserEntity> findByUserNumberAndBranchCodeAndIdNot(@Param("userNumber") Long userNumber,
                                                               @Param("branchCode") String branchCode, @Param("id") Long id);


    @Query("SELECT u FROM UserEntity u JOIN u.branch b WHERE u.email=:email AND b.code =:branchCode")
    Optional<UserEntity> findByEmailAndBranchCode(@Param("email") String email, @Param("branchCode") String branchCode);

    @Query("SELECT u FROM UserEntity u JOIN u.branch b WHERE u.email=:email AND b.code =:branchCode AND u.id!=:id")
    Optional<UserEntity> findByEmailAndBranchCodeAndIdNot(@Param("email") String email,
                                                          @Param("branchCode") String branchCode, @Param("id") Long id);

    // VALIDA EL EMAIL EN TODAS LAS COMPAÑIAS
    @Query("SELECT u FROM UserEntity u WHERE u.email=:email ")
    List<UserEntity> findByEmailInAllCompanies(@Param("email") String email);

    // VALIDA EL EMAIL EN TODAS LAS COMPAÑIAS PARA PROCESO DE ACTUALIZACION
    @Query("SELECT u FROM UserEntity u WHERE u.email=:email AND u.id !=:id")
    List<UserEntity> findByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);

    // VALIDA EL USER NUMBER EN TODAS LAS COMPAÑIAS
    @Query("SELECT u FROM UserEntity u WHERE u.userNumber =:userNumber")
    List<UserEntity> findByUserNumberInAllCompanies(@Param("userNumber") Long userNumber);

    // VALIDA EL USER NUMBER EN TODAS LAS COMPAÑIAS PARA PROCESO DE ACTUALIZACION
    @Query("SELECT u FROM UserEntity u WHERE u.userNumber =:userNumber AND u.id !=:id")
    List<UserEntity> findByUserNumberAndIdNot(@Param("userNumber") Long userNumber, @Param("id") Long id);

    @Query("SELECT u FROM UserEntity u JOIN u.branch b WHERE u.enabled =:enabled AND b.code =:branchCode")
    List<UserEntity> findAllByEnableAndBranchCode(@Param("enabled") boolean enabled,
                                                  @Param("branchCode") String branchCode);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u set u.enabled =:enabled WHERE u.id =:id")
    int enableById(@Param("id") Long id, @Param("enabled") boolean enabled);

    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.configurationComplete=:status WHERE u.id =:id")
    int updateUserConfigurationStatusById(@Param("id") Long id, @Param("status") boolean status);

    @Query("SELECT u.password FROM UserEntity u WHERE u.id=:id")
    String findPasswordById(@Param("id") Long id);

    @Query("SELECT u FROM UserEntity u WHERE u.id =:id AND u.userNumber =:userNumber")
    Optional<UserEntity> findByIdAndUserNumber(@Param("id") Long id, @Param("userNumber") Long userNumber);


}
