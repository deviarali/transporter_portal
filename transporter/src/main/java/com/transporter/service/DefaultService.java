package com.transporter.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.transporter.model.Domain;
import com.transporter.model.IdDomain;


/**
 * @author SHARAN A
 */

public interface DefaultService {

	public Domain createNewTransientDomain(String domainClassName) throws Exception;
	
	public Domain createNewTransientDomain(String domainClassName, Boolean isSearchMode) throws Exception;
	
	public Serializable saveOrUpdate(IdDomain idDomain);
	
	public Serializable saveDomain(Domain domain);
	
	public void saveAll(List<Domain> domains);
	
	public Domain initializeNestedPath(Domain domain, String nestedPathToInitialize);
	
	public Domain initializeNestedPaths(Domain domain, String[] nestedPathsToInitialize);
	
	public Domain loadDomain(Class<? extends Domain> domainClass, Serializable id);
	
	public Domain loadDomain(Class<? extends Domain> domainClass, Serializable id, String nestedPathToInitialize);
	
	public Domain loadDomain(Class<? extends Domain> domainClass, Serializable id, String[] nestedPathsToInitialize);
	
	public Domain getDomain(Class<? extends Domain> domainClass, Serializable id);
	
	public Domain getDomain(Class<? extends Domain> domainClass, Serializable id, String nestedPathToInitialize);
	
	public Domain getDomain(Class<? extends Domain> domainClass, Serializable id, String[] nestedPathsToInitialize);
	
	public List<Domain> getAllDomain(Class<? extends Domain> domainClass);
	
	public List<Domain> getAllDomain(Class<? extends Domain> domainClass, String nestedPathToInitialize);
	
	public List<Domain> getAllDomain(Class<? extends Domain> domainClass, String[] nestedPathsToInitialize);
	
	public void deleteDomain(Domain domain);
	
	public void deleteDomain(Class<? extends Domain> domainClazz, Long domainId);
	
	public void doInActiveDomain(Class<? extends Domain> domainClass, Long domainId);
	
	public void updateDomain(Domain domain);
	
	public Domain get(Class<? extends Domain> domainClass, Serializable id);

	public Domain get(Class<? extends Domain> domainClass, Serializable id, String nestedPathToInitialize);

	public Domain get(Class<? extends Domain> domainClass, Serializable id, String[] nestedPathsToInitialize);
	
	public void evict(Domain domain);
    public void evict(Collection<? extends Domain>domain);
    
    public List<Domain> findDomainByCriteriaMap(Class<? extends Domain> domainClass, Map<String, Object> searchCriteriaMap);
    
    public Integer getCountByDomainCriteriaMap(Class<? extends Domain> domainClass, Map<String, Object> searchCriteriaMap);

    public Long getMaxDomainId(Class domainClass);

    public Integer getRowVersionId(Class domainClass, Long id);
    
    public Integer getRowVersionId(Domain domain);
}
