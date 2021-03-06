package SupavisioJ.FrameConfigSave;

import SupavisioJ.MainFrame.MainFrame;
import javax.swing.AbstractButton;

/**
 *FrameConfigSave is the frame launch if the user wants to change the saving options
 */
public class FrameConfigSave extends javax.swing.JFrame {
    
    private MainFrame supavisioJFrame;
    
    /**
     * Creates a new frame FrameConfigSave
     */
    public FrameConfigSave(MainFrame supavisioJFrame) {
        initComponents();
        this.supavisioJFrame = supavisioJFrame;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        saveImg = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        saveImg.setText(tr("sauvegarder images"));
        saveImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveImgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(saveImg)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 66, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(saveImg)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * This method is called if the corresponding checbox is selected and indicates to the MainFrame the corresponding setting.
     */
    private void saveImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveImgActionPerformed
        AbstractButton AB = (AbstractButton) evt.getSource();
        supavisioJFrame.setSaveImg(AB.getModel().isSelected());
    }//GEN-LAST:event_saveImgActionPerformed
    
    public String tr(String strToTranslate){
        return supavisioJFrame.tr(strToTranslate);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox saveImg;
    // End of variables declaration//GEN-END:variables
}
