package mx.com.endtoend.infrastructure.catalogue.branch.common.business;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.entities.BranchEntity;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.catalogue.branch.calzada.business.CatalogueBranchCalzadaRepository;
import mx.com.endtoend.infrastructure.catalogue.branch.common.repository.GenericCatalogueBranchRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.company.common.converters.CompanyConverter;
import mx.com.endtoend.infrastructure.company.common.entities.CompanyEntity;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;

public class BaseCatalogueBranchBusinessRepository implements GenericCatalogueBranchRepository {

    private final BranchRepository branchRepository;
    private final BranchConverter branchConverter;
    private final CompanyConverter companyConverter;

    private final Logger LOG;

    public BaseCatalogueBranchBusinessRepository(Class<?> loggerClass,
                                                 BranchRepository _branchRepository,
                                                 BranchConverter _branchConverter,
                                                 CompanyConverter _companyConverter){
        branchRepository = _branchRepository;
        branchConverter = _branchConverter;
        companyConverter = _companyConverter;
        LOG= LoggerFactory.getLogger(loggerClass);
    }

    @Transactional
    @Override
    public ResponseModel getBranchListByCompanyCode(String companyCode, String idOperation) {
        try {
            LOG.info(String.format("%s INIT getBranchListByCompanyCode() ", idOperation));
            List<BranchDto> branchDtoList = branchConverter.branchEntityListToBranchDtoList(
                    branchRepository.findAllByCompanyCode(CompanyCodes.valueOf(companyCode)));
            return new ResponseModel(branchDtoList);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN SERCH BRANCH LIST. EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel updateBranchByCompanyCode(BranchDto branchDto, String companyCode, String idOperation) {
        try {
            LOG.info(String.format("%s INIT updateBranchByCompanyCode() ", idOperation));

            BranchEntity branchEntity = branchConverter.branchDtoToBranchEntity(branchDto);
            CompanyEntity companyEntity = companyConverter.companyDtoEntity(branchDto.getCompany());
            branchEntity.setCompany(companyEntity);
            branchEntity = branchRepository.save(branchEntity);
            return new ResponseModel(true);

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR SAVING BRANCH TO UPDATE. EXCEPTION: %s", idOperation, e.getMessage()));
            return new ResponseModel(false);
        }
    }
}
