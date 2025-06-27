package qow.framework.system;

import qow.framework.screen.QCanvas;
import qow.framework.screen.QFrame;
import qow.framework.util.ActionKeyManager;
import qow.framework.util.ActionMouseManager;

import java.awt.*;
import java.awt.event.*;

/**
 * {@link ExecuteLoop}と{@link FrameLoop}内での処理を設定するクラス
 *
 * @author QOW
 * @version 2025/06/14
 * @since 1.0.0
 */
public abstract class Rule implements KeyListener, MouseListener, MouseMotionListener {
    private final ActionKeyManager akm;
    private final ActionMouseManager amm;
    private QFrame qf;
    private QCanvas canvas;
    private ExecuteLoop el;
    private FrameLoop fl;
    private boolean ready;

    /**
     * インスタンス化<br>
     * {@link ActionKeyManager}{@link ActionMouseManager}{@link QFrame}の初期化
     */
    public Rule() {
        ready = false;
        akm = new ActionKeyManager();
        amm = new ActionMouseManager();

        setQFrame(new QFrame());
        canvas = qf.getQCanvas();
    }

    /**
     * 対応した{@link ActionKeyManager}を返す
     *
     * @return キー状態を保存したクラスをまとめるクラス
     */
    public ActionKeyManager getActionKeyManager() {
        return akm;
    }

    /**
     * 対応した{@link ActionMouseManager}を返す
     *
     * @return マウス状態を保存したクラスをまとめるクラス
     */
    public ActionMouseManager getActionMouseManager() {
        return amm;
    }

    /**
     * {@link qow.framework.system.ExecuteLoop#setRule(Rule, FrameLoop)}の最後に呼び出されるメソッド<br>
     * ルールの更新の終わる直前に実行される
     */
    protected void init() {
    }

    /**
     * {@link ExecuteLoop#loop()}によって一秒間にレートの回数実行されるメソッド
     */
    public void loop() {
        if (qf.isActive()) {
            loopActive();
        } else {
            loopInactive();
        }
    }

    /**
     * フレームアクティブ時の処理
     */
    public abstract void loopActive();

    /**
     * フレーム非アクティブ時の処理
     */
    public abstract void loopInactive();

    /**
     * {@link FrameLoop#loop()}によって一秒間にレートの回数実行されるメソッド
     */
    public void draw() {
        Graphics g = canvas.getBufferStrategyGraphics();
        if (g == null) {
            return;
        }

        if (qf.isActive()) {
            paintActive(g);
        } else {
            paintInactive(g);
        }

        try {
            canvas.update();
            Toolkit.getDefaultToolkit().sync();
        } catch (IllegalStateException ignored) {
        } finally {
            g.dispose();
        }
    }

    /**
     * フレームアクティブ時の描写
     *
     * @param g {@link QCanvas}に投影する{@link Graphics}
     */
    public abstract void paintActive(Graphics g);

    /**
     * フレーム非アクティブ時の描写
     *
     * @param g {@link QCanvas}に投影する{@link Graphics}
     */
    public abstract void paintInactive(Graphics g);

    /**
     * {@link QFrame}に投影する{@link QCanvas}を返す
     *
     * @return 設定されたキャンパス
     */
    public QCanvas getQCanvas() {
        return canvas;
    }

    /**
     * 設定された{@link QFrame}を返す
     *
     * @return 設定されたフレーム
     */
    public QFrame getQFrame() {
        return qf;
    }

    /**
     * {@link QFrame}を設定する<br>
     * 過去の{@link QFrame}にListenerが実装されていた場合削除する
     *
     * @param qf 新しいフレーム
     */
    public void setQFrame(QFrame qf) {
        if (this.qf != null) removeListener(this.qf);
        this.qf = qf;
        canvas = qf.getQCanvas();
        addListener(getQFrame());
    }

    /**
     * 現在使用している{@link ExecuteLoop}を取得する<br>
     * レートの設定を行うときなどで使用する
     *
     * @return 処理レートを管理するクラス
     */
    public ExecuteLoop getExecuteLoop() {
        return el;
    }

    /**
     * 使用する{@link ExecuteLoop}を設定する
     *
     * @param el 処理レートを管理するクラス
     */
    public void setExecuteLoop(ExecuteLoop el) {
        this.el = el;
    }

