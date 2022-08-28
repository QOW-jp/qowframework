package qow.framework.logic.screen.window;

import qow.framework.logic.screen.graphics.Canvas;

import javax.swing.*;

/**
 * このフレームワーク内で主に使われる{@link JFrame}
 *
 * @author QOW
 * @version 2022-08-29
 * @since 1.0.0
 */
public class MainFrame extends JFrame {
    private final MainPanel mp;

    /**
     * タイトルを設定せずインスタンス化する
     */
    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        mp = new MainPanel();
        add(mp);
    }

    /**
     * タイトルを設定し、インスタンス化する
     *
     * @param title フレームのタイトル
     */
    public MainFrame(String title) {
        this();
        setTitle(title);
    }

    /**
     * {@link MainPanel}に投影される画像を保持した{@link Canvas}を設定<br>
     * {@link Canvas}のサイズに合わせて{@link MainFrame}と{@link MainPanel}のサイズを変更する
     *
     * @param canvas 新しい{@link Canvas}
     */
    public void setCanvas(Canvas canvas) {
        mp.setCanvas(canvas);
        canvas.setPanel(mp);

        pack();
    }

    /**
     * 設定されている{@link MainPanel}を返す
     *
     * @return このフレームのパネル
     */
    public JPanel getMainPanel() {
        return mp;
    }
}