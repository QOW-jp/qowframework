package qow.framework.util;

import java.awt.event.MouseEvent;

/**
 * {@link java.awt.event.MouseEvent}の状態を記録する
 *
 * @author QOW
 * @version 2022/10/18
 * @since 1.4.4
 */
public class ActionMouse {
    private final int button;
    private boolean press, justRelease, justPress, justPressDelay;

    /**
     * {@link MouseEvent#getButton()}を設定し、インスタンス化する
     *
     * @param button {@link MouseEvent#getButton()}で呼び出された
     */
    public ActionMouse(int button) {
        this.button = button;
    }

    /**
     * {@link MouseEvent#getButton()}を設定せず、インスタンス化する
     * この場合{@link ActionMouse#getButton()}で呼び出される定数は-1となる
     */
    public ActionMouse() {
        this(-1);
    }

    /**
     * 設定された{@link MouseEvent#getButton()}を返す
     *
     * @return 設定された値
     */
    public int getButton() {
        return button;
    }

    /**
     * 今まで保持されたマウスの状態をリセットする
     */
    public void reset() {
        press = false;
        justRelease = false;
        justPress = false;
        justPressDelay = false;
    }

    /**
     * クリックされている状態を記録する
     */
    public void press() {
        press = true;
        if (!justPressDelay) {
            justPressDelay = true;
            justPress = true;
        }
    }

    /**
     * クリックが離された状態を記録する
     */
    public void release() {
        press = false;
        justRelease = true;
        justPressDelay = false;
    }

    /**
     * クリックが初めて押されたかを返す
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
     * クリックが初めて離されたかを返す
     *
     * @return クリックが初めて離されている場合はtrue
     */
    public boolean isJustRelease() {
        if (justRelease) {
            justRelease = false;
            return true;
        }
        return false;
    }

    /**
     * クリックされているかを返す
     *
     * @return クリックされている状態の場合はtrue
     */
    public boolean isPress() {
        return press;
    }
}