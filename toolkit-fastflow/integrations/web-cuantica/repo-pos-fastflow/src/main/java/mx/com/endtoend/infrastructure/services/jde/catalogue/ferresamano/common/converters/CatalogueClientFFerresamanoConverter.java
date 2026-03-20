package mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.client.CFDIDto;
import mx.com.endtoend.domain.catalogue.dto.client.ClientTypeDto;
import mx.com.endtoend.domain.catalogue.dto.client.ContactMethodDto;
import mx.com.endtoend.domain.catalogue.dto.client.RegimeFiscalDto;
import mx.com.endtoend.domain.catalogue.dto.client.WorkTypeDto;
import mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.entities.F0005;

@Component
public class CatalogueClientFFerresamanoConverter {

	public ClientTypeDto f0005ToClientTypeDto(F0005 f0005) {
		ClientTypeDto clientTypeDto = new ClientTypeDto();
		clientTypeDto.setId(null);
		clientTypeDto.setIsEnable(true);
		clientTypeDto.setCode(f0005.getId().getDrky());
		clientTypeDto.setValue(f0005.getDrdl01() + f0005.getDrdl02());
		return clientTypeDto;
	}

	public List<ClientTypeDto> f005ListToClientTypeDtoList(List<F0005> f0005List) {
		List<ClientTypeDto> clientTypeDtoList = new ArrayList<>();
		for (F0005 f0005 : f0005List) {
			clientTypeDtoList.add(f0005ToClientTypeDto(f0005));
		}
		return clientTypeDtoList;
	}

	public RegimeFiscalDto f0005ToRegimeFiscalDto(F0005 f0005) {
		RegimeFiscalDto regimeFiscalDto = new RegimeFiscalDto();
		regimeFiscalDto.setId(null);
		regimeFiscalDto.setIsEnable(true);
		regimeFiscalDto.setCode(f0005.getId().getDrky());
		regimeFiscalDto.setSatCode(f0005.getDrsphd());
		regimeFiscalDto.setValue(f0005.getDrdl01() + f0005.getDrdl02());
		return regimeFiscalDto;
	}

	public List<RegimeFiscalDto> f0005ListToRegimeFiscalDtoList(List<F0005> f0005List) {
		List<RegimeFiscalDto> regimeFiscalDtoList = new ArrayList<>();
		for (F0005 f0005 : f0005List) {
			regimeFiscalDtoList.add(f0005ToRegimeFiscalDto(f0005));
		}
		return regimeFiscalDtoList;
	}

	public ContactMethodDto f0005ToContactMethodDto(F0005 f0005) {
		ContactMethodDto contactMethodDto = new ContactMethodDto();
		contactMethodDto.setId(null);
		contactMethodDto.setIsEnable(true);
		contactMethodDto.setCode(f0005.getId().getDrky());
		contactMethodDto.setValue(f0005.getDrdl01() + f0005.getDrdl02());
		return contactMethodDto;
	}

	public List<ContactMethodDto> f0005ListToContactMethodDtoList(List<F0005> f0005List) {
		List<ContactMethodDto> contactMethodDtoList = new ArrayList<>();
		for (F0005 f0005 : f0005List) {
			contactMethodDtoList.add(f0005ToContactMethodDto(f0005));
		}
		return contactMethodDtoList;
	}

	public CFDIDto f0005ToCFDIDto(F0005 f0005) {
		CFDIDto cfdiDto = new CFDIDto();
		cfdiDto.setId(null);
		cfdiDto.setIsEnable(true);
		cfdiDto.setCode(f0005.getId().getDrky());
		cfdiDto.setValue(f0005.getDrdl01() + f0005.getDrdl02());
		return cfdiDto;
	}

	public List<CFDIDto> f0005ListToCFDIDtoList(List<F0005> f0005List) {
		List<CFDIDto> cfdiDtoList = new ArrayList<>();
		for (F0005 f0005 : f0005List) {
			cfdiDtoList.add(f0005ToCFDIDto(f0005));
		}
		return cfdiDtoList;
	}

	public WorkTypeDto f0005ToWorkTypeDto(F0005 f0005) {
		WorkTypeDto workTypeDto = new WorkTypeDto();
		workTypeDto.setId(null);
		workTypeDto.setIsEnable(true);
		workTypeDto.setCode(f0005.getId().getDrky());
		workTypeDto.setValue(f0005.getDrdl01() + f0005.getDrdl02());
		return workTypeDto;
	}

	public List<WorkTypeDto> f0005ListToWorkTypeDtoList(List<F0005> f0005List) {
		List<WorkTypeDto> workTypeDtoList = new ArrayList<>();
		for (F0005 f0005 : f0005List) {
			workTypeDtoList.add(f0005ToWorkTypeDto(f0005));
		}
		return workTypeDtoList;
	}

}

