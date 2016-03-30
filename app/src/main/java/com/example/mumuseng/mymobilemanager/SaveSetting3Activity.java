package com.example.mumuseng.mymobilemanager;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SaveSetting3Activity extends ActionBarActivity {

    public static SaveSetting3Activity mSaveSetting3Activity;
    public static EditText et_saveset3_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_setting3);
        mSaveSetting3Activity = this;
        Button bt_saveset3_chooseContact = (Button) findViewById(R.id.bt_saveset3_chooseContact);
        et_saveset3_contact = (EditText) findViewById(R.id.et_saveset3_contact);
        bt_saveset3_chooseContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //方法1
              /*  Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
                startActivityForResult(intent, 100);*/
                //方法2
                Intent intent = new Intent(SaveSetting3Activity.this, GetContactsActivity.class);
                startActivityForResult(intent, 200);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    Uri data1 = data.getData();
                    System.out.println(data1 + "data1");
                    String[] projection= {ContactsContract.CommonDataKinds.Phone.NUMBER};
                    Cursor cursor = getContentResolver().query(data1, projection, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String number = cursor.getString(columnIndex);
                    et_saveset3_contact.setText(number);
                    break;
                case 200:
                    String number1 = data.getStringExtra("number");
                    et_saveset3_contact.setText(number1);
                    break;
            }
        }
    }
}
