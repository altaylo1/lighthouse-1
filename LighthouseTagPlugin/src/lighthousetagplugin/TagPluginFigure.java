package lighthousetagplugin;

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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import edu.uci.lighthouse.ui.figures.CompartmentFigure;
import edu.uci.lighthouse.ui.figures.ILighthouseClassFigure.MODE;

import org.eclipse.draw2d.MouseListener;

public class TagPluginFigure extends CompartmentFigure {
	private int NUM_COLUMNS = 2;
	TagPanel tagPanel;
	
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
		 this.add(tagPanel);
		 
	}

	@Override
	public boolean isVisible(MODE mode) {
		return true;
	}

	@Override
	public void populate(MODE mode) {

	}
	
	private class TagPanel extends Panel{
		
		public TagPanel(){
			GridLayout layout = new GridLayout();
			layout.horizontalSpacing = 0;
			layout.verticalSpacing = 0;
			layout.numColumns = 3;
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			this.setLayoutManager(layout);

		}
		
		public void addTag(String tagName){			
			Label nameLabel = new Label(" "+tagName);
			this.add(nameLabel);
		}

	}

	private class TagMouseListener implements MouseListener {

		@Override
		public void mousePressed(MouseEvent me) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent me) {
			SWTDialog dialog = new SWTDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell());
			String tagName = (String)dialog.open();
			if(tagName != null){
				tagPanel.addTag(tagName);
			}
		}

		@Override
		public void mouseDoubleClicked(MouseEvent me) {
			// TODO Auto-generated method stub

		}

	}

	private class SWTDialog extends Dialog {
		String result;
		Shell shell;
		org.eclipse.swt.widgets.Button okButton;
		org.eclipse.swt.widgets.Button cancelButton;
		org.eclipse.swt.widgets.Text textBox;

		public SWTDialog(Shell parent, int style) {
			super(parent, style);
		}

		public SWTDialog(Shell parent) {
			this(parent, 0); // your default style bits go here (not the Shell's
								// style bits)
		}

		public Object open() {
			Shell parent = getParent();
			shell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL);
			shell.setText(getText());
			
			org.eclipse.swt.layout.GridLayout gridLayout = new org.eclipse.swt.layout.GridLayout();
              gridLayout.numColumns = 1;
              
              
            shell.setSize(200, 400);
              shell.setLayout(gridLayout);
			
			shell.setText("Enter Tag");
              
			// Your code goes here (widget creation, set result, etc).
			textBox = new org.eclipse.swt.widgets.Text(shell, 0);
		    GridData gridData = new GridData();
		    gridData.grabExcessHorizontalSpace = true;
		    gridData.grabExcessVerticalSpace = true;
		    gridData.horizontalAlignment = GridData.FILL;
		    gridData.verticalAlignment = GridData.FILL;
		    textBox.setLayoutData(gridData);
			
		    TagButtonListener listener = new TagButtonListener();
		    
		    okButton = new org.eclipse.swt.widgets.Button(shell,0);
		    okButton.setText("Okay");
		    okButton.addMouseListener(listener);
		    
		    cancelButton = new org.eclipse.swt.widgets.Button(shell,0);
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
		
		
		private class TagButtonListener implements org.eclipse.swt.events.MouseListener{

			@Override
			public void mouseDoubleClick(org.eclipse.swt.events.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseDown(org.eclipse.swt.events.MouseEvent e) {
				if(e.getSource() == okButton){
					result = textBox.getText();
					shell.close();
					
				}else if(e.getSource() == cancelButton){
					result = null;
					shell.close();
				}
				
			}

			@Override
			public void mouseUp(org.eclipse.swt.events.MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		}
	}

}
