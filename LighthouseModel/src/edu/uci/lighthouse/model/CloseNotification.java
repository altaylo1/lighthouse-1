package edu.uci.lighthouse.model;

public class CloseNotification implements INotification{
	private String className;
	
	public CloseNotification(String className){
		this.setClassName(className);
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}
}