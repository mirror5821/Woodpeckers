package com.mirror.woodpecker.app.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mirror.woodpecker.app.R;
import com.mirror.woodpecker.app.activity.AboutUsActivity;
import com.mirror.woodpecker.app.activity.LoginActivity;
import com.mirror.woodpecker.app.activity.NormalWebViewActivity;
import com.mirror.woodpecker.app.activity.RepairAddActivity;
import com.mirror.woodpecker.app.activity.UserRepairListActivity;
import com.mirror.woodpecker.app.activity.UserRepairListSinnersActivity;
import com.mirror.woodpecker.app.app.AppContext;
import com.mirror.woodpecker.app.model.Index;
import com.mirror.woodpecker.app.model.User;
import com.mirror.woodpecker.app.util.AppAjaxCallback;
import com.mirror.woodpecker.app.util.SharePreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import dev.mirror.library.android.view.autoscrollviewpager.AutoScrollViewPager;

/**
 * Created by dongqian on 16/1/3.
 */
public class IndexFragment extends BaseFragment {
    private String[] imgs = {"http://77wdgj.com1.z0.glb.clouddn.com/zmn1.jpg",
            "http://77wdgj.com1.z0.glb.clouddn.com/zmn2.jpg",
            "http://77wdgj.com1.z0.glb.clouddn.com/zmn3.jpg"};

    private Button mBtn1,mBtn2,mBtn3,mBtn4;
    private AutoScrollViewPager pager;
    private RadioGroup mRG;
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

        /*JSONObject j = new JSONObject();
        try{
            j.put("key1","我是key1的值");

            JSONArray jaa = new JSONArray();
            for (int i = 0;i<3;i++){
                JSONObject jj = new JSONObject();
                jj.put("image","我是图片"+i);
                jaa.put(jj);
            }

            j.put("images",jaa.toString());

            System.out.println("-------------------------"+j.toString());

        }catch (JSONException e){
            System.out.println("-------------------------"+e.getLocalizedMessage());
        }*/

        if(!AppContext.IS_LOGIN){
            login();
        }

        if(mAdapter == null){
            mRG = (RadioGroup)view.findViewById(R.id.layout_dot);
            pager = (AutoScrollViewPager) view.findViewById(R.id.scroll_pager);
            mBtn1 = (Button)view.findViewById(R.id.btn1);
            mBtn2 = (Button)view.findViewById(R.id.btn2);
            mBtn3 = (Button)view.findViewById(R.id.btn3);
            mBtn4 = (Button)view.findViewById(R.id.btn4);
            mBtn1.setOnClickListener(this);
            mBtn2.setOnClickListener(this);
            mBtn3.setOnClickListener(this);
            mBtn4.setOnClickListener(this);

//            mHttpClient.postData1(INDEX,null,new On);
            mHttpClient.postData(INDEX, null, new AppAjaxCallback.onRecevierDataListener<Index>() {

                @Override
                public void onReceiverData(final List<Index> data, String msg) {
                    if(data== null)
                        return;

                    for(int i=0; i<data.size(); i++){
                        RadioButton rb = (RadioButton) getActivity().getLayoutInflater().inflate(R.layout.view_pager_rb,null);
                        rb.setId(147+i);
                        mRG.addView(rb, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    }

                    mAdapter = new PagerAdapter() {
                        @Override
                        public int getCount() {
                            return data.size();
                        }

                        @Override
                        public boolean isViewFromObject(View view, Object o) {
                            return view == o;
                        }

                        @Override
                        public Object instantiateItem(ViewGroup container, int position) {
                            ImageView view = new ImageView(container.getContext());
                            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            AppContext.displayImage(view,"http://zmnyw.cn/"+ data.get(position).getPath());
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
//                            showToast("You clicked page: " + (position + 1));
                        }
                    });
                    pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            mRG.check(147+position);
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });

                }

                @Override
                public void onReceiverError(String msg) {

                }

                @Override
                public Class<Index> dataTypeClass() {
                    return Index.class;
                }
            });

