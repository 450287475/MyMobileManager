package com.example.mumuseng.mymobilemanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mumuseng.application.MyApplication;
import com.example.mumuseng.utils.MD5Utils;

public class HomeActivity extends ActionBarActivity {

    private static final int SAFE = 0;
    private static final int CALLMSGSAFE = 1;
    private static final int APP = 2;
    private static final int TASKMANAGER = 3;
    private static final int NETMANAGER = 4;
    private static final int TROJAN = 5;
    private static final int SYSOPTIMIZE = 6;
    private static final int ATOOLS = 7;
    private static final int SETTINGS = 8;
    private GridView gv_home_item;
    private String[] titles;
    private int[] icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        titles = new String[]{"手机防盗","通讯卫士","软件管理",
                "进程管理","流量统计","手机杀毒",
                "缓存清理","高级工具","设置中心"};
        icon = new int[]{R.drawable.safe,R.drawable.callmsgsafe ,R.drawable.app ,
                R.drawable.taskmanager ,R.drawable.netmanager ,R.drawable.trojan ,
                R.drawable.sysoptimize,R.drawable.atools,R.drawable.settings};
        gv_home_item = (GridView) findViewById(R.id.gv_home_item);
        gv_home_item.setAdapter(new MyAdapter());
        gv_home_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case SAFE:
                        String safe_pwd = MyApplication.sp.getString("safe_pwd", "");
                        if (safe_pwd.isEmpty()) {
                            showDialogSetUpPwd();
                        } else {
                            showDialogInputPwd();
                        }
                        break;
                    case CALLMSGSAFE:
                        break;
                    case APP:
                        break;
                    case TASKMANAGER:
                        break;
                    case NETMANAGER:
                        break;
                    case TROJAN:
                        break;
                    case SYSOPTIMIZE:
                        break;
                    case ATOOLS:
                        startActivity(new Intent(HomeActivity.this, AdvanceToolActivity.class));
                        break;
                    case SETTINGS:
                        startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                        break;
                }
                Toast.makeText(HomeActivity.this, titles[position], Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showDialogInputPwd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_input_pwd, null);
        final EditText et_dialogInput_pwd = (EditText) view.findViewById(R.id.et_dialogInput_pwd);
        final String safe_pwd = MyApplication.sp.getString("safe_pwd", "");
        Button bt_dialogInput_confirm = (Button)view. findViewById(R.id.bt_dialogInput_confirm);
        Button bt_dialogInput_cancle = (Button)view. findViewById(R.id.bt_dialogInput_cancle);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        //输入密码取消
        bt_dialogInput_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //输入密码确认
        bt_dialogInput_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String pwd = et_dialogInput_pwd.getText().toString();
                pwd= MD5Utils.getMd5Digest(pwd);
                if (!pwd.isEmpty() ) {
                    if(!safe_pwd.isEmpty()){
                        if(safe_pwd.equals(pwd)){
                            Toast.makeText(HomeActivity.this, "密码正确", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            startActivity(new Intent(HomeActivity.this, SaveActivity.class));
                        }else {
                            Toast.makeText(HomeActivity.this, "密码不正确,请重新输入", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(HomeActivity.this, "密码库为空,请重启软件", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showDialogSetUpPwd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
       View view = View.inflate(this, R.layout.dialog_setup_pwd, null);
        final EditText et_dialog_pwd = (EditText) view.findViewById(R.id.et_dialog_pwd);
        final EditText et_dialog_conpwd = (EditText) view.findViewById(R.id.et_dialog_conpwd);
        Button bt_dialog_confirm = (Button) view.findViewById(R.id.bt_dialog_confirm);
        Button bt_dialog_cancle = (Button) view.findViewById(R.id.bt_dialog_cancle);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        //设置密码取消
        bt_dialog_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //设置密码确认
        bt_dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = et_dialog_pwd.getText().toString();
                String conpwd = et_dialog_conpwd.getText().toString();
                if (!pwd.isEmpty() &&!conpwd.isEmpty()) {
                    if (pwd.equals(conpwd)) {
                        String md5Digest = MD5Utils.getMd5Digest(pwd);
                        MyApplication.editor.putString("safe_pwd", md5Digest);
                        MyApplication.editor.commit();
                        dialog.dismiss();
                        Toast.makeText(HomeActivity.this, "密码设置成功!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(HomeActivity.this,SaveActivity.class));
                    } else {
                        Toast.makeText(HomeActivity.this, "两次密码不相同,请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "密码和确认密码都不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    class MyAdapter extends BaseAdapter{

        private View view;

        @Override
        public int getCount() {
            return icon.length;
        }

        @Override
        public Object getItem(int position) {
            return titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            view = View.inflate(HomeActivity.this, R.layout.gridview_home_item, null);
            ImageView iv_gridview_title = (ImageView) view.findViewById( R.id.iv_gridview_title);
            TextView tv_gridview_title = (TextView) view.findViewById( R.id.tv_gridview_title);
            iv_gridview_title.setImageResource(icon[position]);
            tv_gridview_title.setText(titles[position]);
            return view;
        }
    }
}
