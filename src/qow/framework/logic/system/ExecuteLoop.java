package qow.framework.logic.system;

import qow.framework.logic.system.rule.Rule;
/**
*処理のレートを制御するクラス
*可能な限り一秒間に設定されたレートの回数の処理を実行する
*
*@author QOW
*@version 1.0.0
*/
public class ExecuteLoop extends Loop{
	private Rule rule;
	private FrameLoop fl;
	/**
	*レートの初期値を設定し、インスタンス化する
	*@param rate レートの初期値
	*/
	public ExecuteLoop(int rate){
		super(rate);
	}
	/**
	*可能な限り一秒間に設定されたレートの回数このメソッドを実行する
	*/
	public void loop(){
		setRate(rule.getExecuteRate());
		
		rule.loop();
		
		if(rule.isChangeRule()){
			System.out.println("<1> "+Runtime.getRuntime().freeMemory());
			rule.removeListener(rule.getFrame());
			System.out.println("<2> "+Runtime.getRuntime().freeMemory());

			/*
			Rule newRule = rule.getNewRule();
			rule = null;
			setRule(newRule);
			*/
			setRule(rule.getNewRule());
			//System.gc();
			System.out.println("<3> "+Runtime.getRuntime().freeMemory());

		}
	}
	/**
	*設定されたレートより時間がかかってしまった場合の処理
	*@param overTime 過ぎた時間のナノ秒
	*/
	public void overTime(long overTime){
		rule.overTimeExecute(overTime);
	}
	/**
	*使用するのRuleを設定する
	*@param rule 各処理のRuleクラスを継承したクラス
	*/
	public void setRule(Rule rule){
		this.rule = rule;
		fl.setRule(rule);
	}
	/**
	*使用するFrameLoopを設定する
	*@param fl フレームレートを制御するクラス
	*/
	public void setFrameLoop(FrameLoop fl){
		this.fl = fl;
	}
}