package nicehome2018.nicehome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class About_us extends AppCompatActivity {
    Toolbar toolbar;

    TextView isn_website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        //toolbar event
        toolbar=(Toolbar)findViewById(R.id.about_us_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        isn_website=(TextView)findViewById(R.id.isn_website);
        isn_website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://" +isn_website.getText().toString().trim();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });


    }
}
