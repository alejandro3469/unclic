package mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;

import mx.com.endtoend.domain.articles.dto.ArticleDto;
import mx.com.endtoend.domain.articles.dto.GenericSerchParamsArticleDto;
import mx.com.endtoend.genericCommonsFileds.utilities.StringUtil;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.QF4101;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.QF4102;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.QF41021;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.QF4106;

@Repository
@ConditionalOnProperty(name = "app.calzadaOracle.enabled", havingValue = "true", matchIfMissing = false)
public class CustomDslArticleRepositoryImpl extends QuerydslRepositorySupport {

	@Autowired
	@Qualifier("calzadaOracleEntityManagerFactory")
	EntityManager em;

	public CustomDslArticleRepositoryImpl() {
		super(ArticleDto.class);
	}

	private final Logger LOG = LoggerFactory.getLogger(CustomDslArticleRepositoryImpl.class);
	StringUtil stringUtil = new StringUtil();

	public List<ArticleDto> findArticlesByParams(GenericSerchParamsArticleDto serrchParams) {

		Long currentJulianDay = getCurrentJulianDate();
		LOG.info("CURRENT-DAY: " + currentJulianDay);

		JPAQuery<ArticleDto> query = new JPAQuery<>(em);

		QF4101 F4101 = QF4101.f4101;
		QF4102 F4102 = QF4102.f4102;
		QF4106 F4106 = QF4106.f4106;
		QF41021 F41021 = QF41021.f41021;

		/**
		 * Start evaluation of tables
		 */
		query.from(F4101).join(F4102).on(F4101.imitm.eq(F4102.id.ibitm));

		// Determinar la unidad de medida a usar
		// Si se especifica inputUnitMeasure, usar esa unidad específica
		// Si no se especifica, usar la unidad primaria del artículo (imuom1) para mantener compatibilidad
		boolean useSpecificUnit = (serrchParams.getInputUnitMeasure() != null && !serrchParams.getInputUnitMeasure().isEmpty());
		
		if (useSpecificUnit) {
			// Usar la unidad especificada en inputUnitMeasure
			query.join(F4106).on(F4101.imitm.eq(F4106.id.bpitm))
					.on(F4106.id.bpuom.eq(serrchParams.getInputUnitMeasure()))
					.on(F4102.id.ibmcu.eq(F4106.id.bpmcu));
		} else {
			// Usar la unidad primaria del artículo (comportamiento original)
			query.join(F4106).on(F4101.imitm.eq(F4106.id.bpitm))
					.on(F4101.imuom1.eq(F4106.id.bpuom))
					.on(F4102.id.ibmcu.eq(F4106.id.bpmcu));
		}

		if (serrchParams.getIsAvailable()) {

			query.join(F41021).on(F4102.id.ibmcu.eq(F41021.id.limcu)).on(F4101.imitm.eq(F41021.id.liitm));
		}

		LOG.info("WAREHOUSE: " + serrchParams.getWarehouseCode());
		query.where(F4102.id.ibmcu.eq(serrchParams.getWarehouseCode()));
		query.where(F4102.ibstkt.ne("O"));

		/**
		 * Start evaluation of search parameters
		 */

		if (serrchParams.getIsAvailable()) {

			LOG.info("FIND BY AVAILABILITY ");
			query.where(F41021.lipqoh.subtract(F41021.lipcom).subtract(F41021.liot1p).divide(100).gt(0));
		}

		if (serrchParams.getArticleCode() != null) {
			if (!serrchParams.getArticleCode().isEmpty()) {

				LOG.info("FIND BY ARTICLE-CODE: " + serrchParams.getArticleCode());
				query.where(F4101.imlitm.like("%" + stringUtil.cleanStringToQuery(serrchParams.getArticleCode()) + "%"));

			}
		}
		
		if (serrchParams.getBarcode() != null) {
			if (!serrchParams.getBarcode().isEmpty()) {

				LOG.info("FIND BY ARTICLE-CODE BY SCANNER: " + serrchParams.getBarcode());
				query.where(F4101.imlitm.eq(stringUtil.autocompleteSpace(serrchParams.getBarcode(), 25, true)));

			}
		}

		if (serrchParams.getDivision() != null) {
			if (!serrchParams.getDivision().isEmpty()) {

				LOG.info("FIND BY DIVISION: " + serrchParams.getDivision());
				query.where(F4101.imsrp1.trim().eq(serrchParams.getDivision()));

			}
		}

		if (serrchParams.getCategory() != null) {
			if (!serrchParams.getCategory().isEmpty()) {

				LOG.info("FIND BY CATEGORY: " + serrchParams.getCategory());
				query.where(F4101.imsrp2.trim().eq(serrchParams.getCategory().trim()));

			}
		}

		if (serrchParams.getFamily() != null) {
			if (!serrchParams.getFamily().isEmpty()) {

				LOG.info("FIND BY FAMILY: " + serrchParams.getFamily());
				query.where(F4101.imsrp3.trim().eq(serrchParams.getFamily().trim()));

			}
		}

		if (serrchParams.getCategoryCode() != null) {
			if (!serrchParams.getCategoryCode().isEmpty()) {

				LOG.info("FIND BY CATEGORU-CODE: " + serrchParams.getCategoryCode());
				query.where(F4101.imsrp4.trim().eq(serrchParams.getCategoryCode().trim()));

			}
		}

		if (serrchParams.getBrand() != null) {
			if (!serrchParams.getBrand().isEmpty()) {

				LOG.info("FIND BY BRAND: " + serrchParams.getBrand());
				query.where(F4101.imsrp5.trim().eq(serrchParams.getBrand().trim()));

			}

		}

		if (serrchParams.getAlternativeDescription() != null) {
			if (!serrchParams.getAlternativeDescription().isEmpty()) {
				LOG.info("FIND BY ALTERNATIVE-DESCRIPTION: " + serrchParams.getAlternativeDescription());
				query.where(F4101.imsrtx
						.like("%" + stringUtil.cleanStringToQuery(serrchParams.getAlternativeDescription()) + "%"));

			}
		}

		if (serrchParams.getCatalogNumber() != null) {
			if (!serrchParams.getCatalogNumber().isEmpty()) {
				LOG.info("FIND BY CATALOG-NUMBER: " + serrchParams.getCatalogNumber());
				query.where(
						F4101.imaitm.like("%" + stringUtil.cleanStringToQuery(serrchParams.getCatalogNumber()) + "%"));

			}

		}

		if (serrchParams.getArticleDescription() != null) {

			if (!serrchParams.getArticleDescription().isEmpty()) {

				String description = stringUtil.cleanStringToQuery(serrchParams.getArticleDescription());

				description = description.contains("*") ? description.replace("*", "%") : description;

				LOG.info("FIND BY DESCRIPTION: " + description);

				query.where(F4101.imdsc1.trim().concat(F4101.imdsc2.trim()).like("%" + description.trim() + "%"));

			}

		}

		/**
		 * Conditions to get price
		 */
		query.where(F4106.id.bpcgid.eq(new BigDecimal(serrchParams.getPriceType())));
		query.where(F4106.bpeftj.loe(currentJulianDay));
		
		// Construir la subconsulta con la unidad correcta
		if (useSpecificUnit) {
			query.where(F4106.id.bpexdj
					.eq(JPAExpressions.select(F4106.id.bpexdj.min()).from(F4106).where(F4106.id.bpitm.eq(F4101.imitm))
							.where(F4106.id.bpuom.eq(serrchParams.getInputUnitMeasure())).where(F4106.id.bpmcu.eq(F4102.id.ibmcu))
							.where(F4106.id.bpcgid.eq(new BigDecimal(serrchParams.getPriceType())))
							.where(F4106.bpeftj.loe(currentJulianDay)).where(F4106.id.bpexdj.goe(currentJulianDay))));
		} else {
			query.where(F4106.id.bpexdj
					.eq(JPAExpressions.select(F4106.id.bpexdj.min()).from(F4106).where(F4106.id.bpitm.eq(F4101.imitm))
							.where(F4106.id.bpuom.eq(F4101.imuom1)).where(F4106.id.bpmcu.eq(F4102.id.ibmcu))
							.where(F4106.id.bpcgid.eq(new BigDecimal(serrchParams.getPriceType())))
							.where(F4106.bpeftj.loe(currentJulianDay)).where(F4106.id.bpexdj.goe(currentJulianDay))));
		}

		/**
		 * End evaluation
		 */

		/**
		 * Select fields
		 */
		query.select(
				Projections.constructor(ArticleDto.class,

						F4106.id.bpcgid, F4102.id.ibitm, F4102.id.ibmcu, F4102.ibvend, F4102.ibaitm, F4102.ibprgr,
						F4102.iblnty,

						F4101.imdsc1, F4101.imdsc2, F4101.imlitm, F4101.imprp2, 
						F4102.ibstkt, //AJUSTE EN MAPEO
						F4101.imsrp1,
						F4101.imsrp3, F4101.imsrp4, F4101.imsrp5, F4101.imsrtx, F4101.imuom1, F4101.imuom9,
						F4106.id.bpuom, Expressions.numberTemplate(BigDecimal.class, "CAST({0} / 10000 AS big_decimal)", F4106.bpuprc)));

		/**
		 * Execute and return data
		 */
		return query.orderBy(F4101.imdsc1.asc()).limit(50).fetch();

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
			LOG.error("METHOD: getCurrentJulianDate()");
			LOG.error("ERROR AL PARSEAR UN STRING A INTEGER", e.getMessage());
			return null;
		}
	}
}
