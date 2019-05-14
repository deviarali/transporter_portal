package com.transporter.dao;

/**
 * @author SHARAN A
 */
public interface HibernateInitializer {
    /**
     * 
     * @param object
     * @return
     */
    boolean isInitialized(Object object);

    /**
     * 
     * @param proxy
     */
    void initialize(Object proxy);

    /**
     * 
     * @param object
     * @param nestedPathToInitialize
     */
    void initialize(Object object, String nestedPathToInitialize);

    /**
     * 
     * @param object
     * @param nestedPathsToInitialize
     */
    void initialize(Object object, String[] nestedPathsToInitialize);
}
