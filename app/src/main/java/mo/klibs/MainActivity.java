package mo.klibs;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mo.klib.k;
import mo.klib.modle.adapter.KRecycleViewAdapter;
import mo.klib.modle.annotation.methods.KInjectCheckNet;
import mo.klib.modle.annotation.methods.KInjectEvent;
import mo.klib.modle.annotation.methods.KInjectRecycleViewAdapter;
import mo.klib.modle.annotation.methods.KInjectView;
import mo.klib.ui.activity.KMediaActivity;

//@KInjectContentView(R.layout.activity_main)
public class MainActivity extends KMediaActivity {
    @KInjectView(R.id.iv_1)
    private ImageView imageView;
    @KInjectView(R.id.tv_1)
    private TextView tv_1;
    private ArrayList<String> list = new ArrayList<>();


    @KInjectRecycleViewAdapter(value = R.id.krv__, date = "list")
    private KRecycleViewAdapter<String> adapter;

    @KInjectCheckNet(toast = "sssssssssss")
    @KInjectEvent({R.id.tv_1})
    public void onClick1(View view) {
        actionMediaTakePic();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        k.view().inject(this);
        k.view().injectRecycleView(this);
        tv_1.setText("aaaaaaa");
        list.add("111111111");
        list.add("222222222");
        adapter.notifyDataSetChanged();


//        tv_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void KInjectEvent(View v) {
//                actionMediaTakePic();
//            }
//        });
//        //启动后台服务
//        Intent service=new Intent(this, TimeService.class);
//
//        startService(service);
//        DataChangeReceiver receiver = new DataChangeReceiver();
//        registerReceiver(receiver, new IntentFilter(Intent.ACTION_TIME_TICK));
    }

    @Override
    protected void setMediaResult(int phontoType, Bitmap bitmap, String path, Intent data) {
        super.setMediaResult(phontoType, bitmap, path, data);
        imageView.setImageBitmap(bitmap);
    }

}
