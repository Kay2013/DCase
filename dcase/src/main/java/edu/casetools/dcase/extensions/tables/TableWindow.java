
/*
 * Copyright 2015 @author Unai Alegre 
 * 
 * This file is part of DCASE (Design for Context-Aware Systems Engineering), a module 
 * of Modelio that aids the design of a Context-Aware System (C-AS). 
 * 
 * DCASE is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * DCASE is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DCASE. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package edu.casetools.dcase.extensions.tables;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;

import edu.casetools.dcase.utils.ResourcesManager;

// TODO: Auto-generated Javadoc
/**
 * The Class TableWindow.
 */
public class TableWindow extends JFrame {

    private static final long serialVersionUID = -85498783039274469L;

    /**
     * Instantiates a new table window.
     */
    public TableWindow() {
	super();
    }

    /**
     * Inits the icon.
     *
     * @param iconName
     *            the icon name
     */
    protected void initIcon(String iconName) {
	ImageIcon icon = ResourcesManager.getInstance().createImageIcon(iconName);
	if (icon != null)
	    this.setIconImage(icon.getImage());
    }

    /**
     * Window settings.
     */
    protected void windowSettings() {
	this.setSize(300, 300);
	this.setLocationRelativeTo(null);
	this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	this.pack();
	this.setVisible(true);

    }

}
