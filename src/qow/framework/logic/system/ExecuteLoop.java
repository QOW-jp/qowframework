package qow.framework.logic.system;

import qow.framework.logic.system.rule.Rule;

/**
 * 処理のレートを制御する<br>
 * 可能な限り一秒間に設定されたレートの回数の処理を実行する
 *
 * @author QOW
 * @version 2022/09/11
 * @since 1.0.0
 */
public class ExecuteLoop extends Loop {
    private Rule rule;
    private FrameLoop fl;

    /**
     * レートの初期値を設定し、インスタンス化する
     *
     * @param rate レートの初期値
     */
    public ExecuteLoop(double rate) {
        super(rate);
    }

    /**
     * レートの初期値を設定せずインスタンス化する
     */
    public ExecuteLoop() {
        super();
    }

    /**
     * 可能な限り一秒間に設定されたレートの回数このメソッドを実行する<br>
     * 指定時間内に処理が終わらない場合は{@link ExecuteLoop#overTime(double)}が呼び出される
     */
    public void loop() {
        rule.loop();

        if (rule.isChangeRule()) {
            rule.removeListener(rule.getMainFrame());
            setRule(rule.getNewRule());
        }
    }

    /**
     * 設定されたレートより時間がかかってしまった場合の処理
     *
     * @param overTime 過ぎた時間のナノ秒
     */
    public void overTime(double overTime) {
        rule.overTimeExecute(overTime);
    }

    /**
     * 使用するのRuleを設定する
     *
     * @param rule 各処理のRuleクラスを継承したクラス
     */
    public void setRule(Rule rule) {
        this.rule = rule;
//        fl.setRule(rule);
        rule.init();
    }

    /**
     * 使用するFrameLoopを設定する
     *
     * @param fl フレームレートを制御するクラス
     */
    public void setFrameLoop(FrameLoop fl) {
        this.fl = fl;
    }
}