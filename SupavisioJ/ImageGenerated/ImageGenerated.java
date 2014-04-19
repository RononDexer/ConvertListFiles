package SupavisioJ.ImageGenerated;

import SupavisioJ.ConvertListFiles.ADC.ADC;
import SupavisioJ.Spectra.Spectra;
import SupavisioJ.CustomWindowImage.CustomWindowImage;
import ij.*;
import ij.gui.ImageCanvas;
import ij.process.ImageProcessor;
import ij.process.FloatProcessor;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator 
 */
public class ImageGenerated {
  Spectra sourceSpectra;
  double[] sourcePixels;
  FloatProcessor imageProc;
  float startSpectra;
  float endSpectra;//value of energy between two channels
  String title;
  CustomWindowImage imgWindow;
  
  
  public ImageGenerated(Spectra spectra,double[] valNbEventPerXY,float start, float end,int resX,int resY) {
    sourceSpectra=spectra;
    sourcePixels=valNbEventPerXY;
    for(int i=0;i<valNbEventPerXY.length;i++){
      IJ.log(String.valueOf(valNbEventPerXY[i])+" pour i="+String.valueOf(i));
    }
    imageProc = new FloatProcessor(resY+1,resX+1,valNbEventPerXY);
    startSpectra=start;
    endSpectra=end;
  } 
  
  public void show(String title){
    this.title=title;
    show();
  }
  
  public void show(){
    ImagePlus ipOfImageGen = new ImagePlus(title,imageProc);
    imgWindow = new CustomWindowImage(ipOfImageGen);
    ImageCanvas icOfImageGen = imgWindow.getCanvas();
    icOfImageGen.requestFocus();
  }
    
  public ImageProcessor getRoi(){
    ImageProcessor impMaskRoi=imageProc.getMask();
    return impMaskRoi;
  }
  

  public Spectra generateSpectraFromRoi(){
    ImageProcessor impMaskRoi=getRoi();
    ADC adcToCalcFromRoi = new ADC();
    ADC sourceAdc = sourceSpectra.getADC();
    for (int nbEvt=0; nbEvt<sourceAdc.getNEvents(); nbEvt++){
      int[] currentEvt= sourceAdc.getEvent(nbEvt);
      int xPix = currentEvt[0];
      int yPix = currentEvt[1];
      int channelEnerPix = currentEvt[2];
      int channelMin=sourceSpectra.getIndiceEnergy(startSpectra, false);
      int channelMax=sourceSpectra.getIndiceEnergy(endSpectra, true);
      if (impMaskRoi.get(xPix,yPix)>0 && channelEnerPix>channelMin && channelEnerPix<channelMax){
          adcToCalcFromRoi.addEvent(currentEvt);
      }           
    }   
    Spectra spectreNewCalc= new Spectra(adcToCalcFromRoi);
    spectreNewCalc.plotSpectra("essai","essai2");
    return spectreNewCalc;
  }
  
}