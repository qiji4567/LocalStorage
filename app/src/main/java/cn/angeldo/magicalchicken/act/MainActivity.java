package cn.angeldo.magicalchicken.act;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import cn.angeldo.magicalchicken.CardFra;
import cn.angeldo.magicalchicken.Info;
import cn.angeldo.magicalchicken.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnAdd = (Button) findViewById(R.id.btn_add);
        Button btnInfo = (Button) findViewById(R.id.btn_info);

        Log.i("获得文件内容：" + getClass().getSimpleName(), readFromFile(checkFile()));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), CarAddAct.class);
                startActivity(intent);
                finish();
            }
        });
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Info.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //读取文件
    public String checkFile() {
        String urlStr = Environment.getExternalStorageDirectory() + "/magicalchiken/" + "tmp.txt";
        //检查存储设备
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File file = new File(urlStr);
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
            } catch (Exception e) {

            }
        } else {
            Toast.makeText(getApplicationContext(), "存储设备有问题",
                    Toast.LENGTH_SHORT).show();
        }
        return urlStr;
    }

    //读取文件
    public String readFromFile(String url) {
        File file = new File(url);
        String content = "";
        try {
            InputStream instream = new FileInputStream(file);
            if (instream != null) {
                InputStreamReader inputreader = new InputStreamReader(instream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line;


                //分行读取
                while ((line = buffreader.readLine()) != null) {
                    content += line + "\n";
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    CardFra listCard = new CardFra();
                    Bundle bundle2 = new Bundle();
                    //String json_str="{'blueball':'3','created':'2017-07-23 19:49:14','mark':'双色球','redball':'','type':1}";
                    //String json_str=line.substring(1,line.length()-1);
                    bundle2.putString("fragData", content);
                    listCard.setArguments(bundle2);
                    transaction.add(R.id.list_content, listCard);
                    transaction.commit();
                }

                instream.close();
            }
        } catch (java.io.FileNotFoundException e) {
            Log.d("TestFile", e.getMessage());
        } catch (IOException e) {
            Log.d("TestFile", e.getMessage());
        }
        return content;
    }

    //退出调用
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dialog_loginout();
        }
        return false;
    }

    //退出提示
    protected void dialog_loginout() {
        new AlertDialog.Builder(this)
                .setTitle("喂！")
                .setMessage("就这样离开吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        android.os.Process.killProcess(android.os.Process
                                .myPid());
                    }
                })
                .setNegativeButton("取消",
                        new android.content.DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }
}
