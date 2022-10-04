package qow.framework.util;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link qow.framework.screen.QFrame}によって発生した{@link java.awt.event.KeyEvent}の状態を保存する{@link ActionKey}をまとめるクラス
 *
 * @author QOW
 * @version 2022/10/04
 * @since 1.4.6
 */
public class ActionKeyManager {
    private final List<ActionKey> keyList;

    public ActionKeyManager() {
        keyList = new ArrayList<>();
    }

    /**
     * 受け付ける{@link ActionKey}を追加する
     *
     * @param actionKey 追加するキー情報
     */
    public void add(ActionKey actionKey) {
        keyList.add(actionKey);
    }

    /**
     * 指定行の{@link ActionKey}要素を削除する
     *
     * @param index 削除予定のインデックス
     */
    public void remove(int index) {
        keyList.remove(index);
    }

    /**
     * 指定された{@link ActionKey}の要素を削除する
     *
     * @param actionKey 削除予定の{@link ActionKey}
     */
    public void remove(ActionKey actionKey) {
        keyList.remove(actionKey);
    }

    /**
     * リスト内の{@link ActionKey}要素をすべて削除する
     */
    public void clear() {
        keyList.clear();
    }

    /**
     * 指定行の{@link ActionKey}要素を取得する
     *
     * @param index 取得予定のインデックス
     * @return キー情報
     */
    public ActionKey get(int index) {
        return keyList.get(index);
    }

    /**
     * 現在追加されている{@link ActionKey}の要素数を返す
     *
     * @return 追加されたキー情報の数
     */
    public int size() {
        return keyList.size();
    }
}
