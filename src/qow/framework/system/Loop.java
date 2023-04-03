package qow.framework.system;

import java.util.concurrent.TimeUnit;

/**
 * レートを制御するクラス<br>
 * 可能な限り一秒間に設定されたレートの回数{@link Loop#loop()}を実行する
 *
 * @author QOW
 * @version 2022/09/23
 * @since 1.0.0
 */
public abstract class Loop implements Runnable {
    private final int oneSec = (int) Math.pow(10, 9);    //1,000,000,000ns
    private boolean loop, looping;
    private long rate, currentRate;
    private long currentTime;

    /**
     * レートの初期値を設定し、インスタンス化する
     *
     * @param rate レートの初期値
     */
    public Loop(long rate) {
        this.rate = rate;
    }

    /**
     * レートの初期値を設定せずインスタンス化する
     */
    public Loop() {
        this(0);
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
    public double getRate() {
        return rate;
    }

    /**
     * 新しくレートを設定する
     *
     * @param rate 一秒間に実行される回数
     */
    public void setRate(long rate) {
        this.rate = rate;
    }

    private void updateRate() {
        currentRate = oneSec / (System.nanoTime() - currentTime);
        if (0 < rate && rate < currentRate) {
            currentRate = rate;
        }
    }

    /**
     * 現在のレートを取得する
     *
     * @return 一秒間に実行された回数
     */
    public double getCurrentRate() {
        return currentRate;
    }

    /**
     * 可能な限り一秒間に設定されたレートの回数このメソッドを実行する
     */
    public abstract void loop();

    /**
     * 設定されたレートより時間がかかってしまった場合の処理
     *
     * @param overTime 過ぎた時間のナノ秒
     */
    public abstract void overTime(double overTime);

    /**
     * {@link Thread}がループにしようするメソッド
     *
     * @deprecated マルチスレッド用のメソッドなので使用しない
     */
    public void run() {
        looping = true;
        currentTime = System.nanoTime();
        while (loop) {
            loop();

            delay();
        }
        looping = false;

        long error = 0;
        long idealSleep = (1000 << 16) / rate;
        long oldTime;
        long newTime = System.currentTimeMillis() << 16;

        while (true) {
            oldTime = newTime;

            loop();

            newTime = System.currentTimeMillis() << 16;
            long sleepTime = idealSleep - (newTime - oldTime) - error; // 休止できる時間
            if (sleepTime < 0x20000) sleepTime = 0x20000; // 最低でも2msは休止
            oldTime = newTime;
            Thread.sleep(sleepTime >> 16); // 休止
            newTime = System.currentTimeMillis() << 16;
            error = newTime - oldTime - sleepTime; // 休止時間の誤差
        }

    }

    private void delay() {
        if (0 < rate) {
            long sleepTime = (long) (oneSec / rate - (System.nanoTime() - currentTime));
            updateRate();
            if (0 < sleepTime) {
                try {
                    TimeUnit.NANOSECONDS.sleep(sleepTime);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                overTime(sleepTime);
            }
        } else {
            updateRate();
        }
        currentTime = System.nanoTime();
    }
}