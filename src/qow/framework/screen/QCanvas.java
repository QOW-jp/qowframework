package qow.framework.screen;

import java.awt.*;

/**
 * {@link QPanel}に投影する画像を保持する
 *
 * @author QOW
 * @version 2022/10/02
 * @since 1.4.2
 */
public class QCanvas extends Canvas {
    private Graphics qfg;
    // ダブルバッファリング（db）用
    private Graphics dbg;
    private Image dbImage = null;
    private int width, height;

    /**
     * サイズを設定してインスタンス化する
     *
     * @param width  横のサイズ
     * @param height 縦のサイズ
     */
    public QCanvas(int width, int height) {
        this.width = width;
        this.height = height;

        dbImage = createImage(width, height);
        if (dbImage == null) {
            System.out.println("dbImage is null");
        } else {
            // バッファイメージの描画オブジェクト
            dbg = dbImage.getGraphics();
        }
    }

    /**
     * {@link QPanel}に保持している画像を描写する
     *
     * @param g ペイント対象の{@link Graphics}コンテキスト
     */
    protected void draw(Graphics g, int width, int height) {
        g.drawImage(dbImage, 0, 0, width, height, null);
    }

    /**
     * 初回の呼び出し時にバッファを作成
     */
    public void render(Image image) {
        // 初回の呼び出し時にダブルバッファリング用オブジェクトを作成
        if (dbImage == null) {
            // バッファイメージ
            dbImage = image;
            if (dbImage == null) {
                System.out.println("dbImage is null5");
            } else {
                // バッファイメージの描画オブジェクト
                dbg = dbImage.getGraphics();
                System.out.println("dgb"+dbg);
            }
        }
    }

    /**
     * バッファを画面に描画
     */
    private void paint() {
        try {
            // グラフィックオブジェクトを取得
            Graphics g = qfg;
            if ((g != null) && (dbImage != null)) {
                // バッファイメージを画面に描画
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();
            if (g != null) {
                // グラフィックオブジェクトを破棄
                g.dispose();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void clearBuffer() {
        // バッファをクリアする
        dbg.setColor(Color.WHITE);
        dbg.fillRect(0, 0, WIDTH, HEIGHT);
    }

    /**
     * 描写された画像を返す
     *
     * @return 描写された画像
     */
    public Graphics getGraphicsImage() {
        return dbg;
    }

    /**
     * {@link Graphics2D}にキャストされた{@link Graphics}を返す
     *
     * @return ペイント対象
     */
    public Graphics2D getGraphics2D() {
        return (Graphics2D) dbg;
    }

    /**
     * アクティブレンダリング用の再描画メソッド
     * レンダリングをした後描画する
     * 描画が終わればバッファをクリアする
     */
    public void update() {
//        render();
        paint();
        clearBuffer();
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    public void setGraphics(Graphics g){
        qfg = g;
    }
}