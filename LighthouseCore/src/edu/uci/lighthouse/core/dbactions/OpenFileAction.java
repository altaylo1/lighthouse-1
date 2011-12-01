package edu.uci.lighthouse.core.dbactions;

import edu.uci.lighthouse.model.LighthouseEntity;
import edu.uci.lighthouse.model.jpa.JPAException;
import edu.uci.lighthouse.model.jpa.LHEntityDAO;

public class OpenFileAction implements IDatabaseAction{

	private LighthouseEntity entity;
	
	public OpenFileAction(LighthouseEntity entity){
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
