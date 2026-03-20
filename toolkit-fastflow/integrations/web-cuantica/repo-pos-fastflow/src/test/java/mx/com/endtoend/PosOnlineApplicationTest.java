package mx.com.endtoend;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Test simple para demostrar JaCoCo.
 * Este test siempre pasa y cubre el método configure de PosOnlineApplication.
 */
class PosOnlineApplicationTest {

	@Test
	void testConfigure() {
		// Test simple que siempre pasa
		PosOnlineApplication app = new PosOnlineApplication();
		SpringApplicationBuilder builder = new SpringApplicationBuilder();
		
		SpringApplicationBuilder result = app.configure(builder);
		
		// Verifica que retorna algo (siempre pasa)
		assertNotNull(result);
	}
}
