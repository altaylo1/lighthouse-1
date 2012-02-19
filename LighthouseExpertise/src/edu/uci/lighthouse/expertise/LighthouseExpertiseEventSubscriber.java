package edu.uci.lighthouse.expertise;

import java.util.List;

import edu.uci.lighthouse.core.data.ISubscriber;
import edu.uci.lighthouse.model.LighthouseClass;
import edu.uci.lighthouse.model.LighthouseEntity;
import edu.uci.lighthouse.model.LighthouseEvent;
import edu.uci.lighthouse.model.LighthouseModel;
import edu.uci.lighthouse.model.LighthouseModelManager;
import edu.uci.lighthouse.model.QAforums.LHforum;
import edu.uci.lighthouse.ui.utils.GraphUtils;

public class LighthouseExpertiseEventSubscriber implements ISubscriber{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6013962858601965104L;

	public void receive(List<LighthouseEvent> events) {
		System.out.println("[LighthouseQAEventSubscriber.recieve]");
		for(LighthouseEvent event: events){
			if(event instanceof LighthouseEvent){
				//refresh updates sent by others
				
				if(event.getArtifact() != null && event.getArtifact() instanceof LighthouseEntity){
					LighthouseEntity entity = (LighthouseEntity)event.getArtifact();
					System.out.println("artifact is a lighthouse entity");
					if(entity != null && GraphUtils.getGraphViewer().findGraphItem(entity) != null){
						//update model
						LighthouseModelManager manager = new LighthouseModelManager(LighthouseModel.getInstance());
						LighthouseEntity clazz = manager.getMyClass(entity);
						
						if(clazz instanceof LighthouseClass){
							LighthouseClass theClazz = (LighthouseClass)clazz;
							
							theClazz.setDoiModel(((LighthouseClass)entity).getDoiModel());
						}
						System.out.println("[Rebuilding figure]");
						//rebuild figure
						GraphUtils.rebuildFigureForEntity(entity);
					}
				}
			}
		}
	}



}
