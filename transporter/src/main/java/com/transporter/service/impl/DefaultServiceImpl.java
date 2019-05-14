package com.transporter.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.util.CollectionUtils;

import com.transporter.dao.DefaultDao;
import com.transporter.exception.DatabaseException;
import com.transporter.model.AbstractIdDomain;
import com.transporter.model.Domain;
import com.transporter.model.IdDomain;
import com.transporter.service.DefaultService;
import com.transporter.utils.ClassUtils;


/**
 * @author SHARAN A
 */

public class DefaultServiceImpl extends ApplicationObjectSupport implements DefaultService {

	protected final Log logger = LogFactory.getLog(getClass());

	protected DefaultDao defaultDao;
	private Domain domain;

	public void setDefaultDao(DefaultDao defaultDao) {
		this.defaultDao = defaultDao;
	}

	public DefaultDao getDefaultDao() {
		return this.defaultDao;
	}

	public Domain createNewTransientDomain(String domainClassName) throws Exception {
		return createNewTransientDomain(domainClassName, false);
	}

	public Domain createNewTransientDomain(String domainClassName, Boolean isSearchMode) throws Exception {

		Domain newDomainObject;
		try {
			newDomainObject = (Domain) BeanUtils.instantiateClass(Class.forName(domainClassName));
		} catch (Exception e) {
			throw new Exception(e);
		}

		return newDomainObject;
	}
	
//	@Override
	@Transactional
	public Serializable saveOrUpdate(IdDomain idDomain) {
		try {
			Field field = idDomain.getClass().getDeclaredField("id");
			field.setAccessible(true);
			if (field.get(idDomain) != null) {
				updateDomain(idDomain);
			} else {
				saveDomain(idDomain);
			}
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage(), e);
		}
		return idDomain.getId();
	}	

	@Override
	@Transactional
	public Serializable saveDomain(Domain domain) {
		Serializable id = null;
		validateBeforeInit(domain);
		initSave(domain);
	    validateSave(domain);

	    //update rowVersion
		ClassUtils.setPropertyValue(domain, "rowVersion", 0);

	    id = getDefaultDao().saveDomain(domain);
	    afterSave(domain);

		return id;
	}

	@Override
	@Transactional
	public void saveAll(List<Domain> domains) {
		if (!CollectionUtils.isEmpty(domains)) {
			for (Domain domain : domains) {
				getDefaultDao().saveDomain(domain);
			}
		}

	}
	
    @Override
	public Domain initializeNestedPath(Domain domain, String nestedPathToInitialize) {
    	return getDefaultDao().initializeNestedPath(domain, nestedPathToInitialize);
    }

    @Override
	public Domain initializeNestedPaths(Domain domain, String[] nestedPathsToInitialize) {
    	return getDefaultDao().initializeNestedPaths(domain, nestedPathsToInitialize);
    }
    

	@Override
	public Domain loadDomain(Class<? extends Domain> domainClass, Serializable id) {
		return getDefaultDao().loadDomain(domainClass, id);
	}

	@Override
	public Domain loadDomain(Class<? extends Domain> domainClass, Serializable id, String nesteddPathToInitialize) {
		return getDefaultDao().loadDomain(domainClass, id, nesteddPathToInitialize);
	}

	@Override
	public Domain loadDomain(Class<? extends Domain> domainClass, Serializable id, String[] nesteddPathsToInitialize) {
		return getDefaultDao().loadDomain(domainClass, id, nesteddPathsToInitialize);
	}

	@Override
	public Domain get(Class<? extends Domain> domainClass, Serializable id) {
		return getDefaultDao().get(domainClass, id);
	}

	@Override
	public Domain get(Class<? extends Domain> domainClass, Serializable id, String nestedPathToInitialize) {
		return getDefaultDao().get(domainClass, id, nestedPathToInitialize);
	}

	@Override
	public Domain get(Class<? extends Domain> domainClass, Serializable id, String[] nestedPathsToInitialize) {
		return getDefaultDao().get(domainClass, id, nestedPathsToInitialize);
	}

	@Override
	public Domain getDomain(Class<? extends Domain> domainClass, Serializable id) {
		return getDefaultDao().getDomain(domainClass, id);
	}

	@Override
	public Domain getDomain(Class<? extends Domain> domainClass, Serializable id, String nestedPathToInitialize) {
		return getDefaultDao().getDomain(domainClass, id, nestedPathToInitialize);
	}

	@Override
	public Domain getDomain(Class<? extends Domain> domainClass, Serializable id, String[] nestedPathsToInitialize) {
		return getDefaultDao().getDomain(domainClass, id, nestedPathsToInitialize);
	}

	@Override
	public List<Domain> getAllDomain(Class<? extends Domain> domainClass) {
		List<Domain> allDomain = getDefaultDao().getAllDomain(domainClass);
		return allDomain;
	}

	@Override
	public List<Domain> getAllDomain(Class<? extends Domain> domainClass, String nestedPathToInitialize) {
		return getDefaultDao().getAllDomain(domainClass, nestedPathToInitialize);
	}

	@Override
	@Transactional
	public List<Domain> getAllDomain(Class<? extends Domain> domainClass, String[] nestedPathsToInitialize) {
		return getDefaultDao().getAllDomain(domainClass, nestedPathsToInitialize);
	}

	@Override
	@Transactional
	public void deleteDomain(Domain domain) {
		getDefaultDao().deleteDomain(domain);
	}

	@Override
	@Transactional
	public void deleteDomain(Class<? extends Domain> domainClazz, Long domainId) {
		getDefaultDao().deleteDomain(domainClazz, domainId);
	}
	
	@Override
	public void doInActiveDomain(Class<? extends Domain> domainClass, Long domainId) {
		getDefaultDao().doInActiveDomain(domainClass, domainId);
	}

	@Override
	@Transactional
	public void updateDomain(Domain domain) {
		validateBeforeInit(domain);
		initUpdate(domain);
	    validateUpdate(domain);
	    
	    //update rowVersion
	    Long id = (Long) ClassUtils.getPropertyValue(domain, "id");
	    if(domain instanceof AbstractIdDomain) {
		    Integer rowVersion = getRowVersionId(domain);
		    if(rowVersion == null) {
		    	rowVersion = 0;
		    }
			ClassUtils.setPropertyValue(domain, "rowVersion", ++rowVersion);
	    }

		getDefaultDao().updateDomain(domain);
	    afterUpdate(domain);
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	protected void initSave(Domain domain) {
		
	}

	protected void validateSave(Domain domain2) {
		// TODO Auto-generated method stub
	}

	protected void afterSave(Domain domain2) {
		// TODO Auto-generated method stub
	}

	protected void initUpdate(Domain domain2) {
		// TODO Auto-generated method stub
	}

	protected void validateUpdate(Domain domain2) {
		// TODO Auto-generated method stub
	}

	protected void validateBeforeInit(Domain domain2) {
		// TODO Auto-generated method stub
	}

	protected void afterUpdate(Domain domain2) {
		// TODO Auto-generated method stub
	}

	@Override
	public void evict(Domain domain) {
		getDefaultDao().evict(domain);
	}

	@Override
	public void evict(Collection<? extends Domain>domains)
	{
		for (Domain domain : domains) {
			evict(domain);
		}
	}
	
	protected void flush() {
		getDefaultDao().flush();		
	}

	@Override
	public List<Domain> findDomainByCriteriaMap(Class<? extends Domain> domainClass, Map<String, Object> searchCriteriaMap) {
		return getDefaultDao().findDomainByCriteriaMap(domainClass, searchCriteriaMap);
	}

	@Override
	public Integer getCountByDomainCriteriaMap(Class<? extends Domain> domainClass, Map<String, Object> searchCriteriaMap) {
		return getDefaultDao().getCountByDomainCriteriaMap(domainClass, searchCriteriaMap);
	}

	@Override
	public Long getMaxDomainId(Class domainClass) {
		return getDefaultDao().getMaxDomainId(domainClass);
	}

	@Override
	public Integer getRowVersionId(Class domainClass, Long id) {
		return getDefaultDao().getRowVersionId(domainClass, id);
	}

	@Override
	public Integer getRowVersionId(Domain domain) {
		return (Integer) ClassUtils.getPropertyValue(domain, "rowVersion");
	}
}
