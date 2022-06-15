package com.example.r_upgrade.common.tasks;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class CheckTencentStoreVersionTask extends AsyncTask<String, String, String> {
    private final String packageName;
    private final VersionCallBack callBack;

    public CheckTencentStoreVersionTask(String packageName, VersionCallBack callBack) {
        this.packageName = packageName;
        this.callBack = callBack;
    }

    @Override
    protected String doInBackground(String... strings) {
        String latestVersion = "";
        try {
            Document document = Jsoup.connect("https://a.app.qq.com/o/simple.jsp?pkgname="+packageName)
                    .timeout(30000)
                    .get();
            Element element = document.select("div.pp-comp > p.pp-comp-extra-p:last-child")
                    .first();
            if (element != null && element.ownText().split("：").length==2) {
                latestVersion = element.ownText().split("：")[1];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latestVersion;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        callBack.versionName(s);
    }
}
