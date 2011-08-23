package edu.uci.lighthouse.model.QAforums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import edu.uci.lighthouse.model.LighthouseAuthor;

@Entity
public class TeamMember implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1320293063793751427L;
	@OneToOne
	private LighthouseAuthor author;
	
	 @OneToMany
	Collection<Post> posts = new ArrayList<Post>();
	
	public TeamMember(LighthouseAuthor author){
		this.setAuthor(author);
	}
	
	public void respondToThread(ForumThread aThread) {
		throw new UnsupportedOperationException();
	}

	public void setAuthor(LighthouseAuthor author) {
		this.author = author;
	}

	public LighthouseAuthor getAuthor() {
		return author;
	}
}