package mx.com.smartbussiness.generic.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utilidad para conversión segura de double a BigDecimal
 * Durante la migración de DTOs de double a BigDecimal
 * 
 * @author Migration Team
 * @version 1.0.0
 * @since 2025-10-06
 */
public class DoubleToBigDecimalConverter {

    /**
     * Convierte double a BigDecimal con precisión SAT (2 decimales)
     * @param value Valor double a convertir
     * @return BigDecimal con precisión de 2 decimales
     */
    public static BigDecimal convert(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Convierte Double a BigDecimal con precisión SAT (2 decimales)
     * @param value Valor Double a convertir (puede ser null)
     * @return BigDecimal con precisión de 2 decimales, o null si value es null
     */
    public static BigDecimal convert(Double value) {
        if (value == null) {
            return null;
        }
        return convert(value.doubleValue());
    }

    /**
     * Convierte double a BigDecimal con precisión personalizada
     * @param value Valor double a convertir
     * @param scale Precisión deseada
     * @return BigDecimal con la precisión especificada
     */
    public static BigDecimal convert(double value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * Convierte Double a BigDecimal con precisión personalizada
     * @param value Valor Double a convertir (puede ser null)
     * @param scale Precisión deseada
     * @return BigDecimal con la precisión especificada, o null si value es null
     */
    public static BigDecimal convert(Double value, int scale) {
        if (value == null) {
            return null;
        }
        return convert(value.doubleValue(), scale);
    }

    /**
     * Convierte BigDecimal a double (para compatibilidad temporal)
     * @param value Valor BigDecimal a convertir
     * @return double equivalente
     */
    public static double convertToDouble(BigDecimal value) {
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null");
        }
        return value.doubleValue();
    }

    /**
     * Convierte BigDecimal a Double (para compatibilidad temporal)
     * @param value Valor BigDecimal a convertir
     * @return Double equivalente, o null si value es null
     */
    public static Double convertToDoubleWrapper(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return convertToDouble(value);
    }

    /**
     * Valida que un double esté dentro del rango válido para BigDecimal
     * @param value Valor a validar
     * @return true si es válido, false en caso contrario
     */
    public static boolean isValidDouble(double value) {
        return !Double.isNaN(value) && !Double.isInfinite(value);
    }

    /**
     * Valida que un Double esté dentro del rango válido para BigDecimal
     * @param value Valor a validar
     * @return true si es válido, false en caso contrario
     */
    public static boolean isValidDouble(Double value) {
        return value != null && isValidDouble(value.doubleValue());
    }

    /**
     * Convierte double a BigDecimal con validación previa
     * @param value Valor double a convertir
     * @return BigDecimal con precisión de 2 decimales
     * @throws IllegalArgumentException si el valor no es válido
     */
    public static BigDecimal safeConvert(double value) {
        if (!isValidDouble(value)) {
            throw new IllegalArgumentException("Invalid double value: " + value);
        }
        return convert(value);
    }

    /**
     * Convierte Double a BigDecimal con validación previa
     * @param value Valor Double a convertir
     * @return BigDecimal con precisión de 2 decimales, o null si value es null
     * @throws IllegalArgumentException si el valor no es válido
     */
    public static BigDecimal safeConvert(Double value) {
        if (value == null) {
            return null;
        }
        return safeConvert(value.doubleValue());
    }
}


