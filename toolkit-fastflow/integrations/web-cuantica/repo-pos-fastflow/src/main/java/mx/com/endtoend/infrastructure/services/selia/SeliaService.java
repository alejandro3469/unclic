package mx.com.endtoend.infrastructure.services.selia;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import mx.com.endtoend.infrastructure.services.selia.models.SeliaRequest;
import mx.com.endtoend.infrastructure.services.selia.models.SeliaResponse;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;
import mx.com.smartbussiness.generic.validators.PrecisionValidator;
import java.math.BigDecimal;

@Service
public class SeliaService implements SeliaServicePort {

	@Value("${app.calzada.selia.generator.url}")
	String urlSeliaService;

	private final Logger LOG = LoggerFactory.getLogger(SeliaService.class);

	/**
	 * Valida la precisión decimal de datos monetarios antes de enviar a servicios externos
	 * @param amount valor monetario a validar
	 * @throws IllegalArgumentException si la validación falla
	 */
	private void validateMonetaryPrecision(BigDecimal amount) {
		if (amount == null) {
			throw new IllegalArgumentException("Amount cannot be null");
		}
		
		if (!PrecisionValidator.isValidMonetaryRange(amount)) {
			throw new IllegalArgumentException("Amount out of SAT range: " + amount);
		}
		
		if (!PrecisionValidator.isValidScale(amount, 2)) {
			throw new IllegalArgumentException("Amount must have exactly 2 decimal places: " + amount);
		}
		
		LOG.debug("Monetary precision validation passed for amount: " + amount);
	}

	/**
	 * Aplica redondeo SAT a valores monetarios antes de enviar a servicios externos
	 * @param amount valor monetario a procesar
	 * @return valor con redondeo SAT aplicado
	 */
	private BigDecimal applySATRounding(BigDecimal amount) {
		if (amount == null) {
			return amount;
		}
		
		BigDecimal roundedAmount = DecimalPrecisionUtils.roundToTwoDecimals(amount);
		LOG.debug("SAT rounding applied to amount: " + amount + " -> " + roundedAmount);
		return roundedAmount;
	}

	/**
	 * Valida y redondea el campo amount de SeliaRequest
	 * @param seliaRequest objeto de solicitud a validar
	 * @return objeto con amount validado y redondeado
	 */
	private SeliaRequest validateAndRoundAmount(SeliaRequest seliaRequest) {
		if (seliaRequest == null || seliaRequest.getAmount() == null) {
			return seliaRequest;
		}
		
		try {
			// Convertir String amount a BigDecimal
			BigDecimal amount = new BigDecimal(seliaRequest.getAmount().replace("45:", ""));
			
			// Validar precisión
			validateMonetaryPrecision(amount);
			
			// Aplicar redondeo SAT
			BigDecimal roundedAmount = applySATRounding(amount);
			
			// Crear nuevo SeliaRequest con amount redondeado
			SeliaRequest validatedRequest = new SeliaRequest(
				seliaRequest.getCompanyCode().replace("161:", ""),
				seliaRequest.getOriginatorCode().replace("1:", ""),
				seliaRequest.getSaleCashNumber().replace("2:", ""),
				seliaRequest.getRequestDate().replace("3:", ""),
				seliaRequest.getOrderNumber().replace("19:", ""),
				seliaRequest.getGenerateBoucher().replace("163:", ""),
				seliaRequest.getUser().replace("54:", ""),
				seliaRequest.getPassword().replace("219:", ""),
				seliaRequest.getCompanyPhone().replace("7:", ""),
				seliaRequest.getCompanyPhoneCode().replace("56:", ""),
				roundedAmount.toString(), // Amount redondeado como String
				seliaRequest.getPhoneNumber().replace("82:", "")
			);
			
			LOG.info("SeliaRequest amount validated and rounded: " + amount + " -> " + roundedAmount);
			return validatedRequest;
			
		} catch (NumberFormatException e) {
			LOG.warn("Invalid amount format in SeliaRequest: " + seliaRequest.getAmount() + ", using original value");
			return seliaRequest;
		}
	}

	@Override
	public SeliaResponse generatRecharge(SeliaRequest seliaRequest) {
		LOG.info("INIT generatRecharge() with precision validation");
		LOG.info(String.format("PARAMS:[%s]", seliaRequest.toString()));
		
		// Validar y redondear amount antes de enviar
		SeliaRequest validatedRequest = validateAndRoundAmount(seliaRequest);
		LOG.info("Validated SeliaRequest amount for SAT compliance");
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity(urlSeliaService, validatedRequest.generateRequest(),
				String.class);
		String responseService = response.getBody();
		LOG.info("RESPONSE: " + responseService);
		SeliaResponse seliaResponse = new SeliaResponse();
		seliaResponse.generateResponse(responseService);
		return seliaResponse;
	}

}
