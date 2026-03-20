package mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.business;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import mx.com.endtoend.infrastructure.catalogue.orders.common.entities.CatalogueEntity;
import mx.com.endtoend.infrastructure.warehouse.carredana.repositories.F0006CarredanaRepository;
import mx.com.endtoend.infrastructure.warehouse.common.converters.F0006Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.branch.dto.BranchAddressDto;
import mx.com.endtoend.domain.catalogue.dto.CatalogDirectionDto;
import mx.com.endtoend.domain.catalogue.dto.CatalogueDto;
import mx.com.endtoend.domain.catalogue.dto.CatalogueJdeDTO;
import mx.com.endtoend.domain.catalogue.dto.address.ColonyDto;
import mx.com.endtoend.domain.catalogue.dto.address.CoordinateDto;
import mx.com.endtoend.domain.catalogue.dto.address.CountryDto;
import mx.com.endtoend.domain.catalogue.dto.address.FlatDto;
import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityCPDto;
import mx.com.endtoend.domain.catalogue.dto.address.MunicipalityDto;
import mx.com.endtoend.domain.catalogue.dto.address.StateDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleBrandDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleCategoryDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleDivisionDto;
import mx.com.endtoend.domain.catalogue.dto.article.ArticleFamilyDto;
import mx.com.endtoend.domain.catalogue.dto.client.CFDIDto;
import mx.com.endtoend.domain.catalogue.dto.client.ClientTypeDto;
import mx.com.endtoend.domain.catalogue.dto.client.ContactMethodDto;
import mx.com.endtoend.domain.catalogue.dto.client.RegimeFiscalDto;
import mx.com.endtoend.domain.catalogue.dto.client.WorkTypeDto;
import mx.com.endtoend.genericCommonsFileds.utilities.StringUtil;
import mx.com.endtoend.infrastructure.catalogue.orders.calzada.repositories.CatalogueRepository;
import mx.com.endtoend.infrastructure.commons.constants.CategoryCodes;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.common.repository.GenericCatalogueJdeRepository;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.converters.CatalogueAddressFCarConverter;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.converters.CatalogueArticleFCarConverter;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.converters.CatalogueClientFCarConverter;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.converters.F0005FCarConverter;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.converters.F0005JdeFCarConverter;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.converters.F0117FCarConverter;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.entities.F0005;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.entities.F0117;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.repository.F0005FcarRepository;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.repository.F0117FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.repository.impl.CustomDirectionFCarRepositoryImpl;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0115;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F0116;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F0115FCarRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F0116FCarRepository;
import mx.com.endtoend.infrastructure.warehouse.carredana.entities.F0006;
import mx.com.endtoend.smart.bussiness.model.catalogue.dto.StatusDto;
import mx.com.endtoend.smart.bussiness.model.commons.ResponseModel;

@Service
@ConditionalOnProperty(name = "app.carredana.oracle.enabled", havingValue = "true", matchIfMissing = false)
public class CatalogueFCarredanaJdeRepository implements GenericCatalogueJdeRepository {

	private static final String COMPANY_NUMBER = "00003";

	@Autowired
	private CatalogueRepository catalogueRepository;

	@Autowired
	private F0005FCarConverter f0005Converter;

	@Autowired
	private F0005JdeFCarConverter f0005JdeConverter;

	@Autowired
	private F0005FcarRepository f0005Repository;

	@Autowired
	private F0006CarredanaRepository f0006Repository;

	@Autowired
	private F0116FCarRepository f0116Repository;

	@Autowired
	private F0115FCarRepository f0115Repository;

	@Autowired
	private F0117FCarRepository f0117Repository;

	@Autowired
	private F0117FCarConverter f0117Converter;

	@Autowired
	private CatalogueClientFCarConverter catalogueClientConverter;

	@Autowired
	private CatalogueArticleFCarConverter catalogueArticleConverter;

	@Autowired
	private CatalogueAddressFCarConverter catalogueAddressConverter;

	@Autowired
	private CustomDirectionFCarRepositoryImpl customDirectionRepository;

	private final Logger LOG = LoggerFactory.getLogger(CatalogueFCarredanaJdeRepository.class);

