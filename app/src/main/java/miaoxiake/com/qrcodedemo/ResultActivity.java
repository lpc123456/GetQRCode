package miaoxiake.com.qrcodedemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import miaoxiake.com.qrcodedemo.util.QRCodeUtil;

public class ResultActivity extends AppCompatActivity {
    private ImageView iv_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        String content = intent.getStringExtra("content");
        Bitmap bitmap = QRCodeUtil.encodeAsBitmap(content, 260, 260);
        iv_result=findViewById(R.id.iv_result);
        iv_result.setImageBitmap(bitmap);
    }
}
