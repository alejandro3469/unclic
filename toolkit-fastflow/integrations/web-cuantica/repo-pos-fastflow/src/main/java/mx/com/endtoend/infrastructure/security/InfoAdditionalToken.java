package mx.com.endtoend.infrastructure.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.roles.ports.api.PermissionServicePort;
import mx.com.endtoend.domain.userConfigurations.dto.EmployeeDto;
import mx.com.endtoend.domain.userConfigurations.ports.api.UserConfigurationServicePort;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.domain.users.ports.api.UserServicePort;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.permissions.common.coverters.PermissionConverter;
import mx.com.endtoend.infrastructure.permissions.common.entities.PermissionEntity;

@Component
public class InfoAdditionalToken implements TokenEnhancer {

	@Autowired
	private UserServicePort userServicePort;

	@Autowired
	private CompanyServicePort companyServicePort;

	@Autowired
	private UserConfigurationServicePort userConfigurationServicePort;

	@Autowired
	private PermissionServicePort permissionServicePort;

	@Autowired
	private PermissionConverter permissionConverter;

	private Logger LOGGER = LoggerFactory.getLogger(InfoAdditionalToken.class);

	private String module = "USER-INFO-TOKEN";

	private static String moduleUserConfig = "USER_CONFIGURATIONS";

	private String roleJob = "EMPTY";

	@SuppressWarnings("unchecked")
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		LOGGER.info(module + "Additional information by user: " + authentication.getName());

		UserDto userDto = (UserDto) userServicePort.findUserByEmailToLogin(authentication.getName(), module).getData();

		CompanyDto companyDto = (CompanyDto) companyServicePort
				.findCompanyByCode(userDto.getBranch().getCompany().getCode().toString(), module).getData();

		if (!companyDto.getCode().equals(CompanyCodes.ETE.toString())) {

			MethodDto method = (MethodDto) companyServicePort
					.findMethodByCompanyCodeAndModule(companyDto.getCode(), moduleUserConfig, module).getData();

			if (method == null) {
				LOGGER.info(String.format("ERROR IN ASSIGNATION METHODS OF THE COMPANY %s ", companyDto.getCode()));
				throw new GlobalError();
			}

			EmployeeDto employeeDto = (EmployeeDto) userConfigurationServicePort
					.getUserConfigurationByUserId(userDto.getId(), companyDto.getCode(), method.getCode(), module)
					.getData();

			roleJob = ((employeeDto == null) ? "EMPTY"
					: employeeDto.getId() == null ? "EMPTY" : employeeDto.getRoleJob().getName());
		}

		if (companyDto.getCode().equals(CompanyCodes.ETE.toString())){
			roleJob = "ADMINISTRADOR";
		}
		List<PermissionEntity> permissionList = permissionConverter
				.permissionDtoListToPermissionEntityList(permissionServicePort.findAllByUserId(userDto.getId()));

		String operationalRole = permissionList.get(0).getType();

		Map<String, Object> mapInfo = new HashMap<String, Object>();

		mapInfo.put("email", userDto.getEmail());
		mapInfo.put("companyKey", companyDto.getCode());
		mapInfo.put("companyName", companyDto.getName());
		mapInfo.put("branchCode", userDto.getBranch().getCode());
		mapInfo.put("configurationComplite", userDto.getIsConfigurationComplete());
		mapInfo.put("roleJob", roleJob);
		mapInfo.put("operationalRole", operationalRole);

		authentication.setDetails(mapInfo);
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(mapInfo);
		return accessToken;
	}
}
