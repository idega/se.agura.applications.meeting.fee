/*
 * $Id: MeetingFeeBusinessHomeImpl.java,v 1.2 2004/12/06 21:30:34 laddi Exp $
 * Created on 6.12.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package se.agura.applications.meeting.fee.business;




import com.idega.business.IBOHomeImpl;


/**
 * Last modified: $Date: 2004/12/06 21:30:34 $ by $Author: laddi $
 * 
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.2 $
 */
public class MeetingFeeBusinessHomeImpl extends IBOHomeImpl implements MeetingFeeBusinessHome {

	protected Class getBeanInterfaceClass() {
		return MeetingFeeBusiness.class;
	}

	public MeetingFeeBusiness create() throws javax.ejb.CreateException {
		return (MeetingFeeBusiness) super.createIBO();
	}

}
