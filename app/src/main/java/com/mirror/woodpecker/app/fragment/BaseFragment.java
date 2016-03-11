package com.mirror.woodpecker.app.fragment;

import com.mirror.woodpecker.app.model.Constants;
import com.mirror.woodpecker.app.util.AppHttpClient;

import dev.mirror.library.android.fragment.DevBaseFragment;

/**
 * Created by dongqian on 16/1/3.
 */
public class BaseFragment extends DevBaseFragment implements Constants{
    public AppHttpClient mHttpClient = new AppHttpClient();
}
