package mx.com.endtoend.domain.commons.constants;

public enum CatalogueAddressTypeEnum {

    COUNTRY,
    STATE,
    COORDINATE,
    FLAT,
    MUNICIPALITY,
    COLONY;

    public static boolean isValid(String value) {
        CatalogueAddressTypeEnum[] catalogueAddressTypeEnumList = CatalogueAddressTypeEnum.values();
        for (CatalogueAddressTypeEnum addressTypeEnum : catalogueAddressTypeEnumList)
            if (addressTypeEnum.toString().equals(value))
                return true;
        return false;
    }
}
