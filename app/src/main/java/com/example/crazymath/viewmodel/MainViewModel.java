package com.example.crazymath.viewmodel;

import android.os.SystemClock;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainViewModel extends BaseViewModel{
    private final MutableLiveData<Integer> ansA = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> ansB = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> ansC = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> time = new MutableLiveData<>(10);
    private final MutableLiveData<Integer> score = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> best = new MutableLiveData<>(0);
    private final MutableLiveData<String> expression = new MutableLiveData<>("");
    private int answer;
    private Thread th;


    public MutableLiveData<Integer> getAnsA() {
        return ansA;
    }

    public MutableLiveData<Integer> getAnsB() {
        return ansB;
    }

    public MutableLiveData<Integer> getAnsC() {
        return ansC;
    }

    public MutableLiveData<Integer> getTime() {
        return time;
    }

    public MutableLiveData<Integer> getScore() {
        return score;
    }

    public MutableLiveData<Integer> getBest() {
        return best;
    }

    public MutableLiveData<String> getExpression() {
        return expression;
    }

    public void initExpression(){
        Random rd = new Random();
        int a = rd.nextInt(9)+1;
        int b = rd.nextInt(9)+1;
        int sign = rd.nextInt(10);
        expression.postValue(String.format("%s%s%s=?", a, sign>5 ? "+":"-", b));
        answer =sign>5? a+b: a-b;
        int wrong1 = answer + rd.nextInt(3)+1;
        int wrong2 = answer - (rd.nextInt(3)+1);

        List<Integer> list = new ArrayList<>(Arrays.asList(answer, wrong1, wrong2));
        Collections.shuffle(list);
        ansA.postValue(list.get(0));
        ansB.postValue(list.get(1));
        ansC.postValue(list.get(2));
        time.setValue(10);


    }

    public boolean checkAnwser(String textAns) {
        int intAns = Integer.parseInt(textAns);
        if (intAns ==this.answer){
            if (score.getValue() == null) return false;
            score.postValue(score.getValue()+1);
            initExpression();
            return true;
        }
        return false;
    }

    public void startCountdown() {
        if (th == null || !th.isAlive()){
            th = new Thread(() -> {
                while (time.getValue() != 0 && time.getValue()>0){
                    try {
                        SystemClock.sleep(1000);
                        time.postValue(time.getValue()-1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
                th = null;
            });
            th.start();
        }
    }

    public Thread getTh() {
        return th;
    }

    public void gameOver() {
        th.interrupt();
        time.postValue(0);
    }

    public void playAgain() {
        score.postValue(0);
        initExpression();
        startCountdown();
    }
}
