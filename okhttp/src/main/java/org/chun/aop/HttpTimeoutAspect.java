package org.chun.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.chun.handler.HttpTimeoutHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HttpTimeoutAspect {


	@Around("@annotation(httpTimeout)")
	public Object around(ProceedingJoinPoint joinPoint, HttpTimeout httpTimeout) throws Throwable {

		try {

			int connectTimeout = httpTimeout.connectTimeout();
			int socketTimeout = httpTimeout.socketTimeout();

			// 切換timeout時間
			HttpTimeoutHolder.change(connectTimeout, socketTimeout);

			return joinPoint.proceed();
		} finally {
			HttpTimeoutHolder.clear();
		}
	}

}
