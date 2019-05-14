package com.transporter.dao.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.collection.internal.PersistentMap;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.PropertyAccessorUtils;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.transporter.dao.HibernateInitializer;


/**
 * @author SHARAN A
 */
public class BeanWrapperHibernateInitializer implements HibernateInitializer {

    protected Log logger = LogFactory.getLog(BeanWrapperHibernateInitializer.class); 
    
    private final HibernateTemplate hibernateTemplate;
    //private BeanWrapper beanWrapper;

    public BeanWrapperHibernateInitializer(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
        //this.beanWrapper = new BeanWrapperImpl(false);
    }

	@Override
	public boolean isInitialized(Object object) {
        return Hibernate.isInitialized(object);
	}

    /**
     * @see com.ajahsma.carservice.dao.HibernateInitializer#initialize(java.lang.Object)
     */
    @Override
	public void initialize(final Object proxy) {
        Assert.notNull(proxy);
        
        if (proxy instanceof HibernateProxy ) {
            hibernateTemplate.lock(proxy, LockMode.NONE);
            hibernateTemplate.initialize(proxy);
        } else if (PersistentMap.class.isAssignableFrom(proxy.getClass())) {
            Object owner = ((PersistentMap)proxy).getOwner();
            hibernateTemplate.lock(owner, LockMode.NONE);
            hibernateTemplate.initialize(proxy);
        } else if (PersistentCollection.class.isAssignableFrom(proxy.getClass())) {
            Object owner = ((PersistentCollection) proxy).getOwner();
            hibernateTemplate.lock(owner, LockMode.NONE);
            hibernateTemplate.initialize(proxy);
        } 
        
        return;
    }    

    /**
     * @see com.fb.gfw.dal.HibernateInitializer#initialize(java.lang.Object, java.lang.String)
     */
    @Override
	@SuppressWarnings("rawtypes")
	public void initialize(final Object object, final String nestedPathToInitialize) {
        Assert.notNull(object);
        Assert.hasText(nestedPathToInitialize);
        
		if (List.class.isAssignableFrom(object.getClass())) {
			List list = (List) object;

			if (!CollectionUtils.isEmpty(list)) {
				for (Object listObject : list) {
					if (listObject != null) {
						initialize(listObject, nestedPathToInitialize);
					}
				}
			}
			return;
		}
        if (Collection.class.isAssignableFrom(object.getClass())) {
            Iterator iter = ((Collection)object).iterator();
            while (iter.hasNext()) {
                initialize(iter.next(), nestedPathToInitialize); 
            }
            return;
        } else if (Map.class.isAssignableFrom(object.getClass())) {
            Iterator iter = ((Map)object).values().iterator();
            while (iter.hasNext()) {
                initialize(iter.next(), nestedPathToInitialize); 
            }
            return;
        }

        int pos = PropertyAccessorUtils.getFirstNestedPropertySeparatorIndex(nestedPathToInitialize);
        if (pos != -1) {
            // handle nested properties recursively
            String nestedProperty = nestedPathToInitialize.substring(0, pos);

            //beanWrapper.setsetWrappedInstance(object);
            Object nestedPropertyValue = PropertyAccessorFactory.forBeanPropertyAccess(object).getPropertyValue(nestedProperty);
            if (nestedPropertyValue != null) {
                if (!isInitialized(nestedPropertyValue)) {
                    initialize(nestedPropertyValue);
                }    
                // call initialize method again with next nested path to initialize
                String nextNestedPath = nestedPathToInitialize.substring(pos + 1);
                initialize(nestedPropertyValue, nextNestedPath);
            }     
        } else {
            //beanWrapper.setWrappedInstance(object);
            Object nestedPropertyValue = PropertyAccessorFactory.forBeanPropertyAccess(object).getPropertyValue(nestedPathToInitialize);
            if (nestedPropertyValue != null) {
                if (!isInitialized(nestedPropertyValue)) {
                    // initialize nested property value
                    initialize(nestedPropertyValue);
                }    
            }     
        }
        
        return;
    }    

    /**
     * @see com.fb.gfw.dal.HibernateInitializer#initialize(java.lang.Object, java.lang.String[])
     */
    @Override
	public void initialize(final Object object, final String[] nestedPathsToInitialize) {
        Assert.notNull(object);
        
        if (nestedPathsToInitialize != null && nestedPathsToInitialize.length > 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("Nested paths to initialize = " + ToStringBuilder.reflectionToString(nestedPathsToInitialize));
            }
            for (int i=0; i < nestedPathsToInitialize.length ; i++) {
                initialize(object, nestedPathsToInitialize[i]);
            }
        } 
        
        return;
    }

}
