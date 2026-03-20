package mx.com.endtoend.infrastructure.services.jde.catalogue.demo.common.repository;

import java.util.List;

import mx.com.endtoend.domain.catalogue.dto.address.ColonyDto;
import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityCPDto;

public interface CustomDirectionFDemoRepository {

	List<ColonyDto> getColonyList(String stateCode);

	List<String> getMunicipalityNameListByStateCode(String stateCode);

	List<MunicipalityCPDto> getMunicipalityAndCpList(String stateCode);
}
