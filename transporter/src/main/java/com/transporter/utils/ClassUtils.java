package com.transporter.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.transporter.model.Domain;



@SuppressWarnings({"unchecked", "rawtypes", "deprecation", "unused"})
public class ClassUtils {

	public static final String DEFAULT_PROPERTY_PATH_SEPARATOR = ".";

	public static final String GETTER_METHOD_PREFIX = "get";
	public static final String SETTER_METHOD_PREFIX = "set";

	public static final String APPLICATION_ID = "applicationId";
	public static final String SCENARIED_ENTITY_PREFIX = "Sc";

	private static final Map<Class<?>,List<String>> classProperties = new HashMap<Class<?>,List<String>>();

	private static final Map<Class<?>,List<String>> classPropertiesForUI = new HashMap<Class<?>,List<String>>();

	private static final Map<Class<?>,Map<String,Class<?>>> classPropertyTypeExceptions = new HashMap<Class<?>, Map<String,Class<?>>>();

	private static final Map<Class<?>, Class<?>> classTypeExceptions = new HashMap<Class<?>, Class<?>>();

	private static final Map<Class<?>, Class<?>> inverseClassTypeExceptions = new HashMap<Class<?>, Class<?>>();

	
	
	public static boolean existsClass(String className) {
		try {
			Class.forName(className);
		} catch(ClassNotFoundException cnfe) {
			return false;
		}
		return true;
	}

	public static Class<?> getClassForName(String className) {
		final Log logger = LogFactory.getLog(ClassUtils.class);

		Class<?> result = null;
		try {
			result = Class.forName(className);
		} catch(ClassNotFoundException cnfe) {
			logger.error(cnfe);
			throw new RuntimeException(cnfe);
		}
		return result;
	}

	public static boolean isPropertyLob(Class<?> targetClass, String propertyPath) {
		return isPropertyLob(targetClass, propertyPath, DEFAULT_PROPERTY_PATH_SEPARATOR);
	}

	public static boolean isPropertyLob(Class<?> targetClass, String propertyPath, String propertyPathSeparator) {
		if(!propertyPath.contains(propertyPathSeparator)) {
			Method getterMethod = getGetterMethodForPropertyName(targetClass, propertyPath);
			if(getterMethod.getAnnotation(Lob.class) != null) {
				return true;
			}
		} else {
			int indexOfFirstPathSeparator = propertyPath.indexOf(propertyPathSeparator);

			String propertyName = propertyPath.substring(0, indexOfFirstPathSeparator);

			Class<?> type = getPropertyType(targetClass, propertyName, getClassPropertyTypeExceptions());

			if(type != null) {
				if(Set.class.isAssignableFrom(type)) {
					type = getCollectionPropertyType(targetClass, propertyName);
					return isPropertyLob(type, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyPathSeparator);
				}
				if(Domain.class.isAssignableFrom(type)) {
					return isPropertyLob(type, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyPathSeparator);
				}
			}
		}
		return false;
	}
	
	public static boolean isPropertyTransient(Class<?> targetClass, String propertyPath) {
		return isPropertyTransient(targetClass, propertyPath, DEFAULT_PROPERTY_PATH_SEPARATOR);
	}

	public static boolean isPropertyTransient(Class<?> targetClass, String propertyPath, String propertyPathSeparator) {
		if(!propertyPath.contains(propertyPathSeparator)) {
			Method getterMethod = getGetterMethodForPropertyName(targetClass, propertyPath);
			if(getterMethod.getAnnotation(Transient.class) != null) {
				return true;
			}
		} else {
			int indexOfFirstPathSeparator = propertyPath.indexOf(propertyPathSeparator);

			String propertyName = propertyPath.substring(0, indexOfFirstPathSeparator);

			Class<?> type = getPropertyType(targetClass, propertyName, getClassPropertyTypeExceptions());

			if(type != null) {
				if(Set.class.isAssignableFrom(type)) {
					type = getCollectionPropertyType(targetClass, propertyName);
					return isPropertyTransient(type, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyPathSeparator);
				}
				if(Domain.class.isAssignableFrom(type)) {
					return isPropertyTransient(type, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyPathSeparator);
				}
			}
		}

		return false;
	}

	public static boolean isPropertyDeprecated(Class<?> targetClass, String propertyPath) {
		return isPropertyDeprecated(targetClass, propertyPath, DEFAULT_PROPERTY_PATH_SEPARATOR);
	}

	public static boolean isPropertyDeprecated(Class<?> targetClass, String propertyPath, String propertyPathSeparator) {
		if(!propertyPath.contains(propertyPathSeparator)) {
			Method getterMethod = getGetterMethodForPropertyName(targetClass, propertyPath);
			if(getterMethod.getAnnotation(Deprecated.class) != null) {
				return true;
			}
		} else {
			int indexOfFirstPathSeparator = propertyPath.indexOf(propertyPathSeparator);

			String propertyName = propertyPath.substring(0, indexOfFirstPathSeparator);

			Class<?> type = getPropertyType(targetClass, propertyName, getClassPropertyTypeExceptions());

			return isPropertyDeprecated(type, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyPathSeparator);
		}

		return false;
	}

	public static boolean isPropertyNullable(Class<?> targetClass, String propertyPath) {
		return isPropertyNullable(targetClass, propertyPath, DEFAULT_PROPERTY_PATH_SEPARATOR);
	}