    /**
     * 現在使用している{@link FrameLoop}を取得する<br>
     * レートの設定を行うときなどで使用する
     *
     * @return フレームレートを管理するクラス
     */
    public FrameLoop getFrameLoop() {
        return fl;
    }

    /**
     * 使用する{@link FrameLoop}を設定する
     *
     * @param fl フレームレートを管理するクラス
     */
    public void setFrameLoop(FrameLoop fl) {
        this.fl = fl;
    }


    /**
     * {@link MainSystem}{@link ExecuteLoop}により{@link Rule}が更新中に呼び出される<br>
     *
     * @return falseのとき一時的にループ処理をしない
     */
    public boolean isReady() {
        return ready;
    }

    /**
     * {@link MainSystem}{@link ExecuteLoop}により{@link Rule}の更新後に呼び出される<br>
     * ループ処理の一時的な中断と再開ができる
     *
     * @param ready falseのとき一時的にループ処理をしない
     */
    public void setReady(boolean ready) {
        this.ready = ready;
    }

    /**
     * キーがタイプされたときの処理
     *
     * @param e {@link KeyListener}で呼び出された{@link KeyEvent}
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * キーが押されたときの処理<br>
     * 初期状態として{@link ActionKeyManager}による受付がされている
     *
     * @param e {@link KeyListener}で呼び出された{@link KeyEvent}
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        for (int i = 0; i < akm.size(); i++) {
            if (akm.get(i).getKeyCode() == code) {
                akm.get(i).press();
            }
        }
    }

    /**
     * キーが離されたときの処理<br>
     * 初期状態として{@link ActionKeyManager}による受付がされている
     *
     * @param e {@link KeyListener}で呼び出された{@link KeyEvent}
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        for (int i = 0; i < akm.size(); i++) {
            if (akm.get(i).getKeyCode() == code) {
                akm.get(i).release();
            }
        }
    }

    /**
     * マウスがクリックされたときの処理
     *
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * マウスがエンターされたときの処理
     *
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * マウスがフレームからでたときの処理
     *
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * マウスが押されたときの処理<br>
     * 初期状態として{@link ActionMouseManager}による受付がされている
     *
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int button = e.getButton();
        for (int i = 0; i < amm.size(); i++) {
            if (amm.get(i).getButton() == button || amm.get(i).getButton() == -1) {
                amm.get(i).press();
            }
        }
    }

    /**
     * マウスが離されたときの処理<br>
     * 初期状態として{@link ActionMouseManager}による受付がされている
     *
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        for (int i = 0; i < amm.size(); i++) {
            if (amm.get(i).getButton() == button || amm.get(i).getButton() == -1) {
                amm.get(i).release();
            }
        }
    }

    /**
     * マウスがドラッグされたときの処理<br>
     * 初期状態として{@link ActionMouseManager}による受付がされている
     *
     * @param e {@link MouseMotionListener}で呼び出された{@link MouseEvent}
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        amm.setMousePoint(e.getPoint());
    }

    /**
     * マウスが動いたときの処理<br>
     * 初期状態として{@link ActionMouseManager}による受付がされている
     *
     * @param e {@link MouseMotionListener}で呼び出された{@link MouseEvent}
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        amm.setMousePoint(e.getPoint());
    }

    /**
     * インターフェイスを追加する
     *
     * @param qf 設定するフレーム
     */
    public void addListener(QFrame qf) {
    }

    /**
     * インターフェイスを破棄する
     *
     * @param qf 設定されたフレーム
     */
    public void removeListener(QFrame qf) {
    }

    /**
     * 処理に設定されたレートより時間がかかってしまった場合の処理
     * 正の数値で返される
     *
     * @param overTime 過ぎた時間のミリ秒
     */
    public void overTimeExecute(long overTime) {
    }

    /**
     * 描写に設定されたレートより時間がかかってしまった場合の処理
     * 正の数値で返される
     *
     * @param overTime 過ぎた時間のミリ秒
     */
    public void overTimeFrame(long overTime) {
    }

    /**
     * 新しい{@link Rule}に更新するかを返す
     *
     * @return Ruleを更新する場合はtrue
     */
    public abstract boolean isChangeRule();

    /**
     * @return 新しいRule
     */
    public abstract Rule getNewRule();
}