package com.club.bhimclub.bhimclub.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.club.bhimclub.bhimclub.model.ProfileImages;
import com.club.bhimclub.bhimclub.repository.DataRepository;

import java.util.List;

public class ProfileViewModel  extends AndroidViewModel {
    private DataRepository mRepository;
    private LiveData<List<ProfileImages>> prerssonalInfoData;

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        mRepository = new DataRepository(application);
        prerssonalInfoData = mRepository.getAllData();
    }

    public LiveData<ProfileImages> getPrerssonalInfoData(int id) {
        return mRepository.getmPersonalInfo(id);
    }
    public int getCoutn() {
        return mRepository.getmCount();
    }

    public LiveData<List<ProfileImages>> getAlLiveData(){
        return  prerssonalInfoData;
    }
    public void insertPresonalInfo(ProfileImages profileImages){
//        prerssonalInfoData.getValue().

//                add(profileImages);
        mRepository.insertPresonalInfo(profileImages);
    }
}
