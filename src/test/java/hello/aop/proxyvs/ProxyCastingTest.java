package hello.aop.proxyvs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;

public class ProxyCastingTest {

	@Test
	void jdkProxy() {
		MemberServiceImpl target = new MemberServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.setProxyTargetClass(false); // JDK동적 프록시

		// 프록시를 인터페이스로 캐스팅 성공
		MemberService memberService = (MemberService)proxyFactory.getProxy();

		// JDK 동적 프록시를 구현 클래스로 캐스팅 실패, ClassCastException 예외 발생
		assertThrows(ClassCastException.class, () -> {
			MemberServiceImpl memberServiceImpl = (MemberServiceImpl)memberService;
		});
	}

	@Test
	void cglibProxy() {
		MemberServiceImpl target = new MemberServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.setProxyTargetClass(true); // CGLIB 프록시

		// 프록시를 인터페이스로 캐스팅 성공
		MemberService memberService = (MemberService)proxyFactory.getProxy();

		// CGLIB 프록시를 구현 클래스로 캐스팅 성공
		MemberServiceImpl memberServiceImpl = (MemberServiceImpl)memberService;
	}
}
