package edu.uci.lighthouse.lighthouseqandathreads.model;

import java.util.Vector;

import javax.persistence.Entity;

import edu.uci.lighthouse.model.LighthouseAuthor;

@Entity
public class ThreadCreator extends TeamMember {
	
	public ThreadCreator(LighthouseAuthor author){
		super(author);
	}
	
	public void resolveThread(Post answer) {
		throw new UnsupportedOperationException();
	}

	public void startThread(Post post) {
		throw new UnsupportedOperationException();
	}
}