	public static boolean isPropertyNullable(Class<?> targetClass, String propertyPath, String propertyPathSeparator) {
		if(!propertyPath.contains(propertyPathSeparator)) {
			// Collections are always nullable!
			if(Collection.class.isAssignableFrom(getPropertyType(targetClass, propertyPath))) {
				return true;
			}
			if (propertyPath.equals(APPLICATION_ID)) {
				return false;
			}
			Method getterMethod = getGetterMethodForPropertyName(targetClass, propertyPath);
			if(getterMethod.isAnnotationPresent(Column.class)) {
				return getterMethod.getAnnotation(Column.class).nullable();
			} else if(getterMethod.isAnnotationPresent(JoinColumn.class)) {
				return getterMethod.getAnnotation(JoinColumn.class).nullable();
			} else if(getterMethod.isAnnotationPresent(OneToOne.class)) {
				return getterMethod.getAnnotation(OneToOne.class).optional();
			}
		} else {
			int indexOfFirstPathSeparator = propertyPath.indexOf(propertyPathSeparator);

			String propertyName = propertyPath.substring(0, indexOfFirstPathSeparator);

			Class<?> type = getPropertyType(targetClass, propertyName, getClassPropertyTypeExceptions());

			if(type != null) {
				if(Set.class.isAssignableFrom(type)) {
					type = getCollectionPropertyType(targetClass, propertyName);
					return isPropertyNullable(type, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyPathSeparator);
				}
				if(Domain.class.isAssignableFrom(type)) {
					return isPropertyNullable(type, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyPathSeparator);
				}
			}
		}
		return false;
	}

	public static boolean isPropertyManagedBy(Class<?> targetClass, String propertyPath) {
		return isPropertyManagedBy(targetClass, propertyPath, DEFAULT_PROPERTY_PATH_SEPARATOR);
	}

	public static boolean isPropertyManagedBy(Class<?> targetClass, String propertyPath, String propertyPathSeparator) {
		if(!propertyPath.contains(propertyPathSeparator)) {
			Class<?> propertyClass = getPropertyType(targetClass, propertyPath);
			Method getterMethod = getGetterMethodForPropertyName(targetClass, propertyPath);
			if(propertyClass != null) {
				if(Domain.class.isAssignableFrom(propertyClass)) {
					 if(getterMethod.getAnnotation(OneToOne.class) != null && getterMethod.getAnnotation(OneToOne.class).cascade() != null) {
						for(CascadeType cascadeType : getterMethod.getAnnotation(OneToOne.class).cascade()) {
							if(cascadeType.equals(CascadeType.ALL)) {
								return true;
							}
						}
						return false;
					} else if(getterMethod.getAnnotation(ManyToOne.class) != null && getterMethod.getAnnotation(ManyToOne.class).cascade() != null) {
						for(CascadeType cascadeType : getterMethod.getAnnotation(ManyToOne.class).cascade()) {
							if(cascadeType.equals(CascadeType.ALL)) {
								return true;
							}
						}
						return false;
					} else {
						return false;
					}
				} else if(Set.class.isAssignableFrom(propertyClass)) {
					if(getterMethod.getAnnotation(ManyToMany.class) != null) {
						for(CascadeType cascadeType : getterMethod.getAnnotation(ManyToMany.class).cascade()) {
							if(cascadeType.equals(CascadeType.ALL)) {
								return true;
							}
						}
						return false;
					} else if(getterMethod.getAnnotation(OneToMany.class) != null) {
						for(CascadeType cascadeType : getterMethod.getAnnotation(OneToMany.class).cascade()) {
							if(cascadeType.equals(CascadeType.ALL)) {
								return true;
							}
						}
					}
				} else {
					return true;
				}
			}
		} else {
			int indexOfFirstPathSeparator = propertyPath.indexOf(propertyPathSeparator);

			String propertyName = propertyPath.substring(0, indexOfFirstPathSeparator);

			Class<?> type = null;

			if(!CollectionUtils.isEmpty(classPropertyTypeExceptions) && classPropertyTypeExceptions.containsKey(targetClass) && classPropertyTypeExceptions.get(targetClass).containsKey(propertyName)) {
				type = classPropertyTypeExceptions.get(targetClass).get(propertyName);
			} else {
				String getterMethodName = getGetterMethodNameForPropertyName(propertyName);
				try {
					Method getterMethod = targetClass.getMethod(getterMethodName, (Class<?>[])null);
					type = getterMethod.getReturnType();
				} catch(NoSuchMethodException nsme) {
					throw new RuntimeException(nsme);
				}
				if(type != null) {
					if(Set.class.isAssignableFrom(type)) {
						type = getCollectionPropertyType(targetClass, propertyName);
						return isPropertyManagedBy(type, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyPathSeparator);
					}
					if(Domain.class.isAssignableFrom(type)) {
						return isPropertyManagedBy(type, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyPathSeparator);
					}
				}
			}
		}

		return false;
	}

	public static Object getPropertyValue(Object target, String propertyPath) {
		return getPropertyValue(target, propertyPath, DEFAULT_PROPERTY_PATH_SEPARATOR, Boolean.FALSE);
	}

	public static Object getPropertyValue(Domain target, String propertyPath) {
		return getPropertyValue(target, propertyPath, DEFAULT_PROPERTY_PATH_SEPARATOR, Boolean.FALSE);
	}

