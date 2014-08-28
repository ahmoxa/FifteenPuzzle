package ru.mydroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by vlasyk on 25.08.14.
 */
public class MenuActivity extends Activity implements View.OnClickListener{

    TextView tvStart;
    TextView tvExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        initialize();
    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()){
            case R.id.tvStart:
                intent = new Intent(this, FifteenActivity.class);
                break;
            case R.id.tvAbout:
                intent = new Intent(this, AboutActivity.class);
                break;
            case R.id.tvExit:
                finish();
                break;
        }
        if (intent != null) startActivity(intent);
    }

    private void initialize(){
//        tvStart = (TextView) findViewById(R.id.tvStart);
//        tvStart.setOnClickListener(this);
//        tvExit = (TextView)findViewById(R.id.tvExit);
//        tvExit.setOnClickListener(this);
    }
}
