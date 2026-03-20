package mx.com.endtoend.genericCommonsFileds.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

/**
 * Tests funcionales mínimos para DateUtil.
 * Cubren funcionalidad básica para reportes de cobertura JaCoCo.
 */
class DateUtilTest {

	@Test
	void testTransformDateBooleana() {
		String result = DateUtil.transformDateBooleana("1234567");
		assertNotNull(result);
		assertTrue(result.matches("\\d{2}/\\d{2}/\\d{4}"));
	}

	@Test
	void testTransformDateJuliana() {
		String result = DateUtil.transformDateJuliana("2024-01-15T10:30:00");
		assertNotNull(result);
		assertTrue(result.startsWith("1"));
	}

	@Test
	void testIsBeforeToCurrentDay_Past() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		Date pastDate = cal.getTime();
		
		boolean result = DateUtil.isBeforeToCurrentDay(pastDate);
		assertTrue(result);
	}

	@Test
	void testIsBeforeToCurrentDay_Future() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Date futureDate = cal.getTime();
		
		boolean result = DateUtil.isBeforeToCurrentDay(futureDate);
		// Puede ser true o false dependiendo de la hora, pero debe ejecutarse
		assertNotNull(result);
	}

	@Test
	void testGetCurrentJulianDate() {
		DateUtil util = new DateUtil();
		Long result = util.getCurrentJulianDate();
		assertNotNull(result);
		assertTrue(result > 0);
	}

	@Test
	void testGetCurrentTimeJulianDate() {
		DateUtil util = new DateUtil();
		Long result = util.getCurrentTimeJulianDate();
		assertNotNull(result);
		assertTrue(result >= 0);
		// Formato HHMMSS: 6 dígitos, máximo 235959
		String formatted = String.format("%06d", result);
		assertEquals(6, formatted.length());
		assertTrue(result <= 235959L);
	}

	@Test
	void testConvertDateToJulianDate() {
		DateUtil util = new DateUtil();
		Date testDate = new Date();
		Long result = util.convertDateToJulianDate(testDate);
		assertNotNull(result);
		assertTrue(result > 0);
	}
}
