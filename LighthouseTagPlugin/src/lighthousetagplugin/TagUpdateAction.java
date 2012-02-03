package lighthousetagplugin;

import edu.uci.lighthouse.core.dbactions.IDatabaseAction;
import edu.uci.lighthouse.model.LighthouseEntity;
import edu.uci.lighthouse.model.jpa.JPAException;
import edu.uci.lighthouse.model.jpa.LHEntityDAO;

public class TagUpdateAction implements IDatabaseAction{

	private LighthouseEntity entity;
	
	public TagUpdateAction(LighthouseEntity entity){
		this.entity = entity;
	}
	
	public void run() throws JPAException {
		LHEntityDAO entityDAO = new LHEntityDAO();
		try {
			entityDAO.save(entity);
		} catch (JPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}