package qow.framework.system;

import qow.framework.screen.QFrame;
import qow.framework.system.rule.Rule;

/**
 * 画面のレートを制御する<br>
 * 可能な限り一秒間に設定されたレートの回数の画面の再描写をする
 *
 * @author QOW
 * @version 2022/09/23
 * @since 1.0.0
 */
public class FrameLoop extends Loop {
    private QFrame qf;
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
        qf = new QFrame("QFrame");
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
     * @return 設定されているフレーム
     * @see QFrame
     * @see javax.swing.JFrame
     */
    public QFrame getMainFrame() {
        return qf;
    }

    /**
     * ループする{@link Rule}を設定する
     *
     * @param rule 各処理の{@link Rule}を継承したクラス
     */
    public void setRule(Rule rule) {
        this.rule = rule;

        if (rule.hasMainFrame()) {
            qf.dispose();
            qf = rule.getMainFrame();
        } else {
            rule.setMainFrame(qf);
        }
        qf.setResolution(rule.getCanvas());
    }
}