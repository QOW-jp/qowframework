package qow.framework.util;

import java.awt.*;

/**
 * {@link java.awt.event.MouseEvent}の状態を記録する
 *
 * @author QOW
 * @version 2022/10/02
 * @since 1.4.4
 */
public class ActionMouse {
    private boolean press, justRelease, justPress, justPressDelay;
    private final Point mouse;

    public ActionMouse() {
        mouse = new Point(0, 0);
    }

    /**
     * 今まで保持されたマウスの状態をリセットする
     */
    public void reset() {
        press = false;
        justRelease = false;
        justPress = false;
        justPressDelay = false;
        mouse.setLocation(0,0);
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
     * マウスの座標を記録する
     * @param mouse {@link java.awt.event.MouseEvent}により呼び出された{@link Point}
     */
    public void move(Point mouse){
        this.mouse.setLocation(mouse);
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