/*
 * $Id: MeetingFeeBlock.java,v 1.10 2006/04/09 11:47:32 laddi Exp $
 * Created on 25.11.2004
 *
 * Copyright (C) 2004 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package se.agura.applications.meeting.fee.presentation;

import java.rmi.RemoteException;
import java.util.Collection;
import javax.ejb.FinderException;
import se.agura.applications.meeting.fee.business.MeetingFeeBusiness;
import se.agura.applications.meeting.fee.business.MeetingFeeConstants;
import se.agura.applications.meeting.fee.data.MeetingFee;
import se.agura.applications.meeting.fee.data.MeetingFeeFormula;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.core.builder.data.ICPage;
import com.idega.idegaweb.IWApplicationContext;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.Block;
import com.idega.presentation.IWContext;
import com.idega.presentation.Table;
import com.idega.presentation.text.Break;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.BackButton;
import com.idega.presentation.ui.DropdownMenu;
import com.idega.presentation.ui.GenericButton;
import com.idega.presentation.ui.InterfaceObject;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.util.SelectorUtility;
import com.idega.user.business.UserBusiness;
import com.idega.user.data.User;


/**
 * Last modified: 25.11.2004 09:11:42 by: anna
 * 
 * @author <a href="mailto:anna@idega.com">anna</a>
 * @version $Revision: 1.10 $
 */
public abstract class MeetingFeeBlock extends Block {
	
	protected static final String IW_BUNDLE_IDENTIFIER = "se.agura.applications.meeting.fee";
	
	protected static final String PARAMETER_PARTICIPANT_USER_ID = "me_meeting_fee_participant_user_id";
	protected static final String PARAMETER_MEETING_FEE_DATE = "me_meeting_fee_meeting_date";
	protected static final String PARAMETER_MEETING_FEE_CONGREGATION = "me_meeting_fee_congregation";
	protected static final String PARAMETER_MEETING_FEE_MEETING_LOCATION = "me_meeting_fee_meeting_location";
	protected static final String PARAMETER_MEETING_FEE_COMMENT = "me.meeting_comment";
	protected static final String PARAMETER_MEETING_FEE_PARTICIPANTS = "me_meeting_fee_participants";
	protected static final String PARAMETER_ACTION = "me_action";
	protected static final String PARAMETER_MEETING_FEE_ID = MeetingFeeConstants.PARAMETER_PRIMARY_KEY;
	protected static final String PARAMETER_MEETING_FEE_INFO_ID = "me.meeting_fee_info_id";
	
	protected static final String ACTION_EDIT = "edit";
	protected static final String ACTION_NEW = "new";
	protected static final String ACTION_NEXT = "next";
	protected static final String ACTION_SAVE = "save";
	protected static final String ACTION_SEND = "send";
	protected static final String ACTION_REJECT = "reject";
	protected static final String ACTION_BACK = "back";
	protected static final String ACTION_SAVE_FINALLY = "save_finally";
	protected static final String ACTION_CANCEL = "cancel";
	protected static final String ACTION_CLOSE = "close";
	
	private String iTextStyleClass;
	private String iHeaderStyleClass;
	private String iLinkStyleClass;
	private String iInputStyleClass;
	private String iButtonStyleClass;
	private String iRadioStyleClass;
	
	protected int iCellpadding = 3;
	protected int iHeaderColumnWidth = 260;
	protected String iWidth = Table.HUNDRED_PERCENT;

	private ICPage iPage;

	private IWBundle iwb;
	private IWResourceBundle iwrb;
	
	public void main(IWContext iwc) throws Exception {
		this.iwb = getBundle(iwc);
		this.iwrb = getResourceBundle(iwc);
		present(iwc);
	}
	
