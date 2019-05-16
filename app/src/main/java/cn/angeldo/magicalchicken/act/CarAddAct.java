package cn.angeldo.magicalchicken.act;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import org.json.JSONArray;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import cn.angeldo.magicalchicken.R;
import cn.angeldo.magicalchicken.utils.FileUtils;

public class CarAddAct extends Activity {
    public TextView caseResult, tv_num;
    public CheckBox caseTypeNum;
    public CheckBox caseTypeWord;
    public CheckBox caseTypeDXX;
    public EditText caseLength;
    public EditText caseMark;
    public JSONArray arrayResult = new JSONArray();
    public static final String bigChar = "ABCDEFGHJKLMNPQRSTUVWXYZ";
    public static final String smallChar = "abcdefghijkmnpqrstuvwxyz";
    public static final String numberChar = "0123456789";
    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 1;
    private String url = Environment.getExternalStorageDirectory().toString() + "/magicalchiken/" + "tmp.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_card_add);

        FrameLayout fl_back = (FrameLayout) findViewById(R.id.fl_back);
        Button btnCreate = (Button) findViewById(R.id.btn_create);
        Button btnSave = (Button) findViewById(R.id.btn_save);
        caseResult = (TextView) findViewById(R.id.case_result);
        tv_num = (TextView) findViewById(R.id.tv_num);
        caseTypeNum = (CheckBox) findViewById(R.id.case_type_num);
        caseTypeWord = (CheckBox) findViewById(R.id.case_type_word);
        caseTypeDXX = (CheckBox) findViewById(R.id.case_type_dxx);
        caseLength = (EditText) findViewById(R.id.case_length);
        caseMark = (EditText) findViewById(R.id.case_mark);
        caseMark.setText("双色球");

