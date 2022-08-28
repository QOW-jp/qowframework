package qow.framework.logic.screen.window;

import qow.framework.logic.screen.graphics.Canvas;

import javax.swing.*;

/**
 * メインのフレーム
 *
 * @author QOW
 * @version 1.0.0
 */
public class MainFrame extends JFrame {
    private final MainPanel mp;

    /**
     * タイトルを設定し、インスタンス化する
     *
     * @param title フレームのタイトル
     */
    public MainFrame(String title) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        mp = new MainPanel();
        add(mp);
    }
    /**
     * Canvasの情報を更新する
     *
     * @param canvas 新しいCanvas
     */
    public void setCanvas(Canvas canvas) {
        mp.setCanvas(canvas);
        canvas.setPanel(mp);

        pack();
    }

    public JPanel getMainPanel() {
        return mp;
    }
}