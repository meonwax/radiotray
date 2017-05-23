package de.meonwax.radiotray;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import dorkbox.systemTray.SystemTray;

public class Tray {

    private SystemTray tray;

    private Radio radio;

    public Tray(Radio radio) {

        SystemTray.FORCE_GTK2 = true;

        tray = SystemTray.get();
        if (tray == null) {
            throw new RuntimeException("Unable to load SystemTray!");
        }

        this.radio = radio;

        tray.setImage(loadImage("/icon-white.png"));

        tray.setStatus("Stopped");

        JSeparator separator = new JSeparator();
        tray.getMenu().add(separator);

        createStationItems();

        tray.getMenu().add(separator);

        addItem("Stop", e -> {
            radio.stop();
            tray.setStatus("Stopped");
        });

        addItem("Quit", e -> {
            radio.stop();
            radio.quit();
            tray.shutdown();
        });
    }

    private Image loadImage(String filename) {
        try {
            return ImageIO.read(Tray.class.getResourceAsStream(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void createStationItems() {
        List<Map> stations = readStations();
        if (stations != null) {
            for (Map station : stations) {
                String title = (String) station.get("title");
                String mrl = (String) station.get("mrl");
                addItem(title, e -> {
                    radio.stop();
                    radio.play(mrl);
                    tray.setStatus("Playing " + title);
                });
            }
        }
    }

    private List<Map> readStations() {
        YamlReader reader = new YamlReader(new InputStreamReader(Tray.class.getResourceAsStream("/stations.yml")));
        try {
            Object object = reader.read();
            return (List<Map>) object;
        } catch (YamlException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void addItem(String title, Consumer<ActionEvent> action) {
        JMenuItem item = new JMenuItem(title);
        ImageIcon imageIcon = new ImageIcon(loadImage("/icon-white.png"));
        item.setIcon(imageIcon);
        // TODO: Dummy image
        item.addActionListener(e -> action.accept(e));
        tray.getMenu().add(item);
    }
}
