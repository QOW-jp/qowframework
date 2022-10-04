package qow.framework.system;

import qow.framework.screen.QCanvas;
import qow.framework.screen.QFrame;
import qow.framework.screen.QPanel;
import qow.framework.util.ActionKeyManager;

import java.awt.*;
import java.awt.event.*;

/**
 * {@link ExecuteLoop}と{@link FrameLoop}内での処理を設定するクラス
 *
 * @author QOW
 * @version 2022/10/04
 * @since 1.0.0
 */
public abstract class Rule implements KeyListener, MouseListener, MouseMotionListener {
    private final ActionKeyManager akm;
    private ExecuteLoop el;
    private FrameLoop fl;
    private QCanvas canvas;
    private QFrame qf;

    public Rule() {
        akm = new ActionKeyManager();
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
     * {@link qow.framework.system.ExecuteLoop#setRule(Rule, FrameLoop)}で呼び出されるメソッド<br>
     * インスタンス後に{@link QFrame}を定義されてから呼び出される<br>
     * 新しく{@link Rule#getQFrame()}が定義されていない場合{@link Rule#getQFrame()}はここ以降で使用できる
     */
    public void init() {
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
        if (qf.isActive()) {
            paintActive(canvas.getGraphicsImage());
        } else {
            paintInactive(canvas.getGraphicsImage());
        }
        qf.getQPanel().repaint();
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
     * {@link QPanel}に投影する{@link QCanvas}を返す
     *
     * @return 設定された画像キャンパス
     */
    public QCanvas getQCanvas() {
        return canvas;
    }

    /**
     * 新しい{@link QCanvas}を設定する
     *
     * @param canvas 新しい{@link QCanvas}
     */
    public void setQCanvas(QCanvas canvas) {
        this.canvas = canvas;
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
     * {@link QFrame}を設定する
     *
     * @param qf 新しいフレーム
     */
    public void setQFrame(QFrame qf) {
        this.qf = qf;
        addListener(getQFrame());
    }

    /**
     * このクラスに{@link QFrame}が設定されているかを返す
     *
     * @return 設定されている場合はtrue
     */
    public boolean hasQFrame() {
        return qf != null;
    }

    /**
     * このクラスに{@link QCanvas}が設定されているかを返す
     *
     * @return 設定されている場合はtrue
     */
    public boolean hasQCanvas() {
        return canvas != null;
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
     * キーがタイプされたときの処理
     *
     * @param e {@link KeyListener}で呼び出された{@link KeyEvent}
     */
    public void typeKey(KeyEvent e) {
    }

    /**
     * キーが押されたときの処理<br>
     * 初期状態として{@link ActionKeyManager}による受付がされている
     *
     * @param e {@link KeyListener}で呼び出された{@link KeyEvent}
     */
    public void pressKey(KeyEvent e) {
        int code = e.getKeyCode();
        for (int i = 0; i < akm.size(); i++) {
            if (akm.get(i).getKeyCode() == code) {
                akm.get(i).press();
            }
        }
    }

    /**
     * キーが離されたときの処理
     * 初期状態として{@link ActionKeyManager}による受付がされている
     *
     * @param e {@link KeyListener}で呼び出された{@link KeyEvent}
     */
    public void releaseKey(KeyEvent e) {
        int code = e.getKeyCode();
        for (int i = 0; i < akm.size(); i++) {
            if (akm.get(i).getKeyCode() == code) {
                akm.get(i).release();
            }
        }
    }

    /**
     * @param e {@link KeyListener}で呼び出された{@link KeyEvent}
     */
    @Override
    public void keyTyped(KeyEvent e) {
        typeKey(e);
    }

    /**
     * @param e {@link KeyListener}で呼び出された{@link KeyEvent}
     */
    @Override
    public void keyPressed(KeyEvent e) {
        pressKey(e);
    }

    /**
     * @param e {@link KeyListener}で呼び出された{@link KeyEvent}
     */
    @Override
    public void keyReleased(KeyEvent e) {
        releaseKey(e);
    }

    /**
     * マウスがクリックされたときの処理
     *
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    public void clickMouse(MouseEvent e) {
    }

    /**
     * マウスがエンターされたときの処理
     *
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    public void enterMouse(MouseEvent e) {
    }

    /**
     * マウスがフレームからでたときの処理
     *
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    public void exitMouse(MouseEvent e) {
    }

    /**
     * マウスが押されたときの処理
     *
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    public void pressMouse(MouseEvent e) {
    }

    /**
     * マウスが離されたときの処理
     *
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    public void releaseMouse(MouseEvent e) {
    }

    /**
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        clickMouse(e);
    }

    /**
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        enterMouse(e);
    }

    /**
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    @Override
    public void mouseExited(MouseEvent e) {
        exitMouse(e);
    }

    /**
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    @Override
    public void mousePressed(MouseEvent e) {
        pressMouse(e);
    }

    /**
     * @param e {@link MouseListener}で呼び出された{@link MouseEvent}
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        releaseMouse(e);
    }

    /**
     * マウスがドラッグされたときの処理
     *
     * @param e {@link MouseMotionListener}で呼び出された{@link MouseEvent}
     */
    public void dragMouse(MouseEvent e) {
    }

    /**
     * マウスが動いたときの処理
     *
     * @param e {@link MouseMotionListener}で呼び出された{@link MouseEvent}
     */
    public void moveMouse(MouseEvent e) {
    }

    /**
     * @param e {@link MouseMotionListener}で呼び出された{@link MouseEvent}
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        dragMouse(e);
    }

    /**
     * @param e {@link MouseMotionListener}で呼び出された{@link MouseEvent}
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        moveMouse(e);
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
     *
     * @param overTime 過ぎた時間のナノ秒
     */
    public void overTimeExecute(double overTime) {
    }

    /**
     * 描写に設定されたレートより時間がかかってしまった場合の処理
     *
     * @param overTime 過ぎた時間のナノ秒
     */
    public void overTimeFrame(double overTime) {
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