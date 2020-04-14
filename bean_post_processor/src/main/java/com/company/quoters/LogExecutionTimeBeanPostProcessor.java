package com.company.quoters;

import com.company.LogExecutionTime;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogExecutionTimeBeanPostProcessor implements BeanPostProcessor {
    private Map<Class, List<Method>> map = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            LogExecutionTime annotation = method.getAnnotation(LogExecutionTime.class);
            if (annotation != null) {
                String methodName = method.getName();
                if (!map.containsKey(beanClass)) {
                    map.put(beanClass, new ArrayList<Method>());
                }
                List<Method> methodList = map.get(beanClass);
                methodList.add(method);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(final Object bean, String beanName) throws BeansException {
        final Class<?> beanClass = getBeanClass(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(),
                    new InvocationHandler() {
                        @Override
                        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                            ArrayList<Method> methodList = (ArrayList<Method>) map.get(beanClass);
                            System.out.println(method.getAnnotation(LogExecutionTime.class));
                            if (methodList.contains(method)) {
                                System.out.println(method.getName());
                                Object retVal = method.invoke(bean, objects);
                                return retVal;
                            }
                            return bean;
                        }
                    });
        }
        return bean;
    }

    private Class getBeanClass(String beanName) {
        Class result = null;
        for (Class clazz : map.keySet()) {
            if (clazz.getName().equals(beanName)) {
                result = clazz;
                break;
            }
        }
        return result;
    }
}
