package com.transporter.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.transporter.model.Domain;


/**
 * @author SHARAN A
 */

public interface DefaultDao {

	Serializable saveDomain(Domain domain);
	
	Domain loadDomain(Class<? extends Domain> domainClass, Serializable id);
	
	Domain loadDomain(Class<? extends Domain> domainClass, Serializable id, String nestedPathToInitialize);
	
	Domain loadDomain(Class<? extends Domain> domainClass, Serializable id, String[] nestedPathsToInitialize);
	
	Domain getDomain(Class<? extends Domain> domainClass, Serializable id);

	Domain getDomain(Class<? extends Domain> domainClass, Serializable id, String nestedPathToInitialize);

	Domain getDomain(Class<? extends Domain> domainClass, Serializable id, String[] nestedPathsToInitialize);

	public List<Domain> getAllDomain(Class<? extends Domain> domainClass);
	
	public List<Domain> getAllDomain(Class<? extends Domain> domainClass, String nestedPathToInitialize);
	
	public List<Domain> getAllDomain(Class<? extends Domain> domainClass, String[] nestedPathsToInitialize);
	
	public Domain get(Class<? extends Domain> domainClass, Serializable id);

	public Domain get(Class<? extends Domain> domainClass, Serializable id, String nestedPathToInitialize);

	public Domain get(Class<? extends Domain> domainClass, Serializable id, String[] nestedPathsToInitialize);

	void deleteDomain(Domain domain);
	
	void deleteDomain(Class<? extends Domain> domainClazz, Long domainId);
	
	public void doInActiveDomain(Class<? extends Domain> domainClass, Long domainId);

	void updateDomain(Domain domain);
	
    void evict(Domain domain);
    void evict(Collection<? extends Domain>domain);
    
    Domain initializeNestedPath(Domain domain, String nestedPathToInitialize);

    Domain initializeNestedPaths(Domain domain, String[] nestedPathsToInitialize);
    
    /**
     * Flushes the current SQL statements. See <code>HibernateTemplate.flush()</code>
     */
    void flush();

    public List<Domain> findDomainByCriteriaMap(Class<? extends Domain> domainClass, Map<String, Object> searchCriteriaMap);
    
    public Integer getCountByDomainCriteriaMap(Class<? extends Domain> domainClass, Map<String, Object> searchCriteriaMap);

    public Integer getRowVersionId(Class domainClass, Long id);
    
    public Long getMaxDomainId(Class domainClass);
    
}
