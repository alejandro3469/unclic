package mx.com.endtoend.infrastructure.articles.calzada.business;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import mx.com.endtoend.infrastructure.warehouse.calzada.repositories.F0006CalzadaRepository;
import mx.com.endtoend.infrastructure.warehouse.common.converters.F0006Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import mx.com.endtoend.domain.articles.dto.ArticleConvertionFactor;
import mx.com.endtoend.domain.articles.dto.ArticleDto;
import mx.com.endtoend.domain.articles.dto.GenericSerchParamsArticleDto;
import mx.com.endtoend.domain.debug.ports.api.DebugServicePort;
import mx.com.endtoend.genericCommonsFileds.utilities.StringUtil;
import mx.com.endtoend.infrastructure.articles.common.repository.GenericArticleRepository;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F4008;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F41002;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F4102;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F4104;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories.F4008Repository;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories.F41002Repository;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories.F41021Repository;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories.F4102Repository;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories.F4104Repository;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories.F4106Repository;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories.impl.CustomDslArticleRepositoryImpl;
import mx.com.endtoend.infrastructure.articles.common.converters.ArticleConvertionFactorConverter;
import mx.com.endtoend.infrastructure.configurations.genericErrors.exceptions.GlobalError;
import mx.com.endtoend.infrastructure.services.jde.catalogue.calzada.common.entities.F0005;
import mx.com.endtoend.infrastructure.services.jde.catalogue.calzada.common.repository.F0005Repository;
import mx.com.endtoend.infrastructure.services.jde.clients.calzada.entities.F03012;
import mx.com.endtoend.infrastructure.services.jde.clients.calzada.repository.F03012Repository;

/**
 * 
 * @author ddcasas
 *
 */

@ConditionalOnProperty(name = "app.calzadaOracle.enabled", havingValue = "true", matchIfMissing = false)
@Service
public class ArticleCalzadaRepository implements GenericArticleRepository {

	@Autowired
	private DebugServicePort debugServicePort;

	@Autowired(required = false)
	private F4102Repository f4102Repository;

	@Autowired(required = false)
	private F41021Repository f41021Repository;

	@Autowired(required = false)
	private F0005Repository f0005Repository;

	@Autowired(required = false)
	private F41002Repository f41002Repository;

	@Autowired(required = false)
	private F4106Repository f4106Repository;

	@Autowired(required = false)
	private F4008Repository f4008Repository;

	@Autowired(required = false)
	private F4104Repository f4104Repository;

	@Autowired(required = false)
	private F0006CalzadaRepository f0006Repository;

	@Autowired(required = false)
	private F03012Repository f03012Repository;

	@Autowired
	private CustomDslArticleRepositoryImpl customDslArticleRepositoryImpl;

	@Autowired
	private ArticleConvertionFactorConverter articleConvertionFactorConverter;

	private static double taxEmpty = -1;
	private String companyCode = "FCAL";
	private String module = "ARTICLES";

	StringUtil stringUtil = new StringUtil();

	private final Logger LOG = LoggerFactory.getLogger(ArticleCalzadaRepository.class);

	@Transactional
	@Override
	public List<ArticleDto> findArticlesByParams(GenericSerchParamsArticleDto params, String branchCode,
			String idOperation, boolean debug) {

		try {

			LOG.info(String.format("%s INIT findArticlesByParams() ", idOperation));
			LOG.info(String.format("%s PARAMS: [genericSerchParamsArticleDto: %s , branchCode: %s ]", idOperation,
					params.toString(), branchCode));

			debugServicePort.saveLog(module, companyCode, "I-PARAMS: [ " + params.toString() + " ]", idOperation, debug);
			
			long time1 = 0;
			long time2 = 0;
			long time3 = 0;
			long time4 = 0;

			if (params.getWarehouseCode().length() < 12) {

				LOG.info(String.format("%s COMPLETE WAREHOUSE-CODE LENGTH", idOperation));
				params.setWarehouseCode(stringUtil.completeLengthToWarehouseCode(params.getWarehouseCode()));

			}

			debugServicePort.saveLog(module, companyCode, "I-START PRINCIPAL QUERY", idOperation, debug);
			Calendar currentTime1 = Calendar.getInstance();
			time1 = currentTime1.getTimeInMillis();
			
			List<ArticleDto> articleList = customDslArticleRepositoryImpl.findArticlesByParams(params);

			articleList.forEach(article -> {
				double price = article.getPrice().doubleValue();
				BigDecimal roundedPrice = BigDecimal.valueOf(price).setScale(2, RoundingMode.HALF_UP);
				article.setPrice(roundedPrice);
			});

			Calendar currentTime2 = Calendar.getInstance();
			time2 = currentTime2.getTimeInMillis();

			debugServicePort.saveLog(module, companyCode, "I-TIME 1: " + (time2 - time1), idOperation, debug);
			debugServicePort.saveLog(module, companyCode, "I-END PRINCIPAL QUERY", idOperation, debug);
			debugServicePort.saveLog(module, companyCode, "I-START SETTER ADITIONAL DATA", idOperation, debug);

			Calendar currentTime3 = Calendar.getInstance();
			time3 = currentTime3.getTimeInMillis();
			
			articleList = setQuantityByArticle(articleList, params.getIsAvailable(), idOperation);

			setSubCategoryDescriptionByArticle(articleList, idOperation);
			setDivisionDescriptionByArticle(articleList, idOperation);
			setFamilyDescriptionByArticle(articleList, idOperation);
			setCategoryCodeDescription(articleList, idOperation);
			setMarkDescription(articleList, idOperation);
			setConvertionCodeByArticle(articleList, idOperation);
			setUnitMeasurementByArticle(articleList, idOperation);
			setFormFactorByArticle(articleList, idOperation);
			setTaxesByArticle(articleList, branchCode, idOperation);

			Calendar currentTime4 = Calendar.getInstance();
			time4 = currentTime4.getTimeInMillis();

			debugServicePort.saveLog(module, companyCode, "I-TIME 2: " + (time4 - time3), idOperation, debug);
			debugServicePort.saveLog(module, companyCode, "I-END SETTER ADITIONAL DATA", idOperation, debug);

			return articleList;

		} catch (Exception e) {

			LOG.error(String.format("%s ERROR IN findArticlesByParams(). EXCEPTION: %s", idOperation, e.getMessage()));
			throw new GlobalError();
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
					articleDto.setTaxValueOne(BigDecimal.valueOf(0.0));
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
		var f0006= F0006Mapper.toCalzadaF0006(f0006Repository.findWarehouseByCodeAndCompanyNumber("00001", branchCode));
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
				genericSerchParamsArticleDto.setBarcode(f4104.getIvlitm());;
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