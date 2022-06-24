package middlewareManager.middlewares;

import config.Config;
import elementManager.elements.BigBall;
import frameManager.FrameManager;

public class DrawBigBall extends Middleware {
    FrameManager frameManager = Config.getFrameManager();
    BigBall bigBall;
    String panelId = "gamePanel";
    public DrawBigBall(int x, int y){
        super("drawBigBall");
        System.out.println(Config.getFrameManager());
        bigBall = new BigBall(frameManager.getAPanel(panelId));
        bigBall.setPos(x, y);
    }

    public DrawBigBall(int x, int y , String panelId){
        super("drawBigBall");
        System.out.println(Config.getFrameManager());
        this.panelId = panelId;
        bigBall = new BigBall(frameManager.getAPanel(panelId));
        bigBall.setPos(x, y);
    }

    public DrawBigBall(String x, String y){
        super("drawBigBall");
        bigBall = new BigBall(frameManager.getAPanel(panelId));
        bigBall.setPos(Integer.parseInt(x), Integer.parseInt(y));
    }

    public DrawBigBall(String x, String y, String panelId){
        super("drawBigBall");
        bigBall = new BigBall(frameManager.getAPanel(panelId));
        this.panelId = panelId;
        bigBall.setPos(Integer.parseInt(x), Integer.parseInt(y));
    }

    public DrawBigBall(BigBall bigBall){
        super("drawBigBall");
        this.bigBall = bigBall;
    }

    @Override
    public void init(){
        Config.getElementManager().addElement("bigBall", bigBall);
        this.remove();
    }
}