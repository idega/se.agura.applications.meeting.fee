/*
 * $Id: MeetingFeeInfoHomeImpl.java,v 1.1 2004/12/05 20:59:37 anna Exp $
 * Created on 5.12.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package se.agura.applications.meeting.fee.data;

import java.util.Collection;
import javax.ejb.FinderException;
import com.idega.data.IDOFactory;
import com.idega.user.data.User;


/**
 * Last modified: 5.12.2004 16:18:02 by: anna
 * 
 * @author <a href="mailto:anna@idega.com">anna</a>
 * @version $Revision: 1.1 $
 */
public class MeetingFeeInfoHomeImpl extends IDOFactory implements MeetingFeeInfoHome {

	protected Class getEntityInterfaceClass() {
		return MeetingFeeInfo.class;
	}

	public MeetingFeeInfo create() throws javax.ejb.CreateException {
		return (MeetingFeeInfo) super.createIDO();
	}

	public MeetingFeeInfo findByPrimaryKey(Object pk) throws javax.ejb.FinderException {
		return (MeetingFeeInfo) super.findByPrimaryKeyIDO(pk);
	}

	public Collection findByMeetingFee(MeetingFee meetingFee) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		java.util.Collection ids = ((MeetingFeeInfoBMPBean) entity).ejbFindByMeetingFee(meetingFee);
		this.idoCheckInPooledEntity(entity);
		return this.getEntityCollectionForPrimaryKeys(ids);
	}

	public MeetingFeeInfo findByUserAndMeetingFee(User user, MeetingFee meetingFee) throws FinderException {
		com.idega.data.IDOEntity entity = this.idoCheckOutPooledEntity();
		Object pk = ((MeetingFeeInfoBMPBean) entity).ejbFindByUserAndMeetingFee(user, meetingFee);
		this.idoCheckInPooledEntity(entity);
		return this.findByPrimaryKey(pk);
	}
}
