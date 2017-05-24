package de.meonwax.radiotray;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import de.meonwax.radiotray.stations.Group;
import de.meonwax.radiotray.stations.Station;
import dorkbox.systemTray.Menu;
import dorkbox.systemTray.SystemTray;

public class Tray {

    private static final Logger LOGGER = LoggerFactory.getLogger(Tray.class);

    private SystemTray tray;
    private Player player;

    public Tray(Player player) {

        SystemTray.FORCE_GTK2 = true;

        tray = SystemTray.get();
        if (tray == null) {
            throw new RuntimeException("Unable to load SystemTray!");
        }

        this.player = player;

        tray.setImage(loadImage("/transmission-white.png"));

        tray.setStatus("Stopped");

        JSeparator separator = new JSeparator();
        tray.getMenu().add(separator);

        createMenuItems();

        tray.getMenu().add(separator);

        addItem(tray.getMenu(),
                "Stop",
                e -> {
                    player.stop();
                    tray.setStatus("Stopped");
                });

        addItem(tray.getMenu(),
                "Quit",
                e -> {
                    player.stop();
                    player.quit();
                    tray.shutdown();
                });
    }

    private Image loadImage(String filename) {
        try {
            return ImageIO.read(Tray.class.getResourceAsStream(filename));
        } catch (IOException e) {
            LOGGER.error("Error loading image {}", filename);
        }
        return null;
    }

    private void createMenuItems() {
        Group root = readConfig();
        if (root != null) {
            createGroupStations(tray.getMenu(), root);
        }
    }

    private void createGroupStations(Menu menu, Group group) {
        List<Station> stations = group.getStations();
        if (stations != null) {
            for (Station station : stations) {
                addItem(menu,
                        station.getTitle(),
                        e -> {
                            player.stop();
                            player.play(station.getMrl());
                            tray.setStatus("Playing " + station.getTitle());
                        });
            }
        }
        List<Group> subGroups = group.getGroups();
        if (subGroups != null) {
            for (Group subGroup : subGroups) {
                Menu subMenu = new Menu(subGroup.getTitle());
                menu.add(subMenu);
                createGroupStations(subMenu, subGroup);
            }
        }
    }

    private Group readConfig() {
        YamlReader reader = new YamlReader(new InputStreamReader(Tray.class.getResourceAsStream("/stations.yml")));
        reader.getConfig().setClassTag("group", Group.class);
        reader.getConfig().setClassTag("station", Station.class);
        try {
            Group root = reader.read(Group.class);
            if (root != null && "ROOT".equals(root.getTitle())) {
                return root;
            }
        } catch (YamlException e) {
            LOGGER.error("Error parsing station config: {}", e.getMessage());
        }
        return null;
    }

    private void addItem(Menu menu, String title, Consumer<ActionEvent> action) {
        JMenuItem item = new JMenuItem(title);
        ImageIcon imageIcon = new ImageIcon(loadImage("/transmission-white.png"));
        item.setIcon(imageIcon);
        // TODO: Dummy image
        item.addActionListener(action::accept);
        menu.add(item);
    }
}