	public static Object getPropertyValue(Domain target, String propertyPath, String propertyPathSeparator) {
		return getPropertyValue(target, propertyPath, propertyPathSeparator, Boolean.FALSE);
	}

	public static Object getPropertyValue(Object target, String propertyPath, String propertyPathSeparator, Boolean initialize) {
		final Log logger = LogFactory.getLog(ClassUtils.class);
		Object result = null;

		if(target == null) {
			return null;
		}

		if(!propertyPath.contains(propertyPathSeparator)) {
			String getterMethodName = getGetterMethodNameForPropertyName(propertyPath);
			try {
				Method getterMethod = target.getClass().getMethod(getterMethodName, (Class<?>[])null);
				result = getterMethod.invoke(target, (Object[])null);
			} catch(NoSuchMethodException nsme) {
				logger.error(nsme);
				throw new RuntimeException(nsme);
			} catch(IllegalAccessException iae) {
				logger.error(iae);
				throw new RuntimeException(iae);
			} catch(InvocationTargetException ite) {
				logger.error(ite);
				throw new RuntimeException(ite);
			}

		} else {
			int indexOfFirstPathSeparator = propertyPath.indexOf(propertyPathSeparator);

			String propertyName = propertyPath.substring(0, indexOfFirstPathSeparator);

			Object value = null;

			String getterMethodName = getGetterMethodNameForPropertyName(propertyName);
			try {
				Method getterMethod = target.getClass().getMethod(getterMethodName, (Class<?>[])null);
				value = getterMethod.invoke(target, (Object[])null);
			} catch(NoSuchMethodException nsme) {
				logger.error(nsme);
				throw new RuntimeException(nsme);
			} catch(IllegalAccessException iae) {
				logger.error(iae);
				throw new RuntimeException(iae);
			} catch(InvocationTargetException ite) {
				logger.error(ite);
				throw new RuntimeException(ite);
			}
			if(value != null) {
				if(value instanceof Domain) {
					/*
					if(!Hibernate.isInitialized(value)) {
						throw new RuntimeException(String.format("Property %s of %s is not initialized!", new Object[] {propertyName, FbBeanUtils.getEntityClass(target).getSimpleName()}));
					}
					 */
					result = getPropertyValue((Domain)value, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyPathSeparator, initialize);
				} else if(value instanceof Collection<?>) {
					String remainingPropertyPath = propertyPath.substring(indexOfFirstPathSeparator + 1);
					if(remainingPropertyPath.equals("size")) {
						try {
							Method targetMethod = value.getClass().getMethod("size", (Class<?>[])null);
							result = targetMethod.invoke(value, (Object[])null);
						} catch(NoSuchMethodException nsme) {
							logger.error(nsme);
							throw new RuntimeException(nsme);
						} catch(IllegalAccessException iae) {
							logger.error(iae);
							throw new RuntimeException(iae);
						} catch(InvocationTargetException ite) {
							logger.error(ite);
							throw new RuntimeException(ite);
						}

					} else {
						logger.error("Invalid property for Collection: " + remainingPropertyPath);
						throw new RuntimeException("Invalid property for Collection: " + remainingPropertyPath);
					}
				} else {
					logger.error("Invalid property type: " + value.getClass().getName());
					throw new RuntimeException("Invalid property type: " + value.getClass().getName());
				}
			}
		}
		return result;
	}

	public static void setPropertyValue(Object target, String propertyPath, Object propertyValue) {
		setPropertyValue(target, propertyPath, propertyValue, DEFAULT_PROPERTY_PATH_SEPARATOR);
	}

	public static void setPropertyValue(Domain target, String propertyPath, Object propertyValue) {
		setPropertyValue(target, propertyPath, propertyValue, DEFAULT_PROPERTY_PATH_SEPARATOR);
	}

	public static void setPropertyValue(Domain target, String propertyPath, Object propertyValue, String propertyPathSeparator) {
		final Log logger = LogFactory.getLog(ClassUtils.class);

		if(!propertyPath.contains(propertyPathSeparator)) {
			String setterMethodName = getSetterMethodNameForPropertyName(propertyPath);
			try {
				Class<?> propertyType = getPropertyType(target.getClass(), propertyPath);
				Method setterMethod = target.getClass().getMethod(setterMethodName, new Class<?>[] {propertyType} /*(Class<?>[])null*/);
				setterMethod.invoke(target, new Object[] {propertyValue});
			} catch(NoSuchMethodException nsme) {
				logger.error(nsme);
				throw new RuntimeException(nsme);
			} catch(IllegalAccessException iae) {
				logger.error(iae);
				throw new RuntimeException(iae);
			} catch(InvocationTargetException ite) {
				logger.error(ite);
				throw new RuntimeException(ite);
			}
		} else {
			int indexOfFirstPathSeparator = propertyPath.indexOf(propertyPathSeparator);

			String propertyName = propertyPath.substring(0, indexOfFirstPathSeparator);

			Object value = null;

			String getterMethodName = getGetterMethodNameForPropertyName(propertyName);
			try {
				Method getterMethod = target.getClass().getMethod(getterMethodName, (Class<?>[])null);
				value = getterMethod.invoke(target, (Object[])null);
			} catch(NoSuchMethodException nsme) {
				logger.error(nsme);
				throw new RuntimeException(nsme);
			} catch(IllegalAccessException iae) {
				logger.error(iae);
				throw new RuntimeException(iae);
			} catch(InvocationTargetException ite) {
				logger.error(ite);
				throw new RuntimeException(ite);
			}
			if(value != null) {
				if(value instanceof Domain) {
					setPropertyValue((Domain)value, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyValue, propertyPathSeparator);
				} else {
					logger.error("Invalid property type: " + value.getClass().getName());
					throw new RuntimeException("Invalid property type: " + value.getClass().getName());
				}
			}
		}
	}

