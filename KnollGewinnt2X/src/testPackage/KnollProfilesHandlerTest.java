/**
 * 
 */
package testPackage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import handlerPackage.ProfilesHandler;
import handlerPackage.SavingsHandler;

/**
 * @author 2236367
 *
 */
class KnollProfilesHandlerTest {

	
	/**
	 * Test method for {@link handlerPackage.ProfilesHandler#createNewStats()}.
	 */
	@Test
	void testCreateNewStats() {
		ProfilesHandler statsHandler = new ProfilesHandler();
		statsHandler.createNewStats(false);
		
	}
	
	/**
	 * Test method for {@link handlerPackage.ProfilesHandler#writeFile(java.util.HashMap)}.
	 */
	@Test
	void testWriteFile() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link handlerPackage.ProfilesHandler#readFile()}.
	 */
	@Test
	void testReadFile() {
		fail("Not yet implemented");
	}



}
