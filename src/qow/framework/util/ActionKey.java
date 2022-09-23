package qow.framework.util;

import java.awt.event.KeyEvent;

/**
 * {@link KeyEvent}の{@link KeyEvent#getKeyCode()}を保持しキーの状態を記録する
 *
 * @author QOW
 * @version 2022/08/29
 * @since 1.0.0
 */
public class ActionKey {
    private final int keyCode;
    private boolean press, justRelease, justPress, justPressDelay;

    /**
     * {@link KeyEvent#getKeyCode()}を設定し、インスタンス化する
     *
     * @param keyCode {@link java.awt.event.KeyListener}で呼び出された{@link KeyEvent#getKeyCode()}
     */
    public ActionKey(int keyCode) {
        this.keyCode = keyCode;
    }

    /**
     * 設定された{@link KeyEvent#getKeyCode()}を返す
     *
     * @return 設定された値
     */
    public int getKeyCode() {
        return keyCode;
    }

    /**
     * 今まで入力されたキーの状態をリセットする
     */
    public void reset() {
        press = false;
        justRelease = false;
        justPress = false;
        justPressDelay = false;
    }

    /**
     * キーが押されている状態を記録する
     */
    public void press() {
        press = true;
        if (!justPressDelay) {
            justPressDelay = true;
            justPress = true;
        }
    }

    /**
     * キーが離された状態を記録する
     */
    public void release() {
        press = false;
        justRelease = true;
        justPressDelay = false;
    }

    /**
     * キーが初めて押されたかを返す
     *
     * @return キーが初めて押されている場合はtrue
     */
    public boolean isJustPress() {
        if (justPress) {
            justPress = false;
            return true;
        }
        return false;
    }

    /**
     * キーが初めて離されたかを返す
     *
     * @return キーが初めて離されている場合はtrue
     */
    public boolean isJustRelease() {
        if (justRelease) {
            justRelease = false;
            return true;
        }
        return false;
    }

    /**
     * キーが押されているかを返す
     *
     * @return キーが押されている状態の場合はtrue
     */
    public boolean isPress() {
        return press;
    }
}