package com.club.bhimclub.bhimclub.viewModel;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.club.bhimclub.bhimclub.MyApplication;
import com.club.bhimclub.bhimclub.model.Login;
import com.club.bhimclub.bhimclub.repository.DataRepository;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {
    private DataRepository mRepository;
    private LiveData<List<Login>> myLogin;

    public LoginViewModel(Application application) {
        super(application);
        mRepository = new DataRepository(application);
        myLogin = mRepository.getMyLogin();
    }

    public LiveData<List<Login>> getMyLogin() {
        return myLogin;
    }

    public void insert(Login login){
        myLogin.getValue().add(login);
        mRepository.insert(login);
    }
}
