package mx.com.endtoend.infrastructure.cash.bankReference.common.business;

import mx.com.endtoend.domain.cash.bankReference.dto.BankDto;
import mx.com.endtoend.infrastructure.cash.bankReference.common.converters.BankConverter;
import mx.com.endtoend.infrastructure.cash.bankReference.common.entities.BankEntity;
import mx.com.endtoend.infrastructure.cash.bankReference.common.persistence.GenericBankConfigurationPersistence;
import mx.com.endtoend.infrastructure.cash.bankReference.common.repository.BaseBankRepository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
@MappedSuperclass
public class BaseBankConfigurationBusinessRepository implements GenericBankConfigurationPersistence {


    private final BaseBankRepository bankRepository;

    private final BankConverter bankConverter;

    private final Logger LOG;

    public BaseBankConfigurationBusinessRepository(Class<?> loggerClass,
                                                   BankConverter _bankConverter,
                                                   BaseBankRepository _bankRepository) {
        LOG = LoggerFactory.getLogger(loggerClass);
        bankRepository = _bankRepository;
        bankConverter = _bankConverter;
    }

    @Override
    public BankDto createBank(BankDto bankDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT createBank()", idOperation));
            BankEntity bankEntity = bankConverter.bankDtoToBankEntity(bankDto);
            bankEntity = bankRepository.save(bankEntity);
            return bankConverter.bankEntityToBankDto(bankEntity);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN createBank(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public BankDto updateBank(BankDto bankDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT updateBank()", idOperation));
            BankEntity bankEntity = bankConverter.bankDtoToBankEntity(bankDto);
            bankEntity = bankRepository.save(bankEntity);
            return bankConverter.bankEntityToBankDto(bankEntity);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updateBank(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public BankDto getBankById(Long id, String idOperation) {
        try {
            LOG.info(String.format("%s INIT getBankById()", idOperation));
            Optional<BankEntity> bankEntityOptional = bankRepository.findById(id);
            BankDto bankDto = null;
            if (bankEntityOptional.isPresent()) {
                bankDto = bankConverter.bankEntityToBankDto(bankEntityOptional.get());
            }
            return bankDto;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updateBank(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public List<BankDto> getBankListByEnable(boolean active, String idOperation) {
        try {
            LOG.info(String.format("%s INIT getBankListByEnable()", idOperation));
            List<BankEntity> bankEntities = bankRepository.findAllByEnable(active);
            List<BankDto> bankDtoList = null;
            if (!bankEntities.isEmpty()) {
                bankDtoList = bankConverter.bankEntityListToBankDtoList(bankEntities);
            }
            return bankDtoList;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getBankListByEnable(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public BankDto findBankByInstitution(String institutionName, String idOperation) {
        try {
            LOG.info(String.format("%s INIT findBankByInstitution()", idOperation));
            Optional<BankEntity> bankEntityOptional = bankRepository.findByBankingInstitution(institutionName);
            BankDto bankDto = null;
            if (bankEntityOptional.isPresent()) {
                bankDto = bankConverter.bankEntityToBankDto(bankEntityOptional.get());
            }
            return bankDto;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN findBankByInstitution(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public BankDto findBankByInstitutionAndIdNot(BankDto bankDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT findBankByInstitutionAndIdNot()", idOperation));
            Optional<BankEntity> bankEntityOptional = bankRepository
                    .findByBankingInstitutionAndIdNot(bankDto.getBankingInstitution(), bankDto.getId());
            BankDto bank = null;
            if (bankEntityOptional.isPresent()) {
                bank = bankConverter.bankEntityToBankDto(bankEntityOptional.get());
            }
            return bank;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN findBankByInstitutionAndIdNot(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public List<BankDto> getBankListByUseTypeAndEnable(String useType, boolean active, String idOperation) {
        try {
            LOG.info(String.format("%s INIT getBankListByUseTypeAndEnable()", idOperation));
            List<BankEntity> bankEntities = bankRepository.findAllByEnableAndUseType(active, useType);
            List<BankDto> bankDtoList = null;
            if (!bankEntities.isEmpty()) {
                bankDtoList = bankConverter.bankEntityListToBankDtoList(bankEntities);
            }
            return bankDtoList;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getBankListByUseTypeAndEnable(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

}