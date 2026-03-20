package mx.com.endtoend.domain.catalogue.dto.address;

public class MunicipalityDto {

    private Long id;

    private boolean isEnable;

    private String code;

    private String name;

    private String stateCode;

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
        isEnable = enable;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    @Override
    public String toString() {
        return "MunicipalityDto{" +
                "id=" + id +
                ", isEnable=" + isEnable +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }
}
