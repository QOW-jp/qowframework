package qow.framework.screen;

import javax.swing.*;
import java.awt.*;

/**
 * {@link QFrame}に張り付けるパネル<br>
 * {@link QCanvas}から得た画像データをペイントする
 *
 * @author QOW
 * @version 2022/09/29
 * @since 1.4.2
 */
public class QPanel extends JPanel {
    private QCanvas canvas;

    /**
     * {@link QPanel}に投影する{@link QCanvas}を返す
     *
     * @return 設定された画像キャンパス
     */
    public QCanvas getQCanvas() {
        return canvas;
    }

    /**
     * {@link QCanvas}を更新する<br>
     * {@link QCanvas}に合ったサイズに変更する
     *
     * @param canvas 新しい{@link QCanvas}
     */
    public void setQCanvas(QCanvas canvas) {
        this.canvas = canvas;
        setPreferredSize(new Dimension(canvas.getWidth(), canvas.getHeight()));
    }

    /**
     * 再描写するときに呼び出される
     *
     * @param g ペイント対象の{@link Graphics}コンテキスト
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        canvas.draw(g, (int) getPreferredSize().getWidth(), (int) getPreferredSize().getHeight());
    }

    /**
     * 再描画時の画面のチラツキを抑えるために画面の暗転を無効化する
     *
     * @param g ペイント対象の{@link Graphics}コンテキスト
     */
    @Override
    public void update(Graphics g) {
        paintComponent(g);  //次の画面のイメージを描写する
    }
}