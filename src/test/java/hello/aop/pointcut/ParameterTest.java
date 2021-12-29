package hello.aop.pointcut;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import hello.aop.member.MemberService;
import hello.aop.member.annotation.ClassAop;
import hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

	@Autowired
	MemberService memberService;

	@Test
	void success() {
		log.info("memberService Proxy={}", memberService.getClass());
		memberService.hello("helloA");
	}

	@Slf4j
	@Aspect
	static class ParameterAspect {

		@Pointcut("execution(* hello.aop.member..*.*(..))")
		private void allMember() {}

		// hello 메소드로 전달된 helloA라는 String 값을 가져올 수 있다
		@Around("allMember()")
		public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
			Object arg1 = joinPoint.getArgs()[0];
			log.info("[logArgs1]{}, arg={}", joinPoint.getSignature(), arg1);
			return joinPoint.proceed();
		}

		@Around("allMember() && args(arg,..)")
		public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
			log.info("[logArgs2]{}, arg={}", joinPoint.getSignature(), arg);
			return joinPoint.proceed();
		}

		@Before("allMember() && args(arg,..)")
		public void logArgs3(String arg) throws Throwable {
			log.info("[logArgs3] arg={}", arg);
		}

		// 스프링 컨테이너에 저장된 프록시 객체를 가져온다
		@Before("allMember() && this(obj)")
		public void thisArgs(JoinPoint joinPoint,MemberService obj) throws Throwable {
			log.info("[thisArgs]{}, obj={}", joinPoint.getSignature(), obj.getClass());
		}

		// 프록시 객체의 대상이 된 실제 객체를 가져온다
		@Before("allMember() && target(obj)")
		public void targetArgs(JoinPoint joinPoint,MemberService obj) throws Throwable {
			log.info("[targetArgs]{}, obj={}", joinPoint.getSignature(), obj.getClass());
		}

		// 어노테이션 정보도 가져올 수 있다
		@Before("allMember() && @target(annotation)")
		public void atTarget(JoinPoint joinPoint, ClassAop annotation) throws Throwable {
			log.info("[@target]{}, obj={}", joinPoint.getSignature(), annotation);
		}

		@Before("allMember() && @within(annotation)")
		public void atWithin(JoinPoint joinPoint, ClassAop annotation) throws Throwable {
			log.info("[@within]{}, obj={}", joinPoint.getSignature(), annotation);
		}

		// 어노테이션에 저장된 값도 가져올 수 있다
		@Before("allMember() && @annotation(annotation)")
		public void atAnnotation(JoinPoint joinPoint, MethodAop annotation) throws Throwable {
			log.info("[@annotation]{}, annotationValue={}", joinPoint.getSignature(), annotation.value());
		}
	}
}
