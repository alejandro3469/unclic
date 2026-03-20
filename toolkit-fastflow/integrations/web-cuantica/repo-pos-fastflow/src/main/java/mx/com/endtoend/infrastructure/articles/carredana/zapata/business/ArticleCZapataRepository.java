package mx.com.endtoend.infrastructure.articles.carredana.zapata.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import mx.com.endtoend.infrastructure.warehouse.carredana.zapata.entities.F0006;
import mx.com.endtoend.infrastructure.warehouse.carredana.zapata.repositories.F0006ZapataRepository;
import mx.com.endtoend.infrastructure.warehouse.common.converters.F0006Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.articles.dto.ArticleConvertionFactor;
import mx.com.endtoend.domain.articles.dto.ArticleDto;
import mx.com.endtoend.domain.articles.dto.GenericSerchParamsArticleDto;
import mx.com.endtoend.genericCommonsFileds.utilities.StringUtil;
import mx.com.endtoend.infrastructure.articles.common.repository.GenericArticleRepository;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.converters.ArticleConvertionFactorFcarConverter;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.entities.F4008;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.entities.F41002;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.entities.F4102;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.entities.F4104;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.repositories.CustomDslArticleFCarRepositoryImpl;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.repositories.F4008FCarRepository;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.repositories.F41002FCarRepository;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.repositories.F41021FCarRepository;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.repositories.F4102FCarRepository;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.repositories.F4104FCarRepository;
import mx.com.endtoend.infrastructure.articles.carredana.oracle.repositories.F4106FCarRepository;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.entities.F0005;
import mx.com.endtoend.infrastructure.services.jde.catalogue.carredana.common.repository.F0005FcarRepository;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.entities.F03012;
import mx.com.endtoend.infrastructure.services.jde.clients.carredana.repository.F03012FCarRepository;

/**
 * 
 * @author ddcasas
 *
 */

@ConditionalOnProperty(name = "app.carredana.oracle.enabled", havingValue = "true", matchIfMissing = false)
@Service
public class ArticleCZapataRepository implements GenericArticleRepository {

	@Autowired(required = false)
	private F4102FCarRepository f4102Repository;

	@Autowired(required = false)
	private F41021FCarRepository f41021Repository;

	@Autowired(required = false)
	private F0005FcarRepository f0005Repository;

	@Autowired(required = false)
	private F41002FCarRepository f41002Repository;

	@Autowired(required = false)
	private F4106FCarRepository f4106Repository;

	@Autowired(required = false)
	private F4008FCarRepository f4008Repository;

	@Autowired(required = false)
	private F4104FCarRepository f4104Repository;

	@Autowired(required = false)
	private F0006ZapataRepository f0006Repository;

	@Autowired(required = false)
	private F03012FCarRepository f03012Repository;

	@Autowired(required = false)
	private CustomDslArticleFCarRepositoryImpl customDslArticleRepositoryImpl;

	@Autowired(required = false)
	private ArticleConvertionFactorFcarConverter articleConvertionFactorConverter;

	private static final String COMPANY_NUMBER = "00006";
	private static double taxEmpty = -1;

	StringUtil stringUtil = new StringUtil();

	private final Logger LOG = LoggerFactory.getLogger(ArticleCZapataRepository.class);

