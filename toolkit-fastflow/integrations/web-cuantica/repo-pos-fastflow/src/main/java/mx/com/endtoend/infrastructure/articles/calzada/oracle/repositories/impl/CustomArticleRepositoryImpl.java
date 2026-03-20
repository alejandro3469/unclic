package mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import mx.com.endtoend.domain.articles.dto.GenericSerchParamsArticleDto;
import mx.com.endtoend.genericCommonsFileds.utilities.StringUtil;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F4101;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F4102;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F41021;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.entities.F4106;
import mx.com.endtoend.infrastructure.articles.calzada.oracle.repositories.CustomArticleRepository;

@Repository
@ConditionalOnProperty(name = "app.calzadaOracle.enabled", havingValue = "true", matchIfMissing = false)
public class CustomArticleRepositoryImpl implements CustomArticleRepository {

	@Autowired
	@Qualifier("calzadaOracleEntityManagerFactory")
	EntityManager em;

	public CustomArticleRepositoryImpl(EntityManager entityManager) {
		this.em = entityManager;
	}

	StringUtil stringUtil = new StringUtil();

	private final Logger LOG = LoggerFactory.getLogger(CustomArticleRepositoryImpl.class);

	@Override
	public List<Object[]> findArticlesByParams(GenericSerchParamsArticleDto serrchParams) {

		Long currentJulianDay = getCurrentJulianDate();

		LOG.info("CURRENT-DAY: " + currentJulianDay);

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);

		Root<F4101> f4101 = query.from(F4101.class);
		Root<F4102> f4102 = query.from(F4102.class);
		Root<F4106> f4106 = query.from(F4106.class);

		List<Predicate> conditionsPredicate = new ArrayList<Predicate>();

		Predicate joinF4101ToF4102Predicate = cb.equal(f4101.get("imitm"), f4102.get("id").get("ibitm"));
		Predicate joinF4101ToF4106Predicate = cb.equal(f4101.get("imitm"), f4106.get("id").get("bpitm"));

		conditionsPredicate.add(joinF4101ToF4102Predicate);
		conditionsPredicate.add(joinF4101ToF4106Predicate);

		conditionsPredicate
				.add(cb.equal(cb.trim(f4102.<String>get("id").get("ibmcu")), serrchParams.getWarehouseCode().trim()));

		conditionsPredicate.add(cb.notEqual(cb.trim(f4102.<String>get("ibstkt")), "O"));

		/**
		 * Start evaluation of search parameters
		 */

		if (serrchParams.getIsAvailable()) {

			LOG.info("FIND BY AVAILABILITY ");

			Root<F41021> f41021 = query.from(F41021.class);

			Predicate joinF4106ToF41021Predicate = cb.equal(f4106.get("id").get("bpmcu"),
					f41021.get("id").get("limcu"));
			Predicate joinF4106ToF41021PredicateTwo = cb.equal(f4106.get("id").get("bpitm"),
					f41021.get("id").get("liitm"));

			conditionsPredicate.add(joinF4106ToF41021Predicate);
			conditionsPredicate.add(joinF4106ToF41021PredicateTwo);

			conditionsPredicate.add(cb.greaterThanOrEqualTo(

					cb.diff((cb.diff(f41021.get("lipqoh"), f41021.get("lipcom"))), f41021.get("liot1p")), 100

			));

		}

		if (serrchParams.getArticleCode() != null) {
			if (!serrchParams.getArticleCode().isEmpty()) {

				LOG.info("FIND BY ARTICLE-CODE: " + serrchParams.getArticleCode());

				if (serrchParams.getArticleCode().contains("*")) {

					LOG.info("FIND BY PARTIAL ARTICLE CODE: " + serrchParams.getArticleCode());

					conditionsPredicate.add(cb.like(cb.trim(f4101.<String>get("imlitm")),
							"%" + serrchParams.getArticleCode().replace("*", "").trim() + "%"));
				} else {

					LOG.info("FIND BY ARTICLE-CODE COMPLETE: " + serrchParams.getArticleCode());
					conditionsPredicate
							.add(cb.equal(cb.trim(f4101.<String>get("imlitm")), serrchParams.getArticleCode().trim()));
				}
			}
		}

		if (serrchParams.getDivision() != null) {
			if (!serrchParams.getDivision().isEmpty()) {

				LOG.info("FIND BY DIVISION: " + serrchParams.getDivision());

				conditionsPredicate
						.add(cb.equal(cb.trim(f4101.<String>get("imsrp1")), serrchParams.getDivision().trim()));
			}

		}

		if (serrchParams.getCategory() != null) {
			if (!serrchParams.getCategory().isEmpty()) {

				LOG.info("FIND BY CATEGORY: " + serrchParams.getCategory());

				conditionsPredicate
						.add(cb.equal(cb.trim(f4101.<String>get("imsrp2")), serrchParams.getCategory().trim()));
			}
		}

		if (serrchParams.getFamily() != null) {
			if (!serrchParams.getFamily().isEmpty()) {

				LOG.info("FIND BY FAMILY: " + serrchParams.getFamily());

				conditionsPredicate
						.add(cb.equal(cb.trim(f4101.<String>get("imsrp3")), serrchParams.getFamily().trim()));
			}
		}

		if (serrchParams.getCategoryCode() != null) {
			if (!serrchParams.getCategoryCode().isEmpty()) {

				LOG.info("FIND BY CATEGORU-CODE: " + serrchParams.getCategoryCode());

				conditionsPredicate
						.add(cb.equal(cb.trim(f4101.<String>get("imsrp4")), serrchParams.getCategoryCode().trim()));
			}
		}