	public static void setPropertyValue(Object target, String propertyPath, Object propertyValue, String propertyPathSeparator) {
		final Log logger = LogFactory.getLog(ClassUtils.class);

		if(!propertyPath.contains(propertyPathSeparator)) {
			String setterMethodName = getSetterMethodNameForPropertyName(propertyPath);
			try {
				Class<?> propertyType = getPropertyType(target.getClass(), propertyPath);
				Method setterMethod = target.getClass().getMethod(setterMethodName, new Class<?>[] {propertyType} /*(Class<?>[])null*/);
				setterMethod.invoke(target, new Object[] {propertyValue});
			} catch(NoSuchMethodException nsme) {
				logger.error(nsme);
				throw new RuntimeException(nsme);
			} catch(IllegalAccessException iae) {
				logger.error(iae);
				throw new RuntimeException(iae);
			} catch(InvocationTargetException ite) {
				logger.error(ite);
				throw new RuntimeException(ite);
			}
		} else {
			int indexOfFirstPathSeparator = propertyPath.indexOf(propertyPathSeparator);

			String propertyName = propertyPath.substring(0, indexOfFirstPathSeparator);

			Object value = null;

			String getterMethodName = getGetterMethodNameForPropertyName(propertyName);
			try {
				Method getterMethod = target.getClass().getMethod(getterMethodName, (Class<?>[])null);
				value = getterMethod.invoke(target, (Object[])null);
			} catch(NoSuchMethodException nsme) {
				logger.error(nsme);
				throw new RuntimeException(nsme);
			} catch(IllegalAccessException iae) {
				logger.error(iae);
				throw new RuntimeException(iae);
			} catch(InvocationTargetException ite) {
				logger.error(ite);
				throw new RuntimeException(ite);
			}
			if(value != null) {
				if(value instanceof Domain) {
					setPropertyValue((Domain)value, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyValue, propertyPathSeparator);
				} else {
					logger.error("Invalid property type: " + value.getClass().getName());
					throw new RuntimeException("Invalid property type: " + value.getClass().getName());
				}
			}
		}
	}

	public static Class<?> getPropertyType(Class<?> targetClass, String propertyPath) {
		return getPropertyType(targetClass, propertyPath, DEFAULT_PROPERTY_PATH_SEPARATOR, null);
	}

	public static Class<?> getPropertyType(Class<?> targetClass, String propertyPath, String propertyPathSeparator) {
		return getPropertyType(targetClass, propertyPath, propertyPathSeparator, null);
	}

	public static Class<?> getPropertyType(Class<?> targetClass, String propertyPath, Map<Class<?>,Map<String,Class<?>>> classPropertyTypeExceptions) {
		return getPropertyType(targetClass, propertyPath, DEFAULT_PROPERTY_PATH_SEPARATOR, classPropertyTypeExceptions);
	}

	public static Class<?> getPropertyType(Class<?> targetClass, String propertyPath, String propertyPathSeparator, Map<Class<?>,Map<String,Class<?>>> classPropertyTypeExceptions) {
		final Log logger = LogFactory.getLog(ClassUtils.class);
		Class<?> result = null;

		if(!propertyPath.contains(propertyPathSeparator)) {
			if(!CollectionUtils.isEmpty(classPropertyTypeExceptions) && classPropertyTypeExceptions.containsKey(targetClass) && classPropertyTypeExceptions.get(targetClass).containsKey(propertyPath)) {
				result = classPropertyTypeExceptions.get(targetClass).get(propertyPath);
			} else {
				String getterMethodName = getGetterMethodNameForPropertyName(propertyPath);
				try {
					Method getterMethod = targetClass.getMethod(getterMethodName, (Class<?>[])null);
					result = getterMethod.getReturnType();
				} catch(NoSuchMethodException nsme) {
					logger.error(nsme);
					throw new RuntimeException(nsme);
				}
			}
		} else {
			int indexOfFirstPathSeparator = propertyPath.indexOf(propertyPathSeparator);

			String propertyName = propertyPath.substring(0, indexOfFirstPathSeparator);

			Class<?> type = null;

			if(!CollectionUtils.isEmpty(classPropertyTypeExceptions) && classPropertyTypeExceptions.containsKey(targetClass) && classPropertyTypeExceptions.get(targetClass).containsKey(propertyName)) {
				type = classPropertyTypeExceptions.get(targetClass).get(propertyName);
			} else {
				String getterMethodName = getGetterMethodNameForPropertyName(propertyName);
				try {
					Method getterMethod = targetClass.getMethod(getterMethodName, (Class<?>[])null);
					type = getterMethod.getReturnType();
				} catch(NoSuchMethodException nsme) {
					logger.error(nsme);
					throw new RuntimeException(nsme);
				}
			}
			if(type != null) {
				if(Set.class.isAssignableFrom(type)) {
					type = getCollectionPropertyType(targetClass, propertyName);
				}
				if(Domain.class.isAssignableFrom(type)) {
					result = getPropertyType(type, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyPathSeparator, classPropertyTypeExceptions);
				} else {
					logger.error("Not a Domain: " + type.getClass().getName());
					throw new RuntimeException("Not a Domain: " + type.getClass().getName());
				}
			}
		}
		return result;
	}

