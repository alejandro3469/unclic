package mx.com.endtoend.domain.orders.dto.tickets;

public class OrderDetail {

	private String article;

	private String description;

	private String um;

	private String quantity;

	public String getArticle() {
		return article;
	}

	public String getDescription() {
		return description;
	}

	public String getUm() {
		return um;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setUm(String um) {
		this.um = um;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
