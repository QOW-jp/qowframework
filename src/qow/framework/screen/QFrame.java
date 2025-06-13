package qow.framework.screen;

import javax.swing.*;
import java.awt.*;

/**
 * {@link qow.framework.system.FrameLoop}で再描写されるフレーム
 *
 * @author QOW
 * @version 2025/06/14
 * @since 1.4.2
 */
public class QFrame extends JFrame {
    private final QCanvas canvas;

    /**
     * タイトルを設定せずインスタンス化する
     */
    public QFrame() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        canvas = new QCanvas();

        Panel field = new Panel(new BorderLayout(0, 0));
        field.add(canvas, BorderLayout.CENTER);
        add(field, BorderLayout.CENTER);

        pack();
    }

    /**
     * タイトルを設定し、インスタンス化する
     *
     * @param title フレームのタイトル
     */
    public QFrame(String title) {
        this();
        setTitle(title);
    }


    /**
     * 描画する{@link QCanvas}を取得する<br>
     * {@link QCanvas}は{@link Component#requestFocus()}をしているので
     * {@link qow.framework.system.Rule#addListener(QFrame)}{@link qow.framework.system.Rule#removeListener(QFrame)}ではこれに実装する
     *
     * @return 指定されたキャンパス
     */
    public QCanvas getQCanvas() {
        return canvas;
    }

    /**
     * 描画範囲の指定及び{@link JFrame#pack()}
     *
     * @param width  描画範囲の横幅
     * @param height 描画範囲の横幅
     */
    public void setCanvasSize(int width, int height) {
        canvas.setScreenSize(width, height);
        pack();
    }
}