	public static boolean hasProperty(Class<?> target, String propertyPath) {
		return hasProperty(target, propertyPath, DEFAULT_PROPERTY_PATH_SEPARATOR);		
	}

	public static boolean hasProperty(Class<?> target, String propertyPath, String propertyPathSeparator) {
		final Log logger = LogFactory.getLog(ClassUtils.class);
		if(!propertyPath.contains(propertyPathSeparator)) {
			String getterMethodName = getGetterMethodNameForPropertyName(propertyPath);
			try {
				target.getMethod(getterMethodName, (Class<?>[])null);
				return true;
			} catch(NoSuchMethodException nsme) {
				return false;
			}
		} else {
			int indexOfFirstPathSeparator = propertyPath.indexOf(propertyPathSeparator);

			String propertyName = propertyPath.substring(0, indexOfFirstPathSeparator);

			Class<?> type = null;

			if(!CollectionUtils.isEmpty(classPropertyTypeExceptions) && classPropertyTypeExceptions.containsKey(target) && classPropertyTypeExceptions.get(target).containsKey(propertyName)) {
				type = classPropertyTypeExceptions.get(target).get(propertyName);
			} else {
				String getterMethodName = getGetterMethodNameForPropertyName(propertyName);
				try {
					Method getterMethod = target.getMethod(getterMethodName, (Class<?>[])null);
					type = getterMethod.getReturnType();
				} catch(NoSuchMethodException nsme) {
					logger.error(nsme);
					throw new RuntimeException(nsme);
				}
			}
			if(type != null) {
				if(Set.class.isAssignableFrom(type)) {
					type = getCollectionPropertyType(target, propertyName);
					return hasProperty(type, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyPathSeparator);
				} else if(Domain.class.isAssignableFrom(type)) {
					return hasProperty(type, propertyPath.substring(indexOfFirstPathSeparator + 1), propertyPathSeparator);
				}
			}
		}
		return false;
	}

	public static boolean isPropertyTypeCollection(Class<?> target, String propertyPath, Map<Class<?>,Map<String,Class<?>>> classPropertyTypeExceptions) {
		return isPropertyTypeCollection(target, propertyPath, classPropertyTypeExceptions, DEFAULT_PROPERTY_PATH_SEPARATOR);
	}

	public static boolean isPropertyTypeCollection(Class<?> target, String propertyPath, Map<Class<?>,Map<String,Class<?>>> classPropertyTypeExceptions, String propertyPathSeparator) {
		final Log logger = LogFactory.getLog(ClassUtils.class);

		if(!propertyPath.contains(propertyPathSeparator)) {
			String getterMethodName = getGetterMethodNameForPropertyName(propertyPath);
			if(classPropertyTypeExceptions != null && classPropertyTypeExceptions.containsKey(target) && classPropertyTypeExceptions.get(target).containsKey(propertyPath)) {
				if(Set.class.isAssignableFrom(classPropertyTypeExceptions.get(target).get(propertyPath))) {
					return true;
				}
			} else {
				try {
					Method getterMethod = target.getMethod(getterMethodName, (Class<?>[])null);
					if(Set.class.isAssignableFrom(getterMethod.getReturnType())) {
						return true;
					}
				} catch(NoSuchMethodException nsme) {
					logger.error(nsme);
					throw new RuntimeException(nsme);
				}
			}
		} else {
			int indexOfFirstPathSeparator = propertyPath.indexOf(propertyPathSeparator);

			String propertyName = propertyPath.substring(0, indexOfFirstPathSeparator);

			Class<?> type = null;

			if(classPropertyTypeExceptions != null && classPropertyTypeExceptions.containsKey(target) && classPropertyTypeExceptions.get(target).containsKey(propertyName)) {
				type = classPropertyTypeExceptions.get(target).get(propertyName);
			} else {
				String getterMethodName = getGetterMethodNameForPropertyName(propertyName);
				try {
					Method getterMethod = target.getMethod(getterMethodName, (Class<?>[])null);
					type = getterMethod.getReturnType();
				} catch(NoSuchMethodException nsme) {
					logger.error(nsme);
					throw new RuntimeException(nsme);
				}
				if(Set.class.isAssignableFrom(type)) {
					type = getCollectionPropertyType(target, propertyName);
				}
			}
			if(type != null) {
				return isPropertyTypeCollection(type, propertyPath.substring(indexOfFirstPathSeparator + 1), classPropertyTypeExceptions, propertyPathSeparator);
			}
		}
		return false;
	}

	public static Class<?> getCollectionPropertyType(Class<?> target, String propertyPath, Map<Class<?>,Map<String,Class<?>>> classPropertyTypeExceptions) {
		return getCollectionPropertyType(target, propertyPath, classPropertyTypeExceptions, DEFAULT_PROPERTY_PATH_SEPARATOR);
	}

