package qow.framework.logic.system;

import qow.framework.logic.screen.window.MainFrame;
import qow.framework.logic.system.rule.Rule;

/**
 * 画面のレートを制御する<br>
 * 可能な限り一秒間に設定されたレートの回数の画面の描写をする
 *
 * @author QOW
 * @version 2022/09/12
 * @since 1.0.0
 */
public class FrameLoop extends Loop {
    private MainFrame mf;
    private Rule rule;

    /**
     * レートの初期値を設定し、インスタンス化する
     *
     * @param rate レートの初期値
     */
    public FrameLoop(double rate) {
        this();
        setRate(rate);
    }

    /**
     * レートの初期値を設定せずインスタンス化する
     */
    public FrameLoop() {
        super();
        mf = new MainFrame("MainFrame");
    }

    /**
     * 可能な限り一秒間に設定されたレートの回数このメソッドを実行する<br>
     * 指定時間内に処理が終わらない場合は{@link FrameLoop#overTime(double)}が呼び出される
     */
    public void loop() {
        rule.draw();
    }

    /**
     * 設定されたレートより時間がかかってしまった場合の処理
     *
     * @param overTime 過ぎた時間のナノ秒
     */
    public void overTime(double overTime) {
        rule.overTimeFrame(overTime);
    }

    /**
     * @return 設定されているMainFrame
     * @see MainFrame
     * @see javax.swing.JFrame
     */
    public MainFrame getMainFrame() {
        return mf;
    }

    /**
     * 使用するRuleを設定する
     *
     * @param rule 各処理のRuleクラスを継承したクラス
     */
    public void setRule(Rule rule) {
        this.rule = rule;

        if (rule.hasMainFrame()) {
            mf.dispose();
            mf = rule.getMainFrame();
        } else {
            rule.setMainFrame(mf);
        }
        mf.setResolution(rule.getCanvas());
    }
}