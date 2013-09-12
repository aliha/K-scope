package jp.riken.kscope.action;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import jp.riken.kscope.Application;
import jp.riken.kscope.Message;
import jp.riken.kscope.common.Constant;
import jp.riken.kscope.dialog.SSHconnectPropertiesDialog;
import jp.riken.kscope.properties.SSHconnectProperties;
import jp.riken.kscope.service.AppController;
import jp.riken.kscope.service.ProjectService;

public class ProjectSettingSSHAction extends ActionBase {

	
	public ProjectSettingSSHAction(AppController controller) {
		super(controller);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// ステータスメッセージ
        final String message = Message.getString("projectsettingsshconnect.setup.status");  
        Application.status.setMessageMain(message);

        // 親Frameの取得を行う。
        Frame frame = getWindowAncestor( event );

       	openDialog(frame);
	}

	/**
	 *  設定ダイアログを表示する。
	 * @param frame
	 */
	public void openDialog(Frame frame) {
		String status = Message.getString("projectsettingsshconnect.setup.status");
		
        SSHconnectProperties ssh_properties = this.controller.getPropertiesSSH();
        
		SSHconnectPropertiesDialog dialog = new SSHconnectPropertiesDialog(frame, ssh_properties);
		dialog.setModal(true);
		int result = dialog.showDialog(); 
		if (result != Constant.OK_DIALOG) {
        	Application.status.setMessageMain(status + 
        			Message.getString("action.common.cancel.status")); //キャンセル
        	return;
        }
        this.controller.setSSHproperties(ssh_properties);
        // Save properties
        // TODO write code to save settings to properties.xml
       
	}
	
	/**
	 *  Message付きの設定ダイアログを表示する。
	 * @param frame
	 */
	public void openDialog(Frame frame, String message) {
		String status = Message.getString("projectsettingsshconnect.setup.status");
		
        SSHconnectProperties ssh_properties = this.controller.getPropertiesSSH();
        
		SSHconnectPropertiesDialog dialog = new SSHconnectPropertiesDialog(frame, ssh_properties, message);
		dialog.setModal(true);
		int result = dialog.showDialog(); 
		if (result != Constant.OK_DIALOG) {
        	Application.status.setMessageMain(status + 
        			Message.getString("action.common.cancel.status")); //キャンセル
        	return;
        }
        this.controller.setSSHproperties(ssh_properties);
	}
	
	
}