	@Transactional
	@Override
	public ResponseModel getBrancgAddressByCode(String branchCode, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getBrancgAddressByCode()", idOperation));
			BranchAddressDto branchAddressDto = null;

			F0006 f0006 = F0006Mapper.toCarredanaF0006(f0006Repository.findWarehouseByCodeAndCompanyNumber(COMPANY_NUMBER, branchCode.trim()));
			if (f0006 != null) {
				Long an8 = f0006.getMcan8().longValue();
				Optional<F0116> f0116Optional = f0116Repository.findByAn8(an8);
				if (f0116Optional.isPresent()) {

					branchAddressDto = new BranchAddressDto();

					branchAddressDto.setStreet(f0116Optional.get().getStreet());
					branchAddressDto.setColony(f0116Optional.get().getColony());
					branchAddressDto.setCp(f0116Optional.get().getCp());

					String insideNumber = "";
					String outsideNumber = f0116Optional.get().getNoOuNoIn();
					String fullNumber = f0116Optional.get().getNoOuNoIn();

					if (fullNumber.contains("/")) {
						String[] parts = fullNumber.split("/");
						insideNumber = parts[0];
						outsideNumber = parts[1];
					}
					if (fullNumber.contains("-")) {
						String[] parts = fullNumber.split("-");
						insideNumber = parts[0];
						outsideNumber = parts[1];
					}
					branchAddressDto.setInsideNumber(insideNumber);
					branchAddressDto.setOutsideNumber(outsideNumber);

					String phoneNumber = "";
					List<F0115> f0115List = f0115Repository.findByAn8(an8);
					if (!f0115List.isEmpty()) {
						F0115 f0115 = f0115List.get(0);
						phoneNumber = f0115.getNumber() + f0115.getNumber1();
					}
					branchAddressDto.setPhoneNumber(phoneNumber);
				}
			}
			return new ResponseModel(branchAddressDto);
		} catch (Exception e) {
			LOG.error(
					String.format("%s ERROR IN getBrancgAddressByCode(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel getWorkTypeByCompanyCode(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getWorkTypeByCompanyCode()", idOperation));
			List<WorkTypeDto> workTypeDtoList = new ArrayList<WorkTypeDto>();
			List<F0005> f0005List = f0005Repository.findByDrsyAndDrrt("01", "02");
			if (f0005List.size() != 0) {
				LOG.info(String.format("%s INIT CONVERTION", idOperation));
				workTypeDtoList = catalogueClientConverter.f0005ListToWorkTypeDtoList(f0005List);
			}
			return new ResponseModel(workTypeDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getWorkTypeByCompanyCode(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel getClientType(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getClientType()", idOperation));
			List<ClientTypeDto> clientTypeDtoList = new ArrayList<ClientTypeDto>();
			List<F0005> f0005List = f0005Repository.findByDrsyAndDrrt("01", "06");
			if (f0005List.size() != 0) {
				LOG.info(String.format("%s INIT CONVERTION", idOperation));
				clientTypeDtoList = catalogueClientConverter.f005ListToClientTypeDtoList(f0005List);
			}
			return new ResponseModel(clientTypeDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getClientType(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel getFiscalRegimeCatalogue(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getFiscalRegimeCatalogue()", idOperation));
			List<RegimeFiscalDto> regimeFiscalDtoList = new ArrayList<RegimeFiscalDto>();
			List<F0005> f0005List = f0005Repository.findByDrsyAndDrrt("01", "25");
			if (f0005List.size() != 0) {
				LOG.info(String.format("%s INIT CONVERTION", idOperation));
				regimeFiscalDtoList = catalogueClientConverter.f0005ListToRegimeFiscalDtoList(f0005List);
			}
			return new ResponseModel(regimeFiscalDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getFiscalRegimeCatalogue(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel getHowToContactByCompanyCode(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getHowToContactByCompanyCode()", idOperation));
			List<ContactMethodDto> contactMethodDtoList = new ArrayList<ContactMethodDto>();
			List<F0005> f0005List = f0005Repository.findByDrsyAndDrrt("01", "16");
			if (f0005List.size() != 0) {
				LOG.info(String.format("%s INIT CONVERTION", idOperation));
				contactMethodDtoList = catalogueClientConverter.f0005ListToContactMethodDtoList(f0005List);
			}
			return new ResponseModel(contactMethodDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getHowToContactByCompanyCode(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel getCfdi(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getCfdi()", idOperation));
			List<CFDIDto> cfdiDtoList = new ArrayList<CFDIDto>();
			List<F0005> f0005List = f0005Repository.findByDrsyAndDrrt("42", "RC");
			if (f0005List.size() != 0) {
				LOG.info(String.format("%s INIT CONVERTION", idOperation));
				cfdiDtoList = catalogueClientConverter.f0005ListToCFDIDtoList(f0005List);
			}
			return new ResponseModel(cfdiDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getCfdi(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel getArticleBrand(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getArticleBrand()", idOperation));
			List<ArticleBrandDto> articleBrandDtoList = new ArrayList<ArticleBrandDto>();
			List<F0005> f0005List = f0005Repository.findByDrsyAndDrrt("41", "S5");
			if (f0005List.size() != 0) {
				LOG.info(String.format("%s INIT CONVERTION", idOperation));
				articleBrandDtoList = catalogueArticleConverter.f005ListToArticleBrandDtoList(f0005List);
			}
			return new ResponseModel(articleBrandDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getArticleBrand(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel getArticleCategory(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getArticleCategory()", idOperation));
			List<ArticleCategoryDto> articleCategoryDtoList = new ArrayList<ArticleCategoryDto>();
			List<F0005> f0005List = f0005Repository.findByDrsyAndDrrt("41", "S2");
			if (f0005List.size() != 0) {
				LOG.info(String.format("%s INIT CONVERTION", idOperation));
				articleCategoryDtoList = catalogueArticleConverter.f005ListToArticleCategoryDtoList(f0005List);
			}
			return new ResponseModel(articleCategoryDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getArticleCategory(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel getArticleFamily(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getArticleFamily()", idOperation));
			List<ArticleFamilyDto> articleFamilyDtoList = new ArrayList<ArticleFamilyDto>();
			List<F0005> f0005List = f0005Repository.findByDrsyAndDrrt("41", "S3");
			if (f0005List.size() != 0) {
				LOG.info(String.format("%s INIT CONVERTION", idOperation));
				articleFamilyDtoList = catalogueArticleConverter.f005ListToArticleFamilyDtoList(f0005List);
			}
			return new ResponseModel(articleFamilyDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getArticleFamily(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel getArticleDivision(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getArticleDivision()", idOperation));
			List<ArticleDivisionDto> articleDivisionDtoList = new ArrayList<ArticleDivisionDto>();
			List<F0005> f0005List = f0005Repository.findByDrsyAndDrrt("41", "S1");
			if (f0005List.size() != 0) {
				LOG.info(String.format("%s INIT CONVERTION", idOperation));
				articleDivisionDtoList = catalogueArticleConverter.f005ListToArticleDivisionDtoList(f0005List);
			}
			return new ResponseModel(articleDivisionDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getArticleDivision(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Transactional
	@Override
	public ResponseModel getCoordinate(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getCoordinate()", idOperation));
			List<CoordinateDto> coordinateDtoList = new ArrayList<>();
			List<F0005> f0005List = f0005Repository.findByDrsyAndDrrt("42", "SP");
			if (f0005List.size() != 0) {
				LOG.info(String.format("%s INIT CONVERSION", idOperation));
				coordinateDtoList = catalogueAddressConverter.f0005ListToCoordinateDtoList(f0005List);
			}
			return new ResponseModel(coordinateDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getCoordinate(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getFlat(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getCoordinate()", idOperation));
			List<FlatDto> flatDtoList = new ArrayList<>();
			List<F0005> f0005List = f0005Repository.findByDrsyAndDrrt("40", "ZN");
			if (f0005List.size() != 0) {
				LOG.info(String.format("%s INIT CONVERSION", idOperation));
				flatDtoList = catalogueAddressConverter.f0005ListToFlatDtoList(f0005List);
			}
			return new ResponseModel(flatDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getFlat(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getCountry(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getCountry()", idOperation));
			List<CountryDto> countryDtoList = new ArrayList<>();
			List<F0005> f0005List = f0005Repository.findByDrsyAndDrrt("00", "CN");
			if (f0005List.size() != 0) {
				LOG.info(String.format("%s INIT CONVERSION", idOperation));
				countryDtoList = catalogueAddressConverter.f0005ListToCountryDtoList(f0005List);
			}
			return new ResponseModel(countryDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getCountry(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getState(String idOperation) {
		try {
			LOG.info(String.format("%s INIT getState()", idOperation));
			List<StateDto> stateDtoList = new ArrayList<>();
			List<F0005> f0005List = f0005Repository.findByDrsyAndDrrt("00", "S");
			if (f0005List.size() != 0) {
				LOG.info(String.format("%s INIT CONVERSION", idOperation));
				stateDtoList = catalogueAddressConverter.f0005ListToStateDtoList(f0005List);
			}
			return new ResponseModel(stateDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getState(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getColonyByStateCode(String stateCode, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getColonyByStateCode()", idOperation));
			List<ColonyDto> colonyDtoList = customDirectionRepository.getColonyList(stateCode);
			return new ResponseModel(colonyDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getColonyByStateCode(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getMunicipalityNameListByStateCode(String stateCode, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getMunicipalityNameListByStateCode()", idOperation));
			List<String> municipalityNameList = customDirectionRepository.getMunicipalityNameListByStateCode(stateCode);
			return new ResponseModel(municipalityNameList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getMunicipalityNameListByStateCode(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getMunicipalityByNameList(List<String> municipalityNameList, String stateCode,
			String idOperation) {
		try {
			LOG.info(String.format("%s INIT getMunicipalityByNameList()", idOperation));
			List<MunicipalityDto> municipalityDtoList = new ArrayList<>();
			StringUtil stringUtil = new StringUtil();
			List<F0005> f0005List = new ArrayList<>();
			for (String name : municipalityNameList) {
				List<F0005> f0005SearchList = f0005Repository.findByDrsyAndDrrtAndDrdl01("00", "CT",
						stringUtil.cleanAccent(name));
				f0005List.addAll(f0005SearchList);
			}
			if (f0005List.size() != 0) {
				LOG.info(String.format("%s INIT CONVERSION", idOperation));
				municipalityDtoList = catalogueAddressConverter.f0005ListToMunicipalityDtoList(f0005List, stateCode);
			}
			return new ResponseModel(municipalityDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getMunicipalityByNameList(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getMunicipalityCpListBySatetCode(String stateCode, String idOperation) {
		try {
			LOG.info(String.format("%s INIT getMunicipalityCpListBySatetCode()", idOperation));
			List<MunicipalityCPDto> municipalityCPDtoList = new ArrayList<>();
			List<MunicipalityCPDto> municipalityCPDList = new ArrayList<>();
			municipalityCPDList = customDirectionRepository.getMunicipalityAndCpList(stateCode);
			if (!municipalityCPDList.isEmpty()) {
				municipalityCPDtoList = catalogueAddressConverter
						.completeDataInMunicipalityCpDtoList(municipalityCPDList, stateCode);
			}
			return new ResponseModel(municipalityCPDtoList);
		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN getMunicipalityByNameList(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@Override
	public List<CatalogueDto> getBrand() {

		List<CatalogueDto> catalogList = f0005Converter.f0005EntityListToF0005DtoList(
				(List<F0005>) f0005Repository.findByDrsyAndDrrtAndDrsphd("41", "S5", "1"));

		return catalogList;
	}

	@Override
	public List<CatalogueDto> getCategory() {

		List<CatalogueDto> catalogList = f0005Converter.f0005EntityListToF0005DtoList(
				(List<F0005>) f0005Repository.findByDrsyAndDrrtAndDrsphd("41", "S2", "1"));

		return catalogList;
	}

	@Override
	public List<CatalogueDto> getDivision() {

		List<CatalogueDto> catalogList = f0005Converter.f0005EntityListToF0005DtoList(
				(List<F0005>) f0005Repository.findByDrsyAndDrrtAndDrsphd("41", "S1", "1"));

		return catalogList;
	}

	@Override
	public List<CatalogueDto> getFamily() {

		List<CatalogueDto> catalogList = f0005Converter.f0005EntityListToF0005DtoList(
				(List<F0005>) f0005Repository.findByDrsyAndDrrtAndDrsphd("41", "S3", "1"));

		return catalogList;
	}

	@Override
	public CatalogueEntity getCatalogByCode(CatalogueDto catalog, String idOperation) {

		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS  JPA");

		try {
			CatalogueEntity code = catalogueRepository.findByCode(catalog.getCode());

			return code;

		} catch (Exception e) {
			LOG.error(idOperation + "-" + e.getMessage());

			throw new GlobalError();
		}
	}

	@Override
	public List<CatalogueDto> getCatalogueClientType() {

//		List<CatalogueDto> catalogList = f0005Converter
//				.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("01", "15"));
		List<CatalogueDto> catalogList = f0005Converter
				.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("01", "06"));

		return catalogList;
	}

	@Override
	public List<CatalogueDto> getCatalogueCoordinate() {

		List<CatalogueDto> catalogList = f0005Converter
				.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("42", "SP"));

		return catalogList;
	}

	@Override
	public List<CatalogueDto> getCatalogueDelegation() {

		List<CatalogueDto> catalogList = f0005Converter
				.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("00", "CT"));

		return catalogList;
	}

	@Override
	public List<CatalogueDto> getCatalogueFlat() {

		List<CatalogueDto> catalogList = f0005Converter
				.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("40", "ZN"));

		return catalogList;
	}

	@Override
	public List<CatalogueDto> getCatalogueHowToContact() {

//		List<CatalogueDto> catalogList = f0005Converter
//				.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("01", "17"));

		List<CatalogueDto> catalogList = f0005Converter
				.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("01", "16"));

		return catalogList;
	}

	@Override
	public List<CatalogueDto> getCatalogueState() {

		List<CatalogueDto> catalogList = f0005Converter
				.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("00", "S"));

		return catalogList;
	}

	@Override
	public List<CatalogueDto> getCatalogueWorkType() {

		List<CatalogueDto> catalogList = f0005Converter
				.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("01", "16"));

		return catalogList;
	}

	@Override
	public List<CatalogueDto> getCatalogueCountry() {

		List<CatalogueDto> catalogList = f0005Converter
				.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("00", "CN"));

		return catalogList;
	}

	@Override
	public List<CatalogueDto> getCatalogueCfdi() {

		List<CatalogueDto> catalogList = f0005Converter
				.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("42", "RC"));

		return catalogList;
	}

	@Override
	public List<CatalogueDto> getFiscalRegime() {

		List<CatalogueDto> catalogList = f0005Converter
				.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("01", "25"));

		return catalogList;
	}

	@Override
	public void catalogsSave(CatalogueEntity catalogs) {

		catalogueRepository.save(catalogs);

	}

	@Override
	public ResponseModel getAddressColony(String type, String cp, String idOperation) {
		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JPA");

		try {

			List<String> list = new ArrayList<>();
			List<F0117> colony = f0117Repository.findByCp(cp);

			for (F0117 catalog : colony) {

				list.add(catalog.getId().getA7add4());
				Set<String> duplicate = new HashSet<String>(list);
				list.clear();
				list.addAll(duplicate);

			}

			if (list != null)
				LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS");
			return new ResponseModel(list);

		} catch (Exception e) {

			LOG.error(idOperation + "-" + e.getMessage());

			throw new GlobalError();

		}
	}

	@Override
	public ResponseModel getAddress(String type, String colony, String cp, String idOperation) {
		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JPA");

		try {

			List<CatalogDirectionDto> directionEntities = f0117Converter
					.F0117ListToDirectionDtoList((List<F0117>) f0117Repository.findByColony(colony, cp));
			LOG.info(idOperation + "SIZE: " + directionEntities.size());
//			List<CatalogDirectionEntity> directionEntities = catalogDirectionRepository.findByColony(colony,cp);

			if (directionEntities != null)
				LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS");
			return new ResponseModel(directionEntities.get(0));

		} catch (Exception e) {

			LOG.error(idOperation + "-" + e.getMessage());

			throw new GlobalError();

		}
	}

	@Override
	public ResponseModel getAddressDelegation(String type, String state, String idOperation) {
		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JPA");

		try {

			List<String> list = new ArrayList<>();
			List<F0117> delegation = f0117Repository.findByState(state);

			for (F0117 catalog : delegation) {

				String texto = Normalizer.normalize(catalog.getId().getA8coun(), Normalizer.Form.NFD);
				texto = texto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

				list.add(texto);
				Set<String> duplicate = new HashSet<String>(list);
				list.clear();
				list.addAll(duplicate);

			}

			if (list != null)
				LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS");
			return new ResponseModel(list);

		} catch (Exception e) {

			LOG.error(idOperation + "-" + e.getMessage());

			throw new GlobalError();

		}
	}

	@Override
	public ResponseModel getCoordinateByCompanyCode(String type, String idOperation) {
		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JPA");

		try {

			List<CatalogueJdeDTO> catalogList = f0005JdeConverter
					.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("42", "SP"));

			if (catalogList != null)
				LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS");
			return new ResponseModel(catalogList);
		} catch (Exception e) {

			LOG.error(idOperation + "-" + e.getMessage());

			throw new GlobalError();

		}
	}

	@Override
	public ResponseModel getDelegationByCompanyCode(String type, String idOperation) {
		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JPA");

		try {

			List<CatalogueJdeDTO> catalogList = f0005JdeConverter
					.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("00", "CT"));

			if (catalogList != null)
				LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS");
			return new ResponseModel(catalogList);
		} catch (Exception e) {

			LOG.error(idOperation + "-" + e.getMessage());

			throw new GlobalError();

		}
	}

	@Override
	public ResponseModel getFlatByCompanyCode(String type, String idOperation) {
		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JPA");

		try {

			List<CatalogueJdeDTO> catalogList = f0005JdeConverter
					.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("40", "ZN"));

			if (catalogList != null)
				LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS");
			return new ResponseModel(catalogList);
		} catch (Exception e) {

			LOG.error(idOperation + "-" + e.getMessage());

			throw new GlobalError();

		}
	}

	@Override
	public ResponseModel getStateByCompanyCodegetStateByCompanyCode(String type, String idOperation) {
		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JPA");

		try {

			List<CatalogueJdeDTO> catalogList = f0005JdeConverter
					.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("00", "S"));

			if (catalogList != null)
				LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS");
			return new ResponseModel(catalogList);
		} catch (Exception e) {

			LOG.error(idOperation + "-" + e.getMessage());

			throw new GlobalError();

		}
	}

	@Override
	public ResponseModel getCountryByCompanyCode(String type, String idOperation) {
		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JPA");

		try {

			List<CatalogueJdeDTO> catalogList = f0005JdeConverter
					.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("00", "CN"));

			if (catalogList != null)
				LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS");
			return new ResponseModel(catalogList);
		} catch (Exception e) {

			LOG.error(idOperation + "-" + e.getMessage());

			throw new GlobalError();

		}
	}

	@Override
	public ResponseModel getCategoryListByCode(String categoryCode, String idOperation) {

		try {

			CategoryCodes code = CategoryCodes.valueOf(categoryCode);

			switch (code) {

			case BRAND:

				List<CatalogueJdeDTO> BRAND = f0005JdeConverter.f0005EntityListToF0005DtoList(
						(List<F0005>) f0005Repository.findByDrsyAndDrrtAndDrsphd("41", "S5", "1"));

				if (BRAND != null)
					LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS BRAND");
				return new ResponseModel(BRAND);

			case CATEGORY:

				List<CatalogueJdeDTO> CATEGORY = f0005JdeConverter.f0005EntityListToF0005DtoList(
						(List<F0005>) f0005Repository.findByDrsyAndDrrtAndDrsphd("41", "S2", "1"));

				if (CATEGORY != null)
					LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS CATEGORY");
				return new ResponseModel(CATEGORY);

			case DIVISION:

				List<CatalogueJdeDTO> DIVISION = f0005JdeConverter.f0005EntityListToF0005DtoList(
						(List<F0005>) f0005Repository.findByDrsyAndDrrtAndDrsphd("41", "S1", "1"));

				if (DIVISION != null)
					LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS DIVISION");
				return new ResponseModel(DIVISION);

			case FAMILY:

				List<CatalogueJdeDTO> FAMILY = f0005JdeConverter.f0005EntityListToF0005DtoList(
						(List<F0005>) f0005Repository.findByDrsyAndDrrtAndDrsphd("41", "S3", "1"));

				if (FAMILY != null)
					LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS FAMILY");
				return new ResponseModel(FAMILY);

			default:
				return null;

			}

		} catch (Exception e) {

			LOG.error(String.format("%s ERROR IN getCategoryListByCode(). EXCEPTION: %s", idOperation, e.getMessage()));

			throw new GlobalError();
		}
	}

	@Override
	public ResponseModel getStatus(Long id, String idOperation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatusDto> getStatusList(String idOperation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseModel getStateByCompanyCode(String type, String idOperation) {

		LOG.info(idOperation + "INICIA BÚSQUEDA DEL CATALOGOS JPA");

		try {

			List<CatalogueJdeDTO> catalogList = f0005JdeConverter
					.f0005EntityListToF0005DtoList((List<F0005>) f0005Repository.findByDrsyAndDrrt("00", "S"));

			if (catalogList != null)
				LOG.info(idOperation + "BÚSQUEDA DE CATALOGOS");
			return new ResponseModel(catalogList);
		} catch (Exception e) {

			LOG.error(idOperation + "-" + e.getMessage());

			throw new GlobalError();

		}
	}

}
