package mx.com.endtoend.infrastructure.branch.common.adapters;

import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.branch.ports.spi.BranchPersistencePort;
import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.entities.BranchEntity;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.company.common.converters.CompanyConverter;
import mx.com.endtoend.infrastructure.company.common.entities.CompanyEntity;
import mx.com.endtoend.infrastructure.company.common.repositories.CompanyRepository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class BaseBranchJpaAdapter implements BranchPersistencePort {

    private final Logger LOG;
    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;
    private final BranchConverter branchConverter;
    private final BranchRepository branchRepository;

    public BaseBranchJpaAdapter(Class<?> loggerClass,
                                CompanyRepository _companyRepository,
                                CompanyConverter _companyConverter,
                                BranchConverter _branchConverter,
                                BranchRepository _branchRepository) {
        LOG = LoggerFactory.getLogger(loggerClass);

        companyRepository = _companyRepository;
        companyConverter = _companyConverter;
        branchConverter = _branchConverter;
        branchRepository = _branchRepository;
    }

    @Transactional
    @Override
    public ResponseModel createBranch(BranchDto branchDto, String companyCode, String idOperation) {
        try {
            LOG.info(String.format("%s INIT createBranch() ", idOperation));
            Optional<CompanyEntity> companyEntity = companyRepository
                    .findByCompanyCode(CompanyCodes.valueOf(companyCode));
            LOG.info(String.format("%s COMPANY TO BRANCH %s", idOperation, companyEntity.get().getName()));
            BranchEntity branchEntity = branchConverter.branchDtoToBranchEntity(branchDto);
            branchEntity.setCompany(companyEntity.get());
            branchEntity = branchRepository.save(branchEntity);
            if (branchEntity != null) {
                LOG.info(String.format("%s BRANCH CREATED OK: %s", idOperation, branchEntity.getName()));
                return new ResponseModel(branchConverter.branchEntityToBranchDto(branchEntity));
            } else {
                LOG.error(String.format("%s ERROR CREATING BRANCH", idOperation));
                throw new GlobalError();
            }
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR SAVING BRANCH. EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel updateBranch(BranchDto branchDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT updateBranch() ", idOperation));
            BranchEntity branchEntity = branchConverter.branchDtoToBranchEntity(branchDto);
            CompanyEntity companyEntity = companyConverter.companyDtoEntity(branchDto.getCompany());
            branchEntity.setCompany(companyEntity);
            branchEntity = branchRepository.save(branchEntity);
            if (branchEntity != null) {
                LOG.info(String.format("%s BRANCH CREATED OK", idOperation));
                return new ResponseModel(branchConverter.branchEntityToBranchDto(branchEntity));
            } else {
                LOG.error(String.format("%s ERROR IN UPDATE BRANCH", idOperation));
                throw new GlobalError();
            }
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR SAVING BRANCH TO UPDATE. EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
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
    public ResponseModel existsNameByCompanyCode(String name, String companyCode, String idOperation) {
        try {
            LOG.info(String.format("%s INIT existsNameByCompanyCode() ", idOperation));
            boolean valid = false;
            Optional<BranchEntity> branchEntity = branchRepository.findByNameAndCompanyCode(name,
                    CompanyCodes.valueOf(companyCode));
            valid = (branchEntity.isPresent() ? true : false);
            return new ResponseModel(valid);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN SERCH BRANCH BY NAME AND COMPANY_CODE. EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel existsCodeByCompanyCode(String code, String companyCode, String idOperation) {
        try {
            LOG.info(String.format("%s INIT existsCodeByCompanyCode() ", idOperation));
            boolean valid = false;
            Optional<BranchEntity> branchEntity = branchRepository.findByCodeAndCompanyCode(code,
                    CompanyCodes.valueOf(companyCode));
            valid = (branchEntity.isPresent() ? true : false);
            return new ResponseModel(valid);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN SERCH BRANCH BY CODE AND COMPANY_CODE. EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel existsNameByCompanyCodeAndIdNot(String name, String companyCode, Long id, String idOperation) {
        try {
            LOG.info(String.format("%s INIT existsNameByCompanyCodeAndIdNot() ", idOperation));
            boolean valid = false;
            Optional<BranchEntity> branchEntity = branchRepository.findByNameAndCompanyCodeAndIdNot(name,
                    CompanyCodes.valueOf(companyCode), id);
            valid = (branchEntity.isPresent() ? true : false);
            return new ResponseModel(valid);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN SERCH BRANCH BY NAME, COMPANY_CODE AND ID. EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel existsCodeByCompanyCodeAndIdNot(String code, String companyCode, Long id, String idOperation) {
        try {
            LOG.info(String.format("%s INIT existsCodeByCompanyCodeAndIdNot() ", idOperation));
            boolean valid = false;
            Optional<BranchEntity> branchEntity = branchRepository.findByCodeAndCompanyCodeAndIdNot(code,
                    CompanyCodes.valueOf(companyCode), id);
            valid = (branchEntity.isPresent() ? true : false);
            return new ResponseModel(valid);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN SERCH BRANCH BY CODE, COMPANY_CODE AND ID. EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel getBranchById(Long id, String idOperation) {
        try {
            LOG.info(String.format("%s INIT getBranchById() ", idOperation));
            Optional<BranchEntity> branchOptional = branchRepository.findById(id);
            BranchDto branchDto = new BranchDto();
            if (branchOptional.isPresent()) {
                branchDto = branchConverter.branchEntityToBranchDto(branchOptional.get());
                return new ResponseModel(branchDto);
            } else {
                return new ResponseModel(branchDto.branchDtoEmpty());
            }
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN SERCH BRANCH. EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public ResponseModel getBranchDetailByBranchCodeAndCompanyCode(String branchCode, String companyCode,
                                                                   String idOperation) {
        try {
            LOG.info(String.format("%s INIT getBranchDetailByBranchCodeAndCompanyCode() ", idOperation));
            Optional<BranchEntity> branchEntityOptional = branchRepository.findByCodeAndCompanyCode(branchCode,
                    CompanyCodes.valueOf(companyCode));
            BranchDto branchDto = null;
            if (branchEntityOptional.isPresent()) {
                branchDto = branchConverter.branchEntityToBranchDto(branchEntityOptional.get());
            }
            return new ResponseModel(branchDto);

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getBranchDetailByBranchCodeAndCompanyCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }
}
