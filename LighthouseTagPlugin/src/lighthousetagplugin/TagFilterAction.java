package lighthousetagplugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;



import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import edu.uci.lighthouse.core.controller.Controller;
import edu.uci.lighthouse.model.LighthouseClass;
import edu.uci.lighthouse.model.LighthouseEntity;
import edu.uci.lighthouse.model.LighthouseModel;
import edu.uci.lighthouse.ui.utils.GraphUtils;
import edu.uci.lighthouse.ui.views.FilterManager;
import edu.uci.lighthouse.ui.views.actions.PluginAction;

public class TagFilterAction extends PluginAction implements IMenuCreator{
	
	private static final String DESCRIPTION = "Filter by watched classes.";
	
	private Map<String, FilterAction> cachedActions = new HashMap<String, FilterAction>();
	
	private Menu menu;

	public TagFilterAction() {
		super(null, Action.AS_DROP_DOWN_MENU);
		init();
		setMenuCreator(this);
	}
	
	protected void init() {
		setToolTipText(DESCRIPTION);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
		"/icons/tag.png"));

	}
	
	protected List<IAction> createActions() {
		List<IAction> result = new ArrayList<IAction>();
		
		Collection<LighthouseEntity> entities = LighthouseModel.getInstance().getEntities();
		
		for(LighthouseEntity entity: entities){
			if(entity instanceof LighthouseClass){
				LighthouseClass clazz = (LighthouseClass)entity;
				Set<String> tags = clazz.getTags();
				for(String tag: tags){
					FilterAction action = cachedActions.get(tag);
					if (action == null) {
						action = new FilterAction(tag, new TagFilter(tag));
						cachedActions.put(tag, action);
						result.add(action);
					}
					else{
						boolean skip = false;
						for(IAction resultAction: result){
							if(resultAction.getText().equals(tag)){
								
								skip = true;
								break;
							}
						}
						if(!skip)
						result.add(action);
					}
						
				}
			}
		}

		

	
	return result;
}
	
	private class FilterAction extends Action {
		ViewerFilter filter;
		public FilterAction(String name, ViewerFilter filter) {
			super(name, Action.AS_CHECK_BOX);
			this.filter = filter;
		}

		public void run() {
			
			if (isChecked()) {
				FilterManager.getInstance().addViewerFilter(filter);
			} else {
				FilterManager.getInstance().removeViewerFilter(filter);
			}
			GraphUtils.getGraphViewer().refresh();
			
		}
	}

	public void dispose() {
		if (menu != null) {
			menu.dispose();
			menu = null;
		}
	}

	public Menu getMenu(Control parent) {
		if (menu != null) {
			menu.dispose();
		}

		menu = new Menu(parent);
		for (IAction action : createActions()) {
			ActionContributionItem item = new ActionContributionItem(
					action);
			item.fill(menu, -1);
		}

		return menu;
	}

	public Menu getMenu(Menu parent) {
		return null;
	}

}

