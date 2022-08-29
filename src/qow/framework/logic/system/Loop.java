package qow.framework.logic.system;

import java.util.concurrent.TimeUnit;

/**
 * レートを制御するクラス<br>
 * 可能な限り一秒間に設定されたレートの回数{@link Loop#loop()}を実行する
 *
 * @author QOW
 * @version 2022/08/29
 * @since 1.0.0
 */
public abstract class Loop implements Runnable {
    private final int oneSec = (int) Math.pow(10, 9);    //1,000,000,000ns
    private boolean loop, looping;
    private int rate;

    /**
     * レートの初期値を設定し、インスタンス化する
     *
     * @param rate レートの初期値
     */
    public Loop(int rate) {
        this.rate = rate;
    }

    /**
     * レートの初期値を設定せずインスタンス化する
     */
    public Loop() {
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
     * 一秒間に設定されたレートの回数の処理を行う
     *
     * @return 一秒間に実行される回数
     */
    public int getRate() {
        return rate;
    }

    /**
     * 新しくレートを設定する
     *
     * @param rate 一秒間に実行される回数
     */
    public void setRate(int rate) {
        this.rate = rate;
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
    public abstract void overTime(long overTime);

    /**
     * {@link Thread}がループにしようするメソッド
     *
     * @deprecated マルチスレッド用のメソッドなので使用しない
     */
    public void run() {
        try {
            looping = true;
            while (loop) {
                long st = System.nanoTime();

                loop();

                long sleepTime = oneSec / getRate() - (System.nanoTime() - st);
                if (0 > sleepTime) {
                    overTime(sleepTime);
                } else {
                    TimeUnit.NANOSECONDS.sleep(sleepTime);
                }
            }
            looping = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}