package qow.framework.logic.system.rule;

import qow.framework.logic.screen.graphics.Canvas;
import qow.framework.logic.screen.window.MainFrame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
*ループ内での処理を設定するクラス
*
*@author QOW
*@version 1.0.0
*/
public abstract class Rule implements KeyListener,MouseListener,MouseMotionListener{
	private Canvas canvas;
	private MainFrame mf;
	private int executeRate,frameRate;
	/**
	*処理ループのレートを設定する
	*@param executeRate 一秒間に実行される回数
	*/
	public void setExecuteRate(int executeRate){
		this.executeRate = executeRate;
	}
	/**
	*描写ループのレートを設定する
	*@param frameRate 一秒間に実行される回数
	*/
	public void setFrameRate(int frameRate){
		this.frameRate = frameRate;
	}
	/**
	*@return 処理ループの一秒間に実行される予定の回数
	*/
	public int getExecuteRate(){
		return executeRate;
	}
	/**
	*@return 描写ループの一秒間に実行される予定の回数
	*/
	public int getFrameRate(){
		return frameRate;
	}

	/**
	*処理ループによって一秒間にレートの回数実行されるメソッド
	*/
	public void loop(){
		if(mf.isActive()){
			loopActive();
		}else{
			loopInactive();
		}
	}
	/**
	*フレームアクティブ時の処理
	*/
	public abstract void loopActive();
	/**
	*フレーム非アクティブ時の処理
	*/
	public abstract void loopInactive();
	/**
	*描写ループによって一秒間にレートの回数実行されるメソッド
	*/
	public void draw(){
		if(mf.isActive()){
			paintActive(canvas.getGraphicsImage());
		}else{
			paintInactive(canvas.getGraphicsImage());
		}
		canvas.draw();
	}
	/**
	*フレームアクティブ時の描写
	*Canvasに投影するGraphicsをペイントする
	*@param g Canvasに投影するGraphics
	*/
	public abstract void paintActive(Graphics g);
	/**
	*フレーム非アクティブ時の描写
	*Canvasに投影するGraphicsをペイントする
	*@param g Canvasに投影するGraphics
	*/
	public abstract void paintInactive(Graphics g);
	/**
	*新しいCanvasを設定する
	*@param canvas 新しいCanvas
	*/
	public void setCanvas(Canvas canvas){
		this.canvas = canvas;
	}
	/**
	*MainFrameに投影するCanvasを返す
	*@return 設定されたCanvas
	*/
	public Canvas getCanvas(){
		return canvas;
	}
	/**
	*フレームを設定する
	*@param mf 新しいフレーム
	*/
	public void setFrame(MainFrame mf){
		this.mf = mf;
		addListener(getFrame());
	}
	/**
	*設定されたMainFrameを返す
	*@return 設定されたMainFrame
	*/
	public MainFrame getFrame(){
		return mf;
	}
	/**
	*キーがタイプされたときの処理
	*@param e KeyListenerで呼び出されたKeyEvent
	*/
	public void typeKey(KeyEvent e){}
	/**
	*キーが押されたときの処理
	*@param e KeyListenerで呼び出されたKeyEvent
	*/
	public void pressKey(KeyEvent e){}
	/**
	*キーが離されたときの処理
	*@param e KeyListenerで呼び出されたKeyEvent
	*/
	public void releaseKey(KeyEvent e){}
	@Override
	public void keyTyped(KeyEvent e){
		typeKey(e);
	}
	@Override
	public void keyPressed(KeyEvent e){
		pressKey(e);
	}
	@Override
	public void keyReleased(KeyEvent e){
		releaseKey(e);
	}
	
	/**
	*マウスがクリックされたときの処理
	*@param e MouseListenerで呼び出されたMouseEvent
	*/
	public void clickMouse(MouseEvent e){}
	/**
	*マウスがエンターされたときの処理
	*@param e MouseListenerで呼び出されたMouseEvent
	*/
	public void enterMouse(MouseEvent e){}
	/**
	*マウスがフレームからでたときの処理
	*@param e MouseListenerで呼び出されたMouseEvent
	*/
	public void exitMouse(MouseEvent e){}
	/**
	*マウスが押されたときの処理
	*@param e MouseListenerで呼び出されたMouseEvent
	*/
	public void pressMouse(MouseEvent e){}
	/**
	*マウスが離されたときの処理
	*@param e MouseListenerで呼び出されたMouseEvent
	*/
	public void releaseMouse(MouseEvent e){}
	@Override
	public void mouseClicked(MouseEvent e){
		clickMouse(e);
	}
	@Override
	public void mouseEntered(MouseEvent e){
		enterMouse(e);
	}
	@Override
	public void mouseExited(MouseEvent e){
		exitMouse(e);
	}
	@Override
	public void mousePressed(MouseEvent e){
		pressMouse(e);
	}
	@Override
	public void mouseReleased(MouseEvent e){
		releaseMouse(e);
	}
	
	/**
	*マウスがドラッグされたときの処理
	*@param e MouseMotionListenerで呼び出されたMouseEvent
	*/
	public void dragMouse(MouseEvent e){}
	/**
	*マウスが動いたときの処理
	*@param e MouseMotionListenerで呼び出されたMouseEvent
	*/
	public void moveMouse(MouseEvent e){}
	@Override
	public void mouseDragged(MouseEvent e){
		dragMouse(e);
	}
	@Override
	public void mouseMoved(MouseEvent e){
		moveMouse(e);
	}
	/**
	*implementsしたListenerなどを追加する
	*@param mf 設定するフレーム
	*/
	public abstract void addListener(MainFrame mf);
	/**
	*implementsしたListenerなどを破棄する
	*@param mf 設定されたフレーム
	*/
	public abstract void removeListener(MainFrame mf);
	/**
	*処理に設定したfpsより時間がかかってしまった場合の処理
	*@param overTime 過ぎた時間のナノ秒
	*/
	public abstract void overTimeExecute(long overTime);
	/**
	*描写に設定したfpsより時間がかかってしまった場合の処理
	*@param overTime 過ぎた時間のナノ秒
	*/
	public abstract void overTimeFrame(long overTime);
	/**
	*新しいRuleに更新するかを返す
	*@return Ruleを更新する場合はtrue
	*/
	public abstract boolean isChangeRule();
	/**
	*@return 新しいRule
	*/
	public abstract Rule getNewRule();
}