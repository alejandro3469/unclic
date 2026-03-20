package mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F4104;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F4104Id;

@Repository
public interface F4104Repository extends JpaRepository<F4104, F4104Id> {

	@Query("FROM F4104 f WHERE TRIM(f.id.ivxrt) = :filter AND TRIM(f.id.ivcitm) = TRIM(:citm)")
	F4104 findByIvxrtAndivcitm(@Param("filter") String filter, @Param("citm") String citm);
}
