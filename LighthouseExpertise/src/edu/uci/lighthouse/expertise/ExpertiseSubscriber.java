package edu.uci.lighthouse.expertise;

import java.util.ArrayList;
import java.util.List;

import edu.uci.lighthouse.core.controller.Controller;
import edu.uci.lighthouse.core.controller.UpdateLighthouseModel;
import edu.uci.lighthouse.core.data.ISubscriber;
import edu.uci.lighthouse.core.dbactions.OpenFileAction;
import edu.uci.lighthouse.core.dbactions.SaveAuthorAction;
import edu.uci.lighthouse.core.dbactions.push.OpenEventAction;
import edu.uci.lighthouse.core.util.ModelUtility;
import edu.uci.lighthouse.model.INotification;
import edu.uci.lighthouse.model.INotificationSubscriber;
import edu.uci.lighthouse.model.LighthouseAuthor;
import edu.uci.lighthouse.model.LighthouseClass;
import edu.uci.lighthouse.model.LighthouseEntity;
import edu.uci.lighthouse.model.LighthouseEvent;
import edu.uci.lighthouse.model.LighthouseModel;
import edu.uci.lighthouse.model.LighthouseModelManager;
import edu.uci.lighthouse.model.OpenNotification;
import edu.uci.lighthouse.ui.utils.GraphUtils;
import org.eclipse.zest.core.viewers.GraphViewer;

public class ExpertiseSubscriber implements INotificationSubscriber {

	@Override
	public void receive(INotification notification) {

		if (notification instanceof OpenNotification) {
			OpenNotification openNotification = (OpenNotification)notification;
			
			LighthouseModelManager manager = new LighthouseModelManager(
					LighthouseModel.getInstance());
			
			LighthouseClass clazz = (LighthouseClass) manager
					.getEntity(openNotification.getClassName());
			
			if (clazz != null) {
				LighthouseAuthor author = ModelUtility.getAuthor();
				
				clazz.addToInterest(author,1);

				

				Controller.getInstance().getBuffer()
						.offer(new OpenFileAction(clazz));


				LighthouseEvent lh = new LighthouseEvent(
						LighthouseEvent.TYPE.CUSTOM, author, clazz);

				ArrayList<LighthouseEvent> listOfEvents = new ArrayList<LighthouseEvent>();
				listOfEvents.add(lh);
				
				UpdateLighthouseModel.addEvents(listOfEvents);
				ModelUtility.fireModificationsToUI(listOfEvents);
				
				

				OpenEventAction openEventAction = new OpenEventAction(
						listOfEvents);
								
				Controller.getInstance().getBuffer().offer(openEventAction);

				
				GraphUtils.rebuildFigureForEntity(clazz);

			}
		}

	}

}
