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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
import edu.uci.lighthouse.model.QAforums.Post;
import edu.uci.lighthouse.model.expertise.DOIforClass;



@Entity
public class LighthouseClass extends LighthouseEntity {
	
	private static final long serialVersionUID = 2097778395729254060L;
	
	
	/**@author: Lee*/
	//ArrayList<LHclassPluginExtension> extensions = new ArrayList<LHclassPluginExtension>(); 
	
	/**@author lee*/
	 @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private LHforum forum = new LHforum(this.getFullyQualifiedName());

	 /**@author lee*/
		@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
		private Set<DOIforClass> doiModel = new HashSet<DOIforClass>();
		
		@CollectionOfElements(fetch = FetchType.EAGER)
		private Set<String> tags = new HashSet<String>();
	
	
	protected LighthouseClass() {
		this("");
		forum = new LHforum(this.getFullyQualifiedName());
		
	//	loadExtensions();
	}

	public LighthouseClass(String fqn) {
		super(fqn);
		forum = new LHforum(this.getFullyQualifiedName());
		
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

	
	public void setDoiModel(Set<DOIforClass> doiModel) {
		this.doiModel = doiModel;
	}

	public Set<DOIforClass> getDoiModel() {
		return doiModel;
	}
	
	/**
	 * returns ascending order by interest
	 * @return
	 */
	public ArrayList<DOIforClass> getSortedDoiModel(){
		
		int i;
		int min;

		ArrayList<DOIforClass> sortedDoi = new ArrayList<DOIforClass>();
		sortedDoi.addAll(this.doiModel);

		int size = sortedDoi.size();
		
		for (i = 0; i < size; i++) {
			min = i;
			for (int j = i + 1; j < size; j++) {
				if (sortedDoi.get(j).getInterest()
						< sortedDoi.get(min).getInterest()) {
					min = j;
				}
			}

			DOIforClass temp = sortedDoi.get(i);
			sortedDoi.set(i, sortedDoi.get(min));
			sortedDoi.set(min, temp);
		}

		return sortedDoi;
		
	}
	
	
	/**
	 * 
	 * @param clazz
	 * @param amount
	 */
	public void addToInterest(LighthouseAuthor author, int amount){
		System.out.println("[add to interest]"+doiModel.size());
		for(DOIforClass doiForClass: doiModel){
			System.out.println(doiForClass.getClassName() +"=?="+this.getFullyQualifiedName());
			
			if(doiForClass.getAuthorname().equals(author.getName())){

				int total = doiForClass.getInterest()+amount;
				doiForClass.setInterest(total);
				return;
			}
		}
		
		System.out.println("could not find: "+author.getName());
		DOIforClass doi = new DOIforClass(this.getFullyQualifiedName(), author.getName());
		doi.setInterest(amount);
		doiModel.add(doi);
		
	}

	/**
	 * return 0 if no interest can be found. 
	 * @param clazz
	 * @return
	 */
	public int getInterest(LighthouseAuthor author){
		System.out.println("[get interest] "+doiModel.size());
		for(DOIforClass doiForClass: doiModel){
			
			if(doiForClass.getAuthorname().equals(author.getName())){
				return doiForClass.getInterest();
			}
		}
		return 0;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	public Set<String> getTags() {
		return tags;
	}

	public void addTag(String tag){
		this.tags.add(tag);
	}
	
	public void removeTag(String tag){
		this.tags.remove(tag);
		
	}
	
}
