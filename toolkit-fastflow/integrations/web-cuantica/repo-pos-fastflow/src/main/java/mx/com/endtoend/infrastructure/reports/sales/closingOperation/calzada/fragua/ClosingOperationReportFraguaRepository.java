package mx.com.endtoend.infrastructure.reports.sales.closingOperation.calzada.fragua;


import mx.com.endtoend.infrastructure.reports.sales.closingOperation.common.repository.BaseClosingOperationReportRepository;
import org.springframework.stereotype.Service;

import mx.com.endtoend.infrastructure.reports.sales.closingOperation.calzada.fragua.repositories.CustomDSLClosingOperationFraguaRepository;

@Service
public class ClosingOperationReportFraguaRepository extends BaseClosingOperationReportRepository {

	public ClosingOperationReportFraguaRepository(CustomDSLClosingOperationFraguaRepository customDSLClosingOperationFraguaRepository){
		super(ClosingOperationReportFraguaRepository.class, customDSLClosingOperationFraguaRepository);
	}
}