	@Transactional
	@Override
	public List<ArticleDto> findArticlesByParams(GenericSerchParamsArticleDto params, String branchCode,
			String idOperation, boolean debug) {

		try {

			LOG.info(String.format("%s INIT findArticlesByParams() ", idOperation));
			LOG.info(String.format("%s PARAMS: [genericSerchParamsArticleDto: %s , branchCode: %s ]", idOperation,
					params.toString(), branchCode));

			if (params.getWarehouseCode().length() < 12) {

				LOG.info(String.format("%s COMPLETE WAREHOUSE-CODE LENGTH", idOperation));
				params.setWarehouseCode(stringUtil.completeLengthToWarehouseCode(params.getWarehouseCode()));

			}

			List<ArticleDto> articleList = customDslArticleRepositoryImpl.findArticlesByParams(params);

			articleList = setQuantityByArticle(articleList, params.getIsAvailable(), idOperation);

			decimalPlaceAdjustment(articleList);
			setSubCategoryDescriptionByArticle(articleList, idOperation);
			setDivisionDescriptionByArticle(articleList, idOperation);
			setFamilyDescriptionByArticle(articleList, idOperation);
			setCategoryCodeDescription(articleList, idOperation);
			setMarkDescription(articleList, idOperation);
			setConvertionCodeByArticle(articleList, idOperation);
			setUnitMeasurementByArticle(articleList, idOperation);
			setFormFactorByArticle(articleList, idOperation);
			setTaxesByArticle(articleList, branchCode, idOperation);

			return articleList;

		} catch (Exception e) {

			LOG.error(String.format("%s ERROR IN findArticlesByParams(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
		}
	}

	private void decimalPlaceAdjustment(List<ArticleDto> articleList) {
		for (ArticleDto articleDto : articleList) {
			double price = articleDto.getPrice().doubleValue();
			price = Math.floor(price * 100) / 100;
			articleDto.setPrice(BigDecimal.valueOf(price));
		}
	}

	@Transactional
	public List<ArticleDto> setQuantityByArticle(List<ArticleDto> articleDtoList, boolean disponibility,
			String idOperation) {

		LOG.info(String.format("%s INIT setQuantityByArticle() ", idOperation));

		List<Integer> quantities = new ArrayList<Integer>();
		List<ArticleDto> articlesDisponibilities = new ArrayList<ArticleDto>();
		List<ArticleDto> allArticle = new ArrayList<ArticleDto>();

		for (ArticleDto articleDto : articleDtoList) {

			quantities = getQuantities(articleDto.getArticleNumber(), articleDto.getWarehouseCode(), idOperation);

			articleDto.setAviableQuantity(quantities.get(0));
			articleDto.setReservedQuantity(quantities.get(1));
			articleDto.setTotalQuantity(quantities.get(2));

			if (disponibility && quantities.get(0) > 0) {
				articlesDisponibilities.add(articleDto);
			} else {
				allArticle.add(articleDto);
			}
		}

		if (disponibility) {
			return articlesDisponibilities;
		} else {
			return allArticle;
		}

	}

	@Transactional
	public List<Integer> getQuantities(BigDecimal itm, String mcu, String idOperation) {

		LOG.info(String.format("%s INIT getQuantities() ", idOperation));

		List<Object[]> quantitiesList = f41021Repository.findQuantitiesByItmAndMcu(itm, mcu);
		List<Integer> results = new ArrayList<Integer>();

		Integer aviableQuantity = 0;
		Integer reservedQuantity = 0;
		Integer totalQuantity = 0;

		Integer lipqoh = 0;
		Integer lihcom = 0;
		Integer lipcom = 0;
		Integer lifcom = 0;
		Integer liot1p = 0;

		for (Object[] objects : quantitiesList) {

			lipqoh = Integer.parseInt(objects[0].toString());
			lihcom = Integer.parseInt(objects[1].toString());
			lipcom = Integer.parseInt(objects[2].toString());
			lifcom = Integer.parseInt(objects[3].toString());
			liot1p = Integer.parseInt(objects[4].toString());
		}

		aviableQuantity = (lipqoh - lipcom - lihcom - liot1p) / 100;
		reservedQuantity = (lihcom + lipcom + lifcom + liot1p) / 100;
		totalQuantity = (lipqoh / 100);

		results.add(0, aviableQuantity);
		results.add(1, reservedQuantity);
		results.add(2, totalQuantity);

		return results;
	}

	@Transactional
	public void setSubCategoryDescriptionByArticle(List<ArticleDto> articleDtoList, String idOperation) {

		LOG.info(String.format("%s INIT setSubCategoryDescriptionByArticle() ", idOperation));

		String drsy = "41";
		String drrt = "P2";
		for (ArticleDto articleDto : articleDtoList) {
			F0005 f5 = f0005Repository.findDescriptionByDrsyAndDrrtAndDrky(drsy, drrt,
					articleDto.getSubCategoryClassification().trim());
			if (f5 != null) {
				articleDto.setSubCategoryDescription(f5.getDrdl01());
			} else {
				articleDto.setSubCategoryDescription(" ");
			}
		}
	}

	@Transactional
	public void setDivisionDescriptionByArticle(List<ArticleDto> articleDtoList, String idOperation) {

		LOG.info(String.format("%s INIT setDivisionDescriptionByArticle() ", idOperation));

		String drsy = "41";
		String drrt = "S1";
		for (ArticleDto articleDto : articleDtoList) {
			F0005 f5 = f0005Repository.findDescriptionByDrsyAndDrrtAndDrky(drsy, drrt, articleDto.getDivision().trim());
			if (f5 != null) {
				articleDto.setDivisionDescription(f5.getDrdl01());
			} else {
				articleDto.setDivisionDescription(" ");
			}
		}
	}

	@Transactional
	public void setFamilyDescriptionByArticle(List<ArticleDto> articleDtoList, String idOperation) {

		LOG.info(String.format("%s INIT setFamilyDescriptionByArticle() ", idOperation));

		String drsy = "41";
		String drrt = "S3";
		for (ArticleDto articleDto : articleDtoList) {
			F0005 f5 = f0005Repository.findDescriptionByDrsyAndDrrtAndDrky(drsy, drrt, articleDto.getFamily().trim());
			if (f5 != null) {
				articleDto.setFamilyDescription(f5.getDrdl01());
			} else {
				articleDto.setFamilyDescription(" ");
			}
		}
	}

	@Transactional
	public void setCategoryCodeDescription(List<ArticleDto> articleDtoList, String idOperation) {

		LOG.info(String.format("%s INIT setCategoryCodeDescription() ", idOperation));

		String drsy = "41";
		String drrt = "S4";
		for (ArticleDto articleDto : articleDtoList) {
			F0005 f5 = f0005Repository.findDescriptionByDrsyAndDrrtAndDrky(drsy, drrt,
					articleDto.getCategoryCode().trim());
			if (f5 != null) {
				articleDto.setCategoryCodeDescription(f5.getDrdl01());
			} else {
				articleDto.setCategoryCodeDescription(" ");
			}
		}
	}

	@Transactional
	public void setMarkDescription(List<ArticleDto> articleDtoList, String idOperation) {

		LOG.info(String.format("%s INIT setMarkDescription() ", idOperation));

		String drsy = "41";
		String drrt = "S5";
		for (ArticleDto articleDto : articleDtoList) {
			F0005 f5 = f0005Repository.findDescriptionByDrsyAndDrrtAndDrky(drsy, drrt, articleDto.getBrand().trim());
			if (f5 != null) {
				articleDto.setBrandDescription(f5.getDrdl01());

			} else {
				articleDto.setBrandDescription(" ");
			}
		}
	}

	@Transactional
	public void setConvertionCodeByArticle(List<ArticleDto> articleDtoList, String idOperation) {

		LOG.info(String.format("%s INIT setConvertionCodeByArticle() ", idOperation));

		String UMRUM = "KG";
		BigDecimal divisor = new BigDecimal("10000000");
		for (ArticleDto articleDto : articleDtoList) {
			F41002 f = f41002Repository.findByUmitmAndUmumAndUmrum(articleDto.getArticleNumber(),
					articleDto.getPrimaryUnitMeasure(), UMRUM);
			if (f != null) {
				articleDto.setConversionCode(BigDecimal.valueOf((f.getUmconv().divide(divisor)).doubleValue()));
			}
		}

	}

	@Transactional
	public void setUnitMeasurementByArticle(List<ArticleDto> articleDtoList, String idOperation) {

		LOG.info(String.format("%s INIT setUnitMeasurementByArticle() ", idOperation));

		Long currentJulianDay = getCurrentJulianDate();
		BigDecimal date = new BigDecimal(currentJulianDay);
		for (ArticleDto articleDto : articleDtoList) {
			Integer unitM = f4106Repository.findUnitMeasurementByBpitmAndBPmcuAndJulianDate(
					articleDto.getArticleNumber(), articleDto.getWarehouseCode(), date);
			if (unitM != null) {
				articleDto.setUnitMeasurement(unitM);
			} else {
				articleDto.setUnitMeasurement(0);
			}
		}
	}

	@Transactional
	public void setFormFactorByArticle(List<ArticleDto> articleDtoList, String idOperation) {

		LOG.info(String.format("%s INIT setFormFactorByArticle() ", idOperation));

		BigDecimal divisor = new BigDecimal("10000000");

		for (ArticleDto articleDto : articleDtoList) {

			F41002 f = f41002Repository.findByUmitmAndUmumAndUmrum(articleDto.getArticleNumber(),
					articleDto.getPrimaryUnitMeasure(), articleDto.getUnitVolume());
			if (f != null) {
				articleDto.setConversionFactor(BigDecimal.valueOf((f.getUmconv().divide(divisor)).doubleValue()));
			} else {

				if (articleDto.getUnitVolume().contains("PZ") || articleDto.getUnitVolume().contains("BC")) {
					articleDto.setConversionFactor(BigDecimal.valueOf(1));
				} else {
					articleDto.setConversionFactor(BigDecimal.valueOf(0));
				}

			}
		}
	}

	@Transactional
	public List<ArticleDto> setTaxesByArticle(List<ArticleDto> articleDtoList, String branchCode, String idOperation) {

		LOG.info(String.format("%s INIT setTaxesByArticle() ", idOperation));

		Long currentJulianDay = getCurrentJulianDate();
		BigDecimal date = new BigDecimal(currentJulianDay);
		double tax = 0.0;
		double priceTax = 0.0;

		List<ArticleDto> articleList = new ArrayList<ArticleDto>();

		for (ArticleDto articleDto : articleDtoList) {

			articleDto.setApplyTax("N");
			articleDto.setTaxValueOne(BigDecimal.valueOf(taxEmpty));
			articleDto.setTaxValueTwo(BigDecimal.valueOf(taxEmpty));
			articleDto.setTaxValueThree(BigDecimal.valueOf(taxEmpty));
			articleDto.setTaxValueFour(BigDecimal.valueOf(taxEmpty));
			articleDto.setTaxValueFive(BigDecimal.valueOf(taxEmpty));

			LOG.info(String.format("%s PARAMS TO SERCH IN f4102 - ARTICLE-NUMBER: %s , WAREHOUSE-CODE: %s", idOperation,
					articleDto.getArticleNumber().toString(), articleDto.getWarehouseCode()));

			F4102 f4102 = f4102Repository.existApplyTaxes(articleDto.getArticleNumber(), articleDto.getWarehouseCode());

			if (f4102 != null) {
				if (f4102.getIbtax1().equals("Y")) {

					articleDto.setApplyTax("Y");
					LOG.info(
							String.format("%s PARAMS TO SERCH IN f4008 - ARTICLE-NUMBER: %s , DATE: %s , APPLY-TAX: Y ",
									idOperation, articleDto.getArticleNumber().toString(), date.toString()));
					F4008 f4008 = f4008Repository.getTaxesByItm(articleDto.getArticleNumber(), date);

					if (f4008 != null) {

						LOG.info(String.format("%s f4008: %s ", idOperation, f4008.toString()));

						articleDto.setTaxValueOne(
								(!f4008.getTagl01().trim().isEmpty()) ? BigDecimal.valueOf(f4008.getTatxr1() / 1000) : BigDecimal.valueOf(-1));
						tax = (!f4008.getTagl01().trim().isEmpty()) ? tax + (f4008.getTatxr1() / 1000) : tax + 0;

						articleDto.setTaxValueTwo(
								(!f4008.getTagl02().trim().isEmpty()) ? BigDecimal.valueOf(f4008.getTatxr2() / 1000) : BigDecimal.valueOf(-1));
						tax = (!f4008.getTagl02().trim().isEmpty()) ? tax + (f4008.getTatxr2() / 1000) : tax + 0;

						articleDto.setTaxValueThree(
								(!f4008.getTagl03().trim().isEmpty()) ? BigDecimal.valueOf(f4008.getTatxr3() / 1000) : BigDecimal.valueOf(-1));
						tax = (!f4008.getTagl03().trim().isEmpty()) ? tax + (f4008.getTatxr3() / 1000) : tax + 0;

						articleDto.setTaxValueFour(
								(!f4008.getTagl04().trim().isEmpty()) ? BigDecimal.valueOf(f4008.getTatxr4() / 1000) : BigDecimal.valueOf(-1));
						tax = (!f4008.getTagl04().trim().isEmpty()) ? tax + (f4008.getTatxr4() / 1000) : tax + 0;

						articleDto.setTaxValueFive(
								(!f4008.getTagl05().trim().isEmpty()) ? BigDecimal.valueOf(f4008.getTatxr5() / 1000) : BigDecimal.valueOf(-1));
						tax = (!f4008.getTagl05().trim().isEmpty()) ? tax + (f4008.getTatxr5() / 1000) : tax + 0;

						priceTax = (1 + (tax / 100)) * (articleDto.getPrice().doubleValue());
						priceTax = Math.floor(priceTax * 100) / 100;
						articleDto.setPriceTax(BigDecimal.valueOf(priceTax));

						articleList.add(articleDto);

					} else {
						LOG.warn(String.format("%s EMPTY CONFIGURATION ON F4008 BY ARTICLE ", idOperation));
						articleDto.setApplyTax("Y");
						tax = getTaxByDefault(branchCode, idOperation);
						if (tax != 0) {
							articleDto.setTaxValueOne(BigDecimal.valueOf(tax));
							articleDto.setTaxValueByDefault(BigDecimal.valueOf(tax));
							priceTax = (1 + (tax / 100)) * (articleDto.getPrice().doubleValue());
							priceTax = Math.floor(priceTax * 100) / 100;
							articleDto.setPriceTax(BigDecimal.valueOf(priceTax));
							articleList.add(articleDto);
						}
					}
				} else {
					LOG.info(String.format("%s TAX FREE ARTICLE ", idOperation));
					articleList.add(articleDto);
				}
			}
		}
		return articleList;
	}

	public double getTaxByDefault(String branchCode, String idOperation) {
		LOG.info(String.format("%s FIND TAX BY DEFAULT USING GENERIC CLIENTE BY BRANCH: %s", idOperation, branchCode));
		double tax = 0;

		F0006 f0006 = F0006Mapper.toZapataF0006(f0006Repository.findWarehouseByCodeAndCompanyNumber(COMPANY_NUMBER, branchCode));
		if (f0006 != null) {
			F03012 f03012 = f03012Repository.findByIdNoClient(f0006.getMcan8().longValue());
			if (f03012 != null) {
				F4008 f4008 = f4008Repository.getTaxDefaultByTaxCode(f03012.getIva().trim(),
						new BigDecimal(getCurrentJulianDate()));
				if (f4008 != null) {
					tax = f4008.getTatxr1() / 1000;
				}
			}
		}
		return tax;
	}

	@Transactional
	@Override
	public GenericSerchParamsArticleDto findArticleCodeByBarcodeAndWarehouseCode(
			GenericSerchParamsArticleDto genericSerchParamsArticleDto, String barcode, String idOperation) {

		try {

			String ivxrt = "B";

			LOG.info(String.format("%s INIT findArticleCodeByBarcodeAndWarehouseCode()", idOperation));
			LOG.info(String.format("%s PARAMS: [genericSerchParamsArticleDto: %s , barcode: %s ]", idOperation,
					genericSerchParamsArticleDto.toString(), barcode));

			LOG.info(String.format("%s PARAMS TO SERCH: ivxrt - %s , barcode - %s ", idOperation, ivxrt, barcode));
			F4104 f4104 = f4104Repository.findByIvxrtAndivcitm(ivxrt, barcode);

			if (f4104 == null) {
				LOG.warn(String.format("%s BARCODE DOES NOT HAVE AN ASSOCIATED ARTICLE", idOperation));
				genericSerchParamsArticleDto.setArticleCode("");

			} else {

				LOG.info(String.format("%s BARCODE HAVE AN ASSOCIATED ARTICLE", idOperation));
				genericSerchParamsArticleDto.setArticleCode("");
				genericSerchParamsArticleDto.setBarcode(f4104.getIvlitm());
				;
			}

			return genericSerchParamsArticleDto;

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN findArticleNumberByBarcodeAndWarehouseCode(). EXCEPTION: %s",
					idOperation, e.getMessage()));
			throw new GlobalError();
		}

	}

