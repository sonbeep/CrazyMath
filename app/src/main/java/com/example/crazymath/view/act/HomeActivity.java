package com.example.crazymath.view.act;

import com.example.crazymath.databinding.ActivityHomeBinding;
import com.example.crazymath.view.fragment.SplashFragment;
import com.example.crazymath.viewmodel.CommonVM;

public class HomeActivity extends BaseAct<ActivityHomeBinding, CommonVM> {


    @Override
    public void backToPrevios() {
        onBackPressed();
    }

    @Override
    protected void initView() {
        showFragment(SplashFragment.TAG, null, false);
    }

    @Override
    protected Class<CommonVM> getClassViewModel() {
        return CommonVM.class;
    }

    @Override
    protected ActivityHomeBinding initViewBinding() {
        return ActivityHomeBinding.inflate(getLayoutInflater());
    }
}