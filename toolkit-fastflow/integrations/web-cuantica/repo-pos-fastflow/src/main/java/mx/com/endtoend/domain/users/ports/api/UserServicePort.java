package mx.com.endtoend.domain.users.ports.api;

import mx.com.endtoend.domain.logs.security.ports.SecurityLogServicePort;
import mx.com.endtoend.domain.users.dto.GenericSerchParamsUserDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface UserServicePort {

	ResponseModel findUserByEmailToLogin(String email, String idOperation);

	ResponseModel createUser(UserDto user, String idOperation);

	ResponseModel updateUser(SecurityLogServicePort securityLogServicePort, String userLogged, UserDto user,
			String idOperation);

	ResponseModel enableById(Long id, boolean enable, String idOperation);

	ResponseModel findById(Long id, String idOperation);

	ResponseModel findByUserNumber(Long userNumber, String idOperation);

	ResponseModel findAllByBranchAndEnable(String bracnhCode, boolean enable, String idOperation);

	ResponseModel findUserLitsByParamsAndPage(int pageNumber, int rows,
			GenericSerchParamsUserDto genericSerchParamsUserDto, String idOperation);

	ResponseModel changeStatusSessionByUsernameAndBranchCode(String email, String branchCode, boolean status,
			String idOperation);

}
