package cn.com.bob.learn.service.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author songbo
 * @version V1.0
 * @date 2020-01-10
 * bob-learn  自定义注解 AOP 拦截
 * 顺序  doAround -->  doBefore -->  target PROCESS -->  doAround -->  doAfter -->  doAfterReturning
 */
@Aspect
public class BobAop {
    private final static Logger log = LoggerFactory.getLogger(BobAop.class);

    @Pointcut("execution(* cn.com.bob.learn.service.model.BobModel.getMessage())")
    public void joinPointExpression(){
    }

    /**
     * 后置返回通知
     * @param joinPoint
     */
    @AfterReturning(value = "joinPointExpression()")
    public void doAfterReturning(JoinPoint joinPoint){
        System.out.println("我的AOP实例");
    }

}
