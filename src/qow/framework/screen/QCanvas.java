package qow.framework.screen;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * {@link QPanel}に投影する画像を保持する
 *
 * @author QOW
 * @version 2022/10/02
 * @since 1.4.2
 */
public class QCanvas extends Canvas {
    private BufferStrategy bufferStrategy;
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
    public QCanvas(){
        this(800,450);
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

    public Graphics2D getBufferStrategyGraphics2D() {
        /*
         * 一応ぬるぽ対応することでエラーでとまることはなくなるが、
         * 可視化されなければ、ずっとnullかも
         */
        if (bufferStrategy == null) {
            System.err.println("bufferStrategyがぬるぽ");
            try {
                createBufferStrategy(3);
                bufferStrategy = getBufferStrategy();
            } catch (Exception e) {
                System.err.println("エラーポイント②");
                System.err.println("	[frame.pack();]忘れの可能性");
                System.err.println("	[frame.add(canvas);]忘れの可能性");
                System.err.println("	[frame.setVisible(true);]忘れの可能性");
            }
        }

        /*
         * 謎エラーポイント
         * サイズ設定、レイアウト設定、可視化タイミングを疑う
         */
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics(); // Graphicsをゲット！
        if (!bufferStrategy.contentsLost()) { // フルスクリーン化したときになにかをロストするらしいのでその対策
            /*
             * g を使って描画処理
             */
            return g;

        }
        return null;
    }

    /**
     * アクティブレンダリング用の再描画メソッド
     * レンダリングをした後描画する
     * 描画が終わればバッファをクリアする
     */
    public void update() {
//        render();
        paint();
        bufferStrategy.show();
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

    /**
     * peer確定後、バッファストラテジー生成と参照コピーを実行するようにオーバーライド
     * bufferStrategyがぬるぽエラーを回避できる！
     */
    @Override
    public void addNotify() {
        super.addNotify();  //ここでpeer確定
        System.out.println(this.getName() + "のpeer確定");
        //キャンバスのバッファストラテジーを生成

        try {
            createBufferStrategy(3);
            bufferStrategy = getBufferStrategy(); // ループで使用するために参照を保持しておく
            System.out.println(this.getName() + "のバッファストラテジー生成に成功");
        } catch (Exception e) {
            System.err.println("エラーポイント③"); // もしここでエラーがでるというのであれば、わたしはお手あげですｗ
            System.err.println(this.getName() + "のバッファストラテジー生成に失敗");
        }
    }
}