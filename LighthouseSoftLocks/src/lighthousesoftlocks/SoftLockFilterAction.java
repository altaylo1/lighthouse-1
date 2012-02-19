/*******************************************************************************
* Copyright (c) {2009,2011} {Software Design and Collaboration Laboratory (SDCL)
*				, University of California, Irvine}.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*    {Software Design and Collaboration Laboratory (SDCL)
*	, University of California, Irvine}
*			- initial API and implementation and/or initial documentation
*******************************************************************************/
package lighthousesoftlocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.plugin.AbstractUIPlugin;



import edu.uci.lighthouse.ui.utils.GraphUtils;
import edu.uci.lighthouse.ui.views.FilterManager;
import edu.uci.lighthouse.ui.views.actions.PluginAction;

public class SoftLockFilterAction extends PluginAction implements IMenuCreator{
	
	private static final String DESCRIPTION = "Filter by watched classes.";
	
	private Map<String, FilterAction> cachedActions = new HashMap<String, FilterAction>();
	
	private Menu menu;

	public SoftLockFilterAction() {
		super(null, Action.AS_DROP_DOWN_MENU);
		init();
		setMenuCreator(this);
	}
	
	protected void init() {
		setToolTipText(DESCRIPTION);
		setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
		"/icons/eye.png"));

	}
	
	protected List<IAction> createActions() {
		List<IAction> result = new ArrayList<IAction>();
		
		String name1 = "watched";
		FilterAction action = cachedActions.get(name1);
		if (action == null) {
			action = new FilterAction(name1, new WatchingFilter());
			cachedActions.put(name1, action);
		}
		result.add(action);
		
		String name2 = "not watched";
		FilterAction action2 = cachedActions.get(name2);
		if (action2 == null) {
			action2 = new FilterAction(name2, new NotWatchingFilter());
			cachedActions.put(name2, action2);
		}
		result.add(action2);
	
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
