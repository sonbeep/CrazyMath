package com.example.crazymath.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.example.crazymath.view.OnMainCallBack;

public abstract class BaseFragment<B extends ViewBinding, V extends ViewModel> extends Fragment implements View.OnClickListener {
    protected Context context;
    protected B binding;
    protected V viewModel;
    protected OnMainCallBack callBack;
    protected Object data;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = initViewBinding(inflater,container);
        viewModel = new ViewModelProvider(this).get(getClassViewModel());
        initView();
        return binding.getRoot();
    }



    @Override
    public final void onClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(context, androidx.appcompat.R.anim.abc_fade_in));
        clickView(v);
    }

    protected void clickView(View v) {
    }
    protected abstract void initView();

    protected abstract Class<V> getClassViewModel();

    protected abstract B initViewBinding(LayoutInflater inflater, ViewGroup container);

    public final void setCallBack(OnMainCallBack callBack) {
        this.callBack = callBack;
    }

    public void setData(Object data) {
        this.data = data;
    }

    protected final void notify(String msg){
        Toast.makeText(context, msg,Toast.LENGTH_SHORT).show();
    }
    protected final void notify(int msg){
        Toast.makeText(context, msg,Toast.LENGTH_SHORT).show();
    }

}