	public static Class<?> getCollectionPropertyType(Class<?> target, String propertyPath, Map<Class<?>,Map<String,Class<?>>> classPropertyTypeExceptions, String propertyPathSeparator) {
		final Log logger = LogFactory.getLog(ClassUtils.class);
		Class<?> result = null;

		if(!propertyPath.contains(propertyPathSeparator)) {
			String getterMethodName = getGetterMethodNameForPropertyName(propertyPath);

			if(classPropertyTypeExceptions != null && classPropertyTypeExceptions.containsKey(target) && classPropertyTypeExceptions.get(target).containsKey(propertyPath)) {
				result = classPropertyTypeExceptions.get(target).get(propertyPath);
			} else {
				try {
					Method getterMethod = target.getMethod(getterMethodName, (Class<?>[])null);
					Type[] actualTypeArguments;
					Type type = getterMethod.getGenericReturnType();
					if(type instanceof Class<?>) {
						actualTypeArguments = ((Class<?>)type).getTypeParameters();
					} else {
						actualTypeArguments = ((ParameterizedType)type).getActualTypeArguments();
					}
					if(actualTypeArguments[0] instanceof TypeVariable<?>) {
						if(getterMethod.getAnnotation(OneToMany.class) != null) {
							OneToMany annotation = getterMethod.getAnnotation(OneToMany.class);
							result = annotation.targetEntity();
						} else {
							result = null;
						}
					} else {
						result = (Class<?>)actualTypeArguments[0];
					}
				} catch(NoSuchMethodException nsme) {
					logger.error(nsme);
					throw new RuntimeException(nsme);
				}
			}
		} else {
			int indexOfFirstPathSeparator = propertyPath.indexOf(propertyPathSeparator);

			String propertyName = propertyPath.substring(0, indexOfFirstPathSeparator);

			Class<?> type = null;

			if(classPropertyTypeExceptions != null && classPropertyTypeExceptions.containsKey(target) && classPropertyTypeExceptions.get(target).containsKey(propertyName)) {
				type = classPropertyTypeExceptions.get(target).get(propertyName);
			} else {
				String getterMethodName = getGetterMethodNameForPropertyName(propertyName);
				try {
					Method getterMethod = target.getMethod(getterMethodName, (Class<?>[])null);
					type = getterMethod.getReturnType();
				} catch(NoSuchMethodException nsme) {
					logger.error(nsme);
					throw new RuntimeException(nsme);
				}
			}
			if(type != null) {
				if(Domain.class.isAssignableFrom(type)) {
					result = getCollectionPropertyType(type, propertyPath.substring(indexOfFirstPathSeparator + 1), classPropertyTypeExceptions, propertyPathSeparator);
				} else if(Set.class.isAssignableFrom(type)) {
					type = getCollectionPropertyType(target, propertyName);
					result = getCollectionPropertyType(type, propertyPath.substring(indexOfFirstPathSeparator + 1), classPropertyTypeExceptions, propertyPathSeparator);
				} else {
					logger.error("Not a Domain or Set: " + type.getClass().getName());
					throw new RuntimeException("Not a Domain or Set: " + type.getClass().getName());
				}
			}
		}
		return result;
	}

	public static Class<?> getCollectionPropertyType(Class<?> target, String propertyPath) {
		return getCollectionPropertyType(target, propertyPath, null, DEFAULT_PROPERTY_PATH_SEPARATOR);
	}

	public static Class<?> getCollectionPropertyType(Class<?> target, String propertyPath, String propertyPathSeparator) {
		return getCollectionPropertyType(target, propertyPath, null, propertyPathSeparator);
	}

	public static boolean isGetterMethod(Method method) {
		if((method.getName().startsWith(GETTER_METHOD_PREFIX) && method.getParameterTypes().length == 0)) {
			return true;
		}
		return false;
	}

	public static boolean isSetterMethod(Method method) {
		if(method.getName().startsWith(SETTER_METHOD_PREFIX) && method.getParameterTypes().length == 1) {
			return true;
		}
		return false;
	}

	public static String getGetterMethodNameForPropertyName(String propertyName) {
		Assert.notNull(propertyName);
		return "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
	}

	public static Method getGetterMethodForPropertyName(Class<?> target, String propertyName) {
		Assert.notNull(target);
		Assert.notNull(propertyName);
		String getterMethodName = getGetterMethodNameForPropertyName(propertyName);
		Method getterMethod = null;
		try {
			getterMethod = target.getMethod(getterMethodName, (Class<?>[])null);
		} catch(NoSuchMethodException nsme) {
			throw new RuntimeException(nsme);
		}
		return getterMethod;
	}

	public static String getPropertyNameFromGetterMethodName(String getterMethodName) {
		Assert.notNull(getterMethodName);
		return getterMethodName.substring(3, 4).toLowerCase() + getterMethodName.substring(4);
	}

	public static String getSetterMethodNameForPropertyName(String propertyName) {
		Assert.notNull(propertyName);
		return "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
	}

	public static String getPropertyNameFromSetterMethodName(String setterMethodName) {
		Assert.notNull(setterMethodName);
		return setterMethodName.substring(3, 4).toLowerCase() + setterMethodName.substring(4);
	}

	public static void assertValidPropertyMethod(Class<?> targetClass, String methodName) {
		Assert.notNull(targetClass);
		Assert.notNull(methodName);
		if(methodName.substring(3, 4).equals(methodName.substring(3, 4).toLowerCase())) {
			throw new RuntimeException(String.format("Invalid property method: class=%s, method=%s", new Object[] {targetClass.getName(), methodName}));
		}
	}

