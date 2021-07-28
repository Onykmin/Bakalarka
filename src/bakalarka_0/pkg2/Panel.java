
package bakalarka_0.pkg2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.JPanel;


public class Panel extends JPanel{
    ArrayList<double[]> pointsD=new ArrayList<>(); //pole vygenerovaných bodů
    Knot k;
    //tento panel slouží k vykreslení určitého uzlu - při generování se nevyužívá, tato komponenta slouží pro pozdější vizuální ověřování určitého uzlu
    public Panel(){
        this.setSize(800, 800);
        this.setPreferredSize(new Dimension(800,800));
    }
    
    public void addKnot(Knot k){
        pointsD.clear();
        this.k=k;
        //generatePoints();
        this.repaint();
    }
    
//    public void generatePoints(){
//        double x=0;
//        double y=0;
//        boolean generuj=true;
//        double cas=0;
//        while(generuj){ //vždy předgeneruji 1000 bodů
//            x=(this.getWidth()/2)*k.x.getValue(cas);
//            y=(this.getHeight()/2)*k.y.getValue(cas);
//            pointsD.add(new double[]{x,y}); //přidání do pole/listu již vygenerovaných bodů
//            cas+=Math.PI/2000; //posun času
//            if(cas>=2*Math.PI){
//                generuj=false;
//                break;
//            }
//        }
//    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
        
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(
        RenderingHints.KEY_ANTIALIASING, 
        RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(
        RenderingHints.KEY_DITHERING, 
        RenderingHints.VALUE_DITHER_ENABLE);
        g2d.translate(400, 400);
        g2d.scale(1,-1);
        g2d.setStroke(new BasicStroke(1));
        g2d.setColor(Color.BLACK);
        if(this.k==null){return;}
        if(pointsD.size()>1){
            for(int i=0;i<pointsD.size()-1;i++){
                Shape s= new Line2D.Double(pointsD.get(i)[0], pointsD.get(i)[1], pointsD.get(i+1)[0], pointsD.get(i+1)[1]);
                g2d.draw(s);
                //g2d.drawLine(points.get(i)[0], points.get(i)[1], points.get(i+1)[0], points.get(i+1)[1]); //vykreslení úsečky 
            }
            g2d.setColor(Color.red);
            g2d.setStroke(new BasicStroke(5));
            //nahradit body
//            for(int i=0;i<k.cross.size();i++){
//                for(int j=1;j<k.cross.get(i).size();j++)
//                    g2d.draw(new Line2D.Double((this.getWidth()/2)*k.cross.get(i).get(j-1).x,(this.getHeight()/2)*k.cross.get(i).get(j-1).y,(this.getWidth()/2)*k.cross.get(i).get(j).x,(this.getHeight()/2)*k.cross.get(i).get(j).y));
//            }


//            for(int i=0;i<k.odd.size();i++){
//                g2d.draw(new Line2D.Double((this.getWidth()/2)*k.odd.get(i).x,(this.getHeight()/2)*k.odd.get(i).y,(this.getWidth()/2)*k.odd.get(i).x,(this.getHeight()/2)*k.even.get(i).y));
//            }


        }
    }
}
