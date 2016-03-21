package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.adapter.NormalAdapter;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.About;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.activity.MultiImageSelectorActivity;

/**
 * Created by 王沛栋 on 2016/3/11.
 */
public class AboutUsActivity extends BaseActivity{
    private ListView mListView;

    private List<About> mList;
    private NormalAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setBack();
        setTitleText("了解啄木鸟");

        mListView = (ListView)findViewById(android.R.id.list);
        loadData();
    }

    private void loadData(){

        mHttpClient.postData(KNOW_ZMN, null, new AppAjaxCallback.onRecevierDataListener<About>() {
            @Override
            public void onReceiverData(List<About> data, String msg) {
                if(mList == null){
                    mList = new ArrayList<>();
                    mList.addAll(data);
                }

                //添加投诉建议
                About about = new About();
                about.setTitle("投诉建议");
                about.setIcon(4);

                mList.add(about);

                initView();
            }

            @Override
            public void onReceiverError(String msg) {
                showToast(msg);
            }

            @Override
            public Class<About> dataTypeClass() {
                return About.class;
            }
        });
    }

    private void initView(){
        mAdapter = new NormalAdapter(getApplicationContext(),mList,R.layout.item_about);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                About about = mList.get(position);
                List<About.Other> list = about.getList();

                switch (about.getIcon()){
                    case 1:
                        startActivity(new Intent(AboutUsActivity.this, AboutUsDetailsActivity.class).
                                putExtra(INTENT_ID, about.getInfo().getContent()).putExtra("TITLE", about.getTitle()));
                        break;
                    case 2:
                        startActivity(new Intent(AboutUsActivity.this, AboutUsGridViewActivity.class)
                                .putParcelableArrayListExtra(INTENT_ID, (ArrayList<? extends Parcelable>) list)
                                .putExtra("TITLE", about.getTitle()));
                        break;
                    case 3:
                        startActivity(new Intent(AboutUsActivity.this, AboutUsListViewActivity.class)
                                .putParcelableArrayListExtra(INTENT_ID, (ArrayList<? extends Parcelable>) list)
                                .putExtra("TITLE", about.getTitle()));
                        break;
                    case 4:
                        if(AppContext.USER_ROLE_ID == -1){
                            startActivityForResult(new Intent(AboutUsActivity.this,LoginActivity.class),LOGIN_CODE1);
                        }else{
                            startActivity(new Intent(AboutUsActivity.this,ComplainActivity.class));
                        }

                        break;
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case LOGIN_CODE1:
                startActivity(new Intent(AboutUsActivity.this,ComplainActivity.class));
                break;
        }

    }
}
