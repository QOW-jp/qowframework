package qow.framework.system;

/**
 * 処理レートとフレームレートを制御する
 *
 * @author QOW
 * @version 2025/06/13
 * @since 1.0.0
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
        this();
        el.setRate(rate);
        fl.setRate(rate);
    }

    public MainSystem() {
        el = new ExecuteLoop();
        fl = new FrameLoop();
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
     * {@link Rule}に使用する{@link ExecuteLoop}と{@link FrameLoop}を指定する
     *
     * @param rule 各処理のRuleクラスを継承したクラス
     */
    public void setRule(Rule rule) {
        el.setRule(rule, fl);
    }
}