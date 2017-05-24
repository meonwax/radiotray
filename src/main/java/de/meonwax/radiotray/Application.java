package de.meonwax.radiotray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Internet Radio Player in the system tray
 * <p>
 * Start with JVM option -DVLCJ_INITX=no
 * <p>
 * Third party projects used:
 * <p>
 * https://github.com/dorkbox/SystemTray
 * https://github.com/EsotericSoftware/yamlbeans
 * https://github.com/caprica/vlcj
 */
public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {

        LOGGER.info("*** Internet Radio Player ***");

        Player player = new Player();
        Tray tray = new Tray(player);
    }
}