	/**
	 * 
	 * @return
	 */
	public static Map<Class<?>, Map<String, Class<?>>> getClassPropertyTypeExceptions() {
		final Log logger = LogFactory.getLog(ClassUtils.class);
		if(classPropertyTypeExceptions.isEmpty()) {
			try {
				Map<String,Class<?>> propertyTypeExceptions = new HashMap<String,Class<?>>();
				String exceptionPropertyTypeClassName = "com.fb.ghr.employee.dom.EmployeeHrDetails";
				if(existsClass(exceptionPropertyTypeClassName)) {
					propertyTypeExceptions.put("employeeDetails", Class.forName(exceptionPropertyTypeClassName));
					classPropertyTypeExceptions.put(Class.forName("com.fb.gbe.employee.dom.Employee"), propertyTypeExceptions);
					classTypeExceptions.put(getPropertyType(Class.forName("com.fb.gbe.employee.dom.Employee"), "employeeDetails"), Class.forName(exceptionPropertyTypeClassName));
					inverseClassTypeExceptions.put(Class.forName(exceptionPropertyTypeClassName), getPropertyType(Class.forName("com.fb.gbe.employee.dom.Employee"), "employeeDetails"));
				}
			} catch(ClassNotFoundException cnfe) {
				logger.error(cnfe);
				throw new RuntimeException(cnfe);
			}
		}
		return classPropertyTypeExceptions;
	}

	public static Map<Class<?>, Class<?>> getClassTypeExceptions() {
		if(classPropertyTypeExceptions.isEmpty()) {
			getClassPropertyTypeExceptions();
		}
		return classTypeExceptions;
	}

	public static Map<Class<?>, Class<?>> getInverseClassTypeExceptions() {
		if(classPropertyTypeExceptions.isEmpty()) {
			getClassPropertyTypeExceptions();
		}
		return inverseClassTypeExceptions;
	}

	/**
	 * Returns the given value converted to the given type
	 * @param type for the returning value
	 * @param value to be converted
	 * @return the value converted to the given type
	 */
	public static Object parseValue(Class<?> type, String value) {
		final Log logger = LogFactory.getLog(ClassUtils.class);

		if(type == null) {
			// TODO: Define exception!
			throw new RuntimeException("type cannot be null.");
		}

		if(value != null) {
			try {
				if(type.equals(Boolean.class)) {
					return new Boolean(value);
				} else if(type.equals(Integer.class)) {
					return new Integer(value);
				} else if(type.equals(Long.class)) {
					return new Long(value);
				} else if(type.equals(String.class)) {
					return value;
				} else if(type.equals(Date.class)) {
					return DateTimeUtils.parseDateYYYYMMDD(value);
				} else if(type.equals(Calendar.class)) {
					return DateTimeUtils.getCalendar(DateTimeUtils.parseDateTimeYYYYMMDDHHMMSS(value));
				} else if(type.equals(GregorianCalendar.class)) {
					return DateTimeUtils.getCalendar(DateTimeUtils.parseDateTimeYYYYMMDDHHMMSS(value));
				} else if(type.equals(BigDecimal.class)) {
					return new BigDecimal(value);
				}  else if(type instanceof Object) {
					return value;
				} else {
					throw new RuntimeException(type.getName());
				}
			} catch(Exception e) {
				logger.error(e);
				// TODO: Define exception!
				throw new RuntimeException(e);

			}
		}

		return null;
	}

	/**
	 * Returns the String representation of the given value (Object)
	 * @param value from where String representation is to be obtained
	 * @param targetClass (optional)
	 * @param propertyName (optional)
	 * @return String representation of the given value
	 */
	public static String generateString(Object value, Class<?> targetClass, String propertyName) {
		final Log logger = LogFactory.getLog(ClassUtils.class);

		if(value != null) {
			try {
				if(value.getClass().equals(Boolean.class)) {
					return ((Boolean)value).toString();
				} else if(value.getClass().equals(Integer.class)) {
					return ((Integer)value).toString();
				} else if(value.getClass().equals(Long.class)) {
					return ((Long)value).toString();
				} else if(value.getClass().equals(String.class)) {
					return (String)value;
				} else if(value.getClass().equals(Date.class)) {
					return DateTimeUtils.getDateYYYYMMDD((Date)value);
				} else if(value.getClass().equals(Calendar.class)) {
					return DateTimeUtils.getDateYYYYMMDDSPACEHHMMSS(((Calendar)value).getTime());
				} else if(value.getClass().equals(GregorianCalendar.class)) {
					return DateTimeUtils.getDateYYYYMMDDSPACEHHMMSS(((GregorianCalendar)value).getTime());
				} else if(value.getClass().equals(BigDecimal.class)) {
					return ((BigDecimal)value).toPlainString();
				} else {
					// TODO: Change InvalidTypeException to support targetClass & propertyName
					//throw new InvalidTypeException(value.getClass().getName());
					if(targetClass == null && propertyName == null) {
						throw new RuntimeException(String.format("The Type [%s] is not valid", new Object[] {value.getClass().getName()}));
					} else {
						throw new RuntimeException(String.format("The Type [%s] is not valid for class %s, property %s", new Object[] {value.getClass().getName(), targetClass, propertyName}));
					}
				}
			} catch(Exception e) {
				logger.error(e);
				// TODO: Define exception!
				throw new RuntimeException(e);

			}
		}

		return null;
	}

