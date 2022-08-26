package qow.framework.setting;
/**
*キーコンフィグを設定するためのクラス
*@author QOW
*@version 1.0.0
*/
public class KeyConfigWriter{
	/**
	*キーコンフィグファイルを変更する
	*キーコンフィグの初期設定には直接ファイルを準備する必要がある
	*
	*@param path キーコンフィグファイルのパス
	*/
	public KeyConfigWriter(String path){
		//KeyConfigChanger keyChange = new KeyConfigChanger("data\\config\\keyconfig.txt");
		KeyConfigChanger keyChange = new KeyConfigChanger(path);
		keyChange.setVisible(true);
	}
}