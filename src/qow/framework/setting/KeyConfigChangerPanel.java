package qow.framework.setting;

import java.util.List;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.io.File;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class KeyConfigChangerPanel extends JPanel implements KeyListener,ActionListener{
	private String[] keyTitle;
	private List<String[]> keyCode;
	private File dataFile;
	
	private int width,height;
	private int size = 100;
	
	private JLabel[] keyTitleLabel;
	private KeyConfigButton[][] keyButton;
	
	protected KeyConfigChangerPanel(String[] keyTitle,List<String[]> keyCode,File dataFile){
		this.keyTitle = keyTitle;
		this.keyCode = keyCode;
		this.dataFile = dataFile;
		
		width = (keyCode.get(0).length+1)*size;
		height = keyCode.size()*size;
		
		setFocusable(true);
		setSize(width,height);
		
		setLayout(new GridLayout(keyCode.size()+1,keyCode.get(0).length+1));
		
		keyTitleLabel = new JLabel[keyCode.get(0).length+1];
		for(int i=0;i<keyTitleLabel.length;i++){
			if(i==0){
				keyTitleLabel[i] = new JLabel("");
			}else{
				keyTitleLabel[i] = new JLabel(keyTitle[i-1]);
			}
			add(keyTitleLabel[i]);
		}
		
		keyButton = new KeyConfigButton[keyCode.size()][keyCode.get(0).length];
		JLabel[] playerTitle = new JLabel[keyCode.size()];
		for(int i=0;i<keyCode.size();i++){
			for(int ii=0;ii<keyCode.get(i).length;ii++){
				if(ii==0){
					playerTitle[i] = new JLabel((i+1)+"P");
					add(playerTitle[i]);
				}
				keyButton[i][ii] = new KeyConfigButton(KeyEvent.getKeyText(Integer.parseInt(keyCode.get(i)[ii])));
				
				keyButton[i][ii].addActionListener(this);
				keyButton[i][ii].setActionCommand(String.valueOf(i*keyCode.get(i).length+ii));
				
				add(keyButton[i][ii]);
			}
		}
		
		KeyConfigOutFile outFile = new KeyConfigOutFile(keyTitle,keyCode,dataFile);
		
		addKeyListener(this);
		Runtime.getRuntime().addShutdownHook(outFile);
	}
	@Override
	public void actionPerformed(ActionEvent e){
		String cmd = e.getActionCommand();
		int keyCmd = Integer.parseInt(cmd);
		
		for(int i=0;i<keyCode.size();i++){
			for(int ii=0;ii<keyCode.get(i).length;ii++){
				keyButton[i][ii].setReset(false);
			}
		}
		
		keyButton[keyCmd/keyCode.get(0).length][keyCmd%keyCode.get(0).length].setReset(true);
	}
	@Override
	public void keyTyped(KeyEvent e){
	}
	@Override
	public void keyPressed(KeyEvent e){
	}
	@Override
	public void keyReleased(KeyEvent e){
		String key = String.valueOf(e.getKeyCode());
		for(int i=0;i<keyCode.size();i++){
			for(int ii=0;ii<keyCode.get(i).length;ii++){
				if(keyButton[i][ii].getReset()){
					keyButton[i][ii].setReset(false);
					keyButton[i][ii].setText(KeyEvent.getKeyText(e.getKeyCode()));
					keyCode.get(i)[ii] = key;
				}
			}
		}
	}
}