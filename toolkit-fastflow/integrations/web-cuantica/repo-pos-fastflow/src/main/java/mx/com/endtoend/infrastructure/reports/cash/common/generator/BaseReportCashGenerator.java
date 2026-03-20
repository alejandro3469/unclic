package mx.com.endtoend.infrastructure.reports.cash.common.generator;

import java.io.InputStream;
import java.util.*;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;

import mx.com.endtoend.infrastructure.accountingRecord.common.repository.BaseAccountingRecordRepository;
import mx.com.endtoend.infrastructure.cash.emailReport.common.business.BaseEmailCashConfigurationRepository;
import mx.com.endtoend.infrastructure.closings.common.repositories.BaseClosingOperationRepository;
import mx.com.endtoend.infrastructure.openings.common.repositories.BaseOpeningOperationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mx.com.endtoend.domain.accountingRecord.dto.AccountingRecordDto;
import mx.com.endtoend.domain.branch.dto.BranchDto;
import mx.com.endtoend.domain.cash.emailReport.dto.EmailReportCashDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDetailDto;
import mx.com.endtoend.domain.closings.dto.ClosingOperationDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDetailDto;
import mx.com.endtoend.domain.openings.dto.OpeningOperationDto;
import mx.com.endtoend.domain.reports.cash.dto.ClosingOperationReport;
import mx.com.endtoend.domain.reports.cash.dto.OpeningOperationReport;
import mx.com.endtoend.domain.users.dto.UserDto;
import mx.com.endtoend.infrastructure.accountingRecord.common.converters.AccountingRecordConverter;
import mx.com.endtoend.infrastructure.accountingRecord.common.entities.AccountingRecordEntity;
import mx.com.endtoend.infrastructure.branch.common.converters.BranchConverter;
import mx.com.endtoend.infrastructure.branch.common.entities.BranchEntity;
import mx.com.endtoend.infrastructure.branch.common.repositories.BranchRepository;
import mx.com.endtoend.infrastructure.closings.common.converters.ClosingOperationConverter;
import mx.com.endtoend.infrastructure.closings.common.converters.ClosingOperationDetailConverter;
import mx.com.endtoend.infrastructure.closings.common.entities.ClosingOperationEntity;
import mx.com.endtoend.infrastructure.commons.constants.CompanyCodes;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationConverter;
import mx.com.endtoend.infrastructure.openings.common.converters.OpeningOperationDetailConverter;
import mx.com.endtoend.infrastructure.openings.common.entities.OpeningOperationEntity;
import mx.com.endtoend.infrastructure.reports.cash.common.repository.GenericReportCashRepository;
import mx.com.endtoend.infrastructure.users.common.converters.UserConverter;
import mx.com.endtoend.infrastructure.users.common.entities.UserEntity;
import mx.com.endtoend.infrastructure.users.common.repository.UserRepository;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@MappedSuperclass
public class BaseReportCashGenerator implements GenericReportCashRepository {

	private final Map<String, String> TEMPLATES;
	private final BaseOpeningOperationRepository openingOperationRepository;
	private final BaseClosingOperationRepository closingOperationRepository;
	private final BaseAccountingRecordRepository accountingRecordRepository;
	private final BaseEmailCashConfigurationRepository emailCashConfigurationRepository;
    private final Logger LOG;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected BranchRepository branchRepository;

	@Autowired
	protected UserConverter userConverter;

	@Autowired
	protected BranchConverter branchConverter;

	@Autowired
	protected OpeningOperationConverter openingOperationConverter;

	@Autowired
	protected OpeningOperationDetailConverter openingOperationDetailConverter;

	@Autowired
	protected ClosingOperationConverter closingOperationConverter;

	@Autowired
	protected ClosingOperationDetailConverter closingOperationDetailConverter;

	@Autowired
	protected AccountingRecordConverter accountingRecordConverter;


	@Autowired
	public BaseReportCashGenerator(
			Map<String, String> _TEMPLATES,
			Class<?> loggerClass,
			BaseOpeningOperationRepository _openingOperationRepository,
			BaseClosingOperationRepository _closingOperationRepository,
			BaseAccountingRecordRepository _accountingRecordRepository,
			BaseEmailCashConfigurationRepository _emailCashConfigurationRepository
	) {
		TEMPLATES = _TEMPLATES;
		LOG = LoggerFactory.getLogger(loggerClass);
		this.openingOperationRepository = _openingOperationRepository;
		this.closingOperationRepository = _closingOperationRepository;
		this.accountingRecordRepository = _accountingRecordRepository;
		this.emailCashConfigurationRepository = _emailCashConfigurationRepository;
	}


    protected String getTemplatePath(String key) {
        String path = TEMPLATES.get(key);
        if (path == null) {
            throw new IllegalArgumentException("La clave '" + key + "' no existe en los templates.");
        }
        return path;
    }

