package qow.framework.system;

import qow.framework.screen.QCanvas;
import qow.framework.screen.QFrame;
import qow.framework.screen.QPanel;
import qow.framework.util.ActionKeyManager;
import qow.framework.util.ActionMouseManager;

import java.awt.*;
import java.awt.event.*;

/**
 * {@link ExecuteLoop}と{@link FrameLoop}内での処理を設定するクラス
 *
 * @author QOW
 * @version 2023/04/04
 * @since 1.0.0
 */
public abstract class Rule implements KeyListener, MouseListener, MouseMotionListener {
    private final ActionKeyManager akm;
    private final ActionMouseManager amm;
    private ExecuteLoop el;
    private FrameLoop fl;
    private QFrame qf;
    private QCanvas canvas;

    public Rule() {
        akm = new ActionKeyManager();
        amm = new ActionMouseManager();

        qf = new QFrame();
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
     * {@link qow.framework.system.ExecuteLoop#setRule(Rule, FrameLoop)}で呼び出されるメソッド<br>
     * インスタンス後に{@link QFrame}を定義されてから呼び出される<br>
     * 新しく{@link Rule#getQFrame()}が定義されていない場合{@link Rule#getQFrame()}はここ以降で使用できる
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
        // 初回の呼び出し時にダブルバッファリング用オブジェクトを作成
//        canvas.render();

        Graphics g = canvas.getBufferStrategyGraphics();
        if (g == null) {
            System.out.println("g = null");
            return;
        }

        if (qf.isActive()) {
//            System.out.println("active");
            paintActive(g);
        } else {
//            System.out.println("inactive");
            paintInactive(g);
        }
//        qf.getQPanel().repaint(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.update();
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
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

//    /**
//     * 新しい{@link QCanvas}を設定する
//     *
//     * @param canvas 新しい{@link QCanvas}
//     */
//    public void setQCanvas(QCanvas canvas) {
//        this.canvas = canvas;
//        qf.setResolution(canvas);
//    }

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
        //removeListener(this.qf);
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