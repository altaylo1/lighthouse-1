package lighthousetagplugin;

import java.util.Collection;

import edu.uci.lighthouse.core.controller.PushModel;
import edu.uci.lighthouse.core.dbactions.push.AbstractEventAction;
import edu.uci.lighthouse.model.LighthouseEvent;
import edu.uci.lighthouse.model.jpa.JPAException;

public class TagUpdateEventAction extends AbstractEventAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TagUpdateEventAction(Collection<LighthouseEvent> events) {
		super(events);
	}

	@Override
	public void run() throws JPAException {
		PushModel.getInstance().saveEventsInDatabase(getEvents());
		
	}

}
