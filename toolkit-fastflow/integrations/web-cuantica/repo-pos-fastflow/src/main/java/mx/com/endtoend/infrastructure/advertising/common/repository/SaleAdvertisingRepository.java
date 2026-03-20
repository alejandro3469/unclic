package mx.com.endtoend.infrastructure.advertising.common.repository;

import java.math.BigDecimal;
import java.util.List;

import mx.com.endtoend.infrastructure.advertising.common.entities.SaleAdvertisingEntity;
import mx.com.endtoend.smart.bussiness.model.adversiting.dto.SaleAdvertisingDto;
import mx.com.endtoend.smart.bussiness.model.adversiting.dto.SearchAdversitingParamsDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleAdvertisingRepository extends JpaRepository<SaleAdvertisingEntity, Long> {

	SaleAdvertisingDto createAdvertising(SaleAdvertisingDto saleAdvertisingDto, String idOperation);

	List<SaleAdvertisingDto> viewAdvertisingListByParams(SearchAdversitingParamsDto searchAdversitingParams,
			String companyCode, String idOperation);

	SaleAdvertisingDto viewAdvertisingDetailById(Long id, String idOperation);

	void updateSatusByOrderNumberAndCode(BigDecimal orderNumber, String statusCode, String idOperation);

}
