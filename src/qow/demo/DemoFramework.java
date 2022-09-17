package qow.demo;

import qow.framework.logic.screen.graphics.Canvas;
import qow.framework.logic.screen.window.MainFrame;
import qow.framework.logic.system.MainSystem;
import qow.framework.logic.system.rule.Rule;
import qow.framework.logic.util.ActionKey;
import qow.framework.setting.KeyConfigWriter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class DemoFramework {
    public static void main(String[] args) {
        System.out.println("qowframework-1.4.2.jar TestModel");
        try {
            new KeyConfigWriter("data/config/key.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            MainSystem ms = new MainSystem();
            DemoFrameworkGameRule1 gr = new DemoFrameworkGameRule1();
            ms.setRule(gr);

            gr.getMainFrame().setVisible(true);
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
        MainFrame mf = new MainFrame("DemoFrameworkGameRule1");
        //mf.setUndecorated(true);
        setMainFrame(mf);

        setCanvas(new Canvas(530, 300));

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

        getMainFrame().setVisible(true);
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
        g.fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());

        g.setColor(Color.white);
        g.fillOval(ballX - timer / 2, ballY - timer / 2, timer, timer);

        for (int i = 0; i < dragPoint.size(); i++) {
            int range = dragPointRange.get(i);
            int x = (int) dragPoint.get(i).getX() - range / 2;
            int y = (int) dragPoint.get(i).getY() - range / 2;
            g.drawOval(x, y, range, range);
        }

        g.drawString("アンチエイリアシング無効化", 0, getCanvas().getHeight());
    }

    public void paintInactive(Graphics g) {
        if (inactive) {
            inactive = false;
            paintPause(g);
        }
    }

    public void paintPause(Graphics g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());

        FontMetrics fm = g.getFontMetrics();
        String text = "FRAME MODE PAUSE";
        Rectangle rectText = fm.getStringBounds(text, g).getBounds();
        int startX = getCanvas().getWidth() / 2 - rectText.width / 2;
        int startY = getCanvas().getHeight() / 2 - rectText.height / 2 + fm.getMaxAscent();
        g.setColor(Color.white);
        g.drawString(text, startX, startY);
    }

    public void overTimeExecute(double overTime) {
        System.out.println(-overTime / 1000000 + "ms超過s");
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

    public void addListener(MainFrame mf) {
        mf.addKeyListener(this);
        mf.getMainPanel().addMouseMotionListener(this);
    }

    public void removeListener(MainFrame mf) {
        mf.removeKeyListener(this);
        mf.getMainPanel().removeMouseMotionListener(this);
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
        Canvas canvas = new Canvas(530, 300);
        //図形や線のアンチエイリアシングの有効化
        canvas.getGraphics2D().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //文字描画のアンチエイリアシングの有効化
        canvas.getGraphics2D().setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        setCanvas(canvas);

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
        getMainFrame().setTitle("DemoFrameworkGameRule2");

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
        g.fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());

        g.setColor(Color.white);
        g.fillOval(ballX - timer / 2, ballY - timer / 2, timer, timer);

        for (int i = 0; i < clickPoint.size(); i++) {
            int range = clickPointRange.get(i);
            int x = (int) clickPoint.get(i).getX() - range / 2;
            int y = (int) clickPoint.get(i).getY() - range / 2;
            g.drawOval(x, y, range, range);
        }

        g.drawString("アンチエイリアシング有効化", 0, getCanvas().getHeight());
    }

    public void paintInactive(Graphics g) {
        if (inactive) {
            inactive = false;
            paintPause(g);
        }
    }

    public void paintPause(Graphics g) {
        g.setColor(new Color(0, 0, 0, 100));
        g.fillRect(0, 0, getCanvas().getWidth(), getCanvas().getHeight());

        FontMetrics fm = g.getFontMetrics();
        String text = "FRAME MODE PAUSE";
        Rectangle rectText = fm.getStringBounds(text, g).getBounds();
        int startX = getCanvas().getWidth() / 2 - rectText.width / 2;
        int startY = getCanvas().getHeight() / 2 - rectText.height / 2 + fm.getMaxAscent();
        g.setColor(Color.white);
        g.drawString(text, startX, startY);
    }

    public void overTimeExecute(double overTime) {
        System.out.println(-overTime / 1000000 + "ms超過s");
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

    public void addListener(MainFrame mf) {
        mf.addKeyListener(this);
        mf.getMainPanel().addMouseListener(this);
    }

    public void removeListener(MainFrame mf) {
        mf.removeKeyListener(this);
        mf.getMainPanel().removeMouseListener(this);
    }
}