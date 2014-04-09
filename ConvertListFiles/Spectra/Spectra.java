package ConvertListFiles.MPA3;
import java.util.ArrayList;
import ConvertListFiles.ADC.ADC;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class Spectra {
        ArrayList < ADC > mpa = new ArrayList<ADC>();
        double[] adclist;
        int energyMin=0;
        int nbEvents;
        int pasEnergy;
        float[]Energy;
        boolean rightIncluded=true;
        
    public Spectra(ADC adc) {
        this.adclist=adc;
        nbEvents=adc.getSpectra();
        int i = nbEvents.size();
        while (nbEvents[i]==0){
            nbEvents=nbEvents[0,i];
        }
    } 
    
    public Arraylist getSpectra(){
            return calibEnergy(energyMin,pasEnergy);    
    }

    /**
     *
     * @return
     */
    public float[] getEnergy(){
        Energy=Energy.add(nbEvents.size());
        Energy[0]=energyMin;
        for (int i=1;i<Energy.size();i++){
            Energy[i]=Energy[i-1]+p.Energy
        }
    return Energy;
    }
    
    public plotSpectra (string title){
        IJ.plot(nbEvent,getEnergy,getEnergy,title);       
    }

    public getIndiceEnergy(float energyToSearch){
        float[ ] Energy=getEnergy();
        if (rightIncluded){
            for (Energy.size();Energy.size()/2;i--){
                if (Energy[i]<end){
                    return Energy [i+1];
                }
            }
        }
        else {
            for (0;energy.size()/2;k++){
                if (energy[k]>start{
                    return energy[k-1];
                }
        }
             }
        }
    public genratePicture(float start,float end,int resX,int resY)
        int indMin=get IndiceEnergy(start,False);
        int indMax=get Indice.Energy(end,True);
        float [ ] energy= getEnergy();
        Energy=Energy[intMin,indMax];
        float[] addEvent=energy[(resX-1)*(resY-1)]
        for event in nbEvent{
            nbEvent[event[0]*event[1]]+=1;
        }
        valEvent==pixel
        image.show();



}
    
