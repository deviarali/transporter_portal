package com.transporter.model;

import java.io.Serializable;

/**
 * @author SHARAN A
 */
public interface Domain extends Serializable {

	boolean equals(Object other);
//
	int hashCode();

	
}
