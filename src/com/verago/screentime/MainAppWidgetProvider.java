package com.verago.screentime;

import java.util.Iterator;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MainAppWidgetProvider extends AppWidgetProvider {
	public static String CHANGE_TIMEOUT = "com.verago.screentime.CHANGE_TIMEOUT";
	public static TimeValue times = new TimeValue();
	public static Iterator<TimeElement> timesIterator = null;

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		final int N = appWidgetIds.length;

		for (int i=0; i<N; i++) {
			int appWidgetId = appWidgetIds[i];
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_main);
			
			Intent intent = new Intent(CHANGE_TIMEOUT);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(
					context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
			
			// add click listner on the main widget image
			views.setOnClickPendingIntent(R.id.time_image, pendingIntent );

			intent.setAction(CHANGE_TIMEOUT);
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		if (intent.getAction().equals(CHANGE_TIMEOUT)) {
			
			// pick next available timeout
			TimeElement timeEntry = null;
			if ( (MainAppWidgetProvider.timesIterator != null) && (MainAppWidgetProvider.timesIterator.hasNext()) ) {
				timeEntry = MainAppWidgetProvider.timesIterator.next();
			}else{
				MainAppWidgetProvider.timesIterator = MainAppWidgetProvider.times.iterator();
				timeEntry = timesIterator.next();
			}
			
			// widget update
		    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appwidget_main);
	        views.setTextViewText(R.id.time_text, timeEntry.text);
	        views.setImageViewResource(R.id.time_image, timeEntry.res);
            ComponentName cn = new ComponentName(context, MainAppWidgetProvider.class);
            AppWidgetManager.getInstance(context).updateAppWidget(cn, views);
            
            // user notification
            String message = "Screen Timeout: " + timeEntry.text;
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            
            // change timeout
            android.provider.Settings.System.putInt(
            		context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, timeEntry.msec);
		}
				
	} 
} 
