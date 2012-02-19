package edu.uci.lighthouse.ui.views.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;



import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import edu.uci.lighthouse.model.LighthouseClass;
import edu.uci.lighthouse.model.LighthouseEntity;
import edu.uci.lighthouse.model.LighthouseModel;
import edu.uci.lighthouse.ui.figures.CompartmentFigure;
import edu.uci.lighthouse.ui.utils.GraphUtils;
import edu.uci.lighthouse.ui.views.FilterManager;
import edu.uci.lighthouse.views.filters.ICompartmentFilter;
import edu.uci.lighthouse.views.filters.PluginFilter;

public class FilterByPlugin extends PluginAction implements IMenuCreator{

	private static final String DESCRIPTION = "Compartment filter by plugin.";
	
	private Map<String, FilterAction> cachedActions = new HashMap<String, FilterAction>();
	
	private Menu menu;
	
	public FilterByPlugin() {
		super(null, Action.AS_DROP_DOWN_MENU);
		init();
		setMenuCreator(this);
	}
	
	protected void init() {
		setToolTipText(DESCRIPTION);

		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("edu.uci.lighthouse.ui",
		"/icons/sample.gif"));

	}


	
	protected List<IAction> createActions() {
		List<IAction> result = new ArrayList<IAction>();
		
		Collection<LighthouseEntity> entities = LighthouseModel.getInstance().getEntities();
		
		try {
			IConfigurationElement[] config = Platform.getExtensionRegistry()
					.getConfigurationElementsFor("edu.uci.lighthouse.ui.figures.compartment");
			
			for (IConfigurationElement e : config) {
				final Object o = e.createExecutableExtension("class");
				if (o instanceof CompartmentFigure) {
					CompartmentFigure compartment = (CompartmentFigure)o;
					
					String name1 = compartment.getClass().getName();
					
					FilterAction action = cachedActions.get(name1);
					if (action == null) {
						action = new FilterAction(name1, new PluginFilter(compartment.getClass()));
						cachedActions.put(name1, action);
					}
					result.add(action);
					
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		


		

	
	return result;
}
	
	private class FilterAction extends Action {
		ICompartmentFilter filter;
		public FilterAction(String name, ICompartmentFilter filter) {
			super(name, Action.AS_CHECK_BOX);
			this.filter = filter;
		}

		public void run() {
			
			if (isChecked()) {
				FilterManager.getInstance().addCompartmentFilter(filter);
			} else {
				FilterManager.getInstance().removeCompartmentFilter(filter);
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
