/** 
 * KNOLL GEWINNT powered by javax.swing
 * INTERFACE: KnollFileHandler
 * @since 29.05.2018
 * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert, Paul Schwarz
 * @version 0.1
 * (c) 2018
 */
package gamePackage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.URL;
import java.net.URLDecoder;

public interface KnollFileHandler {
	public void writeFile(Object object, Object object2) throws Exception;
	public Object[] readFile(Object object) throws Exception;
}
