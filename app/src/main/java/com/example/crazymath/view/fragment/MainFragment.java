package com.example.crazymath.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.example.crazymath.CommonUlis;
import com.example.crazymath.R;
import com.example.crazymath.databinding.FragmentMainBinding;
import com.example.crazymath.view.dialog.ReadyDialog;
import com.example.crazymath.viewmodel.CommonVM;
import com.example.crazymath.viewmodel.MainViewModel;

public class MainFragment extends BaseFragment<FragmentMainBinding, MainViewModel>{
    public static final String TAG = MainFragment.class.getName();
    private static final String KEY_BEST = "KEY_BEST";

    @Override
    protected void initView() {
        viewModel.startCountdown();
        viewModel.initExpression();
        viewModel.getExpression().observe(this, s -> {
            if (s == null || s.isEmpty())return;
            binding.tvExpression.setText(s);
        });
        viewModel.getAnsA().observe(this, s -> binding.tvAnserA.setText(String.format("%s", s)));
        viewModel.getAnsB().observe(this, s -> binding.tvAnserB.setText(String.format("%s", s)));
        viewModel.getAnsC().observe(this, s -> binding.tvAnserC.setText(String.format("%s", s)));
        viewModel.getBest().observe(this, s -> binding.tvBest.setText(String.format("%s", s)));
        viewModel.getScore().observe(this, s -> binding.tvScore.setText(String.format("%s", s)));
        viewModel.getTime().observe(this, s -> {
            if (s < 0)return;
            binding.tvTime.setText(String.format("%s", s));
            if (s == 0){
                gameOverUI();
            }
        });
        String bestScore = CommonUlis.getInstance().getPref(KEY_BEST);
        if (bestScore!=null){
            viewModel.getBest().postValue(Integer.parseInt(bestScore));
        }
        binding.tvTime.setOnClickListener(this);
        binding.tvAnserA.setOnClickListener(this);
        binding.tvAnserB.setOnClickListener(this);
        binding.tvAnserC.setOnClickListener(this);

        startGame();

    }

    private void startGame() {
        final ReadyDialog dialog = new ReadyDialog(context, v -> viewModel.startCountdown());
        dialog.show();
    }

    @Override
    protected void clickView(View v) {
        if (v.getId() == R.id.tv_anser_a ||
                v.getId() ==R.id.tv_anser_b ||
                v.getId() == R.id.tv_anser_c){
            boolean rs = viewModel.checkAnwser(((TextView)v).getText().toString());
            if (!rs || binding.tvTime.getText().equals("0")){
                viewModel.gameOver();
            }
        }
    }

    private void gameOverUI() {
        int score = 0;
        int best = 0;
        if (viewModel.getScore().getValue() != null){
            score =  viewModel.getScore().getValue();
        }
        if (viewModel.getBest().getValue() != null){
            best =  viewModel.getBest().getValue();
        }
        if (score > best){
            viewModel.getBest().postValue(score);
            CommonUlis.getInstance().savePref(KEY_BEST, score+"");
        }
        final ReadyDialog dialog = new ReadyDialog(context, v -> viewModel.playAgain());
        dialog.setLoseInfo();
        dialog.show();
    }
    @Override
    protected Class<MainViewModel> getClassViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected FragmentMainBinding initViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentMainBinding.inflate(inflater, container, false);
    }

    @Override
    public void onDestroy() {
        viewModel.getTh().interrupt();
        super.onDestroy();
    }
}
