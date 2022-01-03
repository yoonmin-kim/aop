package hello.aop.internalcall;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CallServiceV2 {

	// private final ApplicationContext applicationContext;
	private final ObjectProvider<CallServiceV2> callServiceProvider;

	public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
		this.callServiceProvider = callServiceProvider;
	}

	public void external() {
		// CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
		CallServiceV2 callServiceV2 = callServiceProvider.getObject();
		log.info("callServiceV2={}", callServiceV2.getClass());
		callServiceV2.internal(); // 내부 메서드 호출(this.internal())
	}

	public void internal() {
		log.info("call internal");
	}
}
