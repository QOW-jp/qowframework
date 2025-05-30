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
    private final QPanel qp;

    /**
     * タイトルを設定せずインスタンス化する
     */
    public QFrame() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        qp = new QPanel();
        getContentPane().add(qp, BorderLayout.CENTER);
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
        qp.setQCanvas(canvas);
        pack();
        qp.getQCanvas().setGraphics(getGraphics());
        qp.getQCanvas().render(createImage(WIDTH,HEIGHT));
    }

    /**
     * 解像度は変えずにフレームの大きさを変更する
     *
     * @param width  フレームの横幅
     * @param height フレームの縦幅
     * @deprecated マウスリスナー系統の座標取得がずれる
     */
    public void setFrameSize(int width, int height) {
        remove(qp);
        qp.setPreferredSize(new Dimension(width, height));

        add(qp);

        pack();
    }

    /**
     * 設定されている{@link QPanel}を返す
     *
     * @return このフレームのパネル
     */
    public QPanel getQPanel() {
        return qp;
    }
}