package qow.framework.setting;

import javax.swing.JButton;

class KeyConfigButton extends JButton{
	private boolean reset;
	protected KeyConfigButton(String title){
		setText(title);
		
		reset = false;
		setFocusable(false);
	}
	protected void setReset(boolean b){
		reset = b;
	}
	protected boolean getReset(){
		return reset;
	}
}