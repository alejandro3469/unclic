package mx.com.smartbussiness.generic.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

/**
 * Tests funcionales mínimos para DoubleToBigDecimalConverter.
 * Cubren conversiones básicas para reportes de cobertura JaCoCo.
 */
class DoubleToBigDecimalConverterTest {

	@Test
	void testConvert_Double() {
		BigDecimal result = DoubleToBigDecimalConverter.convert(123.456);
		assertNotNull(result);
		assertEquals(new BigDecimal("123.46"), result);
	}

	@Test
	void testConvert_DoubleWrapper_Null() {
		BigDecimal result = DoubleToBigDecimalConverter.convert((Double) null);
		assertNull(result);
	}

	@Test
	void testConvert_DoubleWrapper_NotNull() {
		BigDecimal result = DoubleToBigDecimalConverter.convert(Double.valueOf(99.99));
		assertNotNull(result);
		// Verifica que se convirtió correctamente (puede tener redondeo)
		assertTrue(result.compareTo(new BigDecimal("99.98")) >= 0);
		assertTrue(result.compareTo(new BigDecimal("100.00")) <= 0);
	}

	@Test
	void testConvert_WithScale() {
		BigDecimal result = DoubleToBigDecimalConverter.convert(123.456, 3);
		assertNotNull(result);
		assertEquals(new BigDecimal("123.456"), result);
	}

	@Test
	void testConvertToDouble() {
		BigDecimal value = new BigDecimal("123.45");
		double result = DoubleToBigDecimalConverter.convertToDouble(value);
		assertEquals(123.45, result, 0.01);
	}

	@Test
	void testConvertToDouble_NullThrowsException() {
		assertThrows(IllegalArgumentException.class, () -> {
			DoubleToBigDecimalConverter.convertToDouble(null);
		});
	}

	@Test
	void testConvertToDoubleWrapper_Null() {
		Double result = DoubleToBigDecimalConverter.convertToDoubleWrapper(null);
		assertNull(result);
	}

	@Test
	void testIsValidDouble_Valid() {
		assertTrue(DoubleToBigDecimalConverter.isValidDouble(123.45));
	}

	@Test
	void testIsValidDoubleWrapper_Valid() {
		assertTrue(DoubleToBigDecimalConverter.isValidDouble(Double.valueOf(123.45)));
	}

	@Test
	void testSafeConvert_Valid() {
		BigDecimal result = DoubleToBigDecimalConverter.safeConvert(123.45);
		assertNotNull(result);
		assertEquals(new BigDecimal("123.45"), result);
	}
}
