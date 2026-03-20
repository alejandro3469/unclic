package mx.com.endtoend.domain.catalogue.dto.address;

public class CoordinateDto {

    private Long id;

    private boolean isEnable;

    private String code;

    private String name;

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

    @Override
    public String toString() {
        return "CoordinateDto{" +
                "id=" + id +
                ", isEnable=" + isEnable +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
