package qow.framework.logic.screen.graphics;

import qow.framework.logic.screen.window.MainPanel;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
*パネルに投影する画面
*
*@author QOW
*@version 1.0.0
*/
public class Canvas{
	private int width,height;
	private final BufferedImage img;
	private final Graphics gra;
	private MainPanel mp;
	
	/**
	*サイズを設定してインスタンス化する
	*
	*@param width 横のサイズ
	*@param height 縦のサイズ
	*/
	public Canvas(int width,int height){
		setWidth(width);
		setHeight(height);
		
		img = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_BGR);
		gra = img.getGraphics();
	}
	
	/**
	*再描写する
	*/
	public void draw(){
		mp.draw();
	}
	/**
	*画面に画像を描写する
	*@param g 画像を描写する画面
	*/
	public void draw(Graphics g){
		g.drawImage(img,0,0,null);
	}
	/**
	*描写された画像を返す
	*@return 描写された画像
	*/
	public Graphics getGraphicsImage(){
		return gra;
	}
	/**
	*MainPanelを設定する
	*@param mp 新しいMainPanel
	*/
	public void setPanel(MainPanel mp){
		this.mp = mp;
	}
	
	/**
	*一般的なアンチエイリアシングを有効かまたは無効化
	*図形、線と文字のアンチエイリアシング
	*@param b アンチエイリアシングをする場合はtrue
	*/
	public void setAntialiasing(boolean b){
		Graphics2D g2 = (Graphics2D)gra;
		if(b){
			//図形や線のアンチエイリアシングの有効化
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			//文字描画のアンチエイリアシングの有効化
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		}else{
			//図形や線のアンチエイリアシングの無効化
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
			//文字描画のアンチエイリアシングの無効化
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		}
	}
	/**
	*2つ以上のアンチエイリアシングの設定を追加または削除
	*@param b アンチエイリアシングのヒント
	*@param rk アンチエイリアシングのタイプ
	*/
	public void addAntialiasing(Object b,RenderingHints.Key... rk){
		Graphics2D g2 = (Graphics2D)gra;
		for(RenderingHints.Key rks : rk){
			g2.setRenderingHint(rks,b);
		}
	}
	
	/**
	*横のサイズを設定する
	*@param width 横のサイズ
	*/
	public void setWidth(int width){
		this.width = width;
	}
	/**
	*縦のサイズを設定する
	*@param height 縦のサイズ
	*/
	public void setHeight(int height){
		this.height = height;
	}
	/**
	*横のサイズを取得する
	*@return 横のサイズ
	*/
	public int getWidth(){
		return width;
	}
	/**
	*縦のサイズを取得する
	*@return 縦のサイズ
	*/
	public int getHeight(){
		return height;
	}
}