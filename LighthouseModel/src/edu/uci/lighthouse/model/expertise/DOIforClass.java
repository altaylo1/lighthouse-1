package edu.uci.lighthouse.model.expertise;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import edu.uci.lighthouse.model.LighthouseAuthor;
import edu.uci.lighthouse.model.LighthouseClass;

/**
 * 
 * @author lee
 *
 */
@Entity
public class DOIforClass implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4165821217314911092L;

	@Id
	String id;
	

	
	private String className;
	
	
	private String authorName;
	
	private int interest;
	
	
	public DOIforClass(){}
	
	public DOIforClass(String clazz, String author){
		id = clazz+"_"+author;
		this.setClassName(clazz);
		this.setAuthorname(author);
	}
	

	public void setInterest(int interest) {
		this.interest = interest;
	}
	public int getInterest() {
		return interest;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}

	public void setAuthorname(String authorname) {
		this.authorName = authorname;
	}

	public String getAuthorname() {
		return authorName;
	}


}
