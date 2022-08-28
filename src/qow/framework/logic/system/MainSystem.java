package qow.framework.logic.system;

import qow.framework.logic.screen.window.MainFrame;
import qow.framework.logic.system.rule.Rule;

/**
 * 処理レートとフレームレートを制御するクラス
 *
 * @author QOW
 * @version 1.2.3
 */
public class MainSystem {
    private final ExecuteLoop el;
    private final FrameLoop fl;

    /**
     * rateの初期値を設定し、インスタンス化する
     *
     * @param rate rateの初期値
     */
    public MainSystem(int rate) {
        el = new ExecuteLoop(rate);
        fl = new FrameLoop(rate);
        el.setFrameLoop(fl);
    }

    /**
     * rateを60に設定し、インスタンス化する
     */
    public MainSystem() {
        this(60);
    }

    /**
     * 処理ループと描写ループの開始または停止する
     *
     * @param start ループを開始または停止
     */
    public void start(boolean start) {
        startExecuteLoop(start);
        startFrameLoop(start);
    }

    /**
     * 処理のループを開始または停止する
     *
     * @param start ループを開始または停止
     */
    public void startExecuteLoop(boolean start) {
        el.start(start);
    }

    /**
     * 描写のループを開始または停止する
     *
     * @param start ループを開始または停止
     */
    public void startFrameLoop(boolean start) {
        fl.start(start);
    }

    /**
     * 設定されているMainFrameを返す
     *
     * @return 設定されているMainFrame
     */
    public MainFrame getFrame() {
        return fl.getFrame();
    }

    /**
     * 使用するのRuleを設定する
     *
     * @param rule 各処理のRuleクラスを継承したクラス
     */
    public void setRule(Rule rule) {
        el.setRule(rule);
    }
}