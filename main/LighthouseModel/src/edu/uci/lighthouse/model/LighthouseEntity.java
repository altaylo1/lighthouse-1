/*******************************************************************************
 * Copyright (c) {2009,2011} {Software Design and Collaboration Laboratory (SDCL)
 *				, University of California, Irvine}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    {Software Design and Collaboration Laboratory (SDCL)
 *	, University of California, Irvine} 
 *			- initial API and implementation and/or initial documentation
 *******************************************************************************/ 
package edu.uci.lighthouse.model;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.apache.log4j.Logger;

import edu.uci.lighthouse.model.util.LHStringUtil;

/**
 * This class represents all the lighthouse entities, such as Fields, Methods, Class etc.
 * 
 * @author nilmax
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class LighthouseEntity implements Serializable {

	private static final long serialVersionUID = 2190743538210738412L;

	private static Logger logger = Logger.getLogger(LighthouseEntity.class);

	@Id
	private String id = "";

	// fullyQualifiedName = Eclipse project + Package + Class name
	@Column(columnDefinition = "VARCHAR(500)")
	private String fullyQualifiedName = "";

	public LighthouseEntity(String fqn) {
		setFullyQualifiedName(fqn);
	}

	protected LighthouseEntity() {
	}

	public String getProjectName() {
		return fullyQualifiedName.replaceAll("\\..*", "");
	}

	public String getPackageName() {
		String result = "";
		int start = fullyQualifiedName.indexOf(".");
		int end = fullyQualifiedName.lastIndexOf(".");
		if (start != -1 && end != -1 && start != end) {
			result = fullyQualifiedName.substring(start+1,end);
		}
		return result;
	}
	
	public String getId() {
		return id;
	}

	protected void setId(String id) {
		this.id = id;
	}
	
	public String getFullyQualifiedName() {
		return fullyQualifiedName;
	}

	protected void setFullyQualifiedName(String fullyQualifiedName) {
		this.fullyQualifiedName = fullyQualifiedName;
		try {
			this.id = LHStringUtil.getMD5Hash(fullyQualifiedName);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e,e);
		}
	}

	/**
	 * @return Class name
	 */
	public String getShortName() {
		return fullyQualifiedName.replaceAll("(\\w+\\.)*", "").replaceAll("(\\w+\\$)*", "");
	}

	@Override
	public String toString() {
		return getFullyQualifiedName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((fullyQualifiedName == null) ? 0 : fullyQualifiedName
						.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LighthouseEntity other = (LighthouseEntity) obj;
		if (fullyQualifiedName == null) {
			if (other.fullyQualifiedName != null)
				return false;
		} else if (!fullyQualifiedName.equals(other.fullyQualifiedName))
			return false;
		return true;
	}

}
