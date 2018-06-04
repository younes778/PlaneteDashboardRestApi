package d2si.apps.planetemobelio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Application
 *
 * Head of the spring server responsible to launch the server
 *
 * @author younessennadj
 */

@SpringBootApplication
public class Application {

	/**
	 * The main methode
	 *
	 * @param args
	 *            the program arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
