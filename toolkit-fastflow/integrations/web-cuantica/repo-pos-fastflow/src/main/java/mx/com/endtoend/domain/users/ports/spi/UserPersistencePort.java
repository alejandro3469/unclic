package mx.com.endtoend.domain.users.ports.spi;

import mx.com.endtoend.domain.users.dto.GenericSerchParamsUserDto;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

public interface UserPersistencePort {

	ResponseModel create(UserDto userDto, String idOperation);

	ResponseModel update(UserDto userDto, String idOperation);

	ResponseModel findById(Long id, String idOperation);

	ResponseModel findByUserNumber(Long userNomber, String idOperation);

	ResponseModel findByUserNumberAndBranchCode(Long userNomber, String branchCode, String idOperation);

	ResponseModel enableById(Long id, boolean enable, String idOperation);

	ResponseModel findPasswordById(Long id, String idOperation);

	ResponseModel existsById(Long id, String idOperation);

	ResponseModel existsByUserNumberAndBranchCode(Long userNumber, String branchCode, String idOperation);

	ResponseModel existsByUserNumberAndBranchCodeAndIdNot(Long userNumber, String branchCode, Long id,
			String idOperation);

	ResponseModel existsByUserNumber(Long userNumber, String idOperation);

	ResponseModel existsByUserNumberAndIdNot(Long userNumber, Long id, String idOperation);

	ResponseModel existEmail(String email, String idOperation);

	ResponseModel existEmailAndIdNot(String email, Long id, String idOperation);

	ResponseModel findAllByEnableAndBranchCode(boolean enable, String branchCode, String idOperation);

	ResponseModel findByEmailToLogin(String email, String idOperation);

	ResponseModel updateStatusActiveByUserNameAndBranchCode(String email, String branchCode, boolean active);

	ResponseModel findAllByParamsAndPage(int pageNumber, int rows, GenericSerchParamsUserDto genericSerchParamsUserDto,
			String idOperation);
}
