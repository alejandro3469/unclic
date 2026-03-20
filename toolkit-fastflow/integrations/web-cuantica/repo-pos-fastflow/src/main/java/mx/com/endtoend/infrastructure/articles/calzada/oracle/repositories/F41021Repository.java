package mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F41021;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F41021Id;

@Repository
public interface F41021Repository extends JpaRepository<F41021, F41021Id> {

	@Query("SELECT " + " COALESCE(SUM(f.lipqoh),0), " + " COALESCE(SUM(f.lihcom),0), " + " COALESCE(SUM(f.lipcom),0),"
			+ " COALESCE(SUM(f.lifcom),0), " + " COALESCE(SUM(f.liot1p),0) "
			+ " FROM F41021 f WHERE f.id.liitm = :liitm AND TRIM(f.id.limcu) = TRIM(:limcu)")
	List<Object[]> findQuantitiesByItmAndMcu(@Param("liitm") BigDecimal liitm, @Param("limcu") String limcu);

	@Query("SELECT " + "(SUM(f.lipqoh) - SUM(f.lihcom) - SUM(f.lipcom) -SUM(f.liot1p)) / 100 " + "FROM F41021 f "
			+ "WHERE f.id.liitm = :articleCode " + "AND " + "TRIM(f.id.limcu) = TRIM(:warehouseCode)")
	BigDecimal getAvailabilityByArticleNumberAndWarehouseCode(@Param("articleCode") BigDecimal articleCode,
			@Param("warehouseCode") String warehouseCode);

	@Transactional
	@Modifying
	@Query("UPDATE F41021 f " + "set f.liot1p =:ot1p + liot1p, " + "f.lipid =:pid, " + "f.liupmj =:upmj, " + "f.liuser =:user, "
			+ "f.litday =:tday " + "WHERE " + "f.id.liitm =:itm " + "AND " + "TRIM(f.id.limcu) =:mcu ")
	int updateItemAvailabilityByItmAndMcu(@Param("ot1p") BigDecimal ot1p, @Param("pid") String pid,
			@Param("upmj") Long upmj, @Param("user") String user, @Param("tday") Long tday,
			@Param("itm") BigDecimal itm, @Param("mcu") String mcu);

}
