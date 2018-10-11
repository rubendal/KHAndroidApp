package com.rubendal.kuroganehammercom;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.util.Assets;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        setTitle("Help");

        TextView text = (TextView)findViewById(R.id.help);
        if (Build.VERSION.SDK_INT >= 24) {
            text.setText(Html.fromHtml(Assets.getAsset(getAssets(),"help.html"),Html.FROM_HTML_MODE_LEGACY));

        } else {
            text.setText(Html.fromHtml(Assets.getAsset(getAssets(),"help.html")));
        }
        text.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
