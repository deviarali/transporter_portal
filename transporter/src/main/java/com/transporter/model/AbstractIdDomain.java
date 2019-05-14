package com.transporter.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author SHARAN A
 */

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractIdDomain implements IdDomain {

	private Long id;
	private Integer rowVersion;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "rowversion", nullable = false)
	public Integer getRowVersion() {
		return rowVersion;
	}

	public void setRowVersion(Integer rowVersion) {
		this.rowVersion = rowVersion;
	}
	
	@Override
	public final boolean equals(Object other) {
        if (this == other) {
        	return true;
        }
        if (other == null) {
			return false;
		}
        if (this.getId() == null) {
        	return false;
        }
        if (!(other instanceof IdDomain)) {
        	return false;
        }
        IdDomain castOther = (IdDomain) other;
        return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
    }

    /**
     * @see AbstractDomain#hashCode()
     */
    @Override
	public final int hashCode() {
        if (this.getId() == null) {
        	return System.identityHashCode(this);
        }
        
        // According to the Java Lang specs two different objects may have the same hashcode
        return new HashCodeBuilder().append(getId()).toHashCode();
    }
	
}
