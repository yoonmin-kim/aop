package hello.aop.internalcall;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import hello.aop.internalcall.aop.CallLogAspect;

@Import(CallLogAspect.class)
@SpringBootTest
class CallServiceV2Test {

	@Autowired
	CallServiceV2 callServiceV2;

	@Test
	void external() {
		callServiceV2.external();
	}

}
