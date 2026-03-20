package mx.com.endtoend.domain.catalogue.dto.client;

public class RegimeFiscalDto {

    private Long id;

    private boolean isEnable;

    private String code;

    private String satCode;

    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean enable) {
        this.isEnable = enable;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSatCode() {
        return satCode;
    }

    public void setSatCode(String satCode) {
        this.satCode = satCode;
    }
    @Override
    public String toString() {
        return "RegimeFiscalDto{" +
                "id=" + id +
                ", isEnable=" + isEnable +
                ", code='" + code + '\'' +
                ", satCode='" + satCode + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
