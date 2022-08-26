package qow.framework.setting;

import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

class KeyConfigOutFile extends Thread{
	private String[] keyTitle;
	private List<String[]> keyCode;
	private File dataFile;
	protected KeyConfigOutFile(String[] keyTitle,List<String[]> keyCode,File dataFile){
		this.keyTitle = keyTitle;
		this.keyCode = keyCode;
		this.dataFile = dataFile;
	}
	@Override
	public void run(){
		try{
			BufferedWriter fw = new BufferedWriter(new FileWriter(dataFile));
			String result="";
			for(String title : keyTitle){
				result += title+":";
			}
			
			fw.write(result);
			fw.newLine();
			
			for(String[] code : keyCode){
				result = "";
				for(String codes : code){
					result+=codes+":";
				}
				fw.write(result);
				fw.newLine();
			}
			System.out.println("ファイルの保存に成功");
			
			fw.close();
		}catch(Exception e){
			System.out.println("ファイルの保存に失敗");
			e.printStackTrace();
		}
	}
}