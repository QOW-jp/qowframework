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
}