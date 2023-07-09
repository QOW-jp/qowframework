package qow.demo;

import qow.framework.screen.QCanvas;
import qow.framework.screen.QFrame;
import qow.framework.system.MainSystem;
import qow.framework.system.Rule;
import qow.framework.util.ActionKey;
import qow.framework.util.ActionMouse;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class DemoFramework {
    public static void main(String[] args) {
        System.out.println("build by JDK-17.0.4");
        System.out.println("qow_framework-1.6.1-α DemoModel\n");
        try {
            MainSystem ms = new MainSystem();
            DemoFrameworkRule1 gr = new DemoFrameworkRule1();
            ms.setRule(gr);

            gr.getQFrame().setVisible(true);
            ms.start(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class DemoFrameworkRule1 extends Rule {
    final int DRAG_MAX_RANGE = 100;
    final int RATE_CHECK_LENGTH = 100;
    int timer;
    ActionKey[][] actionKey;
    ActionMouse actionMouse;
    int ballX, ballY;
    List<Point> dragPoint;
    List<Integer> dragPointRange;
    boolean inactive = true;
    boolean changeRule;
    Point mouseCursor;
    int rateCheckerCountByFrame, rateCheckerCountByExecute;
    int[] rateCheckerByFrame = new int[RATE_CHECK_LENGTH];
    int[] rateCheckerByExecute = new int[RATE_CHECK_LENGTH];


    DemoFrameworkRule1() {
        QFrame qf = new QFrame("DemoFrameworkRule1");
        setQFrame(qf);

        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        DisplayMode displayMode = env.getDefaultScreenDevice().getDisplayMode();
        setQCanvas(new QCanvas(displayMode.getWidth(), displayMode.getHeight()));

        qf.setUndecorated(true);

        int[][] keyCode = {{65, 68, 87, 83, 10}};
        actionKey = new ActionKey[keyCode.length][keyCode[0].length];
        for (int i = 0; i < actionKey.length; i++) {
            for (int j = 0; j < actionKey[i].length; j++) {
                actionKey[i][j] = new ActionKey(keyCode[i][j]);
                getActionKeyManager().add(actionKey[i][j]);
            }
        }

        actionMouse = new ActionMouse(MouseEvent.BUTTON1);
        getActionMouseManager().add(actionMouse);


        dragPoint = new ArrayList<>();
        dragPointRange = new ArrayList<>();

        mouseCursor = getActionMouseManager().getMousePoint();
    }

    public void init() {
        getExecuteLoop().setRate(100);
        getFrameLoop().setRate(100);

        getQFrame().setVisible(true);
    }

    public void loopActive() {
        timer++;

        inactive = true;

        if (timer > 200) {
            timer = 0;
        }

        if (actionKey[0][0].isPress()) {
            ballX -= 5;
        }
        if (actionKey[0][1].isPress()) {
            ballX += 5;
        }
        if (actionKey[0][2].isPress()) {
            ballY -= 5;
        }
        if (actionKey[0][3].isPress()) {
            ballY += 5;
        }
        if (actionKey[0][4].isPress()) {
            changeRule = true;
        }

        if (actionMouse.isPress()) {
            Point point = new Point(mouseCursor);
            if (dragPoint.size() == 0 || dragPoint.get(dragPoint.size() - 1).getX() != point.getX() || dragPoint.get(dragPoint.size() - 1).getY() != point.getY()) {
                dragPoint.add(point);
                dragPointRange.add(0);
            }
        }

        if (0 < dragPoint.size()) {
            for (int i = 0; i < dragPoint.size(); i++) {
                if (dragPointRange.get(i) < DRAG_MAX_RANGE) {
                    dragPointRange.set(i, dragPointRange.get(i) + 1);
                } else {
                    dragPoint.remove(i);
                    dragPointRange.remove(i);
                    i--;
                }
            }
        }

        if (RATE_CHECK_LENGTH <= rateCheckerCountByExecute) {
            rateCheckerCountByExecute = 0;
        }
        rateCheckerByExecute[rateCheckerCountByExecute] = (int) getExecuteLoop().getCurrentRate();
        rateCheckerCountByExecute++;

        if (RATE_CHECK_LENGTH <= rateCheckerCountByFrame) {
            rateCheckerCountByFrame = 0;
        }
        rateCheckerByFrame[rateCheckerCountByFrame] = (int) getFrameLoop().getCurrentRate();
        rateCheckerCountByFrame++;
    }

    public void loopInactive() {
        for (ActionKey[] keys : actionKey) {
            for (ActionKey key : keys) {
                key.release();
            }
        }
    }

    public void paintActive(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getQCanvas().getWidth(), getQCanvas().getHeight());

        g.setColor(Color.white);
        g.fillOval(ballX - timer / 2, ballY - timer / 2, timer, timer);

        try {
            for (int i = 0; i < dragPoint.size(); i++) {
                int range = dragPointRange.get(i);
                int x = (int) dragPoint.get(i).getX() - range / 2;
                int y = (int) dragPoint.get(i).getY() - range / 2;
                g.drawOval(x, y, range, range);
            }
        } catch (IndexOutOfBoundsException ignored) {
        }

        try {
            FontMetrics fm = g.getFontMetrics();
            String text = "予定eps:" + (int) getExecuteLoop().getRate() + " 予定fps:" + (int) getFrameLoop().getRate() + " | eps:" + Math.floor(Arrays.stream(rateCheckerByExecute).average().getAsDouble()) + " fps:" + Math.floor(Arrays.stream(rateCheckerByFrame).average().getAsDouble());
            g.drawString(text, 0, fm.getMaxAscent());
        } catch (NoSuchElementException ignored) {
        }

        g.drawString("アンチエイリアシング無効化", 0, getQCanvas().getHeight());
    }

    public void paintInactive(Graphics g) {
        if (inactive) {
            inactive = false;
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, getQCanvas().getWidth(), getQCanvas().getHeight());

            FontMetrics fm = g.getFontMetrics();
            String text = "FRAME MODE PAUSE";
            Rectangle rectText = fm.getStringBounds(text, g).getBounds();
            int startX = getQCanvas().getWidth() / 2 - rectText.width / 2;
            int startY = getQCanvas().getHeight() / 2 - rectText.height / 2 + fm.getMaxAscent();
            g.setColor(Color.white);
            g.drawString(text, startX, startY);
        }
    }

    public void overTimeExecute(long overTime) {
        System.out.println("EL誤差:" + overTime + "ns" + "  @" + LocalDateTime.now());
    }

    public void overTimeFrame(long overTime) {
        System.out.println("FL誤差:" + overTime + "ns" + "  @" + LocalDateTime.now());
    }

    public boolean isChangeRule() {
        return changeRule;
    }

    public Rule getNewRule() {
        return new DemoFrameworkRule2();
    }

    public void addListener(QFrame qf) {
        qf.addKeyListener(this);
        qf.getQPanel().addMouseListener(this);
        qf.getQPanel().addMouseMotionListener(this);
    }

    public void removeListener(QFrame qf) {
        qf.removeKeyListener(this);
        qf.getQPanel().removeMouseListener(this);
        qf.getQPanel().removeMouseMotionListener(this);
    }
}

class DemoFrameworkRule2 extends Rule {
    final int CLICK_MAX_RANGE = 1200;
    final int RATE_CHECK_LENGTH = 100;
    int timer;
    int ballX, ballY;
    ActionKey[][] actionKey;
    ActionMouse actionMouse;
    List<Point> clickPoint;
    List<Integer> clickPointRange;
    boolean inactive = true;
    boolean changeRule;
    Point mouseCursor;
    int rateCheckerCountByFrame, rateCheckerCountByExecute;
    int[] rateCheckerByFrame = new int[RATE_CHECK_LENGTH];
    int[] rateCheckerByExecute = new int[RATE_CHECK_LENGTH];

    DemoFrameworkRule2() {
        int[][] keyCode = {{65, 68, 87, 83, 10}};
        actionKey = new ActionKey[keyCode.length][keyCode[0].length];
        for (int i = 0; i < actionKey.length; i++) {
            for (int j = 0; j < actionKey[i].length; j++) {
                actionKey[i][j] = new ActionKey(keyCode[i][j]);
                getActionKeyManager().add(actionKey[i][j]);
            }
        }

        actionMouse = new ActionMouse(MouseEvent.BUTTON1);
        getActionMouseManager().add(actionMouse);

        clickPoint = new ArrayList<>();
        clickPointRange = new ArrayList<>();

        mouseCursor = getActionMouseManager().getMousePoint();
    }

    public void init() {
        getQFrame().dispose();
        getQFrame().setUndecorated(false);

        getQFrame().setTitle("DemoFrameworkRule2");
        setQCanvas(new QCanvas(800, 500));
        getQFrame().setResolution(getQCanvas());

        getQFrame().setLocationRelativeTo(null);
        getQFrame().setVisible(true);

        //図形や線のアンチエイリアシングの有効化
        getQCanvas().getGraphics2D().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //文字描画のアンチエイリアシングの有効化
        getQCanvas().getGraphics2D().setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        getExecuteLoop().setRate(200);
        getFrameLoop().setRate(100);
    }

    public void loopActive() {
        timer++;

        inactive = true;

        if (timer > 200) {
            timer = 0;
        }

        if (actionKey[0][0].isPress()) {
            ballX -= 1;
        }
        if (actionKey[0][1].isPress()) {
            ballX += 1;
        }
        if (actionKey[0][2].isPress()) {
            ballY -= 1;
        }
        if (actionKey[0][3].isPress()) {
            ballY += 1;
        }
        if (actionKey[0][4].isPress()) {
            changeRule = true;
        }

        if (actionMouse.isPress()) {
            actionMouse.release();
            clickPoint.add(new Point(mouseCursor));
            clickPointRange.add(0);
        }

        if (0 < clickPoint.size()) {
            for (int i = 0; i < clickPoint.size(); i++) {
                if (clickPointRange.get(i) < CLICK_MAX_RANGE) {
                    clickPointRange.set(i, clickPointRange.get(i) + 1);
                } else {
                    clickPoint.remove(i);
                    clickPointRange.remove(i);
                    i--;
                }
            }
        }

        if (RATE_CHECK_LENGTH <= rateCheckerCountByExecute) {
            rateCheckerCountByExecute = 0;
        }
        rateCheckerByExecute[rateCheckerCountByExecute] = (int) getExecuteLoop().getCurrentRate();
        rateCheckerCountByExecute++;

        if (RATE_CHECK_LENGTH <= rateCheckerCountByFrame) {
            rateCheckerCountByFrame = 0;
        }
        rateCheckerByFrame[rateCheckerCountByFrame] = (int) getFrameLoop().getCurrentRate();
        rateCheckerCountByFrame++;
    }

    public void loopInactive() {
        for (ActionKey[] keys : actionKey) {
            for (ActionKey key : keys) {
                key.release();
            }
        }
    }

    public void paintActive(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getQCanvas().getWidth(), getQCanvas().getHeight());

        g.setColor(Color.white);
        g.fillOval(ballX - timer / 2, ballY - timer / 2, timer, timer);

        try {
            for (int i = 0; i < clickPoint.size(); i++) {
                int range = clickPointRange.get(i);
                int x = (int) clickPoint.get(i).getX() - range / 2;
                int y = (int) clickPoint.get(i).getY() - range / 2;
                g.drawOval(x, y, range, range);
            }
        } catch (IndexOutOfBoundsException ignored) {
        }

        try {
            FontMetrics fm = g.getFontMetrics();
            String text = "予定eps:" + (int) getExecuteLoop().getRate() + " 予定fps:" + (int) getFrameLoop().getRate() + " | eps:" + Math.floor(Arrays.stream(rateCheckerByExecute).average().getAsDouble()) + " fps:" + Math.floor(Arrays.stream(rateCheckerByFrame).average().getAsDouble());
            g.drawString(text, 0, fm.getMaxAscent());
        } catch (NoSuchElementException ignored) {
        }

        g.drawString("アンチエイリアシング有効化", 0, getQCanvas().getHeight());
    }

    public void paintInactive(Graphics g) {
        if (inactive) {
            inactive = false;
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, getQCanvas().getWidth(), getQCanvas().getHeight());

            FontMetrics fm = g.getFontMetrics();
            String text = "FRAME MODE PAUSE";
            Rectangle rectText = fm.getStringBounds(text, g).getBounds();
            int startX = getQCanvas().getWidth() / 2 - rectText.width / 2;
            int startY = getQCanvas().getHeight() / 2 - rectText.height / 2 + fm.getMaxAscent();
            g.setColor(Color.white);
            g.drawString(text, startX, startY);
        }
    }

    public void overTimeExecute(long overTime) {
        System.out.println("EL誤差:" + overTime + "ns" + "  @" + LocalDateTime.now());
    }

    public void overTimeFrame(long overTime) {
        System.out.println("FL誤差:" + overTime + "ns" + "  @" + LocalDateTime.now());
    }

    public boolean isChangeRule() {
        return changeRule;
    }

    public Rule getNewRule() {
        return new DemoFrameworkRule1();
    }

    public void addListener(QFrame qf) {
        qf.addKeyListener(this);
        qf.getQPanel().addMouseListener(this);
        qf.getQPanel().addMouseMotionListener(this);
    }

    public void removeListener(QFrame qf) {
        qf.removeKeyListener(this);
        qf.getQPanel().removeMouseListener(this);
        qf.getQPanel().removeMouseMotionListener(this);
    }
}