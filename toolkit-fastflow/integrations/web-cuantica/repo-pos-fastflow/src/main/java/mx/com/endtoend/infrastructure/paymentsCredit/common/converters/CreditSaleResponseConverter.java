package mx.com.endtoend.infrastructure.paymentsCredit.common.converters;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.paymentsCredit.dto.StatusArticleDetail;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleDetailDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleRequestDto;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleResponseDto;
import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.StatusArticleDetailEntity;
import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.StatusSaleDetailEntity;
import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.StatusSaleRequestEntity;
import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.StatusSaleResponseEntity;

@Component
public class CreditSaleResponseConverter {

	public StatusSaleRequestEntity statusSaleRequestDtoToEntity(StatusSaleRequestDto statusSaleRequestDto) {

		StatusSaleRequestEntity statusSaleRequestEntity = new StatusSaleRequestEntity();

		statusSaleRequestEntity.setOrderNumber(statusSaleRequestDto.getOrderNumber());
		statusSaleRequestEntity.setOrderCode(statusSaleRequestDto.getOrderCode());
		statusSaleRequestEntity.setToken(statusSaleRequestDto.getToken());
		statusSaleRequestEntity.setTransactionId(statusSaleRequestDto.getTransaction_id());
		statusSaleRequestEntity.setCreationDate(new Date());
		statusSaleRequestEntity.setStatusActive(true);

		return statusSaleRequestEntity;
	}

	public StatusSaleRequestDto entityToStatusSaleRequestDto(StatusSaleRequestEntity statusSaleRequestEntity) {

		StatusSaleRequestDto statusSaleRequestDto = new StatusSaleRequestDto();

		statusSaleRequestDto.setOrderNumber(statusSaleRequestEntity.getOrderNumber());
		statusSaleRequestDto.setOrderCode(statusSaleRequestEntity.getOrderCode());
		statusSaleRequestDto.setToken(statusSaleRequestEntity.getToken());
		statusSaleRequestDto.setTransaction_id(statusSaleRequestEntity.getTransactionId());
		statusSaleRequestDto.setCreationDate(statusSaleRequestEntity.getCreationDate());

		return statusSaleRequestDto;

	}

	public StatusSaleResponseEntity statusSaleResponseDtoToEntity(StatusSaleResponseDto statusSaleResponseDto) {

		StatusSaleResponseEntity statusSaleResponseEntity = new StatusSaleResponseEntity();

		statusSaleResponseEntity.setOrderNumber(statusSaleResponseDto.getOrderNumber());
		statusSaleResponseEntity.setOrderCode(statusSaleResponseDto.getOrderCode());
		statusSaleResponseEntity.setCode(statusSaleResponseDto.getCode());
		statusSaleResponseEntity.setName(statusSaleResponseDto.getName());
		statusSaleResponseEntity.setType(statusSaleResponseDto.getType());
		statusSaleResponseEntity.setCreationDate(new Date());
		statusSaleResponseEntity.setStatusActive(true);

		return statusSaleResponseEntity;
	}

	public StatusSaleResponseDto entityToStatusSaleResponseDto(StatusSaleResponseEntity statusSaleResponseEntity) {

		StatusSaleResponseDto statusSaleResponseDto = new StatusSaleResponseDto();

		statusSaleResponseDto.setOrderNumber(statusSaleResponseEntity.getOrderNumber());
		statusSaleResponseDto.setOrderCode(statusSaleResponseEntity.getOrderCode());
		statusSaleResponseDto.setCode(statusSaleResponseEntity.getCode());
		statusSaleResponseDto.setName(statusSaleResponseEntity.getName());
		statusSaleResponseDto.setType(statusSaleResponseEntity.getType());
		statusSaleResponseDto.setCreationDate(statusSaleResponseEntity.getCreationDate());

		return statusSaleResponseDto;

	}

