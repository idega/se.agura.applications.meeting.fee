/*
 * $Id: MeetingFeeHomeImpl.java,v 1.2 2004/12/06 21:30:34 laddi Exp $
 * Created on 6.12.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package se.agura.applications.meeting.fee.data;



import com.idega.data.IDOFactory;


/**
 * Last modified: $Date: 2004/12/06 21:30:34 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.2 $
 */
public class MeetingFeeHomeImpl extends IDOFactory implements MeetingFeeHome {

	protected Class getEntityInterfaceClass() {
		return MeetingFee.class;
	}

	public MeetingFee create() throws javax.ejb.CreateException {
		return (MeetingFee) super.createIDO();
	}

	public MeetingFee findByPrimaryKey(Object pk) throws javax.ejb.FinderException {
		return (MeetingFee) super.findByPrimaryKeyIDO(pk);
	}

}
