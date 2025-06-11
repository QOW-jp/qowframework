package qow.framework.system;

import qow.framework.screen.QFrame;

/**
 * 画面のレートを制御する<br>
 * 可能な限り一秒間に設定されたレートの回数の画面の再描写をする
 *
 * @author QOW
 * @version 2023/04/04
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
    protected FrameLoop(long rate) {
        this();
        setRate(rate);
    }

    /**
     * レートの初期値を設定せずインスタンス化する
     */
    protected FrameLoop() {
        super();
    }

    /**
     * 可能な限り一秒間に設定されたレートの回数このメソッドを実行する<br>
     * 指定時間内に処理が終わらない場合は{@link FrameLoop#overTime(long)}が呼び出される
     */
    public void loop() {
        rule.draw();
    }

    /**
     * 設定されたレートより時間がかかってしまった場合の処理
     * 正の数値で返される
     *
     * @param overTime 過ぎた時間のミリ秒
     */
    public void overTime(long overTime) {
        rule.overTimeFrame(overTime);
    }

    /**
     * ループする{@link Rule}を設定する
     *
     * @param rule 各処理の{@link Rule}を継承したクラス
     */
    public void setRule(Rule rule) {
        this.rule = rule;

        if (qf != null) {
            qf.dispose();
            qf = null;
        }
        qf = rule.getQFrame();

//        if (rule.hasQFrame()) {
//            qf.dispose();
//            qf = rule.getQFrame();
//        } else {
//            rule.setQFrame(qf);
////            if (!rule.hasQCanvas()) {
////                rule.setQCanvas(qf.getQCanvas());
////                return;
////            }
//        }
//        rule.setQCanvas(rule.getQCanvas());
    }
}