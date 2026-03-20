package mx.com.endtoend.genericCommonsFileds.utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests funcionales mínimos para StringUtil.
 * Cubren funcionalidad básica para reportes de cobertura JaCoCo.
 */
class StringUtilTest {

	@Test
	void testCleanString_Basic() {
		String result = StringUtil.cleanString("  hola mundo  ", 20);
		assertNotNull(result);
		assertEquals("hola mundo", result);
	}

	@Test
	void testCleanString_NullReturnsEmpty() {
		assertEquals("", StringUtil.cleanString(null, 10));
	}

	@Test
	void testCleanString_NullSizeReturnsTrimmed() {
		assertEquals("hola", StringUtil.cleanString("  hola  ", null));
		assertEquals("hola", StringUtil.cleanString("  hola  ", 0));
	}

	@Test
	void testCleanString_Truncate() {
		String result = StringUtil.cleanString("texto muy largo que se corta", 10);
		assertNotNull(result);
		assertEquals(9, result.length());
	}

	@Test
	void testCleanStringToQuery() {
		StringUtil util = new StringUtil();
		String result = util.cleanStringToQuery("test%SELECT FROM");
		assertNotNull(result);
		// Verifica que se limpiaron los caracteres especiales y comandos SQL
		assertTrue(result.contains("TEST"));
		assertTrue(!result.contains("%"));
		assertTrue(!result.contains("SELECT"));
	}

	@Test
	void testCompleteLengthToWarehouseCode() {
		StringUtil util = new StringUtil();
		String result = util.completeLengthToWarehouseCode("ABC");
		assertNotNull(result);
		assertEquals(12, result.length());
	}

	@Test
	void testAutocompleteSpace_Right() {
		StringUtil util = new StringUtil();
		String result = util.autocompleteSpace("test", 10, true);
		assertNotNull(result);
		assertEquals(10, result.length());
		assertEquals("test", result.substring(0, 4));
	}

	@Test
	void testAutocompleteSpace_Left() {
		StringUtil util = new StringUtil();
		String result = util.autocompleteSpace("test", 10, false);
		assertNotNull(result);
		assertEquals(10, result.length());
		assertEquals("test", result.substring(6));
	}

	@Test
	void testCleanAccent() {
		StringUtil util = new StringUtil();
		String result = util.cleanAccent("áéíóú");
		assertNotNull(result);
		assertEquals("aeiou", result);
	}

	@Test
	void testCleanAccent_NullReturnsEmpty() {
		StringUtil util = new StringUtil();
		assertEquals("", util.cleanAccent(null));
	}
}
