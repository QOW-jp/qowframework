package qow.framework.test;

import qow.framework.setting.KeyConfigReader;
import qow.framework.setting.KeyConfigWriter;
/*
import qow.framework.setting.KeyConfigOutFile;
import qow.framework.setting.KeyConfigChanger;
import qow.framework.setting.KeyConfigChangerPanel;
import qow.framework.setting.KeyConfigButton;
*/
import qow.framework.logic.system.MainSystem;
import qow.framework.logic.system.ExecuteLoop;
import qow.framework.logic.system.FrameLoop;
import qow.framework.logic.system.Loop;
import qow.framework.logic.system.rule.Rule;
import qow.framework.logic.screen.graphics.Canvas;
import qow.framework.logic.screen.window.MainFrame;
import qow.framework.logic.screen.window.MainPanel;
import qow.framework.logic.util.ActionKey;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ArrayList;

public class Test{
	public static void main(String[] args){
		System.out.println("Testテスト");
		try{
			new KeyConfigWriter("data\\config\\keyconfig.txt");
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			new Test();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public Test(){
		MainSystem ms = new MainSystem();
		TestGameRule gr = new TestGameRule();
		ms.setRule(gr);

		ms.getFrame().setVisible(true);
		ms.start(true);
		System.out.println("start");
	}
}
class TestGameRule extends Rule{
	int timer;
	ActionKey[][] actionKey;

	TestGameRule(){
		setCanvas(new Canvas(530,300));

		int[][] keyCode = {{65,68,87,83,10}};
		actionKey = new ActionKey[keyCode.length][keyCode[0].length];
		for(int i=0;i<actionKey.length;i++){
			for(int j=0;j<actionKey[i].length;j++){
				actionKey[i][j] = new ActionKey(keyCode[i][j]);
			}
		}

		setExecuteRate(100);
		setFrameRate(50);
	}
	@Override
	public void pressKey(KeyEvent e){
		int code = e.getKeyCode();
		for(ActionKey[] keys:actionKey){
			for(ActionKey key:keys){
				if(code == key.getKeyCode()){
					key.press();
				}
			}
		}
	}
	@Override
	public void releaseKey(KeyEvent e){
		int code = e.getKeyCode();
		for(ActionKey[] keys:actionKey){
			for(ActionKey key:keys){
				if(code == key.getKeyCode()){
					key.release();
				}
			}
		}
	}
	@Override
	public void moveMouse(MouseEvent e){

	}
	@Override
	public void dragMouse(MouseEvent e){

	}
	int x=0,y=0;
	public void loopActive(){
		timer++;

		inactive = true;

		if(timer > 200){
			timer = 0;
		}

		if(actionKey[0][0].isPress()){
			x -= 5;
		}
		if(actionKey[0][1].isPress()){
			x += 5;
		}
		if(actionKey[0][2].isPress()){
			y -= 5;
		}
		if(actionKey[0][3].isPress()){
			y += 5;
		}
		if(actionKey[0][4].isPress()){
			changeRule = true;
		}


	}
	boolean inactive = true;
	public void loopInactive(){
		for(ActionKey[] keys:actionKey){
			for(ActionKey key:keys){
				key.release();
			}
		}
	}
	public void paintActive(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0,0,getCanvas().getWidth(),getCanvas().getHeight());

		g.setColor(Color.white);
		g.fillOval(x-timer/2,y-timer/2,timer,timer);

		g.drawString("アンチエイリアシング無効",0,getCanvas().getHeight());
	}
	public void paintInactive(Graphics g){
		if(inactive){
			inactive = false;
			paintPause(g);
		}
	}
	public void paintPause(Graphics g){
		g.setColor(new Color(0,0,0,100));
		g.fillRect(0,0,getCanvas().getWidth(),getCanvas().getHeight());

		FontMetrics fm = g.getFontMetrics();
		String text = "FRAME MODE PAUSE";
		Rectangle rectText = fm.getStringBounds(text, g).getBounds();
		int startX = getCanvas().getWidth()/2-rectText.width/2;
		int startY = getCanvas().getHeight()/2-rectText.height/2+fm.getMaxAscent();
		g.setColor(Color.white);
		g.drawString(text ,startX ,startY);
	}
	public void overTimeExecute(long overTime){
		System.out.println(-overTime/1000000+"ms超過s");
	}
	public void overTimeFrame(long overTime){
		System.out.println(-overTime/1000000+"ms超過f");
	}
	boolean changeRule;
	public boolean isChangeRule(){
		return changeRule;
	}
	public Rule getNewRule(){
		return new TestGameRule2();
	}
	public void addListener(MainFrame mf){
		mf.addKeyListener(this);
	}
	public void removeListener(MainFrame mf){
		mf.removeKeyListener(this);
	}
}
class TestGameRule2 extends Rule{
	int timer;
	ActionKey[][] actionKey;
	private List<Point> clickPoint;
	private List<Integer> clickPointRange;
	private int clickX,clickY;
	private boolean clicked;
	private final int CLICK_MAX_RANGE = 500;

