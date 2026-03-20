package mx.com.endtoend.infrastructure.debug.calzada;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.debug.dto.DebugDto;
import mx.com.endtoend.infrastructure.debug.GenericDebugRepositoryInterface;
import mx.com.endtoend.infrastructure.debug.calzada.mysql.converters.DebugConverter;
import mx.com.endtoend.infrastructure.debug.calzada.mysql.entities.DebugConfigurationEntity;
import mx.com.endtoend.infrastructure.debug.calzada.mysql.repositories.DebugConfigurationRepository;
import mx.com.endtoend.infrastructure.debug.calzada.mysql.repositories.DebugRepository;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
public class DebugCalzadaRepository implements GenericDebugRepositoryInterface {

	@Autowired
	private DebugRepository debugRepository;

	@Autowired
	private DebugConfigurationRepository debugConfigurationRepository;

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
