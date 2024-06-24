package ap.puzzle;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.Serializable;

/**
 *
 * @author Simone Passera
 */
public class EightController extends  javax.swing.JLabel implements Serializable, PropertyChangeListener, VetoableChangeListener {
    private int holePosition = 9;
    
    public EightController() {}

    @Override
    public void vetoableChange(PropertyChangeEvent pce) throws PropertyVetoException {
        if (pce.getPropertyName().equals("setHole")) {
            Integer[] tileInfo = (Integer[]) pce.getNewValue();
            int tilePosition = tileInfo[0];
            int oldLabel = tileInfo[1];

            if (isValidMove(tilePosition)) {
                setText("OK");
                firePropertyChange("holeMoved",  null, oldLabel);
                holePosition = tilePosition;
            } else {
                setText("KO");
                throw new PropertyVetoException("Invalid Move", pce);
            }
        } else if (pce.getPropertyName().equals("flipRequest")) {
            if (holePosition != 9)
                throw new PropertyVetoException("Invalid Flip", pce);
            else
                setText("FLIP");
        }
    }

    private boolean isValidMove(int tilePosition) {
        int rowHole = (holePosition - 1) / 3;
        int colHole = (holePosition - 1) % 3;
        int rowTile = (tilePosition - 1) / 3;
        int colTile = (tilePosition - 1) % 3;

        return (Math.abs(rowHole - rowTile) == 1 && colHole == colTile) || (Math.abs(colHole - colTile) == 1 && rowHole == rowTile);
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if (pce.getPropertyName().equals("restartBoard")) {
            holePosition = 9;
            setText("START");
        }
    }
}
