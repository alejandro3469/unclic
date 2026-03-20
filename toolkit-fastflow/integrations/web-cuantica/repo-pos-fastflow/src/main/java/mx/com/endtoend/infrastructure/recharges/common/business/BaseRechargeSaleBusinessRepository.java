package mx.com.endtoend.infrastructure.recharges.common.business;

import mx.com.endtoend.domain.recharges.dto.CompanyPhoneDto;
import mx.com.endtoend.domain.recharges.dto.CompanyRechargeConfigurationDto;
import mx.com.endtoend.domain.recharges.dto.RechargeRequestDto;
import mx.com.endtoend.domain.recharges.dto.RechargeTickteDto;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.recharges.calzada.entities.OperationTraceSeliaEntity;
import mx.com.endtoend.infrastructure.recharges.calzada.entities.RechargeRequestEntity;
import mx.com.endtoend.infrastructure.recharges.calzada.entities.RechargeSeliaConfigurationEntity;
import mx.com.endtoend.infrastructure.recharges.common.converters.CompanyPhoneConverter;
import mx.com.endtoend.infrastructure.recharges.common.converters.RechargerequestConverter;
import mx.com.endtoend.infrastructure.recharges.common.entities.CompanyPhoneEntity;
import mx.com.endtoend.infrastructure.recharges.common.repository.*;
import mx.com.endtoend.infrastructure.services.selia.SeliaServicePort;
import mx.com.endtoend.infrastructure.services.selia.models.SeliaRequest;
import mx.com.endtoend.infrastructure.services.selia.models.SeliaResponse;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.MappedSuperclass;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

@MappedSuperclass
public class BaseRechargeSaleBusinessRepository implements GenericRechargeSaleRepository {
    private final Map<String, String> TEMPLATES;
    private final BaseCompanyPhoneRepository companyPhoneRepository;
    private final CompanyPhoneConverter companyPhoneConverter;
    private final BaseRechargeSeliaConfigRepository rechargeSeliaConfigRepository;
    private final SeliaServicePort seliaServicePort;
    private final BaseOperationTraceSeliaRepository operationTraceSeliaRepository;
    private final BaseRechargeRequestRepository rechargeRequestRepository;
    private final RechargerequestConverter rechargerequestConverter;
    private final Logger LOG;

    public BaseRechargeSaleBusinessRepository(Class<?> loggerClass, Map<String, String> _TEMPLATES,
                                              BaseCompanyPhoneRepository _companyPhoneRepository,
                                             CompanyPhoneConverter _companyPhoneConverter,
                                             BaseRechargeSeliaConfigRepository _rechargeSeliaConfigRepository,
                                             SeliaServicePort _seliaServicePort,
                                             BaseOperationTraceSeliaRepository _operationTraceSeliaRepository,
                                             BaseRechargeRequestRepository _rechargeRequestRepository,
                                             RechargerequestConverter _rechargerequestConverter) {
        LOG = LoggerFactory.getLogger(loggerClass);
        TEMPLATES = _TEMPLATES;
        companyPhoneRepository = _companyPhoneRepository;
        companyPhoneConverter = _companyPhoneConverter;
        rechargeSeliaConfigRepository = _rechargeSeliaConfigRepository;
        seliaServicePort = _seliaServicePort;
        operationTraceSeliaRepository = _operationTraceSeliaRepository;
        rechargeRequestRepository = _rechargeRequestRepository;
        rechargerequestConverter = _rechargerequestConverter;

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


    @Override
    public CompanyRechargeConfigurationDto getCompanyConfigurationByRecharge(String idOperation) {
        try {
            LOG.info("INIT getCompanyConfigurationByRecharge()");
            List<RechargeSeliaConfigurationEntity> rechargeConfigurationList = rechargeSeliaConfigRepository.findAll();
            RechargeSeliaConfigurationEntity rechargeSeliaConfigurationEntity = rechargeConfigurationList.get(0);
            return new CompanyRechargeConfigurationDto(rechargeSeliaConfigurationEntity.getCompanyCode(),
                    rechargeSeliaConfigurationEntity.getOriginatorCode(), rechargeSeliaConfigurationEntity.getUser(),
                    rechargeSeliaConfigurationEntity.getPassword());
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            throw new GlobalError();
        }

    }

    @Override
    public SeliaResponse sendRechargeRequestToSELIAByCompanyCode(SeliaRequest seliaRequest, String idOperation) {
        try {
            LOG.info("INIT sendRechargeRequestToSELIAByCompanyCode()");
            SeliaResponse seliaResponse = seliaServicePort.generatRecharge(seliaRequest);
            try {
                OperationTraceSeliaEntity operationTrace = new OperationTraceSeliaEntity(null,
                        seliaRequest.getPhoneNumber(), new Date(), seliaRequest.toString(), seliaResponse.toString());
                operationTraceSeliaRepository.save(operationTrace);
            } catch (Exception e) {
                LOG.error("ERROR SAVING RESPONSE FROM EXTERNAL SERVICE: " + e.getMessage());
            }
            return seliaResponse;
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            throw new GlobalError();
        }
    }

    @Override
    public List<CompanyPhoneDto> getCompanyPhoneListByCompanyCode(String idOperation) {
        try {
            LOG.info("INIT getCompanyPhoneListByCompanyCode()");
            List<CompanyPhoneEntity> companyPhoneEntityList = companyPhoneRepository.findAll();
            return companyPhoneConverter.companyPhoneEntityListToCompanyPhoneDtoList(companyPhoneEntityList);
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage());
            return null;
        }

    }

