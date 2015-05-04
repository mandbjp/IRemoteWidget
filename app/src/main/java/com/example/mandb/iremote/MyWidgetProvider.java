package com.example.mandb.iremote;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.mandb.iremote.lib.AsyncHttpRequest;

/**
 * Created by mandb on 15/05/05.
 */
public class MyWidgetProvider extends AppWidgetProvider {

    private static Context context;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        MyWidgetProvider.context = context;

        // ウィジェットレイアウトの初期化
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        // ボタンイベントを登録
        remoteViews.setOnClickPendingIntent(R.id.button, clickButton(context));

        // アップデートメソッド呼び出し
        pushWidgetUpdate(context, remoteViews);
    }

    public static PendingIntent clickButton(Context context) {

        AsyncHttpRequest httpRequest = new AsyncHttpRequest(context);
        Log.d(MyWidgetProvider.class.getName(), "clickButton called");
        String url = MyWidgetProvider.context.getString(R.string.url);
        httpRequest.execute(url);

        // initiate widget update request
        Intent intent = new Intent();
        intent.setAction("UPDATE_WIDGET");
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    // アップデート
    public static void pushWidgetUpdate(Context context, RemoteViews remoteViews) {
        ComponentName myWidget = new ComponentName(context, MyWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        manager.updateAppWidget(myWidget, remoteViews);
    }
}
