package mx.com.endtoend.infrastructure.creditNote.common.business;

import mx.com.endtoend.domain.creditNote.dto.*;
import mx.com.endtoend.domain.creditNote.dto.ticket.CreditNoteTickteDto;
import mx.com.endtoend.infrastructure.catalogue.orders.common.converters.StatusConverter;
import mx.com.endtoend.infrastructure.client.common.converters.ClientConverter;
import mx.com.endtoend.infrastructure.client.common.repository.BaseClientRepository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.creditNote.common.converters.CreditNoteConverter;
import mx.com.endtoend.infrastructure.creditNote.common.converters.CreditNoteDetailConverter;
import mx.com.endtoend.infrastructure.creditNote.common.converters.CreditNoteHeaderConverter;
import mx.com.endtoend.infrastructure.creditNote.common.entities.CreditNoteDetailEntity;
import mx.com.endtoend.infrastructure.creditNote.common.entities.CreditNoteEntity;
import mx.com.endtoend.infrastructure.creditNote.common.entities.CreditNoteHeaderEntity;
import mx.com.endtoend.infrastructure.creditNote.common.persistence.GenericCreditNotePersistenceInterface;
import mx.com.endtoend.infrastructure.creditNote.common.repository.BaseCreditNoteHeaderRepository;
import mx.com.endtoend.infrastructure.creditNote.common.repository.BaseCreditNoteRepository;
import mx.com.endtoend.infrastructure.creditNote.common.repository.BaseCustomDSLCreditNoteRepository;
import mx.com.endtoend.infrastructure.orders.common.converters.AddressConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.OrderDetailConverter;
import mx.com.endtoend.infrastructure.orders.common.converters.TaxConverter;
import mx.com.endtoend.infrastructure.orders.common.entities.OrderEntity;
import mx.com.endtoend.infrastructure.orders.common.repository.BaseOrderRepository;
import mx.com.endtoend.infrastructure.payments.common.converters.PaymentConverter;
import mx.com.endtoend.infrastructure.payments.common.repository.BasePaymentRepository;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.clients.dto.ClientDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import mx.com.endtoend.smart.bussiness.model.orders.dto.AddressDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDetailDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.OrderDto;
import mx.com.endtoend.smart.bussiness.model.orders.dto.TaxDto;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class BaseCreditNoteBusinessRepository implements GenericCreditNotePersistenceInterface {

    private final Map<String, String> TEMPLATES;
    private final Logger LOG;
    private final CreditNoteConverter creditNoteConverter;
    private final CreditNoteHeaderConverter creditNoteHeaderConverter;
    private final CreditNoteDetailConverter creditNoteDetailConverter;
    private final BaseCreditNoteRepository creditNoteRepository;
    private final BaseCreditNoteHeaderRepository creditNoteHeaderRepository;
    private final BaseCustomDSLCreditNoteRepository customDSLCreditNoteRepository;
    private final PaymentConverter paymentConverter;
    private final BasePaymentRepository paymentRepository;
    private final BaseOrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final StatusConverter statusConverter;
    private final ClientConverter clientConverter;
    private final BaseClientRepository clientRepository;
    private final AddressConverter addressConverter;
    private final TaxConverter taxConverter;
    private final OrderDetailConverter orderDetailConverter;

    public BaseCreditNoteBusinessRepository(Class<?> loggerClass, Map<String, String> _TEMPLATES,
                                            CreditNoteConverter _creditNoteConverter,
                                            CreditNoteHeaderConverter _creditNoteHeaderConverter,
                                            CreditNoteDetailConverter _creditNoteDetailConverter,
                                            BaseCreditNoteRepository _creditNoteRepository,
                                            BaseCreditNoteHeaderRepository _creditNoteHeaderRepository,
                                            BaseCustomDSLCreditNoteRepository _customDSLCreditNoteRepository,
                                            PaymentConverter _paymentConverter,
                                            BasePaymentRepository _paymentRepository,
                                            BaseOrderRepository _orderRepository,
                                            OrderConverter _orderConverter,
                                            StatusConverter _statusConverter,
                                            ClientConverter _clientConverter,
                                            BaseClientRepository _clientRepository,
                                            AddressConverter _addressConverter,
                                            TaxConverter _taxConverter,
                                            OrderDetailConverter _orderDetailConverter) {
        this.LOG = LoggerFactory.getLogger(loggerClass);
        this.TEMPLATES = _TEMPLATES;
        this.creditNoteConverter = _creditNoteConverter;
        this.creditNoteHeaderConverter = _creditNoteHeaderConverter;
        this.creditNoteDetailConverter = _creditNoteDetailConverter;
        this.creditNoteRepository = _creditNoteRepository;
        this.creditNoteHeaderRepository = _creditNoteHeaderRepository;
        this.customDSLCreditNoteRepository = _customDSLCreditNoteRepository;
        this.paymentConverter = _paymentConverter;
        this.paymentRepository = _paymentRepository;
        this.orderRepository = _orderRepository;
        this.orderConverter = _orderConverter;
        this.statusConverter = _statusConverter;
        this.clientConverter = _clientConverter;
        this.clientRepository = _clientRepository;
        this.addressConverter = _addressConverter;
        this.taxConverter = _taxConverter;
        this.orderDetailConverter = _orderDetailConverter;
    }

    protected String getTemplatePath(String key) {
        String path = TEMPLATES.get(key);
        if (path == null) {
            throw new IllegalArgumentException("La clave '" + key + "' no existe en los templates.");
        }
        return path;
    }


    protected String getTicketNcTemplatePath() {
        return getTemplatePath("ticket_nc_template");
    }

    protected String getLogoReport() {
        return getTemplatePath("logo_report");
    }


    @Transactional
    @Override
    public CreditNoteDto createCreditNote(CreditNoteDto creditNoteDto, String idOperation) {
        try {
            LOG.info("{} INIT createCreditNote()", idOperation);
            final CreditNoteEntity creditNoteEntity = creditNoteConverter.creditNoteDtoToCreditNoteEntity(creditNoteDto);

            List<CreditNoteHeaderEntity> creditNoteHeaderEntityList = creditNoteDto.getCreditNoteHeaderList()
                    .stream()
                    .map(creditNoteHeaderDto -> {
                        CreditNoteHeaderEntity creditNoteHeaderEntity = creditNoteHeaderConverter
                                .creditNoteHeaderDtoToCreditNoteHeaderEntity(creditNoteHeaderDto);

                        List<CreditNoteDetailEntity> creditNoteDetailEntities = creditNoteDetailConverter
                                .creditNoteDetailDtoListToCreditNoteDetailEntityList(creditNoteHeaderDto.getCreditNoteDetail())
                                .stream()
                                .peek(creditNoteDetailEntity -> creditNoteDetailEntity.setCreditNoteHeader(creditNoteHeaderEntity))
                                .collect(Collectors.toList());

                        creditNoteHeaderEntity.setCreditNoteDetail(creditNoteDetailEntities);
                        return creditNoteHeaderEntity;
                    })
                    .peek(creditNoteHeaderEntity -> creditNoteHeaderEntity.setCreditNote(creditNoteEntity))
                    .collect(Collectors.toList());

            creditNoteEntity.setCreditNoteHeaderList(creditNoteHeaderEntityList);

            CreditNoteEntity savedCreditNoteEntity = creditNoteRepository.save(creditNoteEntity);

            return convertToReturn(savedCreditNoteEntity);

        } catch (Exception e) {
            LOG.error("{} ERROR IN createCreditNote(). EXCEPTION: {}", idOperation, e.getMessage());
            throw new GlobalError();
        }
    }

    @Transactional
	@Override
	public OrderDto getOrderByParams(BigDecimal orderNumber, String orderCode, String companyCode, String idOperation) {

		try {

			LOG.info(String.format("%s INIT getOrderByParams() ", idOperation));
			Optional<OrderEntity> order = orderRepository.findByOrderCodeAndOrderNumber(orderNumber, orderCode);

			if (order.isPresent()) {
				OrderEntity orderEntity = order.get();
				OrderDto orderDto = orderConverter.orderEntityToOrderDto(orderEntity, Arrays.asList(""));

				StatusDto statusDto = statusConverter.statusEntityToStatusDto(orderEntity.getStatus());
				orderDto.setStatus(statusDto);

                ClientDto clientDto = clientConverter
                        .clientEntityToClientDto(clientRepository.findById(order.get().getClientId()));
				orderDto.setClient(clientDto);

				List<AddressDto> addressDtoList = addressConverter
						.addresEntityListToAddresDtoList(orderEntity.getAddresses());
				orderDto.setAddresses(addressDtoList);

				List<TaxDto> taxDtoList = taxConverter.taxEntityListToTaxDtoList(orderEntity.getTaxes());
				orderDto.setTaxes(taxDtoList);

				List<OrderDetailDto> orderDetailDto = orderDetailConverter
						.orderDetailEntityToOrderDetailDetoList(orderEntity.getOrderDetail());
				orderDto.setOrderDetail(orderDetailDto);

				return orderDto;

			} else {
				LOG.warn(String.format("%s ORDER: %s NOT FOUND", idOperation, orderNumber.toString()));
				return null;
			}

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getOrderByParams(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}


    private CreditNoteDto convertToReturn(CreditNoteEntity creditNoteEntity) {
        CreditNoteDto creditNoteSaved = creditNoteConverter.creditNoteEntityToCreditNoteDto(creditNoteEntity);
        List<CreditNoteHeaderDto> creditNoteHeaderSaved = new ArrayList<>();
        for (CreditNoteHeaderEntity creditNoteHeaderEntity : creditNoteEntity.getCreditNoteHeaderList()) {
            CreditNoteHeaderDto creditNoteHeaderDto = creditNoteHeaderConverter
                    .creditNoteHearderEntityToCreditNoteHeaderDto(creditNoteHeaderEntity);
            List<CreditNoteDetailDto> creditNoteDetailDtos = creditNoteDetailConverter
                    .creditNoteEntityListToCreditNoteDetailDtoList(creditNoteHeaderEntity.getCreditNoteDetail());
            creditNoteHeaderDto.setCreditNoteDetail(creditNoteDetailDtos);
            creditNoteHeaderSaved.add(creditNoteHeaderDto);
        }
        creditNoteSaved.setCreditNoteHeaderList(creditNoteHeaderSaved);
        return creditNoteSaved;
    }

    @Transactional
    @Override
    public CreditNoteHeaderDto searchCreditNoteHeaderByFolioAndCodeAndCompanyCode(BigDecimal folio,
                                                                                  String creditNoteCode, String idOperation) {
        try {

            LOG.info(String.format("%s INIT searchCreditNoteHeaderByFolioAndCodeAndCompanyCode()", idOperation));

            Optional<CreditNoteHeaderEntity> creditNoteHeaderOptional = creditNoteHeaderRepository
                    .findByFolioAndCreditNoteCode(folio, creditNoteCode);

            CreditNoteHeaderDto creditNoteHeaderDto = null;
            if (creditNoteHeaderOptional.isPresent()) {
                creditNoteHeaderDto = creditNoteHeaderConverter
                        .creditNoteHearderEntityToCreditNoteHeaderDto(creditNoteHeaderOptional.get());
                List<CreditNoteDetailDto> creditNoteDetailDtos = creditNoteDetailConverter
                        .creditNoteEntityListToCreditNoteDetailDtoList(
                                creditNoteHeaderOptional.get().getCreditNoteDetail());
                creditNoteHeaderDto.setCreditNoteDetail(creditNoteDetailDtos);
            }

            return creditNoteHeaderDto;

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN searchCreditNoteHeaderByFolioAndCodeAndCompanyCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Transactional
    @Override
    public CreditNoteDto searchCreditNoteByOrderNumberAndCodeAndCompanyCode(BigDecimal orderNumber, String orderCode,
                                                                            String idOperation) {
        try {

            LOG.info(String.format("%s INIT searchCreditNoteByOrderNumberAndCodeAndCompanyCode()", idOperation));
            Optional<CreditNoteEntity> creditNoteOptional = creditNoteRepository
                    .findByOrderNumberAndOrderCode(orderNumber, orderCode);

            CreditNoteDto creditNoteDto = null;
            if (creditNoteOptional.isPresent())
                creditNoteDto = convertToReturn(creditNoteOptional.get());

            return creditNoteDto;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN searchCreditNoteByOrderNumberAndCodeAndCompanyCode(). EXCEPTION: %s",
                    idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public List<CreditNoteSummary> searchCreditNoteListByParams(CreditNoteSearchParamsDto noteSearchParamsDto,
                                                                String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchCreditNoteListByParams()", idOperation));
            List<CreditNoteSummary> creditNoteSummaries = customDSLCreditNoteRepository
                    .findCreditNoteSummaryByParams(noteSearchParamsDto);
            return creditNoteSummaries;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN searchCreditNoteListByParams(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public ResponseModel generateTicket(CreditNoteTickteDto creditNoteTickteDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT generateTicket() ", idOperation));
            LOG.info(creditNoteTickteDto.toString());
            LOG.info(String.format("%s LOAD IMAGE ", idOperation));
            InputStream logoImage = this.getClass().getResourceAsStream(getLogoReport());
            creditNoteTickteDto.setImage(logoImage);

            LOG.info(String.format("%s LOAD REPORT ", idOperation));
            InputStream file = this.getClass().getResourceAsStream(getTicketNcTemplatePath());

            LOG.info(String.format("%s LOAD DATA ", idOperation));
            Collection<CreditNoteTickteDto> collection = Collections.singletonList(creditNoteTickteDto);

            LOG.info(String.format("%s GENERATE REPORT TEMPLATE ", idOperation));
            JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
                    new JRBeanCollectionDataSource(collection));

            LOG.info(String.format("%s GENERATE ARRAY BYTE ", idOperation));
            byte[] finalReport = JasperExportManager.exportReportToPdf(jasperPrint);

            LOG.info(String.format("%s RETURN DATA ", idOperation));
            return new ResponseModel(finalReport);

        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN generateTicket(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

    @Override
    public void updteCreditNoteHeaderBalanceByParams(CreditNoteHeaderDto creditNoteHeaderDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT updteCreditNoteHeaderBalanceByParams() ", idOperation));
            creditNoteHeaderRepository.updteBalanceByFolioAndCode(creditNoteHeaderDto.getUsedAmountM().doubleValue(),
                    creditNoteHeaderDto.getPendingAmount().doubleValue(), creditNoteHeaderDto.getFolio(),
                    creditNoteHeaderDto.getCreditNoteCode());
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updteCreditNoteHeaderBalanceByParams(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
        }
    }

    @Override
    public void updatePrintStatusById(Long id, boolean printStatus, String idOperation) {
        try {
            LOG.info(String.format("%s INIT updteCreditNoteHeaderBalanceByParams() ", idOperation));
            creditNoteHeaderRepository.updatePrintStatusById(printStatus, id);
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updatePrintStatusById(). EXCEPTION: %s", idOperation, e.getMessage()));
        }

    }

    @Override
    public void updateCreditNoteBalanceByParams(CreditNoteDto creditNoteDto, String idOperation) {
        try {
            LOG.info(String.format("%s INIT updateCreditNoteBalanceByParams() ", idOperation));
            creditNoteRepository.updateTotalById(creditNoteDto.getIsTotal(), creditNoteDto.getId());
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN updateCreditNoteBalanceByParams(). EXCEPTION: %s", idOperation,
                    e.getMessage()));
        }

    }

    @Transactional
    @Override
    public CreditNoteDto searchCreditNoteById(Long creditNoteId, String idOperation) {
        try {
            LOG.info(String.format("%s INIT searchCreditNoteById() ", idOperation));
            Optional<CreditNoteEntity> creditNoteOptional = creditNoteRepository.findById(creditNoteId);
            CreditNoteDto creditNoteDto = null;
            if (creditNoteOptional.isPresent())
                creditNoteDto = creditNoteConverter.creditNoteEntityToCreditNoteDto(creditNoteOptional.get());
            return creditNoteDto;
        } catch (Exception e) {
            LOG.error(String.format("%s ERROR IN searchCreditNoteById(). EXCEPTION: %s", idOperation, e.getMessage()));
            throw new GlobalError();
        }
    }

}
