package qow.framework.logic.screen.window;

import qow.framework.logic.screen.graphics.Canvas;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
*メインのフレーム
*
*@author QOW
*@version 1.0.0
*/
public class MainFrame extends JFrame{
	private final MainPanel mp;
	
	/**
	*タイトルを設定し、インスタンス化する
	*@param title フレームのタイトル
	*/
	public MainFrame(String title){
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		mp = new MainPanel();
		add(mp);
	}
	/**
	*タイトルを設定せずインスタンス化する
	*/
	public MainFrame(){
		this("");
	}
	/**
	*Canvasの情報を更新する
	*@param canvas 新しいCanvas
	*/
	public void setCanvas(Canvas canvas){
		mp.setCanvas(canvas);
		canvas.setPanel(mp);
		
		pack();
	}

	public JPanel getMainPanel(){
		return mp;
	}
}