	@Override
	public List<ArticleConvertionFactor> findConvertionFactorByArticleNumber(BigDecimal articleNumber,
			String idOperation) {

		try {

			LOG.info(String.format("%s INIT findConvertionFactorByArticleNumber() ", idOperation));
			LOG.info(String.format("%s PARAMS: [ articleNumber: %s ]", idOperation, articleNumber.toString()));

			List<ArticleConvertionFactor> articleConvertionFactorList = new ArrayList<ArticleConvertionFactor>();

			LOG.info(String.format("%s INIT SERCH IN F41002", idOperation));
			List<F41002> f41002List = f41002Repository.findByUmitm(articleNumber, " ");

			if (!f41002List.isEmpty()) {

				LOG.info(String.format("%s INIT CONVERTION TO DTO LIST", idOperation));
				articleConvertionFactorList = articleConvertionFactorConverter
						.f41002ListToArticleConvertionFactorList(f41002List);
			}

			LOG.info(String.format("%s RETURN LIST", idOperation));
			return articleConvertionFactorList;

		} catch (Exception e) {
			LOG.error(String.format("%s ERROR IN findConvertionFactorByArticleNumber(). EXCEPTION: %s", idOperation,
					e.getMessage()));
			throw new GlobalError();
		}
	}

	@SuppressWarnings("deprecation")
	public Long getCurrentJulianDate() {
		Long julianDate = 0L;
		StringBuilder sb = new StringBuilder();
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.setTimeInMillis(System.currentTimeMillis());
		String date = sb.append("1").append(Integer.toString(currentCalendar.get(Calendar.YEAR)).substring(2, 4))
				.append(String.format("%03d", currentCalendar.get(Calendar.DAY_OF_YEAR))).toString();
		try {
			julianDate = new Long(date);
			return julianDate;
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

}