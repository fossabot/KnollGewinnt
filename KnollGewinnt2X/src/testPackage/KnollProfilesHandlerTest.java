/**
 * 
 */
package testPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import handlerPackage.KnollProfilesHandler;
import handlerPackage.KnollSavingsHandler;

/**
 * @author 2236367
 *
 */
class KnollProfilesHandlerTest {

	
	/**
	 * Test method for {@link handlerPackage.KnollProfilesHandler#createNewStats()}.
	 */
	@Test
	void testCreateNewStats() {
		KnollProfilesHandler statsHandler = new KnollProfilesHandler();
		statsHandler.createNewStats(false);
		
	}
	
	/**
	 * Test method for {@link handlerPackage.KnollProfilesHandler#writeFile(java.util.HashMap)}.
	 */
	@Test
	void testWriteFile() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link handlerPackage.KnollProfilesHandler#readFile()}.
	 */
	@Test
	void testReadFile() {
		fail("Not yet implemented");
	}



}
