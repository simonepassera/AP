package ap.puzzle;

import java.io.Serializable;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 *
 * @author Simone Passera
 */
public class EightTile extends javax.swing.JButton implements Serializable, PropertyChangeListener {
    private int positionTile;
    private int labelTile;
    private final Color myRed = new Color(235, 77, 75);
    private final Color myGreen = new Color(46, 213, 115);
    private final Color myYellow = new Color(254, 211, 48);
    private final Color myGray = new Color(121,121,121);
    
    public EightTile() {}
    
    public EightTile(int position) {
        this.positionTile = position;
        
        addActionListener((ActionEvent event) -> {
            setHole();
        });
    }
    
    private void setHole() {        
        try {
            // Send "setHole" event to EightController
            fireVetoableChange("setHole", null, new Integer[]{positionTile, labelTile});
            labelTile = 9;
            updateAppearance();
        } catch (PropertyVetoException ex) {
            setBackground(myRed);
            new Timer(500, e -> updateAppearance()).start();
        }
    }

    private void updateAppearance() {
        setText(labelTile == 9 ? "" : String.valueOf(labelTile));
        
        if (labelTile == 9) {
            setBackground(myGray);
        } else if (positionTile == labelTile) {
            setBackground(myGreen);
        } else {
            setBackground(myYellow);
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        switch (pce.getPropertyName()) {
            case "restartBoard" -> {
                ArrayList<Integer> labels = (ArrayList<Integer>) pce.getNewValue();
                labelTile = labels.get(positionTile - 1);
                updateAppearance();
            }
            case "holeMoved" -> {
                if (labelTile == 9) {
                    labelTile = (int) pce.getNewValue();
                    updateAppearance();
                }
            }
            case "flip" -> {
                if (positionTile == 1) {
                    // Send "setLabel" event to tile in position 2
                    firePropertyChange("setLabel", null, new Integer[]{2, labelTile});
                }
            }
            case "setLabel" -> {
                Integer[] tileInfo = (Integer[]) pce.getNewValue();
                int position =tileInfo[0];
                int label =tileInfo[1];
                
                if (positionTile == position) {
                    if (positionTile != 1)
                        // Send "setLabel" event to tile in position 1
                        firePropertyChange("setLabel", null, new Integer[]{1, labelTile});
                    
                    labelTile = label;
                    updateAppearance();
                }
            }
            default -> {}
        }
    }
}
