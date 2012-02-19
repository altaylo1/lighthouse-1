package lighthousetagplugin;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.zest.core.viewers.EntityConnectionData;

import edu.uci.lighthouse.core.util.ModelUtility;
import edu.uci.lighthouse.model.LighthouseAuthor;
import edu.uci.lighthouse.model.LighthouseClass;
import edu.uci.lighthouse.model.LighthouseRelationship;

public class TagFilter extends ViewerFilter{
	String tagName;
	
	public TagFilter(String tagName){
		this.tagName = tagName;
	}

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof LighthouseClass) {
			LighthouseAuthor author = ModelUtility.getAuthor();
			
			LighthouseClass aClass = (LighthouseClass) element;
			
			
			return aClass.getTags().contains(tagName);
			
		}else if (element instanceof LighthouseRelationship || element instanceof EntityConnectionData){
			return true;
		} 
		return false;
	}

}