	/**
	 * This method 
	 * @param targetClass
	 * @param annotation
	 * @return
	 */
	public static String getPropertyName(Class<?> targetClass, Annotation annotation) {
		List<String> properties = ClassUtils.getAllProperties(targetClass);
		if (CollectionUtils.isEmpty(properties)) {
			return null;
		}
		
		for (String propertyName : properties) {
			if(ClassUtils.getAnnotation(targetClass, propertyName, annotation.getClass()) != null) {
				return propertyName;
			}
		}
		return null;
	}

	public static Annotation[] getAnnotations(Class<?> targetClass, String propertyName) {
		Method getterMethod = getGetterMethodForPropertyName(targetClass, propertyName);
		return getterMethod.getAnnotations();
	}

	public static <T extends Annotation> T getAnnotation(Class<?> targetClass, String propertyName, Class<T> annotationClass) {
		return getAnnotation(targetClass, propertyName, annotationClass, DEFAULT_PROPERTY_PATH_SEPARATOR);
	}

	public static <T extends Annotation> T getAnnotation(Class<?> targetClass, String propertyPath, Class<T> annotationClass, String propertyPathSeparator) {
		if (!propertyPath.contains(propertyPathSeparator)) {
			Method getterMethod = getGetterMethodForPropertyName(targetClass, propertyPath);
			return getterMethod.getAnnotation(annotationClass);
		}
		else {
			int indexOfFirstPathSeparator = propertyPath.indexOf(propertyPathSeparator);

			String propertyName = propertyPath.substring(0, indexOfFirstPathSeparator);

			Class<?> type = getPropertyType(targetClass, propertyName, getClassPropertyTypeExceptions());

			if(type != null) {
				if(Set.class.isAssignableFrom(type)) {
					type = getCollectionPropertyType(targetClass, propertyName);
					return getAnnotation(type, propertyPath.substring(indexOfFirstPathSeparator + 1), annotationClass, propertyPathSeparator);
				}
				if(Domain.class.isAssignableFrom(type)) {
					return getAnnotation(type, propertyPath.substring(indexOfFirstPathSeparator + 1), annotationClass, propertyPathSeparator);
				}
			}
		}
		return null;
	}

	public static boolean isAnnotationPresent(Class<?> targetClass, String propertyPath, Class<? extends Annotation> annotationClass) {
		return isAnnotationPresent(targetClass, propertyPath, annotationClass, DEFAULT_PROPERTY_PATH_SEPARATOR);
	}

	public static boolean isAnnotationPresent(Class<?> targetClass, String propertyPath, Class<? extends Annotation> annotationClass, String propertyPathSeparator) {
		if (!propertyPath.contains(propertyPathSeparator)) {
			Method getterMethod = getGetterMethodForPropertyName(targetClass, propertyPath);
			return getterMethod.isAnnotationPresent(annotationClass);
		}
		else {
			int indexOfFirstPathSeparator = propertyPath.indexOf(propertyPathSeparator);

			String propertyName = propertyPath.substring(0, indexOfFirstPathSeparator);

			Class<?> type = getPropertyType(targetClass, propertyName, getClassPropertyTypeExceptions());

			if(type != null) {
				if(Set.class.isAssignableFrom(type)) {
					type = getCollectionPropertyType(targetClass, propertyName);
					return isAnnotationPresent(type, propertyPath.substring(indexOfFirstPathSeparator + 1), annotationClass, propertyPathSeparator);
				}
				if(Domain.class.isAssignableFrom(type)) {
					return isAnnotationPresent(type, propertyPath.substring(indexOfFirstPathSeparator + 1), annotationClass, propertyPathSeparator);
				}
			}
		}
		return false;
	}

	/**
	 * Splits the {@link list} list into {@link blockCount} smaller lists
	 * @param list
	 * @param blockCount
	 * @return
	 */
	public static Map<Integer,List<?>> splitList(List<?> list, Integer blockCount) {
		Map<Integer,List<?>> results = new HashMap<Integer,List<?>>();
		Integer blockSize = list.size() / blockCount;
		if(list.size() % blockCount > 0) {
			blockSize++;
		}
		Iterator<?> it = list.iterator();
		for(Integer nBlock = 0; nBlock < blockCount; nBlock++) {
			for(Integer blockIndex = 0; blockIndex < blockSize; blockIndex++) {
				if(it.hasNext()) {
					if(!results.containsKey(nBlock)) {
						results.put(nBlock, new ArrayList<Class<? extends Domain>>());
					}
					((List)results.get(nBlock)).add(it.next());
				}
			}
		}
		return results;
	}

	public static List<String> getAllProperties(Class<?> targetClass) {
		List<String> properties = new ArrayList<String>();
		Method[] methods = targetClass.getMethods();
		for(Method method : methods) {
			if(isGetterMethod(method) && !(method.getName().equals("getClass"))) {
				String propertyName = getPropertyNameFromGetterMethodName(method.getName());
				String setterMethodName = getSetterMethodNameForPropertyName(propertyName);
				boolean hasSetterMethod = false;
				try {
					Method setterMethod = targetClass.getMethod(setterMethodName, new Class<?>[] {method.getReturnType()});
					hasSetterMethod = true;
				} catch (Exception e) {
				}

				if(hasSetterMethod) {
					properties.add(propertyName);
				}
			}
		}
		return properties;
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	public static String toLowerCamelCase(final String string) {
		final char charArray[] = string.toCharArray();
		charArray[0] += 32;
		return new String(charArray);
	}

}