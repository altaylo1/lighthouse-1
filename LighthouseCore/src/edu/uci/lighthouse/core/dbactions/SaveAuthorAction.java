package edu.uci.lighthouse.core.dbactions;

import edu.uci.lighthouse.model.LighthouseAuthor;
import edu.uci.lighthouse.model.LighthouseEntity;
import edu.uci.lighthouse.model.jpa.JPAException;
import edu.uci.lighthouse.model.jpa.LHAuthorDAO;
import edu.uci.lighthouse.model.jpa.LHEntityDAO;

public class SaveAuthorAction implements IDatabaseAction{

		private LighthouseAuthor author;
		
		public SaveAuthorAction(LighthouseAuthor author){
			this.author = author;
		}
		
		public void run() throws JPAException {
			LHAuthorDAO entityDAO = new LHAuthorDAO();
			try {
				entityDAO.save(author);
			} catch (JPAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}