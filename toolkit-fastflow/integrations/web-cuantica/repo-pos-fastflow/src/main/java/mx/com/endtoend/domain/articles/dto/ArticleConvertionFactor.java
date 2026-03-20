package mx.com.endtoend.domain.articles.dto;

import java.math.BigDecimal;

/**
 * Modelo de datos para la recuperación de los valores de conversion de unidades
 * por artículo
 * 
 * @author ddcasas
 *
 */

public class ArticleConvertionFactor {

	private String primaryUnitMeasure;

	private BigDecimal convertionFactor;

	private String equivalentUnitMeasure;

	private BigDecimal reverseConvertionFactor;

	public String getPrimaryUnitMeasure() {
		return primaryUnitMeasure;
	}

	public BigDecimal getConvertionFactor() {
		return convertionFactor;
	}

	public String getEquivalentUnitMeasure() {
		return equivalentUnitMeasure;
	}

	public void setPrimaryUnitMeasure(String primaryUnitMeasure) {
		this.primaryUnitMeasure = primaryUnitMeasure;
	}

	public void setConvertionFactor(BigDecimal convertionFactor) {
		this.convertionFactor = convertionFactor;
	}

	public void setEquivalentUnitMeasure(String equivalentUnitMeasure) {
		this.equivalentUnitMeasure = equivalentUnitMeasure;
	}

	public BigDecimal getReverseConvertionFactor() {
		return reverseConvertionFactor;
	}

	public void setReverseConvertionFactor(BigDecimal reverseConvertionFactor) {
		this.reverseConvertionFactor = reverseConvertionFactor;
	}

	@Override
	public String toString() {
		return "ArticleConvertionFactor [primaryUnitMeasure=" + primaryUnitMeasure + ", convertionFactor="
				+ convertionFactor + ", equivalentUnitMeasure=" + equivalentUnitMeasure + ", reverseConvertionFactor="
				+ reverseConvertionFactor + "]";
	}

}
