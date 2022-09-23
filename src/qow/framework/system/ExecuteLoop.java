package qow.framework.system;

import qow.framework.system.rule.Rule;

/**
 * 処理のレートを制御する<br>
 * 可能な限り一秒間に設定されたレートの回数の処理を実行する
 *
 * @author QOW
 * @version 2022/09/23
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
            rule.removeListener(rule.getQFrame());
            setRule(rule.getNewRule(), fl);
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
     * ループする{@link Rule}を設定する
     *
     * @param rule 各処理の{@link Rule}を継承したクラス
     */
    public void setRule(Rule rule, FrameLoop fl) {
        this.rule = rule;
        this.fl = fl;

        rule.setExecuteLoop(this);
        rule.setFrameLoop(fl);

        fl.setRule(rule);

        rule.init();
    }
}