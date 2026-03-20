package mx.com.endtoend.infrastructure.company.common.converters;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.commons.constants.GenericIdentifyMethods;
import mx.com.endtoend.domain.company.dto.MethodDto;
import mx.com.endtoend.infrastructure.company.common.entities.MethodEntity;

/**
 * 
 * @author ddcasas
 *
 */

@Component
public class MethodConverter {

	
	public MethodEntity methodDtoToMethodEntity (MethodDto methodDto) {
		MethodEntity methodEntity = new MethodEntity();
		
		methodEntity.setId(methodDto.getId());
		methodEntity.setModule(methodDto.getModule());
		methodEntity.setMethodCode(GenericIdentifyMethods.valueOf(methodDto.getCode()));
		
		return methodEntity; 
	}
	
	
	public MethodDto methodEntityToMethodDto(MethodEntity methodEntity) {
		
		MethodDto methodDto = new MethodDto();
		
		methodDto.setId(methodEntity.getId());
		methodDto.setCode(methodEntity.getMethodCode().toString());
		methodDto.setModule(methodEntity.getModule());
		
		return methodDto;
		
	}
	
	public List<MethodDto> methodEntityListToMethodDtoList(List<MethodEntity> methodEntityList){
		List<MethodDto> methodDtoList = new ArrayList<MethodDto>();
		for (MethodEntity methodEntity : methodEntityList) {
			methodDtoList.add(methodEntityToMethodDto(methodEntity));
		}	
		return methodDtoList;
	}
	
	public List<MethodEntity> methodDtoListToMethodEntityList(List<MethodDto> methodDtoList){
		List<MethodEntity> methodEntityList = new ArrayList<MethodEntity>();
		for (MethodDto methodDto : methodDtoList) {
			methodEntityList.add(methodDtoToMethodEntity(methodDto));
		}
		return methodEntityList;
	}
}
