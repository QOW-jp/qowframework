package qow.framework.logic.screen.window;

import qow.framework.logic.screen.graphics.Canvas;

import javax.swing.*;
import java.awt.*;

/**
 * {@link MainFrame}に張り付けるパネル<br>
 * {@link Canvas}から得た画像データをペイントする
 *
 * @author QOW
 * @version 2022-08-29
 * @since 1.0.0
 */
public class MainPanel extends JPanel {
    private Canvas canvas;

    /**
     * レイアウトを設定してインスタンス化
     */
    public MainPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    }

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
        canvas.draw(g);
    }

    /**
     * 再描写する
     */
    public void draw() {
        repaint();
    }
}