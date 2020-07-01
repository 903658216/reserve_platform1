package com.jh.event.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * Spring框架中 ->IoC -> Bean工厂
 * Map<beanName,BeanDefinition>
 *
 * 手动将Bean注册到IoC容器中
 *
 * Bean ->BeanDefinition
 *
 */
@Component
public class SpringContextUtil implements BeanDefinitionRegistryPostProcessor {

    /**
     * 注册bean的核心对象
     */
    private  BeanDefinitionRegistry beanDefinitionRegistry;

    /**
     * 自定义工具方法，注册Bean对象
     * @param beanName
     * @param bean
     */
    public  void  registryBean(String beanName,Object bean){

        //将bean封装成BeanDefinition对象
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(bean.getClass(), new Supplier() {
            @Override
            public Object get() {
                return bean;
            }
        });

        //将BeanDefinition注册到Spring容器中
        beanDefinitionRegistry.registerBeanDefinition(beanName,beanDefinitionBuilder.getBeanDefinition());

    }


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

        this.beanDefinitionRegistry = registry;

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
