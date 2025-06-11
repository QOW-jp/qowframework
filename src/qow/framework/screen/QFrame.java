package qow.framework.screen;

import javax.swing.*;
import java.awt.*;

/**
 * {@link qow.framework.system.FrameLoop}で再描写されるフレーム
 *
 * @author QOW
 * @version 2023/11/11
 * @since 1.4.2
 */
public class QFrame extends JFrame {
    private QCanvas canvas;
    private QPanel panel;

    /**
     * タイトルを設定せずインスタンス化する
     */
    public QFrame() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        canvas = new QCanvas();

        panel = new QPanel();
        panel.add(canvas);

        add(panel);

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
     * {@link QPanel}に投影される画像を保持した{@link QCanvas}を設定<br>
     * {@link QCanvas}のサイズに合わせて{@link QFrame}と{@link QPanel}のサイズを変更する
     *
     * @param canvas 新しい{@link QCanvas}
     */
    public void setResolution(QCanvas canvas) {
        this.canvas = canvas;
        panel.setQCanvas(canvas);

        //panel.setPreferredSize(canvas.getPreferredSize());

        System.out.println("1pack前");
        pack();
        System.out.println("1pack後");
    }

    public QCanvas getQCanvas() {
        return canvas;
    }

}