package de.meonwax.radiotray.audio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;

import java.util.List;

public class MyAdapter extends MediaPlayerEventAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyAdapter.class);

    @Override
    public void opening(MediaPlayer mediaPlayer) {
        LOGGER.info("Media player opening with url {}", mediaPlayer.getMediaMeta().getTitle());
    }

    @Override
    public void playing(MediaPlayer mediaPlayer) {
        LOGGER.info("Media player playing");
    }

    @Override
    public void stopped(MediaPlayer mediaPlayer) {
        LOGGER.info("Media player stopped");
        mediaPlayer.release();
    }

//            @Override
//            public void buffering(MediaPlayer mediaPlayer, float newCache) {
//                LOGGER.info("Buffering: {}", newCache);
//            }

    @Override
    public void mediaSubItemAdded(MediaPlayer mediaPlayer, libvlc_media_t subItem) {
        List<String> items = mediaPlayer.subItems();
        LOGGER.info("mediaSubItemAdded: {}", items);
    }

    @Override
    public void finished(MediaPlayer mediaPlayer) {
        LOGGER.info("Media player finished");

        // LOGGER.info("subItemCount: {}", mediaPlayer.subItemCount());

        List<String> subItems = mediaPlayer.subItems();
        // LOGGER.info("subItems: {}", subItems);

        if (subItems != null && !subItems.isEmpty()) {

            String subItemMrl = subItems.get(0);
            LOGGER.info("Sub items found. Now playing {}", subItemMrl);

            // LOGGER.info("subItems: {}", subItems);
            // mediaPlayer.playMedia(subItemMrl);

            new Radio().play(subItemMrl);
        }
        mediaPlayer.release();
    }

    @Override
    public void error(MediaPlayer mediaPlayer) {
        LOGGER.error("Media player error");
        mediaPlayer.release();
    }
}