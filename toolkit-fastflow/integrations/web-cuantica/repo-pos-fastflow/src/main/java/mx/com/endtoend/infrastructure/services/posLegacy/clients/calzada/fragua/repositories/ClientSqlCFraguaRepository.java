package mx.com.endtoend.infrastructure.services.posLegacy.clients.calzada.fragua.repositories;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.services.posLegacy.clients.common.entities.ClientSqlEntity;


@Repository
public interface ClientSqlCFraguaRepository extends JpaRepository<ClientSqlEntity, Serializable>{

//	@Query(value = "SELECT MAX(NoCliente) AS id FROM Clientes", nativeQuery = true)
//	Long lastNoClient();
//	
//	@Query(value = "select * from Clientes where nombre LIKE CONCAT('%',:name,'%') or ApellidoPaterno LIKE CONCAT('%',:name,'%') or ApellidoMaterno LIKE CONCAT('%',:name,'%') or NombreCliente LIKE CONCAT('%',:name,'%')", nativeQuery = true)
//	List<ClientSqlEntity> listClientName(String name);
//	
//	@Query(value = "select * from Clientes where nombre LIKE CONCAT('%',:name,'%') or ApellidoPaterno LIKE CONCAT('%',:name,'%') or ApellidoMaterno LIKE CONCAT('%',:name,'%') or NombreCliente LIKE CONCAT('%',:name,'%') and NoCliente = :noClient", nativeQuery = true)
//	List<ClientSqlEntity> listClientNameNoClient(String name, Long noClient);
//	
//	@Query(value = "select * from Clientes where nombre LIKE CONCAT('%',:name,'%') or ApellidoPaterno LIKE CONCAT('%',:name,'%') or ApellidoMaterno LIKE CONCAT('%',:name,'%') or NombreCliente LIKE CONCAT('%',:name,'%') and NoCliente = :noClient and RFC LIKE CONCAT('%',:rfc,'%')", nativeQuery = true)
//	List<ClientSqlEntity> listClientNameNoClientRfc(String name, Long noClient, String rfc);
//	
//	@Query(value = "select * from Clientes where nombre LIKE CONCAT('%',:name,'%') or ApellidoPaterno LIKE CONCAT('%',:name,'%') or ApellidoMaterno LIKE CONCAT('%',:name,'%') or NombreCliente LIKE CONCAT('%',:name,'%') and RFC LIKE CONCAT('%',:rfc,'%')", nativeQuery = true)
//	List<ClientSqlEntity> listClientNameRfc(String name, String rfc);
//	
//	@Query(value = "select * from Clientes where NoCliente = :noClient and RFC LIKE CONCAT('%',:rfc,'%')", nativeQuery = true)
//	List<ClientSqlEntity> listClientNoClientRfc(Long noClient, String rfc);
//	
//	@Query(value = "select * from Clientes where NoCliente = :noClient", nativeQuery = true)
//	List<ClientSqlEntity> listClientNoClient(Long noClient);
//	
//	@Query(value = "select * from Clientes where RFC LIKE CONCAT('%',:rfc,'%')", nativeQuery = true)
//	List<ClientSqlEntity> listClientRfc(String rfc);
	
	Optional<ClientSqlEntity> findByNoClient(Long id);
}

