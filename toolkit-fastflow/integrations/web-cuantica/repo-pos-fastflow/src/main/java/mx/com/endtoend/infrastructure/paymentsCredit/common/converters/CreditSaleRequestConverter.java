package mx.com.endtoend.infrastructure.paymentsCredit.common.converters;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleDetailDto;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.CreditSaleResponseDto;
import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.CreditSaleDetailEntity;
import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.CreditSaleRequestEntity;
import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.CreditSaleResponseEntity;

@Component
public class CreditSaleRequestConverter {

	public CreditSaleRequestEntity creditSaleRequestDtoToEntity(CreditSaleRequestDto creditSaleRequestDto) {

		CreditSaleRequestEntity creditSaleRequestEntity = new CreditSaleRequestEntity();

		creditSaleRequestEntity.setOrderNumber(creditSaleRequestDto.getOrderNumber());
		creditSaleRequestEntity.setOrderCode(creditSaleRequestDto.getOrderCode());
		creditSaleRequestEntity.setToken(creditSaleRequestDto.getToken());
		creditSaleRequestEntity.setVentaTotal(creditSaleRequestDto.getVenta_total().doubleValue());
		creditSaleRequestEntity.setCreationDate(new Date());
		creditSaleRequestEntity.setStatusActive(true);

		return creditSaleRequestEntity;
	}

	public CreditSaleRequestDto entityToCreditSaleRequestDto(CreditSaleRequestEntity creditSaleRequestEntity) {

		CreditSaleRequestDto creditSaleRequestDto = new CreditSaleRequestDto();

		creditSaleRequestDto.setOrderNumber(creditSaleRequestEntity.getOrderNumber());
		creditSaleRequestDto.setOrderCode(creditSaleRequestEntity.getOrderCode());
		creditSaleRequestDto.setToken(creditSaleRequestEntity.getToken());
		creditSaleRequestDto.setVenta_total(BigDecimal.valueOf(creditSaleRequestEntity.getVentaTotal()));
		creditSaleRequestDto.setCreationDate(creditSaleRequestEntity.getCreationDate());

		return creditSaleRequestDto;

	}

	public CreditSaleDetailEntity creditSaleDetailDtoToEntity(CreditSaleDetailDto creditSaleDetailDto,
			Long creditSaleRequestId) {

		CreditSaleDetailEntity creditSaleDetailEntity = new CreditSaleDetailEntity();

		creditSaleDetailEntity.setCreditSaleRequestId(creditSaleRequestId);
		creditSaleDetailEntity.setProductSku(creditSaleDetailDto.getProduct_sku());
		creditSaleDetailEntity.setAmount(creditSaleDetailDto.getAmount().doubleValue());
		creditSaleDetailEntity.setTotalOperacion(creditSaleDetailDto.getTotal_operacion().doubleValue());

		return creditSaleDetailEntity;
	}

	public CreditSaleDetailDto entityToCreditSaleDetailDto(CreditSaleDetailEntity creditSaleDetailEntity) {

		CreditSaleDetailDto creditSaleDetailDto = new CreditSaleDetailDto();

		creditSaleDetailDto.setProduct_sku(creditSaleDetailEntity.getProductSku());
		creditSaleDetailDto.setAmount(BigDecimal.valueOf(creditSaleDetailEntity.getAmount()));
		creditSaleDetailDto.setTotal_operacion(BigDecimal.valueOf(creditSaleDetailEntity.getTotalOperacion()));

		return creditSaleDetailDto;

	}

	public CreditSaleResponseEntity creditSaleResponseDtoToEntity(CreditSaleResponseDto creditSaleResponseDto) {

		CreditSaleResponseEntity creditSaleResponseEntity = new CreditSaleResponseEntity();

		creditSaleResponseEntity.setOrderNumber(creditSaleResponseDto.getOrderNumber());
		creditSaleResponseEntity.setOrderCode(creditSaleResponseDto.getOrderCode());
		creditSaleResponseEntity.setCode(creditSaleResponseDto.getCode());
		creditSaleResponseEntity.setName(creditSaleResponseDto.getName());
		creditSaleResponseEntity.setTransactionId(creditSaleResponseDto.getTransaction_id());
		creditSaleResponseEntity.setPuntostotal(creditSaleResponseDto.getPuntos_total().doubleValue());
		creditSaleResponseEntity.setType(creditSaleResponseDto.getType());
		creditSaleResponseEntity.setCreationDate(new Date());
		creditSaleResponseEntity.setStatusActive(true);

		return creditSaleResponseEntity;

	}

	public CreditSaleResponseDto entityToCreditSaleResponseDto(CreditSaleResponseEntity creditSaleResponseEntity) {

		CreditSaleResponseDto creditSaleResponseDto = new CreditSaleResponseDto();

		creditSaleResponseDto.setOrderNumber(creditSaleResponseEntity.getOrderNumber());
		creditSaleResponseDto.setOrderCode(creditSaleResponseEntity.getOrderCode());
		creditSaleResponseDto.setCode(creditSaleResponseEntity.getCode());
		creditSaleResponseDto.setName(creditSaleResponseEntity.getName());
		creditSaleResponseDto.setTransaction_id(creditSaleResponseEntity.getTransactionId());
		creditSaleResponseDto.setPuntos_total(BigDecimal.valueOf(creditSaleResponseEntity.getPuntostotal()));
		creditSaleResponseDto.setType(creditSaleResponseEntity.getType());
		creditSaleResponseDto.setCreationDate(creditSaleResponseEntity.getCreationDate());

		return creditSaleResponseDto;

	}

}