		if (serrchParams.getBrand() != null) {
			if (!serrchParams.getBrand().isEmpty()) {

				LOG.info("FIND BY BRAND: " + serrchParams.getBrand());

				conditionsPredicate.add(cb.equal(cb.trim(f4101.<String>get("imsrp5")), serrchParams.getBrand().trim()));
			}

		}

		if (serrchParams.getAlternativeDescription() != null) {
			if (!serrchParams.getAlternativeDescription().isEmpty()) {

				LOG.info("FIND BY ALTERNATIVE-DESCRIPTION: " + serrchParams.getAlternativeDescription());

				conditionsPredicate.add(cb.equal(cb.trim(f4101.<String>get("imsrtx")),
						serrchParams.getAlternativeDescription().trim()));
			}
		}

		if (serrchParams.getCatalogNumber() != null) {
			if (!serrchParams.getCatalogNumber().isEmpty()) {

				LOG.info("FIND BY CATALOG-NUMBER: " + serrchParams.getCatalogNumber());

				conditionsPredicate
						.add(cb.equal(cb.trim(f4101.<String>get("imaitm")), serrchParams.getCatalogNumber().trim()));
			}

		}

		if (serrchParams.getArticleDescription() != null) {
			if (!serrchParams.getArticleDescription().isEmpty()) {

				String description = stringUtil.cleanStringToQuery(serrchParams.getArticleDescription());

				LOG.info("FIND BY DESCRIPTION: " + description);

				Predicate articleDescriptionOne = cb.like(cb.trim(f4101.<String>get("imdsc1")),
						"%" + description.trim() + "%");
				Predicate articleDescriptionTow = cb.like(cb.trim(f4101.<String>get("imdsc2")),
						"%" + description.trim() + "%");
				conditionsPredicate.add(cb.or(articleDescriptionOne, articleDescriptionTow));
			}

		}
		/**
		 * End evaluation
		 */

		/**
		 * Conditions to get price
		 */
		// Determinar la unidad de medida a usar
		// Si se especifica inputUnitMeasure, usar esa unidad específica
		// Si no se especifica, usar la unidad primaria del artículo (imuom1) para mantener compatibilidad
		boolean useSpecificUnit = (serrchParams.getInputUnitMeasure() != null && !serrchParams.getInputUnitMeasure().isEmpty());
		
		if (useSpecificUnit) {
			// Usar la unidad especificada en inputUnitMeasure
			conditionsPredicate.add(cb.equal(f4106.get("id").get("bpuom"), serrchParams.getInputUnitMeasure()));
		} else {
			// Usar la unidad primaria del artículo (comportamiento original)
			conditionsPredicate.add(cb.equal(f4106.get("id").get("bpuom"), f4101.get("imuom1")));
		}
		
		conditionsPredicate.add(cb.equal(f4102.get("id").get("ibmcu"), f4106.get("id").get("bpmcu")));
		
		conditionsPredicate.add(cb.equal(f4106.get("id").get("bpcgid"), new BigDecimal(serrchParams.getPriceType())));
		conditionsPredicate.add(cb.lessThanOrEqualTo(f4106.get("bpeftj"), currentJulianDay));

		Subquery<BigDecimal> sub = query.subquery(BigDecimal.class);
		Root<F4106> subRoot = sub.from(F4106.class);

		List<Predicate> conditionsSubPredicate = new ArrayList<Predicate>();

		conditionsSubPredicate.add(cb.equal(subRoot.get("id").get("bpitm"), f4101.get("imitm")));
		
		// Usar la misma lógica para la subconsulta
		if (useSpecificUnit) {
			conditionsSubPredicate.add(cb.equal(subRoot.get("id").get("bpuom"), serrchParams.getInputUnitMeasure()));
		} else {
			conditionsSubPredicate.add(cb.equal(subRoot.get("id").get("bpuom"), f4101.get("imuom1")));
		}
		
		conditionsSubPredicate.add(cb.equal(subRoot.get("id").get("bpmcu"), f4102.get("id").get("ibmcu")));
		conditionsSubPredicate
				.add(cb.equal(subRoot.get("id").get("bpcgid"), new BigDecimal(serrchParams.getPriceType())));
		conditionsSubPredicate.add(cb.lessThanOrEqualTo(subRoot.get("bpeftj"), currentJulianDay));
		conditionsSubPredicate.add(cb.greaterThanOrEqualTo(subRoot.get("id").get("bpexdj"), currentJulianDay));
		sub.select(cb.min(subRoot.get("id").get("bpexdj")));
		sub.where(conditionsSubPredicate.toArray(new Predicate[0]));

		conditionsPredicate.add(cb.equal(f4106.get("id").get("bpexdj"), sub));

		/**
		 * End conditions
		 */

		query.multiselect(

				f4102.get("id").get("ibitm"), 
				f4102.get("id").get("ibmcu"), 
				f4102.get("ibvend"), 
				f4102.get("ibaitm"),
				f4102.get("ibprgr"), 
				f4102.get("iblnty"),

				f4101.get("imdsc1"), 
				f4101.get("imdsc2"), 
				f4101.get("imlitm"), 
				f4101.get("imprp2"), 
				f4101.get("imstkt"),
				f4101.get("imsrp1"), 
				f4101.get("imsrp3"), 
				f4101.get("imsrp4"), 
				f4101.get("imsrp5"), 
				f4101.get("imsrtx"),
				f4101.get("imuom1"), 
				f4101.get("imuom9"),

				f4106.get("id").get("bpuom"), 
				f4106.get("bpuprc")

		).where(conditionsPredicate.toArray(new Predicate[0])

		);

		query.orderBy(cb.asc(f4101.get("imdsc1")));

		return em.createQuery(query).setFirstResult(0).setMaxResults(50).getResultList();
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