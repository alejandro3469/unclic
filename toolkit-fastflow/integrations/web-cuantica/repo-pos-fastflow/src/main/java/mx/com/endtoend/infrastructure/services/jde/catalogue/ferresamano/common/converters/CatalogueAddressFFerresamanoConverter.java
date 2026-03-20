package mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.catalogue.dto.address.CoordinateDto;
import mx.com.endtoend.domain.catalogue.dto.address.CountryDto;
import mx.com.endtoend.domain.catalogue.dto.address.FlatDto;
import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityCPDto;
import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityDto;
import mx.com.endtoend.domain.catalogue.dto.address.StateDto;
import mx.com.endtoend.infrastructure.services.jde.catalogue.ferresamano.common.entities.F0005;

@Component
public class CatalogueAddressFFerresamanoConverter {

    public CoordinateDto f0005ToCoordinateDto(F0005 f0005) {
        CoordinateDto coordinateDto = new CoordinateDto();
        coordinateDto.setId(null);
        coordinateDto.setIsEnable(true);
        coordinateDto.setCode(f0005.getId().getDrky());
        coordinateDto.setName(f0005.getDrdl01() + f0005.getDrdl02());
        return coordinateDto;
    }

    public List<CoordinateDto> f0005ListToCoordinateDtoList(List<F0005> f0005List) {
        List<CoordinateDto> coordinateDtoList = new ArrayList<>();
        for (F0005 f0005 : f0005List) {
            coordinateDtoList.add(f0005ToCoordinateDto(f0005));
        }
        return coordinateDtoList;
    }

    public FlatDto f0005ToFlatDto(F0005 f0005) {
        FlatDto flatDto = new FlatDto();
        flatDto.setId(null);
        flatDto.setIsEnable(true);
        flatDto.setCode(f0005.getId().getDrky());
        flatDto.setName(f0005.getDrdl01() + " " + f0005.getDrdl02());
        return flatDto;
    }

    public List<FlatDto> f0005ListToFlatDtoList(List<F0005> f0005List) {
        List<FlatDto> flatDtoList = new ArrayList<>();
        for (F0005 f0005 : f0005List) {
            flatDtoList.add(f0005ToFlatDto(f0005));
        }
        return flatDtoList;
    }

    public CountryDto f0005ToCountryDto(F0005 f0005){
        CountryDto countryDto = new CountryDto();
        countryDto.setId(null);
        countryDto.setIsEnable(true);
        countryDto.setCode(f0005.getId().getDrky());
        countryDto.setName(f0005.getDrdl01() + " " + f0005.getDrdl02());
        return  countryDto;
    }

    public List<CountryDto> f0005ListToCountryDtoList(List<F0005> f0005List) {
        List<CountryDto> countryDtoList = new ArrayList<>();
        for (F0005 f0005 : f0005List) {
            countryDtoList.add(f0005ToCountryDto(f0005));
        }
        return countryDtoList;
    }

    public StateDto f0005ToStateDto(F0005 f0005){
        StateDto stateDto = new StateDto();
        stateDto.setId(null);
        stateDto.setIsEnable(true);
        stateDto.setCode(f0005.getId().getDrky());
        stateDto.setName(f0005.getDrdl01() + " " + f0005.getDrdl02());
        return  stateDto;
    }

    public List<StateDto> f0005ListToStateDtoList(List<F0005> f0005List) {
        List<StateDto> stateDtoList = new ArrayList<>();
        for (F0005 f0005 : f0005List) {
            stateDtoList.add(f0005ToStateDto(f0005));
        }
        return stateDtoList;
    }

    public MunicipalityDto f0005ToMunicipalityDto(F0005 f0005, String stateCode){
        MunicipalityDto municipalityDto = new MunicipalityDto();
        municipalityDto.setId(null);
        municipalityDto.setIsEnable(true);
        municipalityDto.setCode(f0005.getId().getDrky());
        municipalityDto.setName(f0005.getDrdl01() + " " + f0005.getDrdl02());
        municipalityDto.setStateCode(stateCode);
        return municipalityDto;
    }

    public List<MunicipalityDto> f0005ListToMunicipalityDtoList(List<F0005> f0005List, String stateCode){
        List<MunicipalityDto> municipalityDtoList = new ArrayList<>();
        for (F0005 f0005 : f0005List) {
            municipalityDtoList.add(f0005ToMunicipalityDto(f0005, stateCode));
        }
        return municipalityDtoList;
    }
    
    public List<MunicipalityCPDto> completeDataInMunicipalityCpDtoList(List<MunicipalityCPDto> municipalityCPDtoList, String stateCode){
    	for (MunicipalityCPDto municipalityCPDto : municipalityCPDtoList) {
    		municipalityCPDto.setStateCode(stateCode);
		}
    	return municipalityCPDtoList;
    }
}

