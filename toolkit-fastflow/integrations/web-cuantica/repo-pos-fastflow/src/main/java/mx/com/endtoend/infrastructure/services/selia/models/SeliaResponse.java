package mx.com.endtoend.infrastructure.services.selia.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class SeliaResponse {

	private static final String REFERENCE = "51";

	private static final String AUTH_CODE = "50";

	private static final String RESPONSE_DESCRIPTION = "16";

	private static final String RESPONSE_CODE = "14";

	private String genericResponse;

	private String responseCode;

	private String responseDescription;

	private String authCode;

	private String reference;

	public void generateResponse(String response) {

		this.genericResponse = response;

		response = response.replace("{", " ");
		response = response.replace("}", " ");

		HashMap<String, String> map = (HashMap<String, String>) Arrays.asList(response.split(";")).stream()
				.map(s -> s.split(":")).collect(Collectors.toMap(e -> e[0], e -> e[1]));

		responseCode = map.get(RESPONSE_CODE);
		responseDescription = map.get(RESPONSE_DESCRIPTION);
		authCode = map.get(AUTH_CODE);
		reference = map.get(REFERENCE);
	}

	public String getGenericResponse() {
		return genericResponse;
	}

	public void setGenericResponse(String genericResponse) {
		this.genericResponse = genericResponse;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	@Override
	public String toString() {
		return "SeliaResponse [genericResponse=" + genericResponse + ", responseCode=" + responseCode
				+ ", responseDescription=" + responseDescription + ", authCode=" + authCode + ", reference=" + reference
				+ "]";
	}

}
