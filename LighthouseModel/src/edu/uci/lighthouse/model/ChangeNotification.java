package edu.uci.lighthouse.model;

public class ChangeNotification implements INotification{
	private String className;
	
	public ChangeNotification(String className){
		this.setClassName(className);
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}
}
