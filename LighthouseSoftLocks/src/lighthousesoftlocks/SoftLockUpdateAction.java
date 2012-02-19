package lighthousesoftlocks;

import edu.uci.lighthouse.core.dbactions.IDatabaseAction;
import edu.uci.lighthouse.model.LighthouseAuthor;
import edu.uci.lighthouse.model.LighthouseClass;
import edu.uci.lighthouse.model.LighthouseEntity;
import edu.uci.lighthouse.model.jpa.JPAException;
import edu.uci.lighthouse.model.jpa.LHAuthorDAO;
import edu.uci.lighthouse.model.jpa.LHEntityDAO;


public class SoftLockUpdateAction implements IDatabaseAction{

	private LighthouseClass entity;
	
	public SoftLockUpdateAction(LighthouseClass entity){
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

