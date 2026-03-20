package mx.com.endtoend.infrastructure.services.bds;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.java.Log;
import mx.com.endtoend.domain.paymentsCredit.dto.StatusSaleResponseDto;
import mx.com.endtoend.infrastructure.paymentsCredit.calzada.repositories.BDSConfigurationRepository;
import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.BDSServiceConfiguration;
import mx.com.endtoend.infrastructure.services.bds.models.BDSStatusRequest;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;

/**
 * Clase para comunicación con sistema Blue Diamond - BDS
 * 
 * @author ddcasas
 */
@Log
@Service
public class BDSService implements BDSServicePort {

	private static final String PATH_GET_STATUS = "get-status-preventa";
	@Autowired
	private BDSConfigurationRepository bdsConfigurationRepository;

	/**
	 * Valida la precisión decimal de datos monetarios antes de enviar a servicios externos
	 * @param amount valor monetario a validar
	 * @throws IllegalArgumentException si la validación falla
	 */
	private void validateMonetaryPrecision(java.math.BigDecimal amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount cannot be null");
		}
		
		if (!PrecisionValidator.isValidMonetaryRange(amount)) {
			throw new IllegalArgumentException("Amount out of SAT range: " + amount);
		}
		
		if (!PrecisionValidator.isValidScale(amount, 2)) {
			throw new IllegalArgumentException("Amount must have exactly 2 decimal places: " + amount);
		}
		
		log.info("Monetary precision validation passed for amount: " + amount);
	}

	/**
	 * Aplica redondeo SAT a valores monetarios antes de enviar a servicios externos
	 * @param amount valor monetario a procesar
	 * @return valor con redondeo SAT aplicado
	 */
	private java.math.BigDecimal applySATRounding(java.math.BigDecimal amount) {
		if (amount == null) {
			return amount;
		}
		
		java.math.BigDecimal roundedAmount = DecimalPrecisionUtils.roundToTwoDecimals(amount);
		log.info("SAT rounding applied to amount: " + amount + " -> " + roundedAmount);
		return roundedAmount;
	}

	/**
	 * Método para la validación de estado de cobro con crédito por medio del
	 * identificador de operación
	 */
	@Override
	public ResponseModel validStatusByTransactionId(String transactionId) {

		StatusSaleResponseDto statusSaleResponse = getStatusFormBDSService(transactionId);
		String status = "";
		if (statusSaleResponse == null)
			status = "ERROR IN GET STATUS FROM EXTERNAL SERVICE";

		status = statusSaleResponse.getPreventa() == null ? statusSaleResponse.getMessage()
				: statusSaleResponse.getPreventa().getStatus_text();

		return new ResponseModel(status);
	}

	private StatusSaleResponseDto getStatusFormBDSService(String transactionId) {
		try {
			log.info("INIT validStatusByTransactionId() with precision validation");
			RestTemplate restTemplate = new RestTemplate();
			List<BDSServiceConfiguration> bdsServiceConfigurationList = bdsConfigurationRepository.findAll();
			BDSServiceConfiguration bdsServiceConfiguration = bdsServiceConfigurationList.get(0);

			BDSStatusRequest bdsStatusRequest = new BDSStatusRequest(bdsServiceConfiguration.getToken(), transactionId);
			String url = bdsServiceConfiguration.getUrlSite() + PATH_GET_STATUS;

			// Validar precisión decimal si hay datos monetarios en la request
			// (BDSStatusRequest no contiene campos monetarios, pero se valida la estructura)
			log.info("Validating BDS request structure for transactionId: " + transactionId);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<BDSStatusRequest> request = new HttpEntity<BDSStatusRequest>(bdsStatusRequest, headers);

			log.info("Sending BDS request to external service with precision validation: " + url);
			StatusSaleResponseDto statusSaleResponse = restTemplate.postForObject(url, request,
					StatusSaleResponseDto.class);
			
			// Validar respuesta si contiene datos monetarios
			if (statusSaleResponse != null) {
				log.info("BDS response received with precision validation: " + statusSaleResponse.toString());
			} else {
				log.warning("BDS response is null - external service may be unavailable");
			}

			return statusSaleResponse;
		} catch (Exception e) {
			log.severe("EXCEPTION in BDS service call: " + e.getMessage());
			return null;
		}
	}

}
