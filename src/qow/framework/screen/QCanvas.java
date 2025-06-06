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
    private final int width,height;

    /**
     * サイズを設定してインスタンス化する
     *
     * @param width  横のサイズ
     * @param height 縦のサイズ
     */
    public QCanvas(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        setPreferredSize(new Dimension(width, height));
    }

    public QCanvas() {
        this(800, 450);
    }


    public Graphics2D getBufferStrategyGraphics2D() {
        /*
         * 一応ぬるぽ対応することでエラーでとまることはなくなるが、
         * 可視化されなければ、ずっとnullかも
         */
        if (bufferStrategy == null) {
            System.err.println("bufferStrategyがぬるぽ");
            try {
                System.out.println("createBufferStrategy");
                createBufferStrategy(2);
                System.out.println("getBufferStrategy");
                bufferStrategy = getBufferStrategy();
            } catch (Exception e) {
                System.err.println("エラーポイント②");
                System.err.println("	[frame.pack();]忘れの可能性");
                System.err.println("	[frame.add(canvas);]忘れの可能性");
                System.err.println("	[frame.setVisible(true);]忘れの可能性");
                e.printStackTrace();
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
        bufferStrategy.show();
    }


    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
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