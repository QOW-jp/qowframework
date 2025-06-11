package qow.framework.screen;

import java.awt.*;

/**
 * {@link QFrame}に張り付けるパネル<br>
 * {@link QCanvas}から得た画像データをペイントする
 *
 * @author QOW
 * @version 2022/09/29
 * @since 1.4.2
 */
public class QPanel extends Panel {
    private QCanvas canvas;

    public QPanel() {
        super();
        //this(QCanvas.DEFAULT_WIDTH, QCanvas.DEFAULT_HEIGHT);
    }

    public QPanel(int width, int height) {
        super();
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * {@link QPanel}に投影する{@link QCanvas}を返す
     *
     * @return 設定された画像キャンパス
     */
    public QCanvas getQCanvas() {
        return canvas;
    }

    /**
     * {@link QCanvas}を更新する<br>
     * {@link QCanvas}に合ったサイズに変更する
     *
     * @param canvas 新しい{@link QCanvas}
     */
    public void setQCanvas(QCanvas canvas) {
        remove(this.canvas);
        this.canvas = canvas;
        add(canvas);
    }

}