package com.trevor.multiplewidgettest;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link WemoWidgetConfigureActivity WemoWidgetConfigureActivity}
 */
public class WemoWidget extends AppWidgetProvider {

    private static final String ACTION_UPDATE_CLICK =
            "com.trevor.multiplewidgettest.action.UPDATE_CLICK";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            WemoWidgetConfigureActivity.deleteTitlePref(context, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = WemoWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wemo_widget);
      //  views.setOnClickPendingIntent(R.id.widgetID,getPendingSelfIntent(context,ACTION_UPDATE_CLICK, appWidgetId));
        Intent intent = new Intent(context, WemoWidget.class);
        intent.setAction(ACTION_UPDATE_CLICK);
        intent.putExtra("ID",appWidgetId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widgetID,pendingIntent);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        views.setTextViewText(R.id.widgetID,Integer.toString(appWidgetId));

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
 /*   private static PendingIntent getPendingSelfIntent(Context context, String action, int id) {
        // An explicit intent directed at the current class (the "self").
        Intent intent = new Intent(context, WemoWidget.class);
        intent.setAction(action);
        intent.putExtra("ID",id);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }*/
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        // Find the widget id from the intent.
        //  Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        int mAppWidgetId = -1;
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        // intent.get
        if (extras != null) {
            //  mAppWidgetId = extras.getInt(
            //           AppWidgetManager.EXTRA_APPWIDGET_ID,
            //           AppWidgetManager.INVALID_APPWIDGET_ID);
            //   int stop=1;

            //  String widgetData = getWidgetData("Widget"+mAppWidgetId,context);
            // CharSequence bundlestring = extras.getCharSequence("ID");
            mAppWidgetId=extras.getInt("ID",0);

            //  ComponentName thisAppWidget = new ComponentName(context.getPackageName(), example_appwidget_info.class.getName());
            //  int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);
            // stop = 2;
        }

        if (ACTION_UPDATE_CLICK.equals(intent.getAction())) {
            //onUpdateFromReceive(context);
            //  updateAppWidget(context,appWidgetManager,mAppWidgetId);
           //TODO: Investigate encoding Action with the ID
            Toast.makeText(context, "From the "+mAppWidgetId+" widget (do special work)", Toast.LENGTH_SHORT).show();
        }
    }


}


