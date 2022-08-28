package qow.framework.logic.screen.graphics;

import qow.framework.logic.screen.window.MainPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * パネルに投影する画面
 *
 * @author QOW
 * @version 1.0.0
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
     * 画面に画像を描写する
     *
     * @param g 画像を描写する画面
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
     * MainPanelを設定する
     *
     * @param mp 新しいMainPanel
     */
    public void setPanel(MainPanel mp) {
        this.mp = mp;
    }
    public Graphics2D getGraphics2D(){
        return (Graphics2D)gra;
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