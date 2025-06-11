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

        System.out.println("create canvas");
        canvas = new QCanvas();

        System.out.println("create panel");
        panel = new QPanel();
        System.out.println("created panel");
        panel.add(canvas);
        System.out.println("panel.add(canvas);");

        add(panel);
        System.out.println("add(panel);");

        System.out.println("pack前");
        pack();
        System.out.println("packあと");

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

//    /**
//     * {@link QPanel}に投影される画像を保持した{@link QCanvas}を設定<br>
//     * {@link QCanvas}のサイズに合わせて{@link QFrame}と{@link QPanel}のサイズを変更する
//     *
//     * @param canvas 新しい{@link QCanvas}
//     */
//    public void setResolution(QCanvas canvas) {
//        this.canvas = canvas;
//        System.out.println("panel.setQCanvas");
//        panel.setQCanvas(canvas);
//
//        //panel.setPreferredSize(canvas.getPreferredSize());
//
//        System.out.println("1pack前");
//        pack();
//        System.out.println("1pack後");
//    }

    public QCanvas getQCanvas() {
        return canvas;
    }

    public void setCanvasSize(int width, int height) {
        canvas.setPreferredSize(new Dimension(width,height));
        pack();
    }
}