package com.example.mandb.iremote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by mandb on 15/05/05.
 */
public class MyWidgetIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("UPDATE_WIDGET")) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            // テキストをクリック回数を元に更新
//            remoteViews.setTextViewText(R.id.title, "クリック回数: " + MyWidgetIntentReceiver.clickCount);

            // もう一回クリックイベントを登録(毎回登録しないと上手く動かず)
            remoteViews.setOnClickPendingIntent(R.id.button, MyWidgetProvider.clickButton(context));

            MyWidgetProvider.pushWidgetUpdate(context.getApplicationContext(), remoteViews);
        }
    }
}