	protected String getClosingTemplate() {return getTemplatePath("CLOSING_TEMPLATE");}
	protected String getOpeningTemplate() {return getTemplatePath("OPENING_TEMPLATE");}
	protected String getLogoReport() {return getTemplatePath("LOGO_REPORT");}

	@Transactional
	@Override
	public ResponseModel generateOpeningOperationReport(OpeningOperationReport openingOperationReport,
			String idOperation) {

		try {
			LOG.info(String.format("%s INIT generateOpeningOperationReport()", idOperation));
			LOG.info(String.format("%s LOAD LOGO-IMAGE ", idOperation));
			InputStream logoImage = this.getClass().getResourceAsStream(getLogoReport());
			openingOperationReport.setLogo(logoImage);

			LOG.info(String.format("%s LOAD REPORT ", idOperation));
			InputStream file = this.getClass().getResourceAsStream(getOpeningTemplate());

			LOG.info(String.format("%s LOAD DATA ", idOperation));
			Collection<OpeningOperationReport> collection = Collections.singletonList(openingOperationReport);

			LOG.info(String.format("%s GENERATE REPORT TEMPLATE ", idOperation));
			JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
					new JRBeanCollectionDataSource(collection));

			LOG.info(String.format("%s GENERATE ARRAY BYTE ", idOperation));
			byte[] finalReport = JasperExportManager.exportReportToPdf(jasperPrint);

			LOG.info(String.format("%s RETURN DATA ", idOperation));
			return new ResponseModel(finalReport);

		} catch (Exception e) {

			LOG.error(String.format("%s ERROR IN generateOpeningOperationReport()", idOperation));
			LOG.error(e.getMessage());
			return new ResponseModel(null);
		}

	}

	@Transactional
	@Override
	public ResponseModel generateClosingOperationReport(ClosingOperationReport closingOperationRepor,
			String idOperation) {

		try {
			LOG.info(String.format("%s INIT generateClosingOperationReport()", idOperation));
			LOG.info(String.format("%s LOAD LOGO-IMAGE ", idOperation));
			InputStream logoImage = this.getClass().getResourceAsStream(getLogoReport());
			closingOperationRepor.setLogo(logoImage);

			LOG.info(String.format("%s LOAD REPORT ", idOperation));
			InputStream file = this.getClass().getResourceAsStream(getClosingTemplate());

			LOG.info(String.format("%s LOAD DATA ", idOperation));
			Collection<ClosingOperationReport> collection = Collections.singletonList(closingOperationRepor);

			LOG.info(String.format("%s GENERATE REPORT TEMPLATE ", idOperation));
			JasperPrint jasperPrint = JasperFillManager.fillReport(file, null,
					new JRBeanCollectionDataSource(collection));

			LOG.info(String.format("%s GENERATE ARRAY BYTE ", idOperation));
			byte[] finalReport = JasperExportManager.exportReportToPdf(jasperPrint);

			LOG.info(String.format("%s RETURN DATA ", idOperation));
			return new ResponseModel(finalReport);

		} catch (Exception e) {

			LOG.error(String.format("%s ERROR IN generateClosingOperationReport()", idOperation));
			LOG.error(e.getMessage());
			return new ResponseModel(null);
		}
	}

	@Transactional
	@Override
	public UserDto getUserInformationByEmail(String email, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getUserInformationByEmail()", idOperation));
			Optional<UserEntity> userOptional = userRepository.findByEmail(email);
			if (userOptional.isPresent()) {
				LOG.info(String.format("%s USER FOUND, CONVERT TO DTO", idOperation));
				return userConverter.userEntityToUserDto(userOptional.get(), true);
			} else {
				LOG.info(String.format("%s USER NOT FOUND, RETURN NULL VALUE", idOperation));
				return null;
			}
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getUserInformationByEmail(). EXCEPTION: %s", idOperation,
					e.getMessage() + e.getCause()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public BranchDto getBranchInformationByCodeAndCompanyCode(String branchCode, String companyCode,
			String idOperation) {
		try {
			LOG.info(String.format("%s INIT getBranchInformationByCodeAndCompanyCode() ", idOperation));
			LOG.info(String.format("%s PARAMS: [branchCode: %s , companyCode: %s ]", idOperation, branchCode,
					companyCode));
			Optional<BranchEntity> branchOptional = branchRepository.findByCodeAndCompanyCode(branchCode,
					CompanyCodes.valueOf(companyCode));
			if (!branchOptional.isPresent()) {
				LOG.info(String.format("%s ERROR IN SERCH BRANCH-DETAIL ", idOperation));
				return null;
			} else {
				BranchDto branchDto = branchConverter.branchEntityToBranchDto(branchOptional.get());
				LOG.info(String.format("%s RETURN BRANCH-DETAIL: %s ", idOperation, branchDto.toString()));
				return branchDto;
			}

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR getBranchInformationByCodeAndCompanyCode(). ERROR: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public OpeningOperationDto getOpeningOperationByEmail(String email, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getOpeningOperationByEmail()", idOperation));
			OpeningOperationDto openingOperationDto = null;
			List<OpeningOperationDetailDto> openingOperationDetailDtoList = new ArrayList<>();

			Optional<OpeningOperationEntity> openingOperationOptional = openingOperationRepository
					.findByEmployeeEmailAndIsActive(email, true);

			if (openingOperationOptional.isPresent()) {

				openingOperationDto = openingOperationConverter
						.openingOperationEntityToOpeningOperationDto(openingOperationOptional.get());
				openingOperationDetailDtoList = openingOperationDetailConverter
						.openingOperationDetailEntityListToOpeningOperationDetailDtoList(
								openingOperationOptional.get().getOpeningOperationDetail());
				openingOperationDto.setOpeningOperationDetail(openingOperationDetailDtoList);
			}

			return openingOperationDto;
		} catch (Exception e) {

			LOG.error(String.format("%s ERROR getOpeningOperationByEmail(). ERROR: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public OpeningOperationDto getLastClosingOpeningOperationByEmail(String email, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getLastClosingOpeningOperationByEmail()", idOperation));
			OpeningOperationDto openingOperationDto = null;
			List<OpeningOperationDetailDto> openingOperationDetailDtoList = new ArrayList<>();
			List<OpeningOperationEntity> openingOperationList = openingOperationRepository
					.findClosingOpeningOperationByEmail(email);
			if (!openingOperationList.isEmpty()) {
				OpeningOperationEntity openingOperation = openingOperationList.get(0);
				openingOperationDto = openingOperationConverter
						.openingOperationEntityToOpeningOperationDto(openingOperation);
				openingOperationDetailDtoList = openingOperationDetailConverter
						.openingOperationDetailEntityListToOpeningOperationDetailDtoList(
								openingOperation.getOpeningOperationDetail());
				openingOperationDto.setOpeningOperationDetail(openingOperationDetailDtoList);
			}
			return openingOperationDto;
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR getLastClosingOpeningOperationByEmail(). ERROR: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
		}

		@Transactional
		@Override
		public ClosingOperationDto getClosingOperationById(Long idClosing, String idOperation) {
			try {
				LOG.info(String.format("%s INIT getClosingOperationById()", idOperation));

				ClosingOperationDto closingOperationDto = null;
				List<ClosingOperationDetailDto> closingOperationDetailList = new ArrayList<>();
				Optional<ClosingOperationEntity> closingOperationOptional = closingOperationRepository
						.findById(idClosing);
				if (closingOperationOptional.isPresent()) {
					closingOperationDto = closingOperationConverter
							.closingOperationEntityToClosingOperationDto(closingOperationOptional.get());
					closingOperationDetailList = closingOperationDetailConverter
							.closingOperationDetailEntityListToClosingOperationDetailDtoList(
									closingOperationOptional.get().getClosingOperationDetail());
					closingOperationDto.setClosingOperationDetail(closingOperationDetailList);
				}
				System.out.println(closingOperationDto.toString());
				return closingOperationDto;
			} catch (Exception e) {
				LOG.error(String.format("%s ERROR getClosingOperationById(). ERROR: %s", idOperation, e.getMessage()));
				throw new GlobalError();
			}
		}

		@Override
		public List<AccountingRecordDto> getAccountingRecordListByOpeningId(Long idOpening, String idOperation) {
			try {
				LOG.info(String.format("%s INIT getAccountingRecordListByOpeningId()", idOperation));
				List<AccountingRecordDto> accountingRecordList = new ArrayList<>();
				List<AccountingRecordEntity> accountingRecordEntityList = accountingRecordRepository
						.findAllByOpeningId(idOpening);
				if (!accountingRecordEntityList.isEmpty())
					accountingRecordList = accountingRecordConverter
							.accountingRecordEntityListToAccountingRecordDtoList(accountingRecordEntityList);
				System.out.println(accountingRecordList.toString());
				return accountingRecordList;
			} catch (Exception e) {
				LOG.error(String.format("%s ERROR getAccountingRecordListByOpeningId(). ERROR: %s", idOperation,
						e.getMessage()));
				throw new GlobalError();
			}
		}

		@Transactional
		@Override
		public EmailReportCashDto getEmailReport(String idOperation) {
			try {
				LOG.info(String.format("%s INIT getEmailReport()", idOperation));
				return emailCashConfigurationRepository.getEmailReport(idOperation);
			} catch (Exception e) {
				LOG.error(String.format("%s ERROR getEmailReport(). ERROR: %s", idOperation, e.getMessage()));
				throw new GlobalError();
			}

		}
	}
