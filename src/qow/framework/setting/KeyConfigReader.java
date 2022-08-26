package qow.framework.setting;

import java.io.File;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
/**
*キーコンフィグファイルを読み込むために必要なクラス
*キーコンフィグファイルを準備する必要があります
*
*@author QOW
*@version 1.0.0
*/
public class KeyConfigReader{
	private List<String[]> keyCodeList;
	private int[][] keyCode;
	/**
	*キーコンフィグファイルを読み込む
	*
	*@param path キーコンフィグファイルのパス
	*/
	public KeyConfigReader(String path){
		try{
			Scanner sc = new Scanner(new File(path));
			sc.nextLine();	//タイトル捨て
			
			keyCodeList = new ArrayList<String[]>();
			
			while(sc.hasNextLine()){
				keyCodeList.add(sc.nextLine().split(":"));
			}
			
			keyCode = new int[keyCodeList.size()][keyCodeList.get(0).length];
			for(int i=0;i<keyCodeList.size();i++){
				for(int j=0;j<keyCodeList.get(i).length;j++){
					keyCode[i][j] = Integer.parseInt(keyCodeList.get(i)[j]);
				}
			}
			
			sc.close();
			keyCodeList.clear();
		}catch(Exception e){
			System.out.println("キーコンフィグファイルが見当たりません");
			e.printStackTrace();
		}
	}
	/**
	*@return int[][]を返す<br>
	*KeyListenerで呼び出された変数e.getKeyCode()の配列
	*/
	public int[][] getInt(){
		return keyCode;
	}
}