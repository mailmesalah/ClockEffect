package clockeffect;

/*******************************************************

 ********************************************************/
//	PACKAGES NEEDED
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import java.awt.event.*;
import java.awt.*;
import interactivex.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

//INTERACTIVE FRAME CLASS
public class ClockEffect extends EffectManager implements CameraListener {

    RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    //private CameraShapesEffect cse;
    BufferedImage backGround;
    BufferedImage center;
    BufferedImage hourHand;
    BufferedImage minuteHand;
    BufferedImage secondHand;
    double centerX = 0.0;
    double centerY = 0.0;
    double percent = 70 / 100.0;
    double secondX = 0.0;
    double secondY = 0.0;

    public ClockEffect() {
        try {
            this.setSize(700, 600);
            this.setVisible(true);
            setConfigurePanel(new JPanelInteractiveConfigure());
            //this.setContentPane(this);
            //jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            backGround = ImageIO.read(new File("Images/BackGround.png"));
            center = ImageIO.read(new File("Images/Center.png"));
            hourHand = ImageIO.read(new File("Images/HourHand.png"));
            minuteHand = ImageIO.read(new File("Images/MinuteHand.png"));
            secondHand = ImageIO.read(new File("Images/SecondHand.png"));
            this.addMouseMotionListener(new MouseMotionAdapter() {

                @Override
                public void mouseMoved(MouseEvent me) {
                    //JOptionPane.showMessageDialog(null, hourHand.getHeight()+""+hourHand.getWidth());
                }

                public void mouseDragged(MouseEvent me) {
                    //JOptionPane.showMessageDialog(null, me.getX() + " " + me.getY());
                }
            });

            initialiseClock();
            animate();
        } catch (IOException ex) {
            Logger.getLogger(ClockEffect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform Affine = g2d.getTransform();
        Point p;
        //g2d.setStroke(new BasicStroke(3));
        hints.add(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.setRenderingHints(hints);
        //rotate the image        
        //secondAffine.translate(secondX,secondY);        
        //Affine.rotate();        
        //draw the image using the AffineTransform 

        //g2d.drawImage(backGround, null, this);
        //g2d.drawImage(hourHand, null, this);
        //g2d.drawImage(minuteHand, null, this);
        //g2d.drawImage(secondHand, (int) x1, (int) y1, this);
        //g2d.drawImage(secondHand, secondAffine, this);  
        //g2d.drawImage(center, secondAffine, this);  
        //g2d.translate(secondX, secondX);

        p = getXYOfSecond();
        g2d.rotate(getSecondRadian(), p.getX(), p.getY());
        g2d.drawImage(secondHand, (int) p.getX(), (int) p.getY(), this);
        //JOptionPane.showMessageDialog(null, p.getX());        
        g2d.setTransform(Affine);



        g2d.setTransform(Affine);
        g2d.drawImage(center, (int) 331, (int) 285, this);
    }

    private void initialiseClock() {


        //centerX = ((frameWidth / 2) - (center.getWidth() / 2));
        //centerY = ((frameHeight / 2) - (center.getHeight() / 2));
        getXYOfSecond();

    }

    private double getSecondRadian() {
        Calendar c = Calendar.getInstance();
        long second = c.get(Calendar.SECOND);
        double ratio = 360 / 60;
        double degree = (second * ratio)+180;
        return Math.toRadians(degree);
    }

    private Point getXYOfSecond() {
        Calendar c = Calendar.getInstance();
        double longestWidthHeight = 0.0;
        double frameWidth = 0.0;
        double frameHeight = 0.0;
        double secondWidth = 0.0;
        double secondHeight = 0.0;        
        long second = c.get(Calendar.SECOND);
        double ratio = 360 / 60;
        double degree = (second * ratio) + 90;
        
        secondWidth = secondHand.getWidth();
        secondHeight = secondHand.getHeight();
        longestWidthHeight = ((secondHeight - (secondHeight * percent)) * 2);
        frameWidth = this.getWidth();
        frameHeight = this.getHeight();

        /*
         * Need To Update using current system time
         */
        ratio = (2 * Math.PI * (longestWidthHeight / 2)) / 60;
        
        double initialX = ((frameWidth / 2) - (secondWidth / 2));
        double initialY = ((frameHeight / 2) - (longestWidthHeight / 2));
        //secondX = ((frameWidth / 2) - (secondWidth / 2));        
        //secondY = ((frameHeight / 2) - (longestWidthHeight / 2));
        double x=((frameWidth / 2) - (secondWidth / 2));        
        double y=((frameHeight / 2));
        double rat=secondWidth/30.0;
        double xAdd=0;
        double yAdd=0;
        double dist=Math.sqrt((secondWidth*secondWidth)+((longestWidthHeight / 2)*(longestWidthHeight / 2)));
        double radAdd=1/(Math.tan((secondWidth/(longestWidthHeight / 2))));
        System.out.println(radAdd);
        /*if(second<=15){
            xAdd=-(second*rat);
            yAdd=(second*rat);
        }else if(second<=30){
            xAdd=-(second*rat);
            yAdd=(second*rat);
        }else if(second<=45){
            xAdd=-(second*rat);
            yAdd=(second*rat);
        }else if(second<=60){
            xAdd=-(second*rat);
            yAdd=(second*rat);
        }*/
        //x=x+((longestWidthHeight / 2)*Math.cos(Math.toRadians(degree)));
        //y=y+((longestWidthHeight / 2)*Math.sin(Math.toRadians(degree)));
        //secondX =x+((longestWidthHeight / 2)*Math.cos(Math.toRadians(degree))); 
        //secondY =y+((longestWidthHeight / 2)*Math.sin(Math.toRadians(degree)));  
        secondX =x+((dist)*Math.cos(Math.toRadians(degree-radAdd))); 
        secondY =y+((dist)*Math.sin(Math.toRadians(degree-radAdd)));  
        //secondX=//(secondWidth/Math.sqrt(1+(Math.tan(degree)*Math.tan(degree))))+x;
        //secondY=//(secondWidth/Math.sqrt(1+(1/(Math.tan(degree)*Math.tan(degree)))))+y;
        return new Point((int) secondX, (int) secondY);
    }

    private double getMinuteRadian() {
        return 0.0;
    }

    private Point getXYOfMinute() {
        Calendar c = Calendar.getInstance();
        long minute = c.get(Calendar.MINUTE);
        //JOptionPane.showMessageDialog(null, minute);
        return new Point();
    }

    public void animate() {

        class RunBack extends Thread {

            RunBack() {
                setDaemon(true);
                start();
            }

            public void run() {
                while (true) {


                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                    }
                    repaint();
                }
            }
        }

        new RunBack();

    }

    /****************************************************************************************/
    public void shapeOnCameraDetected(CameraEvent ce) {
        int x = ce.getX();
        int y = ce.getY();

        double width = this.getBounds().getWidth();
        double height = this.getBounds().getHeight();
        double ratioWidth = width / 320;
        double ratioHeight = height / 240;

    }

    /****************************************************************************************/
    public static void main(String[] arg) {

        ClockEffect iFrame = new ClockEffect();
        JFrame jf = new JFrame("Clock Frame");
        jf.setContentPane(iFrame);
        jf.setSize(700, 650);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //JOptionPane.showMessageDialog(null, jf.getWidth()+" "+jf.getHeight());
    }
}
