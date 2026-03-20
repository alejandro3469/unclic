package mx.com.smartbussiness.generic.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Mapeador de migración de DTOs de double a BigDecimal
 * Define las relaciones y dependencias entre DTOs para migración ordenada
 * 
 * @author Migration Team
 * @version 1.0.0
 * @since 2025-10-06
 */
public class DtoMigrationMapper {

    /**
     * Mapeo de DTOs y sus dependencias para migración ordenada
     * Clave: DTO a migrar
     * Valor: Array de DTOs de los que depende
     */
    private static final Map<String, String[]> DEPENDENCY_MAP = new HashMap<>();

    static {
        // DTOs base (sin dependencias)
        DEPENDENCY_MAP.put("TaxDto", new String[]{});
        DEPENDENCY_MAP.put("ArticleAvilabilityDto", new String[]{});
        
        // DTOs que dependen de TaxDto
        DEPENDENCY_MAP.put("OrderDetailDto", new String[]{"TaxDto"});
        
        // DTOs que dependen de OrderDetailDto y TaxDto
        DEPENDENCY_MAP.put("OrderDto", new String[]{"OrderDetailDto", "TaxDto"});
        
        // DTOs que dependen de OrderDto
        DEPENDENCY_MAP.put("SaleOrderDetailDto", new String[]{"OrderDto"});
        DEPENDENCY_MAP.put("SaleOrderDto", new String[]{"OrderDto", "SaleOrderDetailDto"});
        
        // DTOs de publicidad (independientes)
        DEPENDENCY_MAP.put("AdvertisingDto", new String[]{});
        DEPENDENCY_MAP.put("AdvertisingSummaryCostDto", new String[]{"AdvertisingDto"});
        DEPENDENCY_MAP.put("SaleAdvertisingDto", new String[]{"AdvertisingDto"});
    }

    /**
     * Obtiene el orden de migración recomendado
     * @return Array con los nombres de DTOs en orden de migración
     */
    public static String[] getMigrationOrder() {
        return new String[]{
            "TaxDto",
            "ArticleAvilabilityDto", 
            "AdvertisingDto",
            "OrderDetailDto",
            "OrderDto",
            "SaleOrderDetailDto",
            "SaleOrderDto",
            "AdvertisingSummaryCostDto",
            "SaleAdvertisingDto"
        };
    }

    /**
     * Obtiene las dependencias de un DTO
     * @param dtoName Nombre del DTO
     * @return Array con las dependencias
     */
    public static String[] getDependencies(String dtoName) {
        return DEPENDENCY_MAP.getOrDefault(dtoName, new String[]{});
    }

    /**
     * Verifica si un DTO puede ser migrado (todas sus dependencias están listas)
     * @param dtoName Nombre del DTO
     * @param migratedDtos Array de DTOs ya migrados
     * @return true si puede ser migrado
     */
    public static boolean canMigrate(String dtoName, String[] migratedDtos) {
        String[] dependencies = getDependencies(dtoName);
        for (String dependency : dependencies) {
            boolean found = false;
            for (String migrated : migratedDtos) {
                if (dependency.equals(migrated)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * Mapeo de campos críticos que requieren migración especial
     * Clave: DTO.Campo
     * Valor: Configuración de migración
     */
    private static final Map<String, FieldMigrationConfig> FIELD_MIGRATION_CONFIG = new HashMap<>();

    static {
        // Configuración para campos monetarios (precisión 2 decimales)
        FIELD_MIGRATION_CONFIG.put("OrderDto.subTotal", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("OrderDto.ivaTotal", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("OrderDto.orderTotal", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("OrderDto.pendingPayment", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("OrderDto.discountTotal", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("OrderDto.exchangeRate", new FieldMigrationConfig(true, 4, false));
        FIELD_MIGRATION_CONFIG.put("OrderDto.clientTax", new FieldMigrationConfig(true, 2, false));
        FIELD_MIGRATION_CONFIG.put("OrderDto.invoiceAmount", new FieldMigrationConfig(true, 2, true));

        // Configuración para OrderDetailDto
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.requestAmount", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.unitPrice", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.finalUnitPrice", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.subTotal", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.unitPriceTax", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.subTotalTax", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.articleTax", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.discountSeller", new FieldMigrationConfig(true, 2, false));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.finalDiscountSeller", new FieldMigrationConfig(true, 2, false));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.taxValueOne", new FieldMigrationConfig(true, 2, false));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.taxValueTwo", new FieldMigrationConfig(true, 2, false));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.taxValueThree", new FieldMigrationConfig(true, 2, false));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.taxValueFour", new FieldMigrationConfig(true, 2, false));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.taxValueFive", new FieldMigrationConfig(true, 2, false));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.taxValueByDefault", new FieldMigrationConfig(true, 2, false));
        FIELD_MIGRATION_CONFIG.put("OrderDetailDto.conversionFactor", new FieldMigrationConfig(true, 4, false));

        // Configuración para TaxDto
        FIELD_MIGRATION_CONFIG.put("TaxDto.value", new FieldMigrationConfig(true, 2, true));

        // Configuración para DTOs de publicidad
        FIELD_MIGRATION_CONFIG.put("AdvertisingDto.pricePerWord", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("AdvertisingSummaryCostDto.iva", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("AdvertisingSummaryCostDto.subTotal", new FieldMigrationConfig(true, 2, true));
        FIELD_MIGRATION_CONFIG.put("AdvertisingSummaryCostDto.amountTotal", new FieldMigrationConfig(true, 2, true));
    }

    /**
     * Obtiene la configuración de migración para un campo específico
     * @param dtoName Nombre del DTO
     * @param fieldName Nombre del campo
     * @return Configuración de migración
     */
    public static FieldMigrationConfig getFieldMigrationConfig(String dtoName, String fieldName) {
        String key = dtoName + "." + fieldName;
        return FIELD_MIGRATION_CONFIG.getOrDefault(key, new FieldMigrationConfig(true, 2, false));
    }

    /**
     * Configuración de migración para un campo específico
     */
    public static class FieldMigrationConfig {
        private final boolean requiresMigration;
        private final int scale;
        private final boolean isMonetary;

        public FieldMigrationConfig(boolean requiresMigration, int scale, boolean isMonetary) {
            this.requiresMigration = requiresMigration;
            this.scale = scale;
            this.isMonetary = isMonetary;
        }

        public boolean requiresMigration() {
            return requiresMigration;
        }

        public int getScale() {
            return scale;
        }

        public boolean isMonetary() {
            return isMonetary;
        }
    }
}


