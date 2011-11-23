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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Environment;

import edu.uci.lighthouse.LHmodelExtensions.ClassPluginLoader;
import edu.uci.lighthouse.LHmodelExtensions.LHclassPluginExtension;
import edu.uci.lighthouse.model.QAforums.LHforum;



@Entity
public class LighthouseClass extends LighthouseEntity {
	
	private static final long serialVersionUID = 2097778395729254060L;
	
	
	/**@author: Lee*/
	//ArrayList<LHclassPluginExtension> extensions = new ArrayList<LHclassPluginExtension>(); 
	
	/**@author lee*/
	 @OneToOne(cascade = CascadeType.ALL)
	private LHforum forum;
	 
	 @OneToMany(cascade = CascadeType.ALL)
	private java.util.Set<LighthouseAuthor> interestedAuthors;
	
	
	
	protected LighthouseClass() {
		this("");
		forum = new LHforum();
		
	//	loadExtensions();
	}

	public LighthouseClass(String fqn) {
		super(fqn);
		forum = new LHforum();
		
	//	loadExtensions();
	}
	

	
	public boolean isAnonymous(){
		return getFullyQualifiedName().matches("(\\w+\\.)*(\\w+\\$)+\\d+");
	}
	
	public boolean isInnerClass(){
		return getFullyQualifiedName().matches("(\\w+\\.)*(\\w+\\$)+[a-zA-Z_]+");
	}
	

	/**
	 * @author lee
	 */
	/**private void loadExtensions(){
		List<LHclassPluginExtension> listOfExt = 
			ClassPluginLoader.getInstance().loadClassPluginExtensions();
		extensions.clear();
		extensions.addAll(listOfExt);
		
	}
	
	public List<LHclassPluginExtension> getExtensions(){
		return extensions;
	}**/

	public void setForum(LHforum forum) {
		this.forum = forum;
	}

	public LHforum getForum() {
		return forum;
	}

	public void setInterestedAuthors(java.util.Set interestedAuthors) {
		this.interestedAuthors = interestedAuthors;
	}

	public java.util.Set getInterestedAuthors() {
		return interestedAuthors;
	}

	public void addInterestedAuthor(LighthouseAuthor author){
		if(this.interestedAuthors == null)
			interestedAuthors = new java.util.HashSet<LighthouseAuthor>();
		
		this.interestedAuthors.add(author);
	}

	
}
