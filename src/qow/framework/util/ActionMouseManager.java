package qow.framework.util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link qow.framework.screen.QFrame}によって発生した{@link java.awt.event.MouseEvent}の状態を保存する{@link ActionMouse}をまとめるクラス
 *
 * @author QOW
 * @version 2022/10/18
 * @since 1.5.0
 */
public class ActionMouseManager {
    private final List<ActionMouse> mouseList;
    private Point mouse;

    public ActionMouseManager() {
        mouseList = new ArrayList<>();
        mouse = new Point();
    }

    /**
     * 最後に{@link ActionMouseManager#setMousePoint(Point)}により記録されたマウスの位置を取得する
     *
     * @return マウスの座標
     */
    public Point getMousePoint() {
        return mouse;
    }

    /**
     * ウィンドウ上にあるマウスの位置を記録する
     *
     * @param mouse マウスの座標を{@link Point}で返す
     */
    public void setMousePoint(Point mouse) {
        this.mouse = mouse;
    }

    /**
     * 受け付ける{@link ActionMouse}を追加する
     *
     * @param actionKey 追加する情報
     */
    public void add(ActionMouse actionKey) {
        mouseList.add(actionKey);
    }

    /**
     * 指定行の{@link ActionMouse}要素を削除する
     *
     * @param index 削除予定のインデックス
     */
    public void remove(int index) {
        mouseList.remove(index);
    }

    /**
     * 指定された{@link ActionMouse}の要素を削除する
     *
     * @param actionKey 削除予定の{@link ActionMouse}
     */
    public void remove(ActionMouse actionKey) {
        mouseList.remove(actionKey);
    }

    /**
     * リスト内の{@link ActionMouse}要素をすべて削除する
     */
    public void clear() {
        mouseList.clear();
    }

    /**
     * 指定行の{@link ActionMouse}要素を取得する
     *
     * @param index 取得予定のインデックス
     * @return ボタン情報
     */
    public ActionMouse get(int index) {
        return mouseList.get(index);
    }

    /**
     * 現在追加されている{@link ActionMouse}の要素数を返す
     *
     * @return 追加されたボタン情報の数
     */
    public int size() {
        return mouseList.size();
    }
}