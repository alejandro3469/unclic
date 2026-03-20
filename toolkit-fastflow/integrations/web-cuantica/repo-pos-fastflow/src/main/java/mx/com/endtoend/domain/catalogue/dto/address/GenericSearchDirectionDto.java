package mx.com.endtoend.domain.catalogue.dto.address;

public class GenericSearchDirectionDto {
    private String cp;

    private String stateCode;

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    @Override
    public String toString() {
        return "GenericSearchDirectionDto{" +
                "cp='" + cp + '\'' +
                ", stateCode='" + stateCode + '\'' +
                '}';
    }
}
