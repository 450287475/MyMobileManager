package com.example.mumuseng.mymobilemanager;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mumuseng.bean.Contact;
import com.example.mumuseng.utils.ContactUtils;

import java.util.ArrayList;

public class GetContactsActivity extends ActionBarActivity {

    private ListView lv_getContacts_content;
    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_contacts);
        lv_getContacts_content = (ListView) findViewById(R.id.lv_getContacts_content);
        contacts = ContactUtils.getContacts();
        ContactsAdapter contactsAdapter = new ContactsAdapter();
        lv_getContacts_content.setAdapter(contactsAdapter);
        lv_getContacts_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String number = contacts.get(position).getNumber();
                Intent intent = new Intent();
                intent.putExtra("number",number);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
    class ContactsAdapter extends BaseAdapter{

        private View view;
        private ViewHolder holder;

        @Override
        public int getCount() {
            return contacts.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                holder = new ViewHolder();
                view = View.inflate(GetContactsActivity.this, R.layout.listview_get_contacts, null);
                holder.tv_listview_getContacts_name = (TextView) view.findViewById(R.id.tv_listview_getContacts_name);
                holder.tv_listview_getContacts_number = (TextView) view.findViewById(R.id.tv_listview_getContacts_number);
                view.setTag(holder);
            }else {
                view=convertView;
                holder= (ViewHolder) view.getTag();
            }
            Contact contact = contacts.get(position);
            holder.tv_listview_getContacts_name.setText(contact.getName());
            holder.tv_listview_getContacts_number.setText(contact.getNumber());
            return view;
        }
    }
    class  ViewHolder{
        TextView tv_listview_getContacts_name;
        TextView tv_listview_getContacts_number;
    }
}
