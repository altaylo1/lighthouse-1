package edu.uci.lighthouse.model;

public class OpenNotification implements INotification{
	private String className;
	
	public OpenNotification(String className){
		this.setClassName(className);
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}
}
