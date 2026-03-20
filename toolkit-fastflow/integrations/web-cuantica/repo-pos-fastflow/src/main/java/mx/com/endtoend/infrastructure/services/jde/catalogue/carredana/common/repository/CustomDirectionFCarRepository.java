package mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.repository;

import java.util.List;

import mx.com.endtoend.domain.catalogue.dto.address.ColonyDto;
import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityCPDto;

public interface CustomDirectionFCarRepository {

	List<ColonyDto> getColonyList(String stateCode);

	List<String> getMunicipalityNameListByStateCode(String stateCode);

	List<MunicipalityCPDto> getMunicipalityAndCpList(String stateCode);
}
