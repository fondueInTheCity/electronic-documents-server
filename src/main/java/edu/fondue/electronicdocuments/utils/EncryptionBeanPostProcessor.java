package edu.fondue.electronicdocuments.utils;

import edu.fondue.electronicdocuments.utils.listeners.EncryptionListener;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
@RequiredArgsConstructor
public class EncryptionBeanPostProcessor implements BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EncryptionBeanPostProcessor.class);

    private final EncryptionListener encryptionListener;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof EntityManagerFactory) {
            SessionFactoryImpl sessionFactory = ((EntityManagerFactory) bean).unwrap(SessionFactoryImpl.class);
            EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
            registry.appendListeners(EventType.POST_LOAD, encryptionListener);
            registry.appendListeners(EventType.PRE_INSERT, encryptionListener);
            registry.appendListeners(EventType.PRE_UPDATE, encryptionListener);
            logger.info("Encryption has been successfully set up");
        }
        return bean;
    }
}