    @Override
    public void saveRechargeRequest(RechargeRequestDto rechargeRequestDto, String idOperation) {
        try {
            LOG.info("INIT saveRechargeRequest()");
            RechargeRequestEntity rechargeRequestEntity = rechargerequestConverter
                    .rechargeRequestDtoToRechargeRequestEntity(rechargeRequestDto);
            rechargeRequestRepository.save(rechargeRequestEntity);
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage(), e);
        }
    }

    @Override
    public RechargeRequestDto getRechargeRequestByOrderNumberAndCodeAndCompanyCode(BigDecimal orderNumber,
                                                                                   String orderCode, String idOperation) {
        try {
            LOG.info("INIT getRechargeRequestByOrderNumberAndCodeAndCompanyCode()");
            Optional<RechargeRequestEntity> rechargeOptional = rechargeRequestRepository
                    .findByOrderNumberAndOrderCode(orderNumber, orderCode);
            if (rechargeOptional.isPresent())
                return rechargerequestConverter.rechargeRequestEntityToRechargeRequestDto(rechargeOptional.get());
            return null;
        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage(), e);
            throw new GlobalError();
        }
    }

    @Override
    public ResponseModel generateTicketRechargeSaleByCompanmyCode(RechargeTickteDto rechargeTickteDto,
                                                                  String idOperation) {
        try {
            LOG.info("INIT generateTicketRechargeSaleByCompanmyCode()");

            LOG.info(String.format("%s LOAD REPORT ", idOperation));
            InputStream file = this.getClass().getResourceAsStream(getTicketTemplatePath());

            LOG.info(String.format("%s LOAD DATA ", idOperation));
            Collection<RechargeTickteDto> collection = Collections.singletonList(rechargeTickteDto);

            LOG.info(String.format("%s GENERATE REPORT TEMPLATE ", idOperation));
            JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
                    new JRBeanCollectionDataSource(collection));

            LOG.info(String.format("%s GENERATE ARRAY BYTE ", idOperation));
            byte[] finalReport = JasperExportManager.exportReportToPdf(jasperPrint);

            LOG.info(String.format("%s RETURN DATA ", idOperation));
            return new ResponseModel(finalReport);

        } catch (Exception e) {
            LOG.error("EXCEPTION: " + e.getMessage(), e);
            throw new GlobalError();
        }
    }

}

