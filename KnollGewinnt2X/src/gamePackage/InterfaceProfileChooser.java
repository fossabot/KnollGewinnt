package gamePackage;

import java.awt.event.ActionListener;
import java.util.HashMap;

public interface InterfaceProfileChooser {
	void open(HashMap<String, KnollPlayer> playersMap, ActionListener profileSelected);
	void dispose();
}
