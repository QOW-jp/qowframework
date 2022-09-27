package qow.demo;

import qow.framework.screen.QCanvas;
import qow.framework.screen.QFrame;
import qow.framework.system.MainSystem;
import qow.framework.system.rule.Rule;
import qow.framework.util.ActionKey;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DemoFramework {
    public static void main(String[] args) {
        System.out.println("qowframework-1.4.2.jar DemoModel");
        try {
            MainSystem ms = new MainSystem();
            DemoFrameworkGameRule1 gr = new DemoFrameworkGameRule1();
            ms.setRule(gr);

            gr.getQFrame().setVisible(true);
            ms.start(true);
            System.out.println("start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class DemoFrameworkGameRule1 extends Rule {
    final int DRAG_MAX_RANGE = 100;
    int timer;
    ActionKey[][] actionKey;
    int ballX, ballY;
    boolean dragged;
    int dragX, dragY;
    List<Point> dragPoint;
    List<Integer> dragPointRange;
    boolean inactive = true;
    boolean changeRule;

    DemoFrameworkGameRule1() {
        QFrame qf = new QFrame("DemoFrameworkGameRule1");
        setQFrame(qf);

        setQCanvas(new QCanvas(800, 500));

        int[][] keyCode = {{65, 68, 87, 83, 10}};
        actionKey = new ActionKey[keyCode.length][keyCode[0].length];
        for (int i = 0; i < actionKey.length; i++) {
            for (int j = 0; j < actionKey[i].length; j++) {
                actionKey[i][j] = new ActionKey(keyCode[i][j]);
            }
        }

        dragPoint = new ArrayList<>();
        dragPointRange = new ArrayList<>();
    }

    public void init() {
        getExecuteLoop().setRate(100);
        getFrameLoop().setRate(50);

        getQFrame().setVisible(true);
    }

    public void pressKey(KeyEvent e) {
        int code = e.getKeyCode();
        for (ActionKey[] keys : actionKey) {
            for (ActionKey key : keys) {
                if (code == key.getKeyCode()) {
                    key.press();
                }
            }
        }
    }

    public void releaseKey(KeyEvent e) {
        int code = e.getKeyCode();
        for (ActionKey[] keys : actionKey) {
            for (ActionKey key : keys) {
                if (code == key.getKeyCode()) {
                    key.release();
                }
            }
        }
    }

    public void dragMouse(MouseEvent e) {
        dragged = true;
        dragX = e.getX();
        dragY = e.getY();
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

        if (dragged) {
            dragged = false;
            dragPoint.add(new Point(dragX, dragY));
            dragPointRange.add(0);
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

        for (int i = 0; i < dragPoint.size(); i++) {
            int range = dragPointRange.get(i);
            int x = (int) dragPoint.get(i).getX() - range / 2;
            int y = (int) dragPoint.get(i).getY() - range / 2;
            g.drawOval(x, y, range, range);
        }

        FontMetrics fm = g.getFontMetrics();
        String text = "予定eps:" + (int) getExecuteLoop().getRate() + " 予定fps:" + (int) getFrameLoop().getRate() + " | eps:" + (int) getExecuteLoop().getCurrentRate() + " fps:" + (int) getFrameLoop().getCurrentRate();
        g.drawString(text, 0, fm.getMaxAscent());

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

    public void overTimeExecute(double overTime) {
        System.out.println(-overTime / 1000000 + "ms超過e");
    }

    public void overTimeFrame(double overTime) {
        System.out.println(-overTime / 1000000 + "ms超過f");
    }

    public boolean isChangeRule() {
        return changeRule;
    }

    public Rule getNewRule() {
        return new DemoFrameworkGameRule2();
    }

    public void addListener(QFrame qf) {
        qf.addKeyListener(this);
        qf.getQPanel().addMouseMotionListener(this);
    }

    public void removeListener(QFrame qf) {
        qf.removeKeyListener(this);
        qf.getQPanel().removeMouseMotionListener(this);
    }
}

class DemoFrameworkGameRule2 extends Rule {
    final int CLICK_MAX_RANGE = 1200;
    int timer;
    int ballX, ballY;
    ActionKey[][] actionKey;
    List<Point> clickPoint;
    List<Integer> clickPointRange;
    int clickX, clickY;
    boolean clicked;
    boolean inactive = true;
    boolean changeRule;

    DemoFrameworkGameRule2() {
        QCanvas canvas = new QCanvas(800, 500);
        //図形や線のアンチエイリアシングの有効化
        canvas.getGraphics2D().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //文字描画のアンチエイリアシングの有効化
        canvas.getGraphics2D().setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        setQCanvas(canvas);

        int[][] keyCode = {{65, 68, 87, 83, 10}};
        actionKey = new ActionKey[keyCode.length][keyCode[0].length];
        for (int i = 0; i < actionKey.length; i++) {
            for (int j = 0; j < actionKey[i].length; j++) {
                actionKey[i][j] = new ActionKey(keyCode[i][j]);
            }
        }

        clickPoint = new ArrayList<>();
        clickPointRange = new ArrayList<>();

    }

    public void init() {
        getQFrame().setTitle("DemoFrameworkGameRule2");

        getExecuteLoop().setRate(200);
        getFrameLoop().setRate(100);
    }

    public void pressKey(KeyEvent e) {
        int code = e.getKeyCode();
        for (ActionKey[] keys : actionKey) {
            for (ActionKey key : keys) {
                if (code == key.getKeyCode()) {
                    key.press();
                }
            }
        }
    }

    public void releaseKey(KeyEvent e) {
        int code = e.getKeyCode();
        for (ActionKey[] keys : actionKey) {
            for (ActionKey key : keys) {
                if (code == key.getKeyCode()) {
                    key.release();
                }
            }
        }
    }

    public void clickMouse(MouseEvent e) {
        clickX = e.getX();
        clickY = e.getY();
        clicked = true;
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

        if (clicked) {
            clicked = false;
            clickPoint.add(new Point(clickX, clickY));
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

        for (int i = 0; i < clickPoint.size(); i++) {
            int range = clickPointRange.get(i);
            int x = (int) clickPoint.get(i).getX() - range / 2;
            int y = (int) clickPoint.get(i).getY() - range / 2;
            g.drawOval(x, y, range, range);
        }

        FontMetrics fm = g.getFontMetrics();
        String text = "予定eps:" + (int) getExecuteLoop().getRate() + " 予定fps:" + (int) getFrameLoop().getRate() + " | eps:" + (int) getExecuteLoop().getCurrentRate() + " fps:" + (int) getFrameLoop().getCurrentRate();
        g.drawString(text, 0, fm.getMaxAscent());

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

    public void overTimeExecute(double overTime) {
        System.out.println(-overTime / 1000000 + "ms超過e");
    }

    public void overTimeFrame(double overTime) {
        System.out.println(-overTime / 1000000 + "ms超過f");
    }

    public boolean isChangeRule() {
        return changeRule;
    }

    public Rule getNewRule() {
        return new DemoFrameworkGameRule1();
    }

    public void addListener(QFrame qf) {
        qf.addKeyListener(this);
        qf.getQPanel().addMouseListener(this);
    }

    public void removeListener(QFrame qf) {
        qf.removeKeyListener(this);
        qf.getQPanel().removeMouseListener(this);
    }
}