        final RadioGroup caseGroup = (RadioGroup) findViewById(R.id.case_group);
        final LinearLayout caseZDY = (LinearLayout) findViewById(R.id.case_zdy);
        //单项选择
        caseGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(radioButtonId);
                if (radioButtonId == R.id.other_case) {
                    caseZDY.setVisibility(View.VISIBLE);

                } else {
                    caseZDY.setVisibility(View.GONE);
                }
                caseMark.setText(rb.getText());
            }
        });
        //返回按钮
        fl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FileUtils.isFileExists(url)) {
                    FileUtils.deleteFile(url);
                }
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MainAct.class);
                startActivity(intent);
                finish();
            }
        });
        //确认生成
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkId = caseGroup.getCheckedRadioButtonId();
                //双色球
                switch (checkId) {
                    case R.id.ssq_case:
                        caseResult.setText(ssqCreate());
                        break;
                    //大乐透
                    case R.id.dlt_case:
                        caseResult.setText(dltCreate());
                        break;
                    //自定义
                    case R.id.other_case:
                        boolean iNum = caseTypeNum.isChecked();
                        boolean iWord = caseTypeWord.isChecked();
                        boolean iDXX = caseTypeDXX.isChecked();
                        if (caseLength.getText().length() > 0) {
                            int iLength = Integer.parseInt(caseLength.getText().toString());
                            if ((iNum | iWord | iDXX) && iLength > 0) {
                                caseResult.setText(zdyCreate(iNum, iWord, iDXX, iLength));
                            }
                        } else {
                            Toast.makeText(CarAddAct.this, "请输入方案长度",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;

                    default:
                        break;
                }
            }
        });
        //确认保存
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    if (ActivityCompat.checkSelfPermission(CarAddAct.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(CarAddAct.this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
                    }

                    if (arrayResult == null || TextUtils.isEmpty(caseResult.getText().toString())) {
                        Toast.makeText(CarAddAct.this, "请确认生成选择类型!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String strContent = arrayResult.toString() + "\r\n";
                    if (FileUtils.writeFileFromString(url, strContent, false)) {
                        Toast.makeText(CarAddAct.this, "新的方案结果已经保存",
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CarAddAct.this, MainAct.class);
                        startActivity(intent);
                        finish();
                    }
                }


            }
        });

    }


    //写入文件
    public boolean writeToFile(String content) {
        String url = Environment.getExternalStorageDirectory().toString() + "/magicalchiken/" + "tmp.txt";
        // 每次写入时，都换行写
        String strContent = content + "\r\n";
        Log.d("strContent=============" + getClass().getSimpleName(), strContent);
        try {
            File file = new File(url);
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            Log.d("file=============" + getClass().getSimpleName(), file.length() + "");
            raf.write(strContent.getBytes());
            Log.d("getBytes=============" + getClass().getSimpleName(), strContent.getBytes() + "");
            raf.close();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    //双色球生成方案
    public String ssqCreate() {
        int[] ssqRed = new int[6];
        int[] ssqBlue = new int[1];

        for (int i = 0; i < 6; i++) {
            int randomInt = (int) (Math.random() * 33) + 1;
            while (checkNum(ssqRed, randomInt)) {
                randomInt = (int) (Math.random() * 33) + 1;
            }
            ssqRed[i] = randomInt;
        }
        int randomInt = (int) (Math.random() * 16) + 1;
        ssqBlue[0] = randomInt;
        Arrays.sort(ssqRed);

        String ssqRedStr = Arrays.toString(ssqRed);
        String ssqBlueStr = Arrays.toString(ssqBlue);

        JSONObject jo = new JSONObject();
        //赋值
        jo.put("type", 1);
        jo.put("redball", ssqRedStr.substring(1, ssqRedStr.length() - 1));
        jo.put("blueball", ssqBlueStr.substring(1, ssqBlueStr.length() - 1));
        jo.put("mark", caseMark.getText().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jo.put("created", dateFormat.format(new Date()));
        //把JSONObject 添加到JSONArray
        arrayResult.put(jo);

        tv_num.setText("生成数量:" + arrayResult.length() + " 注");
        Log.i("双色球的json" + getClass().getSimpleName(), "" + arrayResult.toString() + "数量：" + arrayResult.length());
        return ssqRedStr + ssqBlueStr;
    }

    //大乐透生成方案
    public String dltCreate() {
        int[] dltRed = new int[5];
        int[] dltBlue = new int[2];
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int randomInt = random.nextInt(35) + 1;
            while (checkNum(dltRed, randomInt)) {
                randomInt = random.nextInt(35) + 1;
            }
            dltRed[i] = randomInt;
        }
        for (int i = 0; i < 2; i++) {
            int randomInt = random.nextInt(12) + 1;
            while (checkNum(dltBlue, randomInt)) {
                randomInt = random.nextInt(12) + 1;
            }
            dltBlue[i] = randomInt;
        }
        Arrays.sort(dltRed);
        Arrays.sort(dltBlue);

        String dltRedStr = Arrays.toString(dltRed);
        String dltBlueStr = Arrays.toString(dltBlue);

        JSONObject jo = new JSONObject();
        //赋值
        jo.put("type", 2);
        jo.put("redball", dltRedStr.substring(1, dltRedStr.length() - 1));
        jo.put("blueball", dltBlueStr.substring(1, dltBlueStr.length() - 1));
        jo.put("mark", caseMark.getText().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jo.put("created", dateFormat.format(new Date()));
        //把JSONObject 添加到JSONArray
        arrayResult.put(jo);
        Log.i("大乐透的json" + getClass().getSimpleName(), "" + arrayResult.toString());
        tv_num.setText("生成数量:" + arrayResult.length() + " 注");
        return Arrays.toString(dltRed) + Arrays.toString(dltBlue);
    }

    //自定义生成方案
    public String zdyCreate(boolean sNum, boolean sWord, boolean sDXX, int slength) {
        String seedStr = "";
        if (sNum) {
            seedStr = seedStr + numberChar;
        }
        if (sWord) {
            seedStr = seedStr + smallChar;
        }
        if (sDXX) {
            seedStr = seedStr + bigChar;
        }
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < slength; i++) {
            sb.append(seedStr.charAt(random.nextInt(seedStr.length())));
        }
        String sbStr = sb.toString();
        JSONObject jo = new JSONObject();
        //赋值
        jo.put("type", 3);
        jo.put("charstr", sbStr);
        jo.put("mark", caseMark.getText().toString());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        jo.put("created", dateFormat.format(new Date()));
        //把JSONObject 添加到JSONArray
        arrayResult.put(jo);
        Log.i("自定义的json", "" + arrayResult.toString());
        tv_num.setText("生成数量:" + arrayResult.length() + " 注");
        return sbStr;
    }

    //检测数字是否重复
    public boolean checkNum(int[] arr, int num) {
        boolean result = false;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == num) {
                result = true;
                break;
            }
        }
        return result;
    }

    //退出调用
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MainAct.class);
            startActivity(intent);
            finish();
        }
        return false;
    }
}
