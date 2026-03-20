package mx.com.endtoend.infrastructure.cash.creditCardReference.common.business;

import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardDto;
import mx.com.endtoend.domain.cash.creditCardReference.dto.CreditCardHistoryChangeDto;
import mx.com.endtoend.domain.cash.creditCardReference.dto.PaymentOptionDto;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.CreditCardHistoryChangeConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.CreditCardReferenceConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.converters.PaymentOptionReferenceConverter;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities.CreditCardEntity;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities.CreditCardHistoryChangeEntity;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.entities.PaymentOptionEntity;
import mx.com.endtoend.infrastructure.cash.creditCardReference.common.persistence.GenericCreditCardConfigurationPersistence;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.MappedSuperclass;
import java.util.List;
import java.util.Optional;

@MappedSuperclass
public class BaseCreditCardConfigurationRepository implements GenericCreditCardConfigurationPersistence {

    private final CreditCardReferenceConverter creditCardReferenceConverter;
    private final PaymentOptionReferenceConverter paymentOptionReferenceConverter;
    private final CreditCardHistoryChangeConverter cardHistoryChangeConverter;
    private final BaseCreditCardRepository creditCardRepository;
    private final BasePaymentOptionRepository paymentOptionRepository;
    private final BaseCreditCardHistoryChangeRepository cardHistoryChangeRepository;
    private final Logger LOG;

    public BaseCreditCardConfigurationRepository(Class<?> loggerClass,
                                                 CreditCardReferenceConverter _creditCardReferenceConverter,
                                                 PaymentOptionReferenceConverter _paymentOptionReferenceConverter,
                                                 CreditCardHistoryChangeConverter _cardHistoryChangeConverter,
                                                 BaseCreditCardRepository _creditCardRepository,
                                                 BasePaymentOptionRepository _paymentOptionRepository,
                                                 BaseCreditCardHistoryChangeRepository _cardHistoryChangeRepository ){
        LOG=  LoggerFactory.getLogger(loggerClass);
        creditCardReferenceConverter= _creditCardReferenceConverter;
        paymentOptionReferenceConverter = _paymentOptionReferenceConverter;
        cardHistoryChangeConverter = _cardHistoryChangeConverter;
        creditCardRepository= _creditCardRepository;
        paymentOptionRepository= _paymentOptionRepository;
        cardHistoryChangeRepository= _cardHistoryChangeRepository;
    }

