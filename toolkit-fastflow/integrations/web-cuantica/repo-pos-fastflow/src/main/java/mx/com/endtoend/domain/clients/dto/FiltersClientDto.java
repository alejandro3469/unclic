package mx.com.endtoend.domain.clients.dto;

public class FiltersClientDto {
	
	private String name;

	private Long noClient;
	
	private String rfc;
	
	private String businessName;

	private Integer page;

	private Integer row;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNoClient() {
		return noClient;
	}

	public void setNoClient(Long noClient) {
		this.noClient = noClient;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRow() {
		return row;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	@Override
	public String toString() {
		return "FiltersClientDto [name=" + name + ", noClient=" + noClient + ", rfc=" + rfc + ", page=" + page
				+ ", row=" + row + "]";
	}
	
	

}