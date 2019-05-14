package com.transporter.dao.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.TransientObjectException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.transporter.constants.AppConstants;
import com.transporter.dao.DefaultDao;
import com.transporter.model.Domain;
import com.transporter.utils.ClassUtils;

/**
 * @author SHARAN A
 */

@Transactional
public class DefaultDaoImpl extends AbstractDefaultDaoImpl implements DefaultDao {

	protected Query createQuery(String queryString) {
		Query query = getSession().createQuery(queryString);
		
		String maxRecords = getParameter(AppConstants.PARAMETER_MAXIMUM_RECORDS_ALLOWED_TO_FETCH);
		query.setMaxResults(Integer.valueOf(maxRecords));
		return query;
	}

	@Override
	public Domain get(Class<? extends Domain> domainClass, Serializable id) {
		Criteria criteria = createCriteria(domainClass);
		criteria.add(Restrictions.eq("id", id));
		return (Domain) criteria.list();
	}

	@Override
	public Domain get(Class<? extends Domain> domainClass, Serializable id, String nestedPathToInitialize) {
		String[] nestedPathsToInitialize = convertStringArray(nestedPathToInitialize);
		return get(domainClass, id, nestedPathsToInitialize);
	}

	@Override
	public Domain get(Class<? extends Domain> domainClass, Serializable id, String[] nestedPathsToInitialize) {

		return initializeNestedPathsOfDomain(get(domainClass, id), nestedPathsToInitialize);
		
	}

	protected Criteria createCriteria(Class<?> persistentClass) {
		Criteria criteria = getSession().createCriteria(persistentClass);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		String maxRecords = getParameter(AppConstants.PARAMETER_MAXIMUM_RECORDS_ALLOWED_TO_FETCH);
		criteria.setMaxResults(Integer.valueOf(maxRecords));
		
		return criteria;
	}

	protected Criteria createCriteria(Class<?> persistentClass, String alias) {
		Criteria criteria = getSession().createCriteria(persistentClass, alias);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		String maxRecords = getParameter(AppConstants.PARAMETER_MAXIMUM_RECORDS_ALLOWED_TO_FETCH);
		criteria.setMaxResults(Integer.valueOf(maxRecords));

		return criteria;
	}

	@Override
	public Serializable saveDomain(Domain domain) {
		return getHibernateTemplate().save(domain);
	}

	@Override
	public Domain loadDomain(Class<? extends Domain> domainClass, Serializable id) {

		return (Domain) getHibernateTemplate().load(domainClass, id);
	}

	@Override
	public Domain loadDomain(Class<? extends Domain> domainClass, Serializable id, String nestedPathToInitialize) {
		String[] nestedPathsToInitialize = convertStringArray(nestedPathToInitialize);
		return loadDomain(domainClass, id, nestedPathsToInitialize);
	}

	@Override
	public Domain loadDomain(Class<? extends Domain> domainClass, Serializable id, String[] nestedPathsToInitialize) {
		return initializeNestedPathsOfDomain(loadDomain(domainClass, id), nestedPathsToInitialize);
	}

	@Override
	public Domain getDomain(Class<? extends Domain> domainClass, Serializable id) {
		return (Domain) getHibernateTemplate().get(domainClass, id);
	}

	@Override
	public Domain getDomain(Class<? extends Domain> domainClass, Serializable id, String nestedPathToInitialize) {
		String[] nestedPathsToInitialize = convertStringArray(nestedPathToInitialize);
		
		return getDomain(domainClass, id, nestedPathsToInitialize);
	}

	@Override
	public Domain getDomain(Class<? extends Domain> domainClass, Serializable id, String[] nestedPathsToInitialize) {
		return initializeNestedPathsOfDomain(getDomain(domainClass, id), nestedPathsToInitialize);
	}

	@Override
	public List<Domain> getAllDomain(Class<? extends Domain> domainClass) {
		Criteria criteria = createCriteria(domainClass);
		List list = criteria.list();
		//return (List<Domain>) criteria.list();
		return list;
	}

	@Override
	public List<Domain> getAllDomain(Class<? extends Domain> domainClass, String nestedPathToInitialize) {
		String[] nestedPathsToInitialize = convertStringArray(nestedPathToInitialize);
		return getAllDomain(domainClass, nestedPathsToInitialize);
	}

	@Override
	public List<Domain> getAllDomain(Class<? extends Domain> domainClass, String[] nestedPathsToInitialize) {
		return initializeNestedPathsOfListResults(getAllDomain(domainClass), nestedPathsToInitialize);
	}

	@Override
	public void deleteDomain(Domain domain) {
		getHibernateTemplate().delete(domain);
	}

	@Override
	public void deleteDomain(Class<? extends Domain> domainClass, Long domainId) {
		Criteria criteria = createCriteria(domainClass);
		criteria.add(Restrictions.eq("id", domainId));
		Domain domain = (Domain) firstResult(criteria.list());
		
		getHibernateTemplate().delete(domain);
	}

	@Override
	public void doInActiveDomain(Class<? extends Domain> domainClass, Long domainId) {
		Criteria criteria = createCriteria(domainClass);
		criteria.add(Restrictions.eq("id", domainId));
		Domain domain = (Domain) firstResult(criteria.list());
		
		ClassUtils.setPropertyValue(domain, "isActive", false);
		getHibernateTemplate().update(domain);
	}

	@Override
	public void updateDomain(Domain domain) {
		getHibernateTemplate().update(domain);

	}

