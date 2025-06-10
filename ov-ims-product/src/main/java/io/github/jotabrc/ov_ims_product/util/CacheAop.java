package io.github.jotabrc.ov_ims_product.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.jotabrc.ov_ims_product.config.RedisConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.StringJoiner;

@Component
@Aspect
@Slf4j
@AllArgsConstructor
public class CacheAop {

    private final RedisConfig redisConfig;
    private final ObjectMapper objectMapper;

    @Around("@annotation(io.github.jotabrc.ov_ims_product.util.Cache)")
    public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getName();
        log.info("Cache lookup started for {}", className);

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Cache cacheAnnotation = method.getAnnotation(Cache.class);
        Object[] args = joinPoint.getArgs();

        StringJoiner keyJoin = new StringJoiner(":");
        Object[] annotatedParameters = getAnnotationClassNames(cacheAnnotation);
        findClassesAndMethods(args, annotatedParameters, cacheAnnotation, keyJoin);
//        Arrays.stream(joinPoint.getArgs())
//                .forEach(arg -> {
//                    switch (arg) {
//                        case PageFilter p -> keyJoin
//                                .add("uuid")
//                                .add(p.getUuid())
//                                .add("category")
//                                .add(p.getCategory());
//                        case Pageable p -> keyJoin
//                                .add("pageSize")
//                                .add(String.valueOf(p.getPageSize()))
//                                .add("pageNumber")
//                                .add(String.valueOf(p.getPageNumber()))
//                                .add("offSet")
//                                .add(String.valueOf(p.getOffset()))
//                                .add("sort")
//                                .add(
//                                        p.getSort()
//                                                .stream()
//                                                .map(s -> s.getProperty() + ":" + s.getDirection())
//                                                .collect(Collectors.joining("-", ":", ":"))
//                                );
//                        case null, default -> {
//                        }
//                    }
//                });

        return executeCache(joinPoint, keyJoin);
    }

    private Object[] getAnnotationClassNames(Cache cacheAnnotation) {
        return Arrays.stream(cacheAnnotation.params())
                .flatMap(s -> Arrays.stream(s.split(":"), 0, 1))
                .toArray();
    }

    private void findClassesAndMethods(Object[] args, Object[] annotatedParameters, Cache cacheAnnotation, StringJoiner keyJoin) {
        Arrays.stream(args)
                .forEach(object -> Arrays.stream(annotatedParameters)
                        .forEach(annotation -> Arrays.stream(cacheAnnotation.params())
                                .forEach(string -> {
                                    if (string.startsWith(annotation.toString())) {
                                        invokeMethods(keyJoin, object, string);
                                    }
                                })
                        )
                );
    }

    private void invokeMethods(StringJoiner keyJoin, Object object, String string) {
        String[] names = getMethodsNames(string);
        for (int i = 1; i < names.length; i++) {
            try {
                Class<?> objClass = object.getClass();
                Method objMethod = objClass.getMethod(names[i]);
                objMethod.setAccessible(true);
                Object inv = objMethod.invoke(object);
                keyJoin.add(inv.toString());
            } catch (IllegalAccessException |
                     InvocationTargetException |
                     NullPointerException |
                     SecurityException |
                     NoSuchMethodException e) {
                log.error("Reflection failed for {}. Error: {}", string, e.getMessage());
            }
        }
    }

    private String[] getMethodsNames(String string) {
        return string.split(":");
    }

    private Object executeCache(ProceedingJoinPoint joinPoint, StringJoiner keyJoin) throws Throwable {
        Object cache = redisConfig.redisTemplate().opsForValue().get(keyJoin.toString());
        if (cache != null) {
            log.info("Found cache for key {}", keyJoin);
        } else {
            log.info("Saving new cache with key {}", keyJoin);
            cache = joinPoint.proceed();
            redisConfig.redisTemplate().opsForValue().setIfAbsent(keyJoin.toString(), cache);
        }
        return cache;
    }
}
