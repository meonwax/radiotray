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

        Radio radio = new Radio();
        Tray tray = new Tray(radio);
//        radio.play("http://nsbradio.co.uk/listen128k.pls");
        // radio.play("http://metafiles.gl-systemhaus.de/hr/hrinfo_2.m3u");
        // radio.play("http://mp3.planetradio.de/planetradio/hqlivestream.aac");

        // 404
        // radio.play("http://streams.br-online.de/br-klassik_2.asx");

        // ASX with AAC
        // radio.play("http://www.ebm-radio.de/tunein/EBM_Radio-wma.asx");

        // MMS with WMA
        // radio.play("mms://apasf.apa.at/radio_kaernten");

        // radio.play("mms://apasf.apa.at/radio_kaernten");

//        try {
//            Thread.currentThread().join();
//        } catch (InterruptedException e) {
//        }

        // TrayIcon trayIcon = new TrayIcon();
    }
}
