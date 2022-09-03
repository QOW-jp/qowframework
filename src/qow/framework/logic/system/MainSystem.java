package qow.framework.logic.system;

import qow.framework.logic.screen.window.MainFrame;
import qow.framework.logic.system.rule.Rule;

/**
 * 処理レートとフレームレートを制御する
 *
 * @author QOW
 * @version 1.3.2   2022/09/03
 */
public class MainSystem {
    /**
     * 一秒間に{@link ExecuteLoop#loop()}が実行される回数
     */
    public static int executeRate;
    /**
     * 一秒間に{@link FrameLoop#loop()}が実行される回数
     */
    public static int frameRate;
    private final ExecuteLoop el;
    private final FrameLoop fl;

    /**
     * rateの初期値を設定し、インスタンス化する
     *
     * @param rate rateの初期値
     */
    public MainSystem(int rate) {
        this();
        executeRate = rate;
        frameRate = rate;
    }

    public MainSystem() {
        el = new ExecuteLoop();
        fl = new FrameLoop();
        el.setFrameLoop(fl);
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
     * 処理ループを開始または停止する
     *
     * @param start ループを開始または停止
     * @see ExecuteLoop#start(boolean)
     */
    public void startExecuteLoop(boolean start) {
        el.start(start);
    }

    /**
     * 描写ループを開始または停止する
     *
     * @param start ループを開始または停止
     * @see FrameLoop#start(boolean)
     */
    public void startFrameLoop(boolean start) {
        fl.start(start);
    }

    /**
     * {@link FrameLoop}に設定されている{@link MainFrame}を返す
     *
     * @return 描写されるフレーム
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