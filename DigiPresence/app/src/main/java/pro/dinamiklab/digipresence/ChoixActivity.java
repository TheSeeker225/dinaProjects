package pro.dinamiklab.digipresence;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChoixActivity extends AppCompatActivity {

    // Set the variable to get permission to send SMS
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    // Set the employee's code
    String strMatricule;

    // Set the phone number to send at the server
    String phoneNumber = "+2250777587173";

    //Set the service center address if needed, otherwise null.
    String scAddress = null;
    // Set pending intents to broadcast
    // when message sent and when delivered, or set to null.
    PendingIntent sentIntent = null, deliveryIntent = null;

    public String getScAddress() {
        return scAddress;
    }

    public PendingIntent getSentIntent() {
        return sentIntent;
    }

    public PendingIntent getDeliveryIntent() {
        return deliveryIntent;
    }

    public static int getMyPermissionsRequestSendSms() {
        return MY_PERMISSIONS_REQUEST_SEND_SMS;
    }

    public String getStrMatricule() {
        return strMatricule;
    }

    public void setStrMatricule(String strMatricule) {
        this.strMatricule = strMatricule;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    Button btnAbs;
    Button btnA;
    Button btnDM;
    Button btnAM;
    Button btnDP;
    Button btnAP;
    Button btnDC;
    Button btnAC;
    Button btnD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choix);

        this.setStrMatricule( getIntent().getStringExtra("Matricule") );

        btnDC = findViewById(R.id.btn_dc);
        btnDP = findViewById(R.id.btn_dp);
        btnDM = findViewById(R.id.btn_dm);

        btnAC = findViewById(R.id.btn_ac);
        btnAM = findViewById(R.id.btn_am);
        btnAP = findViewById(R.id.btn_ap);

        btnA = findViewById(R.id.btn_a);
        btnD = findViewById(R.id.btn_d);
        btnAbs = findViewById(R.id.btn_abs);

        btnAbs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                smsSendMessage("abs");
            }
        });

        btnA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                smsSendMessage("a");
            }
        });

        btnDM.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                smsSendMessage("dm");
            }
        });

        btnAM.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                smsSendMessage("am");
            }
        });

        btnDP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                smsSendMessage("dp");
            }
        });

        btnAP.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                smsSendMessage("ap");
            }
        });

        btnDC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                smsSendMessage("dc");
            }
        });

        btnAC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                smsSendMessage("ac");
            }
        });

        btnD.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                smsSendMessage("d");
            }
        });
    }

    public void composeSmsMessage(String choix) {
        // Concatenate "smsto:" with phone number to create smsNumber.
        String smsNumber = "smsto:" + this.getPhoneNumber();
        // Create the intent.
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        // Set the data for the intent as the phone number.
        smsIntent.setData(Uri.parse(smsNumber));
        // Add the message (sms) with the key ("sms_body").
        smsIntent.putExtra("sms_body", this.getStrMatricule() + "*" + choix);
        // If package resolves (target app installed), send intent.
        if (smsIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(smsIntent);
        } else {
            Toast.makeText(ChoixActivity.this, "Message non envoyé",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (permissions[0].equalsIgnoreCase(Manifest.permission.SEND_SMS)
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted.
                    Toast.makeText(ChoixActivity.this, "Permission accordée",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // Permission denied.
                    Toast.makeText(ChoixActivity.this, "Message non envoyé",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void smsSendMessage(String choix) {

        Intent retourAuMatricule = new Intent(ChoixActivity.this, MainActivity.class);

        // Get the phone number
        String destinationAddress = this.getPhoneNumber();
        // Get the text of the SMS message.
        String smsMessage = this.getStrMatricule() + "*" + choix;
        startActivity(retourAuMatricule);
        // Check for permission first.
        checkForSmsPermission();
        // Use SmsManager.
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage
                (destinationAddress, this.getScAddress(), smsMessage,
                        this.getSentIntent(), this.getDeliveryIntent());
        Toast.makeText(ChoixActivity.this,
                "Message envoyé avec succès !", Toast.LENGTH_SHORT).show();

    }

    private void checkForSmsPermission() {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            // Permission not yet granted. Use requestPermissions().
            // MY_PERMISSIONS_REQUEST_SEND_SMS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS}, getMyPermissionsRequestSendSms());
        } else
            Toast.makeText(ChoixActivity.this,
                    "Permission accordée !", Toast.LENGTH_SHORT).show();
    }
}