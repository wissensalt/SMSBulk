package com.zisal.smsbulk;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ActivityMain extends ActionBarActivity {

    @InjectView(R.id.txtContent) TextView txtContent;
    @InjectView(R.id.txtTargetNumber) TextView txtTargetNumber;
    @InjectView(R.id.txtMessageNumber) TextView txtMessageNumber;
    @InjectView(R.id.btSendMessage) Button btSendMessage;
    @InjectView(R.id.btSendWA) Button btSendWA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btSendMessage)
    public void sendMessage(){
        if(txtTargetNumber.getText().length()>0){
            if(txtContent.getText().length()>0){
                if(txtMessageNumber.getText().length()>0){
                    String targetNumber = txtTargetNumber.getText().toString();
                    String content = txtContent.getText().toString();
                    int messageNumber = Integer.parseInt(txtMessageNumber.getText().toString());
                    for(int a=0; a<messageNumber; a++){
                        /*Send message*/
                        sendSms(targetNumber, content);
                    }
                }else{
                    Toast.makeText(this, "Message number must not be null", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Content must not be null", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Target number must not be null", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btSendWA)
    public void sendWhatsApp(){
        if(txtContent.getText().length()>0){
            if(txtMessageNumber.getText().length()>0){
                String content = txtContent.getText().toString();
                int msgNumber = Integer.parseInt(txtMessageNumber.getText().toString());
                for(int a=0; a<msgNumber; a++){
                    sendWhatsAppMsg(content);
                }
            }else{
                Toast.makeText(this, "Message number must not be null", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Content must not be null", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendSms(String targetNumber, String content){
        PendingIntent pi = PendingIntent.getActivity(this, 0, null, 0);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(targetNumber, null, content, pi, null);
    }

    public void sendWhatsAppMsg(String message) {
        Intent waIntent = new Intent(Intent.ACTION_SEND);
        waIntent.setType("text/plain");
        waIntent.setPackage("com.whatsapp");
        if (waIntent != null) {
            waIntent.putExtra(Intent.EXTRA_TEXT, message);//
            startActivity(Intent.createChooser(waIntent, message));
        } else {
            Toast.makeText(this, "WhatsApp not found", Toast.LENGTH_SHORT).show();
        }

    }
}
