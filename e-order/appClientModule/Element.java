import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class Element {
	
	public static void createElement(JComponent component, JPanel panel, int xPosition, int yPosition , int gridWidth, boolean constraintsIsHORIZONTAL ) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = xPosition;
		c.gridy = yPosition;
		c.fill = GridBagConstraints.HORIZONTAL;			
		c.weighty=1;
		c.weightx=1;
		if (gridWidth != 0) {
			c.gridwidth = gridWidth;
		}	
	
		
		panel.add(component, c);
	}
	
	public static void createElement(JComponent component, JPanel panel, int xPosition, int yPosition, int[] insets, boolean constraintsIsHORIZONTAL ) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = xPosition;
		c.gridy = yPosition;
		c.insets = new Insets(insets[0], insets[1],insets[2],insets[3]);
		c.fill = GridBagConstraints.HORIZONTAL;
//		c.fill   = GridBagConstraints.NONE;  
		c.anchor = GridBagConstraints.NORTHWEST;
		panel.add(component, c);
	}
	
	public static void createElement(JComponent component, JPanel panel, int xPosition, int yPosition , int gridWidth, int gridHeight, boolean constraintsIsHORIZONTAL, String gridBagConstraints ) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = xPosition;
		c.gridy = yPosition;
		c.weighty=1;
		c.weightx=1;
		
		if (gridBagConstraints.equals("")) {
		
		} 
		else if (gridBagConstraints.equals("BOTH")) {
			c.fill = GridBagConstraints.BOTH;
		}
		else if (gridBagConstraints.equals("SOUTHEAST")) {
			c.fill = GridBagConstraints.SOUTHEAST;
		}
		
		else if (gridBagConstraints.equals("ABOVE_BASELINE")) {
			//c.fill = GridBagConstraints.NORTHWEST;
		}
		
		
		
		if (gridWidth != 0) 	c.gridwidth = gridWidth;	
		if (gridHeight != 0) 	c.gridheight = gridHeight;
		
		
		panel.add(component, c);
	}
}

	