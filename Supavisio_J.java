

import SupavisioJ.FrameSJ.FrameSJ;
import ij.plugin.*;
import javax.swing.SwingUtilities;



public class Supavisio_J implements PlugIn {
   
    public void run(String arg) {
          
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
			FrameSJ frame_SJ = new FrameSJ();
			frame_SJ.setVisible(true);
			}
		});
	}
}