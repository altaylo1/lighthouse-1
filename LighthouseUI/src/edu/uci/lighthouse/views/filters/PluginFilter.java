package edu.uci.lighthouse.views.filters;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import edu.uci.lighthouse.model.LighthouseEntity;
import edu.uci.lighthouse.model.LighthouseEvent;
import edu.uci.lighthouse.model.LighthouseModel;

public class PluginFilter  implements  ICompartmentFilter{

	private Class compartmentClazz;
	
	public PluginFilter(Class clazz){
		this.compartmentClazz = clazz;
	}
	public boolean isVisble(Class clazz) {
		
		if( clazz.equals(compartmentClazz))
			return false;
		
		return true;
	}




}
