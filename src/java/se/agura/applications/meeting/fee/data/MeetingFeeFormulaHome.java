/*
 * $Id: MeetingFeeFormulaHome.java,v 1.1 2004/12/05 20:59:37 anna Exp $
 * Created on 5.12.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package se.agura.applications.meeting.fee.data;

import javax.ejb.FinderException;
import com.idega.data.IDOHome;


/**
 * Last modified: 5.12.2004 15:25:51 by: anna
 * 
 * @author <a href="mailto:anna@idega.com">anna</a>
 * @version $Revision: 1.1 $
 */
public interface MeetingFeeFormulaHome extends IDOHome {

	public MeetingFeeFormula create() throws javax.ejb.CreateException;

	public MeetingFeeFormula findByPrimaryKey(Object pk) throws javax.ejb.FinderException;

	/**
	 * @see se.agura.applications.meeting.fee.data.MeetingFeeFormulaBMPBean#ejbFindLatestFormula
	 */
	public MeetingFeeFormula findLatestFormula() throws FinderException;
}
