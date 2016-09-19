package de.bahnhoefe.deutschlands.bahnhofsfotos.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import de.bahnhoefe.deutschlands.bahnhofsfotos.DetailsActivity;
import de.bahnhoefe.deutschlands.bahnhofsfotos.R;
import de.bahnhoefe.deutschlands.bahnhofsfotos.model.Bahnhof;

public class NearbyBahnhofWithoutPhotoNotificationManager extends NearbyBahnhofNotificationManager {

    private static final long[] VIBRATION_PATTERN = new long[]{300, 100, 300, 100, 300};
    public static final int LED_COLOR = 0x0000ffff;

    public NearbyBahnhofWithoutPhotoNotificationManager(Context context, Bahnhof bahnhof, double distance) {
        super(context, bahnhof, distance);
        Log.d(TAG, "Creating " + getClass().getSimpleName());
    }

    // helpers that create notification elements that are common to "with foto" and "without foto"
    private PendingIntent getFotoPendingIntent() {
        // Build an intent for an action to take a picture
        // actually this launches DetailsActivity with a specific Extra that causes it to launch
        // Photo immediately.
        Intent detailFotoIntent = getDetailIntent();
        detailFotoIntent.putExtra(DetailsActivity.EXTRA_TAKE_FOTO, true);
        return PendingIntent.getActivity(context, 0, detailFotoIntent, 0);
    }


    /**
     * Build a notification for a station without photo. Call onNotificationReady if done.
     *
     */
    @Override
    public void notifyUser() {
        NotificationCompat.Builder notificationBuilder = getBasicNotificationBuilder();

        PendingIntent fotoPendingIntent = getFotoPendingIntent();

        notificationBuilder
                .addAction(R.drawable.ic_photo_camera_white_48px, "Foto", fotoPendingIntent)
                .setVibrate(VIBRATION_PATTERN)
                .setColor(LED_COLOR)
        ;

        onNotificationReady(notificationBuilder.build());
    }

}