	TestGameRule2(){
		Canvas canvas = new Canvas(530,300);
		canvas.setAntialiasing(true);
		setCanvas(canvas);

		int[][] keyCode = {{65,68,87,83,10}};
		actionKey = new ActionKey[keyCode.length][keyCode[0].length];
		for(int i=0;i<actionKey.length;i++){
			for(int j=0;j<actionKey[i].length;j++){
				actionKey[i][j] = new ActionKey(keyCode[i][j]);
			}
		}

		clickPoint = new ArrayList<Point>();
		clickPointRange = new ArrayList<Integer>();

		setExecuteRate(200);
		setFrameRate(100);
	}
	@Override
	public void pressKey(KeyEvent e){
		int code = e.getKeyCode();
		for(ActionKey[] keys:actionKey){
			for(ActionKey key:keys){
				if(code == key.getKeyCode()){
					key.press();
				}
			}
		}
	}
	@Override
	public void releaseKey(KeyEvent e){
		int code = e.getKeyCode();
		for(ActionKey[] keys:actionKey){
			for(ActionKey key:keys){
				if(code == key.getKeyCode()){
					key.release();
				}
			}
		}
	}

	@Override
	public void clickMouse(MouseEvent e){
		clickX = e.getX();
		clickY = e.getY();
	}
	int x=0,y=0;
	public void loopActive(){
		timer++;

		inactive = true;

		if(timer > 200){
			timer = 0;
		}

		if(actionKey[0][0].isPress()){
			x -= 5;
		}
		if(actionKey[0][1].isPress()){
			x += 5;
		}
		if(actionKey[0][2].isPress()){
			y -= 5;
		}
		if(actionKey[0][3].isPress()){
			y += 5;
		}
		if(actionKey[0][4].isPress()){
			changeRule = true;
		}

		if(clicked){
			clicked = false;
			clickPoint.add(new Point(clickX,clickY));
			clickPointRange.add(0);
		}

		if(0 < clickPoint.size()){
			for(int i=0;i<clickPoint.size();i++){
				//int x = (int) clickPoint.get(i).getX();
				//int y = (int) clickPoint.get(i).getX();
				clickPoint.set(i,new Point(clickX,clickY));

				if(clickPointRange.get(i) < CLICK_MAX_RANGE) {
					clickPointRange.set(i, clickPointRange.get(i) + 1);
				}else{
					clickPoint.remove(i);
					clickPointRange.remove(i);
				}
			}
		}
	}
	boolean inactive = true;
	public void loopInactive(){
		for(ActionKey[] keys:actionKey){
			for(ActionKey key:keys){
				key.release();
			}
		}
	}
	public void paintActive(Graphics g){
		g.setColor(Color.black);
		g.fillRect(0,0,getCanvas().getWidth(),getCanvas().getHeight());

		g.setColor(Color.white);
		g.fillOval(x-timer/2,y-timer/2,timer,timer);

		for(int i=0;i<clickPoint.size();i++){
			int range = clickPointRange.get(i);
			int x = (int)clickPoint.get(i).getX()-range;
			int y = (int)clickPoint.get(i).getY()-range;
			g.drawOval(x,y,range,range);
		}

		g.drawString("アンチエイリアシング有効",0,getCanvas().getHeight());
	}
	public void paintInactive(Graphics g){
		if(inactive){
			inactive = false;
			paintPause(g);
		}
	}
	public void paintPause(Graphics g){
		g.setColor(new Color(0,0,0,100));
		g.fillRect(0,0,getCanvas().getWidth(),getCanvas().getHeight());

		FontMetrics fm = g.getFontMetrics();
		String text = "FRAME MODE PAUSE";
		Rectangle rectText = fm.getStringBounds(text, g).getBounds();
		int startX = getCanvas().getWidth()/2-rectText.width/2;
		int startY = getCanvas().getHeight()/2-rectText.height/2+fm.getMaxAscent();
		g.setColor(Color.white);
		g.drawString(text ,startX ,startY);
	}
	public void overTimeExecute(long overTime){
		System.out.println(-overTime/1000000+"ms超過s");
	}
	public void overTimeFrame(long overTime){
		System.out.println(-overTime/1000000+"ms超過f");
	}
	boolean changeRule;
	public boolean isChangeRule(){
		return changeRule;
	}
	public Rule getNewRule(){
		return new TestGameRule();
	}
	public void addListener(MainFrame mf){
		mf.addKeyListener(this);
	}
	public void removeListener(MainFrame mf){
		mf.removeKeyListener(this);
	}
}