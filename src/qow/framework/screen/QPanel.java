package qow.framework.screen;

import javax.swing.*;
import java.awt.*;

/**
 * {@link QFrame}に張り付けるパネル<br>
 * {@link QCanvas}から得た画像データをペイントする
 *
 * @author QOW
 * @version 2022/09/23
 * @since 1.4.2
 */
public class QPanel extends JPanel {
    private QCanvas canvas;

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
}