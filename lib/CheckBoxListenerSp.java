package lib;
import ij.IJ;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;

public class CheckBoxListenerSp implements ItemListener{
     XYPlotSp sourceXYPlotSp;
     static ArrayList<JCheckBox> checkBoxList = new ArrayList<JCheckBox>();
     static ArrayList<int[]> colorList = new ArrayList<int[]>(); 
     static ArrayList<Float> lineDrewPositions = new ArrayList<Float>();
     static ArrayList<ValueMarker> lineDrewMarkers = new ArrayList<ValueMarker>();

   public CheckBoxListenerSp(XYPlotSp sourceXYPlotSp) {
      this.sourceXYPlotSp= sourceXYPlotSp;    
    }
  
   public boolean isNotTaken(int r, int g,int b, int numberOfCall){
        //check if the color is a good one
        if (  g+b>(70*r/100) ) { //to avoid a red color
            if(colorList.size()==0)
                return true;
            //check if the color is not already taken (or close of)
            //choose a low treshold if numberOfCall is high
            int treshold;
            if(numberOfCall>40){
                treshold=1;//treshold=0 not possible with this code
            }
            else if(numberOfCall>25){
                treshold=5;
            }
            else if(numberOfCall>10){
                treshold=10;
            }
            else {
                treshold=20;
            }
            for(int i=0;i<colorList.size();i++){
                int[] colorRGB = colorList.get(i);
                int r2=colorRGB[0], g2=colorRGB[1], b2=colorRGB[2];
                if ( (r>r2+treshold || r<r2-treshold) && (g>g2+treshold || g<g2-treshold) ){
                  return true;
                }
                if ( (g>g2+treshold || g<g2-treshold) && (b>b2+treshold || b<b2-treshold) ){
                  return true;
                }
            }
        }
        return false;
   } 
   
   public void drawVerticalLine(float position, JCheckBox checkBoxCurrent){
        // position is the value on the axis
        ValueMarker marker = new ValueMarker(position);
        Color colorToDraw;
        int r=0,g=0,b=0;
        if(!checkBoxList.contains(checkBoxCurrent)){
            int numberOfCall=0;
            while(!isNotTaken(r,g,b,numberOfCall)){
                r=randInt(30,200);
                g=randInt(50,255);
                b=randInt(50,255);
                numberOfCall++;
            }
        }
        else {
            int index=checkBoxList.indexOf(checkBoxCurrent);
            int[] colorRGB = colorList.get(index);
            r = colorRGB[0];
            g = colorRGB[1];
            b = colorRGB[2];
        }
        colorToDraw = new Color(r,g,b);
        marker.setPaint(colorToDraw);
        marker.setOutlinePaint(colorToDraw);
        // change line style (dashboard)
        BasicStroke dashedStroke = new BasicStroke(
                   1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                   1.0f, new float[] {6.0f, 6.0f}, 0.0f);

        marker.setStroke(dashedStroke);
        JFreeChart chart=sourceXYPlotSp.getChart();
        XYPlot XYPlotOfChart = (XYPlot) chart.getPlot();
        //draw vertical line 
        XYPlotOfChart.addDomainMarker(marker);
        
        lineDrewPositions.add(position);
        lineDrewMarkers.add(marker);
        if (!checkBoxList.contains(checkBoxCurrent)){
            checkBoxList.add(checkBoxCurrent);
            int[] colorToAdd = new int[3];
            colorToAdd[0]=r;
            colorToAdd[1]=g;
            colorToAdd[2]=b;
            colorList.add(colorToAdd);
        }
    }
    
    public void removeVerticalLine(float position,JCheckBox checkBoxCurrent, int numberOfCall){
        JFreeChart chart=sourceXYPlotSp.getChart();
        XYPlot XYPlotOfChart = (XYPlot) chart.getPlot();
        //before remove : check if the vertical line is not the same for an other checkbox
        //in this case the position is also taken in another checkbox selected
        ArrayList<JCheckBox> checkBoxsSelected = sourceXYPlotSp.getCheckBoxSelected();
        boolean isPresent = false;
        int indexIsPresent=-1;
        if (checkBoxsSelected.size()>0){//so other box are selected
            Vector vectValues = sourceXYPlotSp.getValuesMinMaxNames(true,false);
            ArrayList<float[]> minMaxSpectra = (ArrayList<float[]>) vectValues.get(1);
            for(int i=0;i<minMaxSpectra.size();i++){
                boolean isEqual1 = Float.compare(position,minMaxSpectra.get(i)[0])==0;
                boolean isEqual2 = Float.compare(position,minMaxSpectra.get(i)[1])==0;
                if (isEqual1||isEqual2) {//so no suppression
                    //no suppression but color may not correspond
                    //--> suppression then readd
                    isPresent=true;
                    indexIsPresent=i;
                }           
            }
        }
        //remove vertical line
        int index = lineDrewPositions.indexOf(position);
        ValueMarker marker = lineDrewMarkers.get(index);
        XYPlotOfChart.removeDomainMarker(marker);
        lineDrewPositions.remove(position);
        lineDrewMarkers.remove(marker);
        //commented code below : if you want to NOT keep the same color after coching/decoching one checkbox
        //if (numberOfCall==2){
        //    int index2 = checkBoxList.indexOf(checkBoxCurrent);
        //    colorList.remove(index2);
        //    checkBoxList.remove(index2);
        //}
        if(isPresent){ //so need to found the right color/box and draw
            //find only checkboxselected
            JCheckBox checkBoxSimilarValue = checkBoxsSelected.get(indexIsPresent);
            drawVerticalLine(position, checkBoxSimilarValue);
        } 
    }
   
    public void itemStateChanged(ItemEvent e) {
        Vector vectButtons = sourceXYPlotSp.getVectButtonsSupp();
        for (int i=0;i<vectButtons.size();i++){
            JComponent[] tabJCompToCheck = (JComponent[]) vectButtons.get(i);
            JCheckBox checkBoxCurrent = (JCheckBox) tabJCompToCheck[0];
            if(e.getSource()==checkBoxCurrent){
                for (int j=0;j<2;j++){
                    JTextField textFieldMinMax = (JTextField) tabJCompToCheck[j+2];
                    if ( !(textFieldMinMax.getText().equals("Min") || textFieldMinMax.getText().equals("Max")) ) {
                        float minMax = -1;
                        try {
                            minMax = Float.valueOf(textFieldMinMax.getText());
                        }
                        catch(NumberFormatException e2){}
                        if (minMax>-1){
                            if(checkBoxCurrent.isSelected()) {
                                if(! lineDrewPositions.contains(minMax)){
                                    drawVerticalLine(minMax,checkBoxCurrent);
                                }
                            }
                            else {
                                if(lineDrewPositions.contains(minMax)) {
                                    removeVerticalLine(minMax,checkBoxCurrent,j+1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public static int randInt(int min, int max) {
        // Usually this can be a field rather than a method variable
        Random rand = new Random();
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;
        return randomNum;
    }
    
}