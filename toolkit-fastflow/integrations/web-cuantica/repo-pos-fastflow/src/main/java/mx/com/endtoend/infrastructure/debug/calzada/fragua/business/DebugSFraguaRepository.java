package mx.com.endtoend.infrastructure.debug.calzada.fragua.business;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.debug.dto.DebugDto;
import mx.com.endtoend.infrastructure.debug.GenericDebugRepositoryInterface;
import mx.com.endtoend.infrastructure.debug.calzada.fragua.repositories.DebugConfigurationFraguaRepository;
import mx.com.endtoend.infrastructure.debug.calzada.fragua.repositories.DebugFraguaRepository;
import mx.com.endtoend.infrastructure.debug.calzada.mysql.converters.DebugConverter;
import mx.com.endtoend.infrastructure.debug.calzada.mysql.entities.DebugConfigurationEntity;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class DebugSFraguaRepository implements GenericDebugRepositoryInterface {

	@Autowired
	private DebugFraguaRepository debugRepository;

	@Autowired
	private DebugConfigurationFraguaRepository debugConfigurationRepository;

	@Autowired
	private DebugConverter debugConverter;

	@Override
	public ResponseModel getDebugStatusByCompanyCodeAndModule(String module) {
		try {
			boolean active = false;
			Optional<DebugConfigurationEntity> debugConfigurationOptional = debugConfigurationRepository
					.findByModule(module);
			if (debugConfigurationOptional.isPresent()) {
				active = debugConfigurationOptional.get().isActive();
			}
			return new ResponseModel(active);
		} catch (Exception e) {
			return new ResponseModel(false);
		}

	}

	@Override
	public void saveLog(DebugDto debugDto) {
		try {
			debugRepository.save(debugConverter.debugDtoToDebugEntity(debugDto));
		} catch (Exception e) {
			System.out.println("ERROR");
		}

	}

}
