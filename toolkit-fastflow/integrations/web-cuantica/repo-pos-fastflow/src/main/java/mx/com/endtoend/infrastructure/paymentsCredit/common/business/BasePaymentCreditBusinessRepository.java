package mx.com.endtoend.infrastructure.paymentsCredit.common.business;

import mx.com.endtoend.domain.paymentsCredit.dto.*;
import mx.com.endtoend.infrastructure.paymentsCredit.calzada.business.PaymentCreditCalzadaRepository;
import mx.com.endtoend.infrastructure.paymentsCredit.calzada.repositories.*;
import mx.com.endtoend.infrastructure.paymentsCredit.common.converters.CreditSaleRequestConverter;
import mx.com.endtoend.infrastructure.paymentsCredit.common.converters.CreditSaleResponseConverter;
import mx.com.endtoend.infrastructure.paymentsCredit.common.entities.*;
import mx.com.endtoend.infrastructure.paymentsCredit.common.repository.*;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

public class BasePaymentCreditBusinessRepository implements PaymentCreditRepositoryInterface {

    private final Map<String, String> TEMPLATES;
    private final BaseCreditSaleRequestRepository creditSaleRequestRepository;
    private final BaseCreditSaleDetailrepository creditSaleDetailrepository;
    private final BaseCreditSaleResponseRepository creditSaleResponseRepository;
    private final CreditSaleRequestConverter creditSaleRequestConverter;
    private final BaseStatusSaleRequestRepository statusSaleRequestRepository;
    private final BaseStatusSaleResponseRepository statusSaleResponseRepository;
    private final BaseStatusSaleDetailRepository statusSaleDetailRepository;
    private final BaseStatusArticleDetailRepository statusArticleDetailRepository;
    private final CreditSaleResponseConverter creditSaleResponseConverter;
    private final Logger LOG;


    public BasePaymentCreditBusinessRepository(Class<?> loggerClass,Map<String, String> _TEMPLATES,
                                               BaseCreditSaleRequestRepository _creditSaleRequestRepository,
                                                BaseCreditSaleDetailrepository _creditSaleDetailrepository,
                                                BaseCreditSaleResponseRepository _creditSaleResponseRepository,
                                                CreditSaleRequestConverter _creditSaleRequestConverter,
                                                BaseStatusSaleRequestRepository _statusSaleRequestRepository,
                                                BaseStatusSaleResponseRepository _statusSaleResponseRepository,
                                                BaseStatusSaleDetailRepository _statusSaleDetailRepository,
                                                BaseStatusArticleDetailRepository _statusArticleDetailRepository,
                                                CreditSaleResponseConverter _creditSaleResponseConverter) {
        LOG = LoggerFactory.getLogger(loggerClass);
        TEMPLATES = _TEMPLATES;
        creditSaleRequestRepository = _creditSaleRequestRepository;
        creditSaleDetailrepository = _creditSaleDetailrepository;
        creditSaleResponseRepository = _creditSaleResponseRepository;
        creditSaleRequestConverter = _creditSaleRequestConverter;
        statusSaleRequestRepository = _statusSaleRequestRepository;
        statusSaleResponseRepository = _statusSaleResponseRepository;
        statusSaleDetailRepository = _statusSaleDetailRepository;
        statusArticleDetailRepository = _statusArticleDetailRepository;
        creditSaleResponseConverter = _creditSaleResponseConverter;
    }

    protected String getTemplatePath(String key) {
        String path = TEMPLATES.get(key);
        if (path == null) {
            throw new IllegalArgumentException("La clave '" + key + "' no existe en los templates.");
        }
        return path;
    }

    protected String getTicketTemplatePath() {
        return getTemplatePath("ticket_template");
    }

    protected String getLogoReport() {
        return getTemplatePath("logo_report");
    }



