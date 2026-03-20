package mx.com.endtoend.domain.articles.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import mx.com.smartbussiness.generic.utils.DecimalPrecisionUtils;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CustomArticleDto {

	private Long id;

	private String name;

	@NotNull(message = "El precio es obligatorio")
	@DecimalMin(value = "0.0", message = "El precio debe ser mayor o igual a 0")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal price;

	private boolean isEnable;

	private String saleType;

	private BigDecimal articleNumber;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public boolean getIsEnable() {
		return isEnable;
	}

	public String getSaleType() {
		return saleType;
	}

	public BigDecimal getArticleNumber() {
		return articleNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(BigDecimal price) {
		this.price = DecimalPrecisionUtils.roundToTwoDecimals(price);
	}

	public void setIsEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public void setArticleNumber(BigDecimal articleNumber) {
		this.articleNumber = articleNumber;
	}

	@Override
	public String toString() {
		return "CustomArticleDto [id=" + id + ", name=" + name + ", price=" + price + ", isEnable=" + isEnable
				+ ", saleType=" + saleType + ", articleNumber=" + articleNumber + "]";
	}

}