    @Override
    public CreditCardDto createCreditCardReference(CreditCardDto creditCardDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT createCreditCardReference() ", idOperation));

            CreditCardEntity creditCardEntity = creditCardReferenceConverter
                    .creditCardDtoToCreditCardEntity(creditCardDto);
            List<PaymentOptionEntity> paymentOptionEntities = paymentOptionReferenceConverter
                    .paymentOptionDtoListToPaymentOptionEntityList(creditCardDto.getPaymentOptionDetail());
            for (PaymentOptionEntity paymentOptionEntity : paymentOptionEntities) {
                paymentOptionEntity.setCreditCard(creditCardEntity);
            }
            creditCardEntity.setPaymentOptionDetail(paymentOptionEntities);
            creditCardRepository.save(creditCardEntity);
            LOG.info(String.format("%s RETURN SAVE DATA", idOperation));
            return creditCardReferenceConverter.creditCardEntityToCreditCardDto(creditCardEntity);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN createCreditCardReference(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public CreditCardDto updateCreditCardReference(CreditCardDto creditCardDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT updateCreditCardReference() ", idOperation));

            CreditCardEntity creditCardEntity = creditCardReferenceConverter
                    .creditCardDtoToCreditCardEntity(creditCardDto);
            List<PaymentOptionEntity> paymentOptionEntities = paymentOptionReferenceConverter
                    .paymentOptionDtoListToPaymentOptionEntityList(creditCardDto.getPaymentOptionDetail());
            for (PaymentOptionEntity paymentOptionEntity : paymentOptionEntities) {
                paymentOptionEntity.setCreditCard(creditCardEntity);
            }
            creditCardEntity.setPaymentOptionDetail(paymentOptionEntities);
            creditCardRepository.save(creditCardEntity);
            LOG.info(String.format("%s RETURN SAVE DATA", idOperation));
            return creditCardReferenceConverter.creditCardEntityToCreditCardDto(creditCardEntity);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updateCreditCardReference(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public CreditCardDto getCreditCardReferenceById(Long id, String idOperation) {
        try {
            LOG.info(String.format("%s INIT getCreditCardReferenceById() ", idOperation));
            Optional<CreditCardEntity> creditCardOptional = creditCardRepository.findById(id);
            CreditCardDto creditCardDto = null;
            if (creditCardOptional.isPresent()) {
                creditCardDto = creditCardReferenceConverter.creditCardEntityToCreditCardDto(creditCardOptional.get());
                List<PaymentOptionDto> paymentOptionDtos = paymentOptionReferenceConverter
                        .paymentOptionEntityListToPaymentOptionDtoList(
                                creditCardOptional.get().getPaymentOptionDetail());
                creditCardDto.setPaymentOptionDetail(paymentOptionDtos);
            }
            LOG.info(String.format("%s RETURN DATA", idOperation));
            return creditCardDto;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getCreditCardReferenceById(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public List<CreditCardDto> getCreditCardReferenceListByEnable(boolean enable, String idOperation) {
        try {
            LOG.info(String.format("%s INIT getCreditCardReferenceListByEnable() ", idOperation));
            List<CreditCardDto> creditCardDtoList = null;
            List<CreditCardEntity> credCardEntityList = creditCardRepository.findAllByEnable(enable);
            if (!credCardEntityList.isEmpty()) {
                creditCardDtoList = creditCardReferenceConverter
                        .creditCardEntityListToCreditCardDtoList(credCardEntityList);
            }
            LOG.info(String.format("%s RETURN DATA", idOperation));
            return creditCardDtoList;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN getCreditCardReferenceListByEnable(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }

    }

    @Override
    public CreditCardDto findCreditCardReferenceByCodeOrInstitutionOrType(CreditCardDto creditCardDto,
                                                                          String idOperation) {
        try {
            LOG.info(String.format("%s INIT findCreditCardReferenceByCodeOrInstitution() ", idOperation));
            Optional<CreditCardEntity> creditCardOptional = creditCardRepository.findByCodeOrInstitutionOrType(
                    creditCardDto.getCode(), creditCardDto.getType(), creditCardDto.getBankingInstitution());
            CreditCardDto creditCard = null;
            if (creditCardOptional.isPresent()) {
                creditCard = creditCardReferenceConverter.creditCardEntityToCreditCardDto(creditCardOptional.get());
                List<PaymentOptionDto> paymentOptionDtos = paymentOptionReferenceConverter
                        .paymentOptionEntityListToPaymentOptionDtoList(
                                creditCardOptional.get().getPaymentOptionDetail());
                creditCard.setPaymentOptionDetail(paymentOptionDtos);
            }
            LOG.info(String.format("%s RETURN DATA", idOperation));
            return creditCard;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN findCreditCardReferenceByCodeOrInstitution(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public CreditCardDto findCreditCardReferenceByCodeOrInstitutionOrTypeAndIdNot(CreditCardDto creditCardDto,
                                                                                  String idOperation) {
        try {
            LOG.info(String.format("%s INIT findCreditCardReferenceByCodeOrInstitutionAndIdNot() ", idOperation));
            Optional<CreditCardEntity> creditCardOptional = creditCardRepository.findByCodeOrInstitutionOrTypeAndIdNot(
                    creditCardDto.getCode(), creditCardDto.getType(), creditCardDto.getBankingInstitution(),
                    creditCardDto.getId());
            CreditCardDto creditCard = null;
            if (creditCardOptional.isPresent()) {
                creditCard = creditCardReferenceConverter.creditCardEntityToCreditCardDto(creditCardOptional.get());
                List<PaymentOptionDto> paymentOptionDtos = paymentOptionReferenceConverter
                        .paymentOptionEntityListToPaymentOptionDtoList(
                                creditCardOptional.get().getPaymentOptionDetail());
                creditCard.setPaymentOptionDetail(paymentOptionDtos);
            }
            LOG.info(String.format("%s RETURN DATA", idOperation));
            return creditCard;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN findCreditCardReferenceByCodeOrInstitutionAndIdNot(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public PaymentOptionDto findPaymentOptionByPeriodAndInstitutionAndType(String period, String institution,
                                                                           String type, String idOperation) {
        try {
            LOG.info(String.format("%s INIT findPaymentOptionByPeriodAndInstitutionAndType() ", idOperation));
            Optional<PaymentOptionEntity> paymentOptionOptional = paymentOptionRepository
                    .findByPeriodAndBankingInstitutionAndType(period, type, institution);
            PaymentOptionDto paymentOptionDto = null;
            if (paymentOptionOptional.isPresent()) {
                paymentOptionDto = paymentOptionReferenceConverter
                        .paymentOptionEntityToPaymentOptionDto(paymentOptionOptional.get());
            }
            LOG.info(String.format("%s RETURN DATA", idOperation));
            return paymentOptionDto;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN findPaymentOptionByPeriodAndInstitutionAndType(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public PaymentOptionDto findPaymentOptionByPeriodAndInstitutionAndTypeAndIdNot(Long idPaymentOption, String period,
                                                                                   String institution, String type, String idOperation) {
        try {
            LOG.info(String.format("%s INIT findPaymentOptionByPeriodAndInstitutionAndIdNot() ", idOperation));
            Optional<PaymentOptionEntity> paymentOptionOptional = paymentOptionRepository
                    .findByPeriodAndBankingInstitutionAndTypeAndIdInstitutionNot(period, institution, type,
                            idPaymentOption);
            PaymentOptionDto paymentOptionDto = null;
            if (paymentOptionOptional.isPresent()) {
                paymentOptionDto = paymentOptionReferenceConverter
                        .paymentOptionEntityToPaymentOptionDto(paymentOptionOptional.get());
            }
            LOG.info(String.format("%s RETURN DATA", idOperation));
            return paymentOptionDto;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN findPaymentOptionByPeriodAndInstitutionAndIdNot(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public void saveCreditCardChanges(List<CreditCardHistoryChangeDto> creditCardHistoryChangeList,
                                      String idOperation) {
        try {
            LOG.info(String.format("%s INIT saveCreditCardChanges() ", idOperation));
            List<CreditCardHistoryChangeEntity> creditCardHistoryChangeEntities = cardHistoryChangeConverter
                    .creditCardHistoryDtoListToCreditCardHistoryEntityList(creditCardHistoryChangeList);

            for (CreditCardHistoryChangeEntity creditCardHistoryChangeEntity : creditCardHistoryChangeEntities) {
                cardHistoryChangeRepository.save(creditCardHistoryChangeEntity);
            }
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN saveCreditCardChanges(). EXCEPTION: %s", idOperation, e.getMessage()));
        }
    }

}