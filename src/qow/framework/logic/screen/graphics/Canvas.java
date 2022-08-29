package qow.framework.logic.screen.graphics;

import qow.framework.logic.screen.window.MainPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * {@link MainPanel}に投影する画像を保持する
 *
 * @author QOW
 * @version 2022/08/29
 * @since 1.0.0
 */
public class Canvas {
    private final BufferedImage img;
    private final Graphics gra;
    private int width, height;
    private MainPanel mp;

    /**
     * サイズを設定してインスタンス化する
     *
     * @param width  横のサイズ
     * @param height 縦のサイズ
     */
    public Canvas(int width, int height) {
        setWidth(width);
        setHeight(height);

        img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_BGR);
        gra = img.getGraphics();
    }

    /**
     * 再描写する
     */
    public void draw() {
        mp.draw();
    }

    /**
     * {@link MainPanel}に保持している画像を描写する
     *
     * @param g ペイント対象の{@link Graphics}コンテキスト
     */
    public void draw(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }

    /**
     * 描写された画像を返す
     *
     * @return 描写された画像
     */
    public Graphics getGraphicsImage() {
        return gra;
    }

    /**
     * 描写する{@link MainPanel}を設定する
     *
     * @param mp 新しいMainPanel
     */
    public void setPanel(MainPanel mp) {
        this.mp = mp;
    }

    /**
     * {@link Graphics2D}にキャストされた{@link Graphics}を返す
     *
     * @return ペイント対象
     */
    public Graphics2D getGraphics2D() {
        return (Graphics2D) gra;
    }

    /**
     * 横のサイズを取得する
     *
     * @return 横のサイズ
     */
    public int getWidth() {
        return width;
    }

    /**
     * 横のサイズを設定する
     *
     * @param width 横のサイズ
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * 縦のサイズを取得する
     *
     * @return 縦のサイズ
     */
    public int getHeight() {
        return height;
    }

    /**
     * 縦のサイズを設定する
     *
     * @param height 縦のサイズ
     */
    public void setHeight(int height) {
        this.height = height;
    }
}