package qow.framework.logic.screen.window;

import qow.framework.logic.screen.graphics.Canvas;

import javax.swing.*;
import java.awt.*;

/**
 * フレームに張り付けるパネル
 *
 * @author QOW
 * @version 1.0.0
 */
public class MainPanel extends JPanel {
    private Canvas canvas;

    /**
     * インスタンス化
     */
    public MainPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
    }

    /**
     * Canvasを更新する
     * Canvasに合ったサイズに変更する
     *
     * @param canvas 新しいCanvas
     */
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        setPreferredSize(new Dimension(canvas.getWidth(), canvas.getHeight()));
    }

    /**
     * 再描写するときに呼び出される
     *
     * @param g Graphicsの変数
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