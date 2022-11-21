package med.voll.api;

import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiApplicationTests {

	@Test
	void contextLoads() {
		String string = "Pedro é gato.";
		Assert.doesNotContain(string, "Daniel");
	}

}
