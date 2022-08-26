package qow.framework.logic.system;

import qow.framework.logic.system.rule.Rule;
import qow.framework.logic.screen.window.MainFrame;
/**
*画面のレートを制御するクラス
*可能な限り一秒間に設定されたレートの回数の画面の描写をする
*
*@author QOW
*@version 1.0.0
*/
public class FrameLoop extends Loop{
	private Rule rule;
	private MainFrame mf;
	/**
	*レートの初期値を設定し、インスタンス化する
	*@param rate レートの初期値
	*/
	public FrameLoop(int rate){
		super(rate);
		mf = new MainFrame("MainFrame");
	}
	/**
	*可能な限り一秒間に設定されたレートの回数このメソッドを実行する
	*/
	public void loop(){
		setRate(rule.getFrameRate());
		
		rule.draw();
	}
	/**
	*設定されたレートより時間がかかってしまった場合の処理
	*@param overTime 過ぎた時間のナノ秒
	*/
	public void overTime(long overTime){
		rule.overTimeFrame(overTime);
	}
	/**
	*@return 設定されているMainFrame
	*/
	public MainFrame getFrame(){
		return mf;
	}
	/**
	*使用するRuleを設定する
	*@param rule 各処理のRuleクラスを継承したクラス
	*/
	public void setRule(Rule rule){
		this.rule = rule;
		rule.setFrame(mf);
		mf.setCanvas(rule.getCanvas());
	}
}