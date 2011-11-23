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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CollectionOfElements;

import edu.uci.lighthouse.model.expertise.DOIforClass;

/**
 * This is the class that represents each developer
 */
@Entity
public class LighthouseAuthor implements Serializable{

	private static final long serialVersionUID = 4952522633654542472L;
	
	@Id
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL)
	private Set<DOIforClass> doiModel;
	 
	public LighthouseAuthor(String name) {
		this.name = name;
	}

	protected LighthouseAuthor() {
		this("");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		LighthouseAuthor other = (LighthouseAuthor) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public void setDoiModel(Set<DOIforClass> doiModel) {
		this.doiModel = doiModel;
	}

	public Set<DOIforClass> getDoiModel() {
		return doiModel;
	}
	
	public void addDOIforClass(LighthouseClass clazz, int interest){
		DOIforClass doi = new DOIforClass();
		doi.setClazz(clazz);
		doi.setInterest(interest);
		
		if(doiModel == null)
			doiModel = new HashSet<DOIforClass>();
		
		doiModel.add(doi);
	}




	
}
