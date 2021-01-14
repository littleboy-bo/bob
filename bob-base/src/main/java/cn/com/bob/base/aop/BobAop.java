package cn.com.bob.base.aop;


import cn.com.bob.base.aop.annotation.BobService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author songbo
 * @version V1.0
 * @date 2020-01-10
 * bob-learn  自定义注解 AOP 拦截
 * 顺序  doAround -->  doBefore -->  target PROCESS -->  doAround -->  doAfter -->  doAfterReturning
 */
@Aspect
@Component
public class BobAop {
    private final static Logger log = LoggerFactory.getLogger(BobAop.class);

    @Pointcut(value = "@annotation(cn.com.bob.learn.base.aop.annotation.BobService)")
    public void joinPointExpression(){

    }

    /**
     * 前置通知（目标方法调用前被调用）
     * @param joinPoint
     */
    //@Before("joinPointExpression()")
    public void doBefore(JoinPoint joinPoint){
        log.debug("BobAop.doBefore start ...");


        log.debug("BobAop.doBefore end ...");
    }

    @Around("@annotation(bobService)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint, BobService bobService) throws Throwable {
        log.debug("BobAop.doAround start ...");

        //调用方法的参数
        Object [] args = proceedingJoinPoint.getArgs();
        //调用的方法名
        String method = proceedingJoinPoint.getSignature().getName();
        //获取目标对象
        Object target = proceedingJoinPoint.getTarget();

        log.debug("调用方法的参数 args = " + args);
        log.debug("调用的方法名 method = " + method);
        log.debug("获取目标对象 args = " + target);

        //调用proceed方法，就会触发切入点方法执行
        //执行完方法的返回值
        try {
            Object result = proceedingJoinPoint.proceed();
            log.debug("BobAop.doAround end ...");
            return result;
        }catch (Throwable t){
            throw t;
        }


    }

    /**
     * 后置返回通知
     * @param joinPoint
     */
    //@AfterReturning(value = "joinPointExpression()")
    public void doAfterReturning(JoinPoint joinPoint){
        log.debug("BobAop.doAfterReturning start ...");

        log.debug("BobAop.doAfterReturning end ...");

    }

    /**
     * 后置返回通知
     * 注意：
     *      如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     *      如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     *       returning:限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，
     *       对于returnning对应的通知方法参数为String类型的将匹配任何目标返回值
     * @param retStr
     */
    //@AfterReturning(value = "joinPointExpression()", returning = "retStr", argNames = "retStr")
    public void doAfterReturning1(String retStr){
        log.debug("BobAop.doAfterReturning1 start ...");

        log.debug("doAfterReturning1 返回值= " + retStr);

        log.debug("BobAop.doAfterReturning1 end ...");

    }

    //@AfterReturning(value = "joinPointExpression()", returning = "object")
    public void doAfterReturning2(JoinPoint joinPoint, Object object){
        log.debug("BobAop.doAfterReturning2 start ...");

        log.debug("doAfterReturning2 返回值= " + object);

        log.debug("BobAop.doAfterReturning2 end ...");

    }

    /**
     * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
     * @param joinPoint
     */
    //@After("joinPointExpression()")
    public void doAfter(JoinPoint joinPoint){
        log.debug("BobAop.doAfter start ...");


        log.debug("BobAop.doAfter end ...");

    }

}
