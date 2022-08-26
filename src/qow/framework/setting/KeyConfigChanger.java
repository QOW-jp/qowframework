package qow.framework.setting;

import javax.swing.JFrame;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

class KeyConfigChanger extends JFrame{
	private File dataFile;
	protected KeyConfigChanger(String fileURL){
		setTitle(fileURL+" キーコンフィグ設定画面");
		
		dataFile = new File(fileURL);
		
		List<String[]> keyCode = new ArrayList<String[]>();
		String[] keyTitle = null;
		try{
			Scanner sc = new Scanner(dataFile);
			
			for(int i=0;sc.hasNextLine();i++){
				if(i == 0){
					String result = sc.nextLine();
					keyTitle = result.split(":");
				}
				String result = sc.nextLine();
				keyCode.add(result.split(":"));
			}
			
			sc.close();
		}catch(Exception e){}
		
		KeyConfigChangerPanel keyPanel = new KeyConfigChangerPanel(keyTitle,keyCode,dataFile);
		add(keyPanel);
		
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}