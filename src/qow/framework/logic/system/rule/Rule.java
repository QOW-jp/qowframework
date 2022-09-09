package qow.framework.logic.system.rule;

import qow.framework.logic.screen.graphics.Canvas;
import qow.framework.logic.screen.window.MainFrame;

import java.awt.*;
import java.awt.event.*;

/**
 * ループ内での処理を設定するクラス
 *
 * @author QOW
 * @version 2022/09/03
 * @since 1.0.0
 */
public abstract class Rule implements KeyListener, MouseListener, MouseMotionListener {
    private Canvas canvas;
    private MainFrame mf;

    /**
     * {@link qow.framework.logic.system.ExecuteLoop#setRule(Rule)}で呼び出されるメソッド<br>
     * {@link Rule#getFrame()}はこれ以降で使用できる
     */
    public void init() {
    }

    /**
     * 処理ループによって一秒間にレートの回数実行されるメソッド
     */
    public void loop() {
        if (mf.isActive()) {
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
     * 描写ループによって一秒間にレートの回数実行されるメソッド
     */
    public void draw() {
        if (mf.isActive()) {
            paintActive(canvas.getGraphicsImage());
        } else {
            paintInactive(canvas.getGraphicsImage());
        }
        canvas.draw();
    }

    /**
     * フレームアクティブ時の描写
     *
     * @param g {@link Canvas}に投影する{@link Graphics}
     */
    public abstract void paintActive(Graphics g);

    /**
     * フレーム非アクティブ時の描写
     *
     * @param g {@link Canvas}に投影する{@link Graphics}
     */
    public abstract void paintInactive(Graphics g);

    /**
     * {@link qow.framework.logic.screen.window.MainPanel}に投影する{@link Canvas}を返す
     *
     * @return 設定されたCanvas
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * 新しい{@link Canvas}を設定する
     *
     * @param canvas 新しい{@link Canvas}
     */
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * 設定された{@link MainFrame}を返す
     *
     * @return 設定されたフレーム
     */
    public MainFrame getFrame() {
        return mf;
    }

    /**
     * {@link MainFrame}を設定する
     *
     * @param mf 新しいフレーム
     */
    public void setFrame(MainFrame mf) {
        this.mf = mf;
        addListener(getFrame());
    }

    /**
     * キーがタイプされたときの処理
     *
     * @param e {@link KeyListener}で呼び出された{@link KeyEvent}
     */
    public void typeKey(KeyEvent e) {
    }

    /**
     * キーが押されたときの処理
     *
     * @param e {@link KeyListener}で呼び出された{@link KeyEvent}
     */
    public void pressKey(KeyEvent e) {
    }

    /**
     * キーが離されたときの処理
     *
     * @param e {@link KeyListener}で呼び出された{@link KeyEvent}
     */
    public void releaseKey(KeyEvent e) {
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
     * @param mf 設定するフレーム
     */
    public void addListener(MainFrame mf) {
    }

    /**
     * インターフェイスを破棄する
     *
     * @param mf 設定されたフレーム
     */
    public void removeListener(MainFrame mf) {
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