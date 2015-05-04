package com.example.mandb.iremote.lib;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @see "http://shirusu-ni-tarazu.hatenablog.jp/entry/2013/01/20/033030"
 */
public class AsyncHttpRequest extends AsyncTask<String, Void, String> {

    private Context context;

    public AsyncHttpRequest(Context context) {

        // 呼び出し元のアクティビティ
        this.context = context;
    }

    // このメソッドは必ずオーバーライドする必要があるよ
    // ここが非同期で処理される部分みたいたぶん。
    @Override
    protected String doInBackground(String... stringUrls) {
        // httpリクエスト投げる処理を書く。
        // ちなみに私はHttpClientを使って書きましたー
        StringBuilder sb = new StringBuilder();
        for(String stringUrl : stringUrls) {
            try {
                URL url = new URL(stringUrl);
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("GET");
                http.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();

            } catch (IOException e) {
                return "Exception " + e.getMessage();
            }
        }
        return sb.toString();
    }


    // このメソッドは非同期処理の終わった後に呼び出されます
    @Override
    protected void onPostExecute(String result) {
        // 取得した結果をテキストビューに入れちゃったり
        Toast.makeText(this.context, result, Toast.LENGTH_SHORT).show();
    }
}