	protected DropdownMenu getCongregationMenu(IWContext iwc) {
		try {
			SelectorUtility util = new SelectorUtility();
			DropdownMenu menu = (DropdownMenu) getInput(util.getSelectorFromIDOEntities(new DropdownMenu(PARAMETER_MEETING_FEE_CONGREGATION), getBusiness(iwc).getParishes(), "getName"));
			return menu;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}
	
	protected DropdownMenu getParticipantsMenu(IWContext iwc, User user) {
		try {
			SelectorUtility util = new SelectorUtility();
			DropdownMenu menu = (DropdownMenu) getInput(util.getSelectorFromIDOEntities(new DropdownMenu(PARAMETER_MEETING_FEE_PARTICIPANTS), getBusiness(iwc).getMeetingGroups(user), "getName"));
			return menu;
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}
	
	public MeetingFee getMeetingFee(IWContext iwc) {
		MeetingFee meetingFee = null;
		try {
			meetingFee = getBusiness(iwc).getMeetingFee(iwc.getParameter(PARAMETER_MEETING_FEE_ID));
		}
		catch (RemoteException re) {
			log(re);
		}
		catch (FinderException fe) {
			log(fe);
		}
		return meetingFee;
	}
	
	public Collection getMeetingFeeInfo(IWContext iwc, MeetingFee meetingFee) {
		Collection collection = null;
		try {
			collection = getBusiness(iwc).getMeetingFeeInfo(meetingFee);
		}
		catch (RemoteException re) {
			log(re);
		}
		catch (FinderException fe) {
			log(fe);
		}
		return collection;
	}
	
	public MeetingFeeFormula getMeetingFeeFormula(IWContext iwc) {
		try {
			return getBusiness(iwc).getMeetingFeeFormula();
		}
		catch (FinderException fe) {
			throw new IBORuntimeException("No formula exists in database...");
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}
	
	protected MeetingFeeBusiness getBusiness(IWApplicationContext iwac) {
		try {
			return (MeetingFeeBusiness) IBOLookup.getServiceInstance(iwac, MeetingFeeBusiness.class);
		}
		catch (IBOLookupException ible) {
			throw new IBORuntimeException(ible);
		}
	}

	protected UserBusiness getUserBusiness(IWApplicationContext iwac) {
		try {
			return (UserBusiness) IBOLookup.getServiceInstance(iwac, UserBusiness.class);
		}
		catch (IBOLookupException ible) {
			throw new IBORuntimeException(ible);
		}
	}

	public abstract void present(IWContext iwc);

	public String getBundleIdentifier() {
		return IW_BUNDLE_IDENTIFIER;
	}

	/**
	 * @return Returns the iwb.
	 */
	protected IWBundle getBundle() {
		return this.iwb;
	}

	/**
	 * @return Returns the iwrb.
	 */
	protected IWResourceBundle getResourceBundle() {
		return this.iwrb;
	}
	protected Text getText(String string) {
		Text text = new Text(string);
		if (this.iTextStyleClass != null) {
			text.setStyleClass(this.iTextStyleClass);
		}
		return text;
	}
	
	protected Text getHeader(String string) {
		Text text = new Text(string);
		if (this.iHeaderStyleClass != null) {
			text.setStyleClass(this.iHeaderStyleClass);
		}
		return text;
	}
	
	protected Link getLink(String string) {
		Link link = new Link(string);
		if (this.iLinkStyleClass != null) {
			link.setStyleClass(this.iLinkStyleClass);
		}
		return link;
	}
	
	protected InterfaceObject getInput(InterfaceObject input) {
		if (this.iInputStyleClass != null) {
			input.setStyleClass(this.iInputStyleClass);
		}
		return input;
	}
	
	protected InterfaceObject getRadioButton(InterfaceObject radioButton) {
		if (this.iRadioStyleClass != null) {
			radioButton.setStyleClass(this.iRadioStyleClass);
		}
		return radioButton;
	}
	
	protected GenericButton getButton(GenericButton button) {
		if (this.iButtonStyleClass != null) {
			button.setStyleClass(this.iButtonStyleClass);
		}
		return button;
	}
	
	/**
	 * @param buttonStyleClass The buttonStyleClass to set.
	 */
	public void setButtonStyleClass(String buttonStyleClass) {
		this.iButtonStyleClass = buttonStyleClass;
	}
	/**
	 * @param headerStyleClass The headerStyleClass to set.
	 */
	public void setHeaderStyleClass(String headerStyleClass) {
		this.iHeaderStyleClass = headerStyleClass;
	}
	/**
	 * @param inputStyleClass The inputStyleClass to set.
	 */
	public void setInputStyleClass(String inputStyleClass) {
		this.iInputStyleClass = inputStyleClass;
	}
	/**
	 * @param linkStyleClass The linkStyleClass to set.
	 */
	public void setLinkStyleClass(String linkStyleClass) {
		this.iLinkStyleClass = linkStyleClass;
	}
	/**
	 * @param radioStyleClass The radioStyleClass to set.
	 */
	public void setRadioStyleClass(String radioStyleClass) {
		this.iRadioStyleClass = radioStyleClass;
	}
	/**
	 * @param textStyleClass The textStyleClass to set.
	 */
	public void setTextStyleClass(String textStyleClass) {
		this.iTextStyleClass = textStyleClass;
	}
	
	public SubmitButton getNextButton() {
		SubmitButton nextButton = (SubmitButton) getButton(new SubmitButton(getResourceBundle().getLocalizedString("meeting.fee.next", "OK"), PARAMETER_ACTION, ACTION_NEXT));
		nextButton.setToolTip(getResourceBundle().getLocalizedString("meeting.fee.next_step.tooltip","Proceeds to next step"));
		return nextButton;
	}
	
	public SubmitButton getSaveButton() {
		SubmitButton saveButton = (SubmitButton) getButton(new SubmitButton(getResourceBundle().getLocalizedString("meeting.fee.save", "Save"), PARAMETER_ACTION, ACTION_SAVE));
		saveButton.setToolTip(getResourceBundle().getLocalizedString("meeting.fee.save.tooltip","Saves the application"));
		saveButton.setSubmitConfirm(getResourceBundle().getLocalizedString("meeting.fee.save.popup","Are you sure you want to save the application now?"));
		return saveButton;
	}

	protected SubmitButton getCloseButton() {
		SubmitButton closeButton = (SubmitButton) getButton(new SubmitButton(getResourceBundle().getLocalizedString("vacation_approver.close_application", "Close"), PARAMETER_ACTION, ACTION_CLOSE));
		closeButton.setToolTip(getResourceBundle().getLocalizedString("meeting.fee.close.tooltip","Puts the application to a closed status"));
		closeButton.setSubmitConfirm(getResourceBundle().getLocalizedString("meeting.fee.close.popup","Are you sure you want to finally close the application?"));

		return closeButton;
	}

	public GenericButton getEditButton(ICPage page) {
		return getEditButton(page, null, null);
	}
	
	public GenericButton getEditButton(String parameter, String value) {
		return getEditButton(null, parameter, value);
	}
	
	public GenericButton getEditButton(ICPage page, String parameter, String value) {
		GenericButton editButton = getButton(new GenericButton("edit", getResourceBundle().getLocalizedString("meeting.fee.edit", "Edit")));
		if (page != null) {
			editButton.setPageToOpen(page);
		}
		else if (getPage() != null) {
			editButton.setPageToOpen(getPage());
		}
		if (parameter != null) {
			editButton.addParameterToPage(parameter, value);
		}
		editButton.setToolTip(getResourceBundle().getLocalizedString("meeting.fee.edit.tooltip","Allows you to make changes to the report."));
		return editButton;
	}

	public SubmitButton getCalculateButton() {
		SubmitButton calculateButton = (SubmitButton) getButton(new SubmitButton(getResourceBundle().getLocalizedString("meeting.fee.calculate", "Calculate"), PARAMETER_ACTION, ACTION_NEXT));
		calculateButton.setToolTip(getResourceBundle().getLocalizedString("meeting.fee.calculate.tooltip","Calculates the payment for this meeting."));
		return calculateButton;
	}
	
	public BackButton getBackButton() {
		BackButton back = (BackButton) getButton(new BackButton(getResourceBundle().getLocalizedString("meeting.fee.back", "Back")));
		back.setToolTip(getResourceBundle().getLocalizedString("meeting.fee.back.tooltip","Moves back to previous page"));
		return back;
	} 
	
	public SubmitButton getRejectButton() {
		SubmitButton rejectButton = (SubmitButton) getButton(new SubmitButton(getResourceBundle().getLocalizedString("meeting.fee.reject", "Reject"), PARAMETER_ACTION, ACTION_REJECT));
		rejectButton.setToolTip(getResourceBundle().getLocalizedString("meeting.fee.reject.tooltip","Sends the report back to the assistent who wrote it."));
		rejectButton.setSubmitConfirm(getResourceBundle().getLocalizedString("meeting.fee.reject.popup","Are you sure you want to send this report back to the assistant?"));
		return rejectButton;
	}
	
	public SubmitButton getSendButton() {
		SubmitButton sendButton = (SubmitButton) getButton(new SubmitButton(getResourceBundle().getLocalizedString("meeting.fee.send", "OK"), PARAMETER_ACTION, ACTION_SEND));
		sendButton.setToolTip(getResourceBundle().getLocalizedString("meeting.fee.sign_send.tooltip","Signes and sends the report."));
		sendButton.setSubmitConfirm(getResourceBundle().getLocalizedString("meeting.sign_send.popup","Are you sure you want to sign this report?"));
		return sendButton;
	}
	
	protected void showMessage(String message) {
		add(getHeader(message));
		add(new Break(2));

		Link link = getLink(getResourceBundle().getLocalizedString("meeting.home_page", "Back to My Page"));
		if (getPage() != null) {
			link.setPage(getPage());
		}
		add(link);
	}

	/**
	 * @return Returns the iPage.
	 */
	protected ICPage getPage() {
		return this.iPage;
	}
	/**
	 * 
	 * @param page
	 *          The page to set.
	 */
	public void setPage(ICPage page) {
		this.iPage = page;
	}
	
	/**
	 * @param cellpadding The cellpadding to set.
	 */
	public void setCellpadding(int cellpadding) {
		this.iCellpadding = cellpadding;
	}
	
	/**
	 * @param headerColumnWidth The headerColumnWidth to set.
	 */
	public void setHeaderColumnWidth(int headerColumnWidth) {
		this.iHeaderColumnWidth = headerColumnWidth;
	}
}