/** 
 * KNOLL GEWINNT powered by javax.swing
 * CLASS: KnollMenuBar
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert, Paul Schwarz
 * @version 0.1
 * (c) 2018
 */
package gamePackage;

import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class KnollMenuBar extends JMenuBar {

	
	JMenuItem menuItemFileExit;
	JMenuItem menueItemFileProfiles;
	JMenuItem menueItemFileAddProfiles;
	JMenuItem menueItemFileDelProfiles;
	JMenuItem menueItemFileStats;
	JMenuItem menueItemFileBash;
	JMenuItem menueItemHelpAbout;
	JMenuItem menueItemHelpHelp;
	
	
	public KnollMenuBar() {
		super();
		// ---MenuItems
		menuItemFileExit = new JMenuItem("Exit");
		menueItemFileProfiles = new JMenuItem("Profiles");
		menueItemFileAddProfiles = new JMenuItem("Add Profile");
		menueItemFileDelProfiles = new JMenuItem("Delete Profile");
		menueItemFileStats = new JMenuItem("Profiles & Stats");
		menueItemHelpAbout = new JMenuItem("About");
		menueItemHelpHelp = new JMenuItem("Help");

		// ---MenuBar with Menus and the corresponding MenuItems with actionMenu
		// Listener---
		
		JMenu menuFile = new JMenu("File");
		JMenu menuHelp = new JMenu("Help");
		this.add(menuFile);
		this.add(menuHelp);
		// ---Add SelectProfiles---
		menuFile.add(menueItemFileProfiles);
		
		// ---Add AddProfiles---
		menuFile.add(menueItemFileAddProfiles);
		
		// ---Add DelProfiles---
		menuFile.add(menueItemFileDelProfiles);
		
		// ---Add Stats---
		menuFile.add(menueItemFileStats);

		
		// ---Add Exit---
		menuFile.add(menuItemFileExit);
		
		// ---Add About---
		menuHelp.add(menueItemHelpAbout);

		// ---Add Help---
		menuHelp.add(menueItemHelpHelp);
	
	}
	
	/**
	 * @return the menuItemFileExit
	 */
	public JMenuItem getMenuItemFileExit() {
		return menuItemFileExit;
	}

	/**
	 * @return the menueItemFileProfiles
	 */
	public JMenuItem getMenueItemFileProfiles() {
		return menueItemFileProfiles;
	}

	/**
	 * @return the menueItemFileAddProfiles
	 */
	public JMenuItem getMenueItemFileAddProfiles() {
		return menueItemFileAddProfiles;
	}

	/**
	 * @return the menueItemFileDelProfiles
	 */
	public JMenuItem getMenueItemFileDelProfiles() {
		return menueItemFileDelProfiles;
	}

	/**
	 * @return the menueItemFileStats
	 */
	public JMenuItem getMenueItemFileStats() {
		return menueItemFileStats;
	}

	/**
	 * @return the menueItemFileBash
	 */
	public JMenuItem getMenueItemFileBash() {
		return menueItemFileBash;
	}

	/**
	 * @return the menueItemHelpAbout
	 */
	public JMenuItem getMenueItemHelpAbout() {
		return menueItemHelpAbout;
	}

	/**
	 * @return the menueItemHelpHelp
	 */
	public JMenuItem getMenueItemHelpHelp() {
		return menueItemHelpHelp;
	}

	public void addActionListener(ActionListener actionMenu) {
		menueItemHelpHelp.addActionListener(actionMenu);
		menueItemHelpAbout.addActionListener(actionMenu);
		menuItemFileExit.addActionListener(actionMenu);
		menueItemFileStats.addActionListener(actionMenu);
		menueItemFileDelProfiles.addActionListener(actionMenu);
		menueItemFileAddProfiles.addActionListener(actionMenu);
		menueItemFileProfiles.addActionListener(actionMenu);
	}

}
