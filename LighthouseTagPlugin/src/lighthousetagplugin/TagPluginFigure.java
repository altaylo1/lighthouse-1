package lighthousetagplugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.Panel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import edu.uci.lighthouse.model.LighthouseClass;
import edu.uci.lighthouse.model.LighthouseEntity;
import edu.uci.lighthouse.ui.figures.CompartmentFigure;
import edu.uci.lighthouse.ui.figures.ILighthouseClassFigure.MODE;

import org.eclipse.draw2d.MouseListener;

public class TagPluginFigure extends CompartmentFigure {
	private int NUM_COLUMNS = 2;
	TagPanel tagPanel;
	Set<String> tags = new HashSet<String>();
	LighthouseClass clazz = null;

	public TagPluginFigure() {
		GridLayout layout = new GridLayout();
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.numColumns = NUM_COLUMNS;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayoutManager(layout);

		Image icon = AbstractUIPlugin.imageDescriptorFromPlugin(
				Activator.PLUGIN_ID, "/icons/tag.png").createImage();

		ImageFigure imageFigure = new ImageFigure(icon);
		this.add(imageFigure);

		imageFigure.addMouseListener(new TagMouseListener());
		tagPanel = new TagPanel();
		GridLayout layout1 = new GridLayout();
		//layout.horizontalSpacing = 0;
		//layout.verticalSpacing = 0;
		layout1.numColumns = NUM_COLUMNS;	
		
		//layout.marginHeight = 0;
		//layout.marginWidth = 0; 
		tagPanel.setLayoutManager(layout1);
		
		this.add(tagPanel);

	}

	@Override
	public boolean isVisible(MODE mode) {
		return true;
	}

	@Override
	public void populate(MODE mode) {
		LighthouseEntity entity = this.getUmlClass();
		if (entity instanceof LighthouseClass) {

			clazz = (LighthouseClass) entity;
			tags = clazz.getTags();

			tagPanel.populate(clazz.getTags());

		}
	}

	private class TagPanel extends Panel {

		HashMap<String, Label> tagLabels = new HashMap<String, Label>();

		public TagPanel() {
			GridLayout layout = new GridLayout();
			layout.horizontalSpacing = 0;
			layout.verticalSpacing = 0;
			layout.numColumns = 3;
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			this.setLayoutManager(layout);

		}

		public void populate(Set<String> tags) {
			for (String tagName : tags) {
				if (tagLabels.get(tagName) == null) {
					Label nameLabel = new Label(" " + tagName);
					tagLabels.put(tagName, nameLabel);
					this.add(nameLabel);
				}
			}
		}

		/**
		 * can not add a tag that already is present
		 * 
		 * @param tagName
		 */
		public void addTag(String tagName, LighthouseClass clazz) {

			if (tagLabels.get(tagName) == null) {
				Label nameLabel = new Label(" " + tagName);
				tagLabels.put(tagName, nameLabel);
				this.add(nameLabel);
				// clazz add
				clazz.addTag(tagName);
			}
		}

		public void removeTag(String tagName, LighthouseClass clazz) {
			// remove label
			this.remove(tagLabels.get(tagName));
			// remove from mapping
			tagLabels.remove(tagName);
			// clazz remove
			clazz.removeTag(tagName);
		}

		/**
		 * can only modify a tag that exists
		 * 
		 * @param tagName
		 * @param newTagName
		 */
		public void modifyTag(String tagName, String newTagName,
				LighthouseClass clazz) {
			Label tagLabel = tagLabels.get(tagName);
			if (tagLabel != null) {
				// modify label text
				tagLabel.setText(newTagName);

				// update mapping
				tagLabels.remove(tagName);
				tagLabels.put(newTagName, tagLabel);
				// clazz modify
				clazz.removeTag(tagName);
				clazz.addTag(newTagName);

			}
		}

	}

	private class TagMouseListener implements MouseListener {

		@Override
		public void mousePressed(MouseEvent me) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent me) {
			SWTDialog dialog = new SWTDialog(PlatformUI.getWorkbench()
					.getDisplay().getActiveShell());
			String tagName = (String) dialog.open();

		}

		@Override
		public void mouseDoubleClicked(MouseEvent me) {
			// TODO Auto-generated method stub

		}

	}

	private class SWTDialog extends Dialog {
		String result;
		Shell shell;
		org.eclipse.swt.widgets.Button addButton;
		org.eclipse.swt.widgets.Button removeButton;
		org.eclipse.swt.widgets.Button modifyButton;
		org.eclipse.swt.widgets.Button cancelButton;
		org.eclipse.swt.widgets.Text textBox;
		org.eclipse.swt.widgets.List tagList;
		public SWTDialog(Shell parent, int style) {
			super(parent, style);
		}

		public SWTDialog(Shell parent) {
			this(parent, 0); // your default style bits go here (not the Shell's
								// style bits)
		}

		public Object open() {
			Shell parent = getParent();
			shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			shell.setText(getText());

			org.eclipse.swt.layout.GridLayout gridLayout = new org.eclipse.swt.layout.GridLayout();
			gridLayout.numColumns = 1;

			shell.setSize(200, 500);
			shell.setLayout(gridLayout);

			shell.setText("Enter Tag");

			GridData gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			gridData.grabExcessVerticalSpace = true;
			gridData.horizontalAlignment = GridData.FILL;
			gridData.verticalAlignment = GridData.FILL;

			// List of tags
			tagList = new org.eclipse.swt.widgets.List(
					shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
			tagList.setLayoutData(gridData);
			tagList.addListener(SWT.Selection , new ListListener());
			
			
			
			
			for (String tag : tags) {
				tagList.add(tag);
			}

			// text box
			textBox = new org.eclipse.swt.widgets.Text(shell, 0);

			textBox.setLayoutData(gridData);

			TagButtonListener listener = new TagButtonListener();

			// buttons
			addButton = new org.eclipse.swt.widgets.Button(shell, 0);
			addButton.setText("Add");
			addButton.addMouseListener(listener);

			removeButton = new org.eclipse.swt.widgets.Button(
					shell, 0);
			removeButton.setText("Remove");
			removeButton.addMouseListener(listener);

			modifyButton = new org.eclipse.swt.widgets.Button(
					shell, 0);
			modifyButton.setText("Modify");
			modifyButton.addMouseListener(listener);

			cancelButton = new org.eclipse.swt.widgets.Button(shell, 0);
			cancelButton.setText("Cancel");
			cancelButton.addMouseListener(listener);

			shell.open();
			Display display = parent.getDisplay();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
			return result;
		}
		
		private class ListListener implements Listener{

			@Override
			public void handleEvent(Event event) {
				textBox.setText(tagList.getSelection()[0]);
				
			}
			
		}

		private class TagButtonListener implements
				org.eclipse.swt.events.MouseListener {

			@Override
			public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {

			}

			@Override
			public void mouseUp(org.eclipse.swt.events.MouseEvent e) {
				
				
				if (e.getSource() == addButton) {
					result = textBox.getText();
					tagPanel.addTag(result, clazz);
					textBox.setText("");
					shell.close();

				} else if (e.getSource() == removeButton) {
					result = tagList.getSelection()[0];
					tagPanel.removeTag(result, clazz);
					textBox.setText("");
					shell.close();

				} else if (e.getSource() == modifyButton) {
					result = textBox.getText();
					 tagPanel.modifyTag(tagList.getSelection()[0],result,clazz);
					textBox.setText("");
					shell.close();

				} else if (e.getSource() == cancelButton) {
					result = null;
					textBox.setText("");
					shell.close();
				}

			}

		}
	}

}
