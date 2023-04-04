package qow.framework.system;

/**
 * レートを制御するクラス<br>
 * 可能な限り一秒間に設定されたレートの回数{@link Loop#loop()}を実行する
 *
 * @author QOW
 * @version 2023/04/04
 * @since 1.0.0
 */
public abstract class Loop implements Runnable {
    private boolean loop, looping;
    private long rate, currentRate;
    private long sleep;
    private long fpsCheck;

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
        sleep = 1000000000L / rate;
    }

    private void updateRate(long fpsCheck) {
        long delay = fpsCheck - this.fpsCheck;
        if (delay <= 0) delay++;
        currentRate = 1000 / delay;
        this.fpsCheck = fpsCheck;
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
     *
     * @param overTime 過ぎた時間のミリ秒
     */
    public abstract void overTime(long overTime);

    /**
     * {@link Thread}がループにしようするメソッド
     *
     * @deprecated マルチスレッド用のメソッドなので使用しない
     */
    public void run() {
        try {
            looping = true;

            long timeDiff;
            long overSleepTime = 0L;

            while (loop) {
                long beforeTime = System.nanoTime();

                loop();

                updateRate(System.currentTimeMillis());

                long afterTime = System.nanoTime();
                timeDiff = afterTime - beforeTime;
                // 前回のフレームの休止時間誤差も引いておく
                long sleepTime = (sleep - timeDiff) - overSleepTime;

                if (0 < sleepTime / 1000000) {
                    // 休止時間がとれる場合
                    Thread.sleep(sleepTime / 1000000); // nano->ms
                    // sleep()の誤差
                    overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
                } else if (sleepTime / 1000000 < 0) {
                    overTime(-sleepTime / 1000000);
                    // 休止時間がとれない場合
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