	public StatusSaleDetailEntity statusSaleDetailDtoToEntity(StatusSaleDetailDto saleDetailDto,
			Long statusSaleResponseId) {

		StatusSaleDetailEntity statusSaleDetailEntity = new StatusSaleDetailEntity();

		statusSaleDetailEntity.setStatusSaleResponseId(statusSaleResponseId);
		statusSaleDetailEntity.setTrId(saleDetailDto.getTr_id());
		statusSaleDetailEntity.setTotal(saleDetailDto.getTotal().doubleValue());
		statusSaleDetailEntity.setStatus(saleDetailDto.getStatus());
		statusSaleDetailEntity.setNotaCancelacion(saleDetailDto.getNota_cancelacion());
		statusSaleDetailEntity.setPuntos(saleDetailDto.getPuntos().doubleValue());
		statusSaleDetailEntity.setStatusTex(saleDetailDto.getStatus_text());
		statusSaleDetailEntity.setCreatedAt(saleDetailDto.getCreated_at());
		statusSaleDetailEntity.setUpdatedAt(saleDetailDto.getUpdated_at());

		return statusSaleDetailEntity;

	}

	public StatusSaleDetailDto entityToStatusSaleDetailDto(StatusSaleDetailEntity statusSaleDetailEntity) {

		StatusSaleDetailDto statusSaleDetailDto = new StatusSaleDetailDto();

		statusSaleDetailDto.setTr_id(statusSaleDetailEntity.getTrId());
		statusSaleDetailDto.setTotal(BigDecimal.valueOf(statusSaleDetailEntity.getTotal()));
		statusSaleDetailDto.setStatus(statusSaleDetailEntity.getStatus());
		statusSaleDetailDto.setNota_cancelacion(statusSaleDetailEntity.getNotaCancelacion());
		statusSaleDetailDto.setPuntos(BigDecimal.valueOf(statusSaleDetailEntity.getPuntos()));
		statusSaleDetailDto.setStatus_text(statusSaleDetailEntity.getStatusTex());
		statusSaleDetailDto.setCreated_at(statusSaleDetailEntity.getCreatedAt());
		statusSaleDetailDto.setUpdated_at(statusSaleDetailEntity.getUpdatedAt());

		return statusSaleDetailDto;

	}

	public StatusArticleDetailEntity statusArticleDetailDtoToEntity(StatusArticleDetail statusArticleDetail,
			Long statusSaleDetailId) {

		StatusArticleDetailEntity statusArticleDetailEntity = new StatusArticleDetailEntity();

		statusArticleDetailEntity.setStatusSaleDetailId(statusSaleDetailId);
		statusArticleDetailEntity.setDetail_id(statusArticleDetail.getDetail_id());
		statusArticleDetailEntity.setProductsku(statusArticleDetail.getProduct_sku());
		statusArticleDetailEntity.setCantidad(statusArticleDetail.getCantidad().doubleValue());
		statusArticleDetailEntity.setTotalOperacion(statusArticleDetail.getTotal_operacion().doubleValue());
		statusArticleDetailEntity.setTrDisposicionId(statusArticleDetail.getTr_disposicion_id());

		return statusArticleDetailEntity;
	}

	public StatusArticleDetail entityToStatusArticleDetail(StatusArticleDetailEntity statusArticleDetailEntity) {

		StatusArticleDetail statusArticleDetail = new StatusArticleDetail();

		statusArticleDetail.setDetail_id(statusArticleDetailEntity.getDetail_id());
		statusArticleDetail.setProduct_sku(statusArticleDetailEntity.getProductsku());
		statusArticleDetail.setCantidad(BigDecimal.valueOf(statusArticleDetailEntity.getCantidad()));
		statusArticleDetail.setTotal_operacion(BigDecimal.valueOf(statusArticleDetailEntity.getTotalOperacion()));
		statusArticleDetail.setTr_disposicion_id(statusArticleDetailEntity.getTrDisposicionId());

		return statusArticleDetail;
	}

}
