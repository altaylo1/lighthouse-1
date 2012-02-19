package lighthousesoftlocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.Panel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import edu.uci.lighthouse.core.controller.Controller;
import edu.uci.lighthouse.core.controller.UpdateLighthouseModel;
import edu.uci.lighthouse.core.util.ModelUtility;
import edu.uci.lighthouse.model.LighthouseAuthor;
import edu.uci.lighthouse.model.LighthouseClass;
import edu.uci.lighthouse.model.LighthouseEntity;
import edu.uci.lighthouse.model.LighthouseEvent;
import edu.uci.lighthouse.ui.figures.CompartmentFigure;
import edu.uci.lighthouse.ui.figures.ILighthouseClassFigure.MODE;
import edu.uci.lighthouse.ui.utils.GraphUtils;

public class SoftLockFigure extends CompartmentFigure {
	private int NUM_COLUMNS = 2;
	SoftLockPanel lockPanel;
	Set<String> tags = new HashSet<String>();
	LighthouseClass clazz = null;
	Image icon;
	ImageFigure imageFigure;
	boolean watching = false;
	
	public SoftLockFigure(){
		GridLayout layout = new GridLayout();
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.numColumns = NUM_COLUMNS;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayoutManager(layout);

		icon = AbstractUIPlugin.imageDescriptorFromPlugin(
				Activator.PLUGIN_ID, "/icons/closed_eye.png").createImage();

		imageFigure = new ImageFigure(icon);
		this.add(imageFigure);

		imageFigure.addMouseListener(new SoftLockListener());
		lockPanel = new SoftLockPanel();
		GridLayout layout1 = new GridLayout();
		// layout.horizontalSpacing = 0;
		// layout.verticalSpacing = 0;
		layout1.numColumns = NUM_COLUMNS;

		// layout.marginHeight = 0;
		// layout.marginWidth = 0;
		lockPanel.setLayoutManager(layout1);

		this.add(lockPanel);
	}
	@Override
	public boolean isVisible(MODE mode) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void populate(MODE mode) {
		LighthouseClass clazz = (LighthouseClass)this.getUmlClass();
		Set<String> authorsWatching = clazz.getAuthorsWatching();
		LighthouseAuthor author = ModelUtility.getAuthor();
		
		watching = false;
		for(String authorName: authorsWatching){
			
			if(authorName.equals(author.getName())){
				watching = true;
				lockPanel.isWatching();
				icon = AbstractUIPlugin.imageDescriptorFromPlugin(
						Activator.PLUGIN_ID, "/icons/eye.png").createImage();
				imageFigure.setImage(icon);
				break;
			}
		}
		if(!watching){
			lockPanel.notWatching();
			icon = AbstractUIPlugin.imageDescriptorFromPlugin(
					Activator.PLUGIN_ID, "/icons/closed_eye.png").createImage();
			imageFigure.setImage(icon);
		}
		
		
	}
	
	private void saveData(LighthouseClass clazz) {
		LighthouseAuthor author = ModelUtility.getAuthor();
		
		Controller.getInstance().getBuffer().offer(new SoftLockUpdateAction(clazz));

		LighthouseEvent lh = new LighthouseEvent(LighthouseEvent.TYPE.CUSTOM,
				author, clazz);

		ArrayList<LighthouseEvent> listOfEvents = new ArrayList<LighthouseEvent>();
		listOfEvents.add(lh);

		UpdateLighthouseModel.addEvents(listOfEvents);
		ModelUtility.fireModificationsToUI(listOfEvents);

		SoftLockEventAction tagEventAction = new SoftLockEventAction(listOfEvents);

		Controller.getInstance().getBuffer().offer(tagEventAction);

		GraphUtils.rebuildFigureForEntity(clazz);
	}
	
	private class SoftLockPanel extends Panel {

		HashMap<String, Label> tagLabels = new HashMap<String, Label>();
		Label watchingLabel;
		
		public SoftLockPanel() {
			GridLayout layout = new GridLayout();
			layout.horizontalSpacing = 0;
			layout.verticalSpacing = 0;
			layout.numColumns = 3;
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			this.setLayoutManager(layout);
			 watchingLabel = new Label("not watching");
			this.add(watchingLabel);
		}
		
		public void isWatching(){
			this.watchingLabel.setText("watching");
		}
		
		public void notWatching(){
			this.watchingLabel.setText("not watching");
		}
		


	}

	private class SoftLockListener implements MouseListener {

		@Override
		public void mousePressed(MouseEvent me) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent me) {

			if(!watching){
				icon = AbstractUIPlugin.imageDescriptorFromPlugin(
						Activator.PLUGIN_ID, "/icons/eye.png").createImage();
				imageFigure.setImage(icon);
				
				LighthouseClass clazz = (LighthouseClass)getUmlClass();
				LighthouseAuthor author = ModelUtility.getAuthor();
				clazz.addAuthorWatching(author.getName());
				saveData(clazz);
				watching = true;
			}else{
				icon = AbstractUIPlugin.imageDescriptorFromPlugin(
						Activator.PLUGIN_ID, "/icons/closed_eye.png").createImage();
				imageFigure.setImage(icon);
				
				LighthouseClass clazz = (LighthouseClass)getUmlClass();
				LighthouseAuthor author = ModelUtility.getAuthor();
				clazz.removeAuthorWatching(author.getName());
				saveData(clazz);
				watching = false;
			}
		}

		@Override
		public void mouseDoubleClicked(MouseEvent me) {
			// TODO Auto-generated method stub

		}

	}

	
}
