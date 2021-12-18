package hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class PointCuts {

	@Pointcut("execution(* hello.aop.order..*(..))")
	public void allOrder(){} // pointcut signature

	// 클래스 이름패턴이 *Service
	@Pointcut("execution(* *..*Service.*(..))")
	public void allService(){}

	@Pointcut("allOrder() && allService()")
	public void orderAndService() {}
}