//            mAdapter = new PagerAdapter() {
//                @Override
//                public int getCount() {
//                    return imgs.length;
//                }
//
//                @Override
//                public boolean isViewFromObject(View view, Object o) {
//                    return view == o;
//                }
//
//                @Override
//                public Object instantiateItem(ViewGroup container, int position) {
//                    ImageView view = new ImageView(container.getContext());
//                    view.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                    AppContext.displayImage(view, imgs[position]);
//                    container.addView(view);
//                    return view;
//                }
//
//                @Override
//                public void destroyItem(ViewGroup container, int position, Object object) {
//                    container.removeView((View) object);
//                }
//            };
//
//            pager.setAdapter(mAdapter);
//            pager.setScrollFactgor(5);
//            pager.setOffscreenPageLimit(3);
//            pager.startAutoScroll(2000);
//            pager.setOnPageClickListener(new AutoScrollViewPager.OnPageClickListener() {
//                @Override
//                public void onPageClick(AutoScrollViewPager autoScrollPager, int position) {
//                    showToast("You clicked page: " + (position + 1));
//                }
//            });

        }else{
            pager.startAutoScroll();
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
                if(AppContext.IS_LOGIN){
                    startActivity(new Intent(getActivity(), RepairAddActivity.class));
                }else{
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), LOGIN_CODE1);
                }
               /* if(AppContext.USER_ROLE_ID == 1||AppContext.USER_ROLE_ID == 2||AppContext.USER_ROLE_ID == 0){
                    startActivity(new Intent(getActivity(), RepairAddActivity.class));
                }else{
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), LOGIN_CODE1);
                }*/

                break;
            case R.id.btn2:
                if(AppContext.IS_LOGIN){
//                    startActivity(new Intent(getActivity(), UserRepairListActivity.class));
                    startActivity(new Intent(getActivity(), UserRepairListSinnersActivity.class));
                }else{
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), LOGIN_CODE1);
                }

                /*if(AppContext.USER_ROLE_ID == 1||AppContext.USER_ROLE_ID == 2||AppContext.USER_ROLE_ID == 0){
                    startActivity(new Intent(getActivity(), UserRepairListActivity.class));
                }else{
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), LOGIN_CODE2);
                }*/

                break;
            case R.id.btn3:
//                startActivity(new Intent(getActivity(), ZiXunDetailsActivity.class));
                startActivity(new Intent(getActivity(), NormalWebViewActivity.class).
                        putExtra(INTENT_ID,"Api/consult").putExtra("TITLE", "业务咨询"));
                http://zmnyw.cn/index.php?s=/Home/Api/consult
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

    public void login(){
        final String name;
        final String pass;
        if(SharePreferencesUtil.getLoginInfo(getActivity())!=null){
            name = SharePreferencesUtil.getLoginInfo(getActivity()).getUsername();
            pass = SharePreferencesUtil.getLoginInfo(getActivity()).getEmail();
        }else{
            return;
        }


        JSONObject jb = new JSONObject();
        try{
            jb.put("username", name);
            jb.put("password", pass);


        }catch (JSONException e){

        }

        mHttpClient.postData1(LOGIN_TEST, jb.toString(), new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                /**
                 * 返回的数据中role_id为角色ID，数据1代表单位主管，
                 * 2代表部门主管，3代表客服，4代表维修人员，0为普通用户
                 */
                SharePreferencesUtil.saveLoginInfo(getActivity(), name, pass);
                SharePreferencesUtil.saveUserInfo(getActivity(), data);
                User user= SharePreferencesUtil.getUserInfo(getActivity());
                AppContext.USER_ROLE_ID = user.getRole_id();
                AppContext.USER_ID = user.getId();
                AppContext.IS_LOGIN = true;

            }

            @Override
            public void onError(String msg) {
                showToast(msg);
            }
        });
    }
}
