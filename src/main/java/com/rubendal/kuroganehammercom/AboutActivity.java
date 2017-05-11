package com.rubendal.kuroganehammercom;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.util.Assets;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setTitle("About/Credits");

        TextView text = (TextView)findViewById(R.id.about);
        if (Build.VERSION.SDK_INT >= 24) {
            text.setText(Html.fromHtml(Assets.getAsset(getAssets(),"about.html"),Html.FROM_HTML_MODE_LEGACY));

        } else {
            text.setText(Html.fromHtml(Assets.getAsset(getAssets(),"about.html")));
        }
        text.setMovementMethod(LinkMovementMethod.getInstance());

    }


}
