package com.mirror.woodpecker.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.activity.AboutUsActivity;
import com.mirror.woodpecker.app.activity.LoginActivity;
import com.mirror.woodpecker.app.activity.OrderDetailsActivity;
import com.mirror.woodpecker.app.activity.RepairAddActivity;
import com.mirror.woodpecker.app.activity.UserRepairListActivity;
import com.mirror.woodpecker.app.activity.ZiXunDetailsActivity;
import com.mirror.woodpecker.app.app.AppContext;

import dev.mirror.library.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by dongqian on 16/1/3.
 */
public class IndexFragment extends BaseFragment {
    private String[] imgs = {"http://h.hiphotos.baidu.com/image/w%3D1920%3Bcrop%3D0%2C0%2C1920%2C1080/sign=fed1392e952bd40742c7d7f449b9a532/e4dde71190ef76c6501a5c2d9f16fdfaae5167e8.jpg",
            "http://a.hiphotos.baidu.com/image/w%3D1920%3Bcrop%3D0%2C0%2C1920%2C1080/sign=25d477ebe51190ef01fb96d6fc2ba675/503d269759ee3d6df51a20cd41166d224e4adedc.jpg",
            "http://c.hiphotos.baidu.com/image/w%3D1920%3Bcrop%3D0%2C0%2C1920%2C1080/sign=70d2b81e60d0f703e6b291d53aca6a5e/0ff41bd5ad6eddc4ab1b5af23bdbb6fd5266333f.jpg"};
    private String[] titles = {"Page 1", "Page 2", "Page 3"};

    private Button mBtn1,mBtn2,mBtn3,mBtn4;
    private AutoScrollViewPager pager;
    private PagerAdapter mAdapter;

//    public IndexFragment() {
//    }


    @Override
    public int setLayoutId() {
        return R.layout.fragment_index;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_index, container, false);
//    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        if(mAdapter == null){
            pager = (AutoScrollViewPager) view.findViewById(R.id.scroll_pager);
            mBtn1 = (Button)view.findViewById(R.id.btn1);
            mBtn2 = (Button)view.findViewById(R.id.btn2);
            mBtn3 = (Button)view.findViewById(R.id.btn3);
            mBtn4 = (Button)view.findViewById(R.id.btn4);
            mBtn1.setOnClickListener(this);
            mBtn2.setOnClickListener(this);
            mBtn3.setOnClickListener(this);
            mBtn4.setOnClickListener(this);

            mAdapter = new PagerAdapter() {
                @Override
                public int getCount() {
                    return imgs.length;
                }

                @Override
                public boolean isViewFromObject(View view, Object o) {
                    return view == o;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    ImageView view = new ImageView(container.getContext());
                    view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    AppContext.displayImage(view, imgs[position]);
                    container.addView(view);
                    return view;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            };

            pager.setAdapter(mAdapter);
            pager.setScrollFactgor(5);
            pager.setOffscreenPageLimit(3);
            pager.startAutoScroll(2000);
            pager.setOnPageClickListener(new AutoScrollViewPager.OnPageClickListener() {
                @Override
                public void onPageClick(AutoScrollViewPager autoScrollPager, int position) {
                    showToast("You clicked page: " + (position + 1));
                }
            });

        }

    }
    @Override
    public void onClick(View v) {
        super.onClick(v);
        /**
         * 返回的数据中role_id为角色ID，数据1代表单位主管，
         * 2代表部门主管，3代表客服，4代表维修人员，0为普通用户
         * 根据这个进行登录判定
         */
        switch (v.getId()){
            case R.id.btn1:
                if(AppContext.USER_ROLE_ID == 1||AppContext.USER_ROLE_ID == 2||AppContext.USER_ROLE_ID == 0){
                    startActivity(new Intent(getActivity(), RepairAddActivity.class));
                }else{
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), LOGIN_CODE1);
                }

                break;
            case R.id.btn2:
                if(AppContext.USER_ROLE_ID == 1||AppContext.USER_ROLE_ID == 2||AppContext.USER_ROLE_ID == 0){
                    startActivity(new Intent(getActivity(), UserRepairListActivity.class));
                }else{
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), LOGIN_CODE2);
                }

                break;
            case R.id.btn3:
                startActivity(new Intent(getActivity(), ZiXunDetailsActivity.class));

                break;
            case R.id.btn4:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case LOGIN_CODE1:
                    startActivity(new Intent(getActivity(), RepairAddActivity.class));
                    /*if(AppContext.USER_ROLE_ID == 1||AppContext.USER_ROLE_ID == 2||AppContext.USER_ROLE_ID == 0){
                        startActivity(new Intent(getActivity(), RepairAddActivity.class));
                    }*/
                    break;
                case LOGIN_CODE2:
                    startActivity(new Intent(getActivity(), UserRepairListActivity.class));
//                    if(AppContext.USER_ROLE_ID == 1||AppContext.USER_ROLE_ID == 2||AppContext.USER_ROLE_ID == 0){
//                        startActivity(new Intent(getActivity(), UserRepairListActivity.class));
//                    }
                    break;

            }
        }

    }
}
