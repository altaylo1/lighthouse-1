package lighthousetagplugin;

import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

import edu.uci.lighthouse.ui.figures.CompartmentFigure;
import edu.uci.lighthouse.ui.figures.ILighthouseClassFigure.MODE;

public class TagPluginFigure extends CompartmentFigure {
	private int NUM_COLUMNS = 1;

	public TagPluginFigure(){
		GridLayout layout = new GridLayout();
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.numColumns = NUM_COLUMNS;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		setLayoutManager(layout);
		
		Image icon = AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
		"/icons/tag.png").createImage();
		
		ImageFigure imageFigure = new ImageFigure(icon);
		this.add(imageFigure);
		
	}
	@Override
	public boolean isVisible(MODE mode) {
		return true;
	}

	@Override
	public void populate(MODE mode) {
		
		
	}

}
