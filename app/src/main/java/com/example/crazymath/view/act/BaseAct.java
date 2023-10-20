package com.example.crazymath.view.act;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.example.crazymath.R;
import com.example.crazymath.view.OnMainCallBack;
import com.example.crazymath.view.fragment.BaseFragment;

import java.lang.reflect.Constructor;

public abstract class BaseAct<T extends ViewBinding, M extends ViewModel> extends AppCompatActivity implements View.OnClickListener, OnMainCallBack {
    protected T binding;
    private M viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = initViewBinding();
        viewModel = new ViewModelProvider(this).get(getClassViewModel());
        setContentView(binding.getRoot());
        initView();
    }

    protected abstract void initView();

    protected abstract Class<M> getClassViewModel();

    protected abstract T initViewBinding();

    @Override
    public void onClick(View v) {

    }

    protected final void notify(String msg){
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }
    protected final void notify(int msg){
        Toast.makeText(this, msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showFragment(String tag, Object data, boolean isBack) {
        try {
            Class<?> clazz = Class.forName(tag);
            Constructor<?> cons = clazz.getConstructor();
            BaseFragment<?, ?> frg = (BaseFragment<?, ?>) cons.newInstance();
            frg.setData(data);
            frg.setCallBack(this);

            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

            if (isBack){
                trans.addToBackStack(null);
            }
            trans.replace(R.id.ln_main, frg, tag).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
