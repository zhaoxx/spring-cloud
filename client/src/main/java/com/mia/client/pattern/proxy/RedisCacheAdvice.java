package com.mia.client.pattern.proxy;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class RedisCacheAdvice {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private static RedisTemplate<String, Object> redisTemplate;

    @Pointcut("@annotation(com.mia.client.pattern.proxy.CacheCable)")
    public void redisCachePointcut() {
    }

    @Around(value = "redisCachePointcut()")
    public Object get(final ProceedingJoinPoint pJointPoint) throws Throwable {
        CacheCable redisCache = getAnnotation(pJointPoint);
        if (redisCache == null) {
            return pJointPoint.proceed();
        }

        //校验参数
        boolean check = checkParams(redisCache);
        if (!check) {
            return pJointPoint.proceed();
        }

        Method method = null;
        try {
            method = getMethod(pJointPoint); //获取方法名
        } catch (Exception e) {
            logger.error("缓存Aop:获取的是方法对象异常", e);
            return pJointPoint.proceed();
        }

        String spelValue = null;
        try {
            spelValue = parseSpel(redisCache.key(), method,
                    pJointPoint.getArgs()); //获取组合key
        } catch (Exception e) {
            logger.error("获取缓存的key对应参数值异常:key=" + redisCache.key(), e);
            return pJointPoint.proceed();
        }


        if (StringUtils.isBlank(spelValue)) {
            logger.error("获取缓存的key对应参数值[:key=" + redisCache.key() + "]为空");
            return pJointPoint.proceed();
        }

        String[] nameSpace = redisCache.value(); //缓存值
        long expiration = redisCache.expiration(); //redis缓存过期时间

        //从redis缓存获取
        Object value = null;
        try {
            value = redisTemplate.opsForValue().get(spelValue);
        } catch (Exception e) {
            logger.error("redis缓存读取数据时异常 key=" + spelValue, e);
            return pJointPoint.proceed();
        }


        logger.debug("缓存注解CacheCable查询数据为空，准备执行目标方法，" + method.getName() + "获取缓存:spelValue=" + spelValue + " ,nameSpace=" + redisCache.value() + " ,expiration=" + redisCache.expiration());
        value = pJointPoint.proceed();
        if (value != null) {
            try {
                redisTemplate.opsForValue().set(spelValue, value, expiration);
            } catch (Exception e) {
                logger.error("缓存注解CacheCable处理[结果存入缓存]异常， key=" + spelValue, e);
            }
        }

        return value;
    }

    /**
     * 检验注解参数
     *
     * @param redisCache
     * @return true:参数合法；false:参数不合法
     */
    private boolean checkParams(CacheCable redisCache) {
        String key = redisCache.key();
        String[] nameSpace = redisCache.value();
        if (StringUtils.isBlank(key) ||
                nameSpace.length == 0) {
            return false;
        }
        boolean allParamsNull = true;    //注解参数中至少包含一个非空字符串
        for (String value : nameSpace) {
            if (StringUtils.isNotBlank(value)) {
                allParamsNull = false;
                break;
            }
        }
        if (allParamsNull) {
            return false;
        }
        return true;
    }

    private CacheCable getAnnotation(final ProceedingJoinPoint pJointPoint) throws Throwable {
        final Signature sig = pJointPoint.getSignature();
        if (!(sig instanceof MethodSignature)) {
            throw new NoSuchMethodException("This annotation is only valid on a method.");
        }
        final MethodSignature msig = (MethodSignature) sig;
        final Object target = pJointPoint.getTarget();
        Method method = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        return method.getAnnotation(CacheCable.class);
    }

    /**
     * 获取的是方法对象
     *
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    @SuppressWarnings("rawtypes")
    public Method getMethod(ProceedingJoinPoint pjp) throws SecurityException, NoSuchMethodException {
        Method method = null;
        Object[] args = pjp.getArgs();
        Class[] argTypes = new Class[pjp.getArgs().length];    // 获取参数的类型
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        method = pjp.getTarget().getClass()
                .getMethod(pjp.getSignature().getName(), argTypes);

        return method;
    }

    /**
     * 获取缓存的key对应参数值 (SPEL表达式)
     *
     * @param key
     * @param method
     * @param args
     * @return
     */
    private String parseSpel(String key, Method method, Object[] args) throws Exception {
        ExpressionParser parser = new SpelExpressionParser();
        LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = parameterNameDiscoverer.getParameterNames(method);
        // 无入参情况直接返回原始key
        if (paraNameArr == null || paraNameArr.length == 0) {
            return key;
        }
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paraNameArr.length; i++) {
            // 判断目标方法参数
            if (args[i] == null) {
                throw new Exception("缓存目标方法对应参数值:" + paraNameArr[i] + "=null");
            }
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }

}
