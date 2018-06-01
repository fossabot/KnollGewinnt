package underConstruction;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class KLogger {
	FileWriter logfileWriter;
	BufferedWriter bufferedLogWriter;

	public KLogger() throws IOException {
		logfileWriter = new FileWriter("logfile.txt", true);
		bufferedLogWriter = new BufferedWriter(logfileWriter);
		bufferedLogWriter.write("<logfile>" + System.currentTimeMillis()+"\n");
		bufferedLogWriter.write("/** \r\n" + " * KNOLL GEWINNT powered by javax.swing\r\n"
				+ " * CLASS: KnollGewinntMain\r\n" + " * @since 29.05.2018\r\n"
				+ " * @author Caspar Goldmann, Elias Klewar, Moritz Cabral, Timo Büchert\r\n" + " * @version 0.1\r\n"
				+ " * (c) 2018\r\n" + " */");
		bufferedLogWriter.close();
	}

	public void logPass(String step) throws IOException {
		bufferedLogWriter = new BufferedWriter(logfileWriter);
		bufferedLogWriter.write("\n" + step);
	}
}
