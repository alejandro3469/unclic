package mx.com.endtoend.infrastructure.catalogue.address.converters;

import mx.com.endtoend.domain.catalogue.dto.address.CountryDto;
import mx.com.endtoend.infrastructure.catalogue.address.entities.CountryEntity;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CountryConverter {

    public CountryDto countryEntityToCountryDto(CountryEntity countryEntity) {
        CountryDto countryDto = new CountryDto();
        countryDto.setId(countryEntity.getId());
        countryDto.setIsEnable(countryEntity.isEnable());
        countryDto.setCode(countryEntity.getCode());
        countryDto.setName(countryEntity.getName());
        return countryDto;
    }

    public CountryEntity countryDtoToCountryEntity(CountryDto countryDto) {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setId(countryEntity.getId());
        countryEntity.setEnable(countryDto.getIsEnable());
        countryEntity.setCode(countryDto.getCode());
        countryEntity.setName(countryDto.getName());
        return countryEntity;
    }

    public List<CountryDto> countryEntityListToCountryDtoList(List<CountryEntity> countryEntityList) {
        List<CountryDto> countryDtoList = new ArrayList<>();
        for (CountryEntity countryEntity : countryEntityList) {
            countryDtoList.add(countryEntityToCountryDto(countryEntity));
        }
        return countryDtoList;
    }

    public List<CountryEntity> countryDtoListToCountryEntityList(List<CountryDto> countryDtoList) {
        List<CountryEntity> countryEntityList = new ArrayList<>();
        for (CountryDto countryDto : countryDtoList) {
            countryEntityList.add(countryDtoToCountryEntity(countryDto));
        }
        return countryEntityList;
    }
}
