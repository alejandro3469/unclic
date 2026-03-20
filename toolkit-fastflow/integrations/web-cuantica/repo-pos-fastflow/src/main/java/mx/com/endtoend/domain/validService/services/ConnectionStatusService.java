package mx.com.endtoend.domain.validService.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.company.dto.CompanyDto;
import mx.com.endtoend.domain.company.ports.api.CompanyServicePort;
import mx.com.endtoend.domain.validService.business.CompanyFactory;
import mx.com.endtoend.domain.validService.business.StatusConnectionInterface;
import mx.com.endtoend.domain.validService.dto.ConnectionStatus;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.constants.CompanyCodes;

@Service
public class ConnectionStatusService implements StatusSystemServicePort {

	@Autowired
	private CompanyFactory companyFactory;

	@Autowired
	private CompanyServicePort companyServicePort;

	@SuppressWarnings("unchecked")
	@Override
	public ResponseModel getStatusDBConnection() {

		List<ConnectionStatus> connectionStatusServices = new ArrayList<>();

		ResponseModel responseModel = companyServicePort.companyList("VALID-SERVICE", CompanyCodes.ETE.toString());
		List<CompanyDto> companyDtoList = (List<CompanyDto>) responseModel.getData();

		for (CompanyDto companyDto : companyDtoList) {
			System.out.println(companyDto.getCode());
			StatusConnectionInterface statusConnectionInterface = companyFactory.getCompanyByCode(companyDto.getCode());
			if (statusConnectionInterface != null)
				connectionStatusServices.add(statusConnectionInterface.getDBStatus());
		}

		return new ResponseModel(connectionStatusServices);
	}

}