	public Session getSession() {
		try {
			return getHibernateTemplate().getSessionFactory().getCurrentSession();
		} catch (Exception e) {
			return getHibernateTemplate().getSessionFactory().openSession();
		}
	}

	private String[] convertStringArray(String value) {
		String[] nestedPathsToInitialize = null;
		if (value != null) {
			nestedPathsToInitialize = new String[1];
			nestedPathsToInitialize[0] = value;
		}
		return nestedPathsToInitialize;
	}
	
    @Override
	public void evict(Domain domain) {
		//Assert.isTrue(supports(domain));
    	getHibernateTemplate().evict(domain);
    }
    
    @Override
    public void evict(Collection<? extends Domain>domains)
    {
    	for (Domain domain : domains) {
			evict(domain);
		}
    }
    
    /* (non-Javadoc)
     * @see com.fb.gfw.dal.DefaultDao#flush()
     */
    @Override
    public void flush() {
    	getHibernateTemplate().flush();
    }
    
    @Override
	public Domain initializeNestedPath(Domain domain, String nestedPathToInitialize) {
        Assert.notNull(domain);
        Assert.notNull(nestedPathToInitialize);

        try {
            getHibernateTemplate().lock(domain, LockMode.NONE);
        } catch (DataAccessException dae) {
            if ( !(dae.getCause() instanceof TransientObjectException)) {
                throw dae;
            }
        }
        
        initializeNestedPathsOfDomain(domain, nestedPathToInitialize);
        
        return domain;
    }

    @Override
	public Domain initializeNestedPaths(Domain domain, String[] nestedPathsToInitialize) {
        Assert.notNull(domain);

        try {
            getHibernateTemplate().lock(domain, LockMode.NONE);
        } catch (DataAccessException dae) {
            if ( !(dae.getCause() instanceof TransientObjectException)) {
                throw dae;
            }
        }
        
        initializeNestedPathsOfDomain(domain, nestedPathsToInitialize);
        
        
        return domain;
    }
    
    @Override
    public List<Domain> findDomainByCriteriaMap(Class<? extends Domain> domainClass, Map<String, Object> searchCriteriaMap) {
    	
    	Criteria criteria = createCriteria(domainClass);
    	if(!CollectionUtils.isEmpty(searchCriteriaMap)) {
    		for (String property : searchCriteriaMap.keySet()) {
    			if(searchCriteriaMap.get(property) != null) {
        			criteria.add(Restrictions.eq(property, searchCriteriaMap.get(property)));
    			}
			}
    	}
    	
    	return criteria.list();
    	
    }
    
    @Override
    public Integer getCountByDomainCriteriaMap(Class<? extends Domain> domainClass, Map<String, Object> criteriaMap) {
    	
    	Map<String, Object> searchCriteriaMap = null;
    	Map<String, Object> searchCriteriaExcludeMap = null;
    	
    	if(!CollectionUtils.isEmpty(criteriaMap)) {
    		if(criteriaMap.get(AppConstants.EXCLUDE_PROPERTIES) != null) {
    			searchCriteriaExcludeMap = (Map) criteriaMap.get(AppConstants.EXCLUDE_PROPERTIES);
    			criteriaMap.remove(AppConstants.EXCLUDE_PROPERTIES);
    		}
    		searchCriteriaMap = criteriaMap;
    	}
    	
    	Criteria criteria = createCriteria(domainClass);
    	
    	if(!CollectionUtils.isEmpty(searchCriteriaMap)) {
    		for (String property : searchCriteriaMap.keySet()) {
    			if(searchCriteriaMap.get(property) != null) {
        			criteria.add(Restrictions.eq(property, searchCriteriaMap.get(property)));
    			}
			}
    	}
    	if(!CollectionUtils.isEmpty(searchCriteriaExcludeMap)) {
    		for (String property : searchCriteriaExcludeMap.keySet()) {
    			if(searchCriteriaExcludeMap.get(property) != null) {
        			criteria.add(Restrictions.ne(property, searchCriteriaExcludeMap.get(property)));
    			}
			}
    	}
		criteria.setProjection(Projections.rowCount());

		List list = criteria.list();
		
		Object ret = new Long(0);
		
		if(!CollectionUtils.isEmpty(list)) {
			ret = criteria.list().get(0);
		}
    	
    	return Integer.valueOf(((Long)ret).intValue());
    	
    }
    
    @Override
    public Long getMaxDomainId(Class domainClass) {
    	
    	Query query = createQuery("select max(domain.id) from " + domainClass.getSimpleName() + " domain ");
    			
    	return (Long) firstResult(query.list());
    }
    
    @Override
    public Integer getRowVersionId(Class domainClass, Long id) {
    	
    	Query query = createQuery("select domain.rowVersion from " + domainClass.getSimpleName() + " domain where domain.id = "+id+"");
    			
    	return (Integer) firstResult(query.list());
    }
    
    public Object firstResult(Collection collection) {
        if (collection.size() == 0) {
            return null;
        }
        Object object = collection.iterator().next();
        if(object == null) {
        	object = 0L;
        }
        return object;
    }

	private String getParameter(String parameterName) {
		Query query = null;
		
		String hqlQuery = "SELECT parameter.value FROM ParameterModel parameter where parameter.parameterName=:parameterName";
		
		query = getSession().createQuery(hqlQuery);
		
		query.setParameter("parameterName", parameterName);

		String paramValue = (String) query.uniqueResult();;
		
		if(!StringUtils.hasText(paramValue) || Integer.valueOf(paramValue) < 10) {
			paramValue = "500";
		}

		return paramValue;
		
	}

}
