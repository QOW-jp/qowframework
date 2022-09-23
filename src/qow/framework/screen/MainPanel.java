package qow.framework.screen;

import javax.swing.*;
import java.awt.*;

/**
 * {@link MainFrame}に張り付けるパネル<br>
 * {@link Canvas}から得た画像データをペイントする
 *
 * @author QOW
 * @version 2022/09/12
 * @since 1.0.0
 */
public class MainPanel extends JPanel {
    private Canvas canvas;

    /**
     * {@link Canvas}を更新する<br>
     * {@link Canvas}に合ったサイズに変更する
     *
     * @param canvas 新しい{@link Canvas}
     */
    public void setCanvas(Canvas canvas) {
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