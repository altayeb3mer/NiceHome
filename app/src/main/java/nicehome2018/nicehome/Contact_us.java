package nicehome2018.nicehome;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Contact_us extends AppCompatActivity {
    Toolbar toolbar;

    TextView nicehome_location,phone1,phone2,phone3,phone4,phone5,phone6,phone7,info_email,website;
    public static final int REQUEST_CALL = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        //toolbar event
        toolbar=(Toolbar)findViewById(R.id.contact_us_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar_on_back_press);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        nicehome_location=(TextView)findViewById(R.id.nicehome_location);
        nicehome_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:25.288851,51.536203"));
                i.setClassName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity");
                startActivity(i);
            }
        });
        website=(TextView)findViewById(R.id.website);
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://" +website.getText().toString().trim();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });


        phone1=(TextView)findViewById(R.id.phone1);
        phone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAlertMessageDial(phone1.getText().toString().trim());
            }
        });
        phone2=(TextView)findViewById(R.id.phone2);
        phone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAlertMessageDial(phone2.getText().toString().trim());
            }
        });
        phone3=(TextView)findViewById(R.id.phone3);
        phone3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAlertMessageDial(phone3.getText().toString().trim());
            }
        });
        phone4=(TextView)findViewById(R.id.phone4);
        phone4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAlertMessageDial(phone4.getText().toString().trim());
            }
        });
        phone5=(TextView)findViewById(R.id.phone5);
        phone5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAlertMessageDial(phone5.getText().toString().trim());
            }
        });
        phone6=(TextView)findViewById(R.id.phone6);
        phone6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAlertMessageDial(phone6.getText().toString().trim());
            }
        });
        phone7=(TextView)findViewById(R.id.phone7);
        phone7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAlertMessageDial(phone7.getText().toString().trim());
            }
        });


        info_email=(TextView)findViewById(R.id.info_email);
        info_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(Contact_us.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Contact_us.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CALL);
                } else {

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:"+info_email.getText().toString().trim()));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.send_mail_extras));

                    try {
                        startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.send_intent_title)));
                    }catch (Exception e){
                        Toast.makeText(Contact_us.this,getResources().getString(R.string.send_mail_error), Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }


    /////////
    protected void buildAlertMessageDial(final String nom) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dail_no)+" "+nom+"?")
                .setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        if (ContextCompat.checkSelfPermission(Contact_us.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Contact_us.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                        } else {
                            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + nom)));
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    ////////


}