    @Override
    public Boolean saveCreditSaleRequest(CreditSaleRequestDto creditSaleRequestDto, String idOperation) {
        try {
            CreditSaleRequestEntity creditSaleRequestEntity = creditSaleRequestConverter
                    .creditSaleRequestDtoToEntity(creditSaleRequestDto);
            creditSaleRequestEntity = creditSaleRequestRepository.save(creditSaleRequestEntity);

            for (CreditSaleDetailDto creditSaleDetailDto : creditSaleRequestDto.getDetail()) {
                CreditSaleDetailEntity creditSaleDetailEntity = creditSaleRequestConverter
                        .creditSaleDetailDtoToEntity(creditSaleDetailDto, creditSaleRequestEntity.getId());
                creditSaleDetailrepository.save(creditSaleDetailEntity);
            }
            return true;
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean saveCreditSaleResponse(CreditSaleResponseDto creditSaleResponseDto, String idOperation) {
        try {
            CreditSaleResponseEntity creditSaleResponseEntity = creditSaleRequestConverter
                    .creditSaleResponseDtoToEntity(creditSaleResponseDto);
            creditSaleResponseEntity = creditSaleResponseRepository.save(creditSaleResponseEntity);
            return true;
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean savePaymentStateRequest(StatusSaleRequestDto statusSaleRequestDto, String idOperation) {
        try {
            StatusSaleRequestEntity saleRequestEntity = creditSaleResponseConverter
                    .statusSaleRequestDtoToEntity(statusSaleRequestDto);
            saleRequestEntity = statusSaleRequestRepository.save(saleRequestEntity);
            return true;
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Boolean savePaymentStateResponse(StatusSaleResponseDto statusSaleResponseDto, String idOperation) {
        try {
            StatusSaleResponseEntity saleResponseEntity = creditSaleResponseConverter
                    .statusSaleResponseDtoToEntity(statusSaleResponseDto);
            saleResponseEntity = statusSaleResponseRepository.save(saleResponseEntity);

            if (statusSaleResponseDto.getPreventa() != null) {
                StatusSaleDetailEntity statusSaleDetailEntity = creditSaleResponseConverter
                        .statusSaleDetailDtoToEntity(statusSaleResponseDto.getPreventa(), saleResponseEntity.getId());
                statusSaleDetailEntity = statusSaleDetailRepository.save(statusSaleDetailEntity);

                if (statusSaleResponseDto.getPreventa().getDetail() != null) {
                    for (StatusArticleDetail statusArticleDetail : statusSaleResponseDto.getPreventa().getDetail()) {
                        StatusArticleDetailEntity statusArticleDetailEntity = creditSaleResponseConverter
                                .statusArticleDetailDtoToEntity(statusArticleDetail, statusSaleDetailEntity.getId());
                        statusArticleDetailEntity = statusArticleDetailRepository.save(statusArticleDetailEntity);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            return false;
        }
    }

    @Override
    public CreditSaleStausDto getPaymentStateByOrderNumberAndCode(BigDecimal orderNumber, String orderCode,
                                                                  String idOperation) {
        try {
            List<CreditSaleRequestEntity> creditSaleRequestList = creditSaleRequestRepository
                    .findLastRecordByOrderNumberAndOrderCode(orderNumber, orderCode);
            CreditSaleRequestDto creditSaleRequest = null;
            if (creditSaleRequestList.size() > 0) {
                creditSaleRequest = creditSaleRequestConverter
                        .entityToCreditSaleRequestDto(creditSaleRequestList.get(0));
                List<CreditSaleDetailEntity> creditSaleDetailList = creditSaleDetailrepository
                        .findAllByRequestId(creditSaleRequestList.get(0).getId());
                List<CreditSaleDetailDto> creditSaleDetailDtoLit = new ArrayList<>();
                for (CreditSaleDetailEntity creditSaleDetailEntity : creditSaleDetailList) {
                    creditSaleDetailDtoLit
                            .add(creditSaleRequestConverter.entityToCreditSaleDetailDto(creditSaleDetailEntity));
                }
                creditSaleRequest.setDetail(creditSaleDetailDtoLit);
            }

            List<CreditSaleResponseEntity> creditSaleResponseList = creditSaleResponseRepository
                    .findLastRecordByOrderNumberAndOrderCode(orderNumber, orderCode);
            CreditSaleResponseDto lastCreditSaleResponse = null;
            if (creditSaleResponseList.size() > 0)
                lastCreditSaleResponse = creditSaleRequestConverter
                        .entityToCreditSaleResponseDto(creditSaleResponseList.get(0));

            List<StatusSaleRequestEntity> statusSaleRequestEntityList = statusSaleRequestRepository
                    .findLastRecordByOrderNumberAndOrderCode(orderNumber, orderCode);
            StatusSaleRequestDto lastStatusSaleRequest = null;
            if (statusSaleRequestEntityList.size() > 0) {
                lastStatusSaleRequest = creditSaleResponseConverter
                        .entityToStatusSaleRequestDto(statusSaleRequestEntityList.get(0));
            }

            List<StatusSaleResponseEntity> statusSaleResponseEntityList = statusSaleResponseRepository
                    .findLastRecordByOrderNumberAndOrderCode(orderNumber, orderCode);
            StatusSaleResponseDto lastStatusSaleResponse = null;
            if (statusSaleResponseEntityList.size() > 0) {
                lastStatusSaleResponse = creditSaleResponseConverter
                        .entityToStatusSaleResponseDto(statusSaleResponseEntityList.get(0));
                Optional<StatusSaleDetailEntity> statusSaleDetailOptional = statusSaleDetailRepository
                        .findAllByRequestId(statusSaleResponseEntityList.get(0).getId());
                if (statusSaleDetailOptional.isPresent()) {
                    StatusSaleDetailDto statusSaleDetailDto = creditSaleResponseConverter
                            .entityToStatusSaleDetailDto(statusSaleDetailOptional.get());

                    List<StatusArticleDetailEntity> statusArticleDetailEntityList = statusArticleDetailRepository
                            .findAllByRequestId(statusSaleDetailOptional.get().getId());
                    List<StatusArticleDetail> statusArticleDetailList = new ArrayList<>();
                    for (StatusArticleDetailEntity statusArticleDetailEntity : statusArticleDetailEntityList) {
                        statusArticleDetailList.add(
                                creditSaleResponseConverter.entityToStatusArticleDetail(statusArticleDetailEntity));
                    }

                    statusSaleDetailDto.setDetail(statusArticleDetailList);
                    lastStatusSaleResponse.setPreventa(statusSaleDetailDto);

                }

            }
            CreditSaleStausDto creditSaleStausDto = new CreditSaleStausDto(orderNumber, orderCode, creditSaleRequest,
                    lastCreditSaleResponse, lastStatusSaleRequest, lastStatusSaleResponse);

            return creditSaleStausDto;
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            return null;
        }
    }

    @Override
    public ResponseModel generateCreditPaymentTicket(PaymentCreditTicketDto paymentCreditTicketDto,
                                                     String idOperation) {
        try {

            LOG.info(String.format("%s INIT generateCreditPaymentTicket() ", idOperation));

            LOG.info(String.format("%s LOAD IMAGE ", idOperation));
            InputStream logoImage = this.getClass().getResourceAsStream(getLogoReport());

            paymentCreditTicketDto.setLogo(logoImage);
            LOG.info(String.format("%s LOAD REPORT ", idOperation));
            InputStream file = this.getClass().getResourceAsStream(getTicketTemplatePath());

            LOG.info(String.format("%s LOAD DATA ", idOperation));
            Collection<PaymentCreditTicketDto> collection = Collections.singletonList(paymentCreditTicketDto);

            LOG.info(String.format("%s GENERATE REPORT TEMPLATE ", idOperation));
            JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
                    new JRBeanCollectionDataSource(collection));

            LOG.info(String.format("%s GENERATE ARRAY BYTE ", idOperation));
            byte[] finalReport = JasperExportManager.exportReportToPdf(jasperPrint);

            LOG.info(String.format("%s RETURN DATA ", idOperation));
            return new ResponseModel(finalReport);
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean cancelCreditPaymentByOrderNumberAndCode(BigDecimal orderNumber, String orderCode,
                                                           String idOperation) {
        try {
            creditSaleRequestRepository.changeCreditStatus(false, orderNumber, orderCode);
            creditSaleResponseRepository.changeCreditStatus(false, orderNumber, orderCode);
            statusSaleRequestRepository.changeCreditStatus(false, orderNumber, orderCode);
            statusSaleResponseRepository.changeCreditStatus(false, orderNumber, orderCode);
            return true;
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            return false;
        }
    }

}
