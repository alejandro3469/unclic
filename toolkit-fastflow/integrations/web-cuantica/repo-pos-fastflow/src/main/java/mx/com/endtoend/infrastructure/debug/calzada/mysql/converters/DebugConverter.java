package mx.com.endtoend.infrastructure.debug.calzada.mysql.converters;

import org.springframework.stereotype.Component;

import mx.com.endtoend.domain.debug.dto.DebugDto;
import mx.com.endtoend.infrastructure.debug.calzada.mysql.entities.DebugEntity;

@Component
public class DebugConverter {

	public DebugEntity debugDtoToDebugEntity(DebugDto debugDto) {
		DebugEntity debugEntity = new DebugEntity();
		debugEntity.setModule(debugDto.getModule());
		debugEntity.setIdInstance(debugDto.getIdInstance());
		debugEntity.setCompanyCode(debugDto.getCompanyCode());
		debugEntity.setLog(debugDto.getLog());
		debugEntity.setDate(debugDto.getDate());
		debugEntity.setIdOperation(debugDto.getIdOperation());
		return debugEntity;
	}
}
