package qow.framework.screen;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * {@link QFrame}に投影する画像を保持する
 *
 * @author QOW
 * @version 2025/06/14
 * @since 1.4.2
 */
public class QCanvas extends Canvas {
    /**
     * {@link QFrame}のデフォルトのウィンドウサイズの指定
     */
    public static final int DEFAULT_WIDTH = 1280;
    /**
     * {@link QFrame}のデフォルトのウィンドウサイズの指定
     */
    public static final int DEFAULT_HEIGHT = 720;

    private BufferStrategy bufferStrategy;
    private int numBuffers = 2;
    private int width, height;

    /**
     * サイズを設定してインスタンス化
     *
     * @param width  横のサイズ
     * @param height 縦のサイズ
     */
    public QCanvas(int width, int height) {
        super();
        setScreenSize(width, height);
    }

    /**
     * サイズをデフォルトに指定してインスタンス化
     */
    public QCanvas() {
        this(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }


    /**
     * アクティブレンダリング用のGraphicsを取得
     *
     * @return drawGraphics
     */
    public Graphics getBufferStrategyGraphics() {

        if (bufferStrategy == null) {
            try {
                createBufferStrategy(numBuffers);
                bufferStrategy = getBufferStrategy();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Graphics g = bufferStrategy.getDrawGraphics();
        if (!bufferStrategy.contentsLost()) {   //フルスクリーン化したときになにかをロストするらしいのでその対策
            return g;
        }
        return null;
    }

    /**
     * アクティブレンダリング用の再描画メソッド
     */
    public void update() {
        bufferStrategy.show();
    }

    /**
     * peer確定後、バッファストラテジー生成と参照コピーを実行するようにシャローコピー
     */
    @Override
    public void addNotify() {
        super.addNotify();  //ここでpeer確定

        try {
            createBufferStrategy(numBuffers);
            bufferStrategy = getBufferStrategy(); // ループで使用するために参照を保持しておく
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 描画範囲の指定
     *
     * @param width  横幅
     * @param height 縦幅
     */
    public void setScreenSize(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        this.width = width;
        this.height = height;
    }

    /**
     * @return 描画範囲の横幅
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return 描画範囲の縦幅
     */
    public int getHeight() {
        return height;
    }

    /**
     * アクティブレンダリング用のバッファ数の指定
     *
     * @param numBuffers バッファ数
     */
    public void setNumBuffers(int numBuffers) {
        this.numBuffers = numBuffers;
    }

    /**
     * アクティブレンダリング用のバッファ数の取得
     *
     * @return バッファ数
     */
    public int getNumBuffers() {
        return numBuffers;
    }
}