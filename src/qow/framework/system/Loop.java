package qow.framework.system;

import java.util.concurrent.TimeUnit;

/**
 * レートを制御するクラス<br>
 * 可能な限り一秒間に設定されたレートの回数{@link Loop#loop()}を実行する
 *
 * @author QOW
 * @version 2025/06/14
 * @since 1.0.0
 */
public abstract class Loop implements Runnable {
    private boolean loop;   //ループを実行したか
    private boolean looping;    //ループしている最中か
    private long rate;  //理想的なフレームレート
    private long currentRate;   //現在のフレームレート
    private long interval;  //一ループあたりにかかる時間
    private long checkPoint;  //前回実行した時間
    /**
     * {@link Loop}のデフォルトのレート
     */
    public static int DEFAULT_RATE = 60;

    /**
     * レートの初期値を設定し、インスタンス化する
     *
     * @param rate レートの初期値
     */
    public Loop(long rate) {
        setRate(rate);
    }

    /**
     * レートの初期値を設定せずインスタンス化する
     */
    public Loop() {
        this(DEFAULT_RATE);
    }

    /**
     * ループ処理を開始または停止する
     *
     * @param loop ループ処理を開始する場合はtrue
     */
    public void start(boolean loop) {
        this.loop = loop;
        if (loop && !looping) {
            new Thread(this).start();
        }
    }

    /**
     * 現在の設定されたレートを取得する
     *
     * @return 一秒間に実行される回数
     */
    public long getRate() {
        return rate;
    }

    /**
     * 新しくレートを設定する
     *
     * @param rate 一秒間に実行される回数
     */
    public void setRate(long rate) {
        this.rate = rate;
        if (rate <= 0) {
            interval = 0;
        } else {
            interval = 1000000000L / rate;
        }
    }

    private void updateRate(long fpsCheck) {
        long delay = fpsCheck - this.checkPoint;
        if (delay == 0) {
            currentRate = Long.MAX_VALUE;
        } else {
            currentRate = 1000000000L / delay;
        }
        this.checkPoint = fpsCheck;
    }

    /**
     * 現在のレートを取得する
     *
     * @return 一秒間に実行された回数
     */
    public long getCurrentRate() {
        return currentRate;
    }

    /**
     * 可能な限り一秒間に設定されたレートの回数このメソッドを実行する
     */
    public abstract void loop();

    /**
     * 設定されたレートより時間がかかってしまった場合の処理
     * 元々の一ループあたりにかかる時間との差異をナノ秒で返す
     *
     * @param overTime 過ぎた時間のナノ秒
     */
    public abstract void overTime(long overTime);

    /**
     * {@link Thread}がループに使用するメソッド
     */
    public final void run() {
        try {
            looping = true;

            int noDelays = 0;
            long overSleepTime = 0L;

            while (loop) {
                long beforeTime = System.nanoTime();

                //fps計測
                updateRate(System.nanoTime());

                loop();

                long afterTime = System.nanoTime();
                //前回のフレームの休止時間誤差も引いておく
                long sleepTime = this.interval - (afterTime - beforeTime) - overSleepTime;

                if (0 < sleepTime) {
                    noDelays = 0;
                    //休止時間がとれる場合
                    //一回のループ時間が元々の一ループあたりにかかる時間よりかからなかった場合
                    TimeUnit.NANOSECONDS.sleep(sleepTime);
                    //スレッドスリープの誤差
                    overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
                } else if (sleepTime < 0) {
                    //休止時間が取れない場合
                    //一回のループ時間が元々の一ループあたりにかかる時間を超えた場合
                    overTime(-sleepTime);
                    if (16 <= ++noDelays) {
                        Thread.yield(); //他のスレッドを強制実行
                        noDelays = 0;
                    }
                    overSleepTime = 0L;
                } else {
                    //休止時間がとれない場合
                    //一回のループ時間が元々の一ループあたりにかかる時間と全く同じ場合(基本的にない)
                    overSleepTime = 0L;
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            looping = false;
        }
    }
}