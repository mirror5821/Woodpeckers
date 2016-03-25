package com.mirror.woodpecker.app.activity;

import android.content.Intent;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.model.Complain;
import com.mirror.woodpecker.app.util.AppAjaxCallback;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;
import dev.mirror.library.android.util.DateUtil;

/**
 * Created by 王沛栋 on 2016/3/21.
 */
public class ComplainListActivity extends BaseRecyclerViewActivity {
    @Override
    public void initOtherView() {
        super.initOtherView();
        setTitleText("建议列表");
        setBack();
    }

    @Override
    public int setLayoutById() {
        mList = new ArrayList<>();
        return R.layout.activity_complain_list;
    }

    @Override
    public void loadData() {
        mHttpClient.postData(COMPLAIN_LIST, null, new AppAjaxCallback.onRecevierDataListener<Complain>() {

            @Override
            public void onReceiverData(List<Complain> data, String msg) {
                if(mDefaultPage == pageNo){
                    mList.clear();
                }

                mList.addAll(data);
                setAdapter();
                mRecyclerView.setLoadingMoreEnabled(false);
                mRecyclerView.refreshComplete();

            }

            @Override
            public void onReceiverError(String msg) {
                setAdapter();
            }

            @Override
            public Class<Complain> dataTypeClass() {
                return Complain.class;
            }
        });
    }

    @Override
    public int setItemLayoutId() {
        return R.layout.item_complain;
    }

    @Override
    public void setItemView(DevRecyclerViewHolder holder, Object item) {
        final Complain c = (Complain)item;
        String html = "<font color='#121661'>"+c.getUname()+"</font>:留言";
        TextView name = holder.getView(R.id.name);
        TextView content = holder.getView(R.id.content);
        TextView time = holder.getView(R.id.time);

        name.setText(Html.fromHtml(html));
        content.setText(c.getContent());
        time.setText(DateUtil.TimeStamp2Date("yyyy-MM-dd HH:mm", c.getAddtime()));

        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComplainListActivity.this,ComplainReplayListActivity.class).putExtra(INTENT_ID,c.getId()));
            }
        });
    }
}
