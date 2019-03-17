package com.club.bhimclub.bhimclub.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.club.bhimclub.bhimclub.MyRoomDatabase;
import com.club.bhimclub.bhimclub.dao.DataDao;
import com.club.bhimclub.bhimclub.model.Login;
import com.club.bhimclub.bhimclub.model.ProfileImages;

import java.util.List;

public class DataRepository {
    private DataDao mDataDao;
    private LiveData<List<Login>> myLogin;
    private LiveData<List<ProfileImages>> mPersonalInfo;

    public DataRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabaseData(application);
        mDataDao = db.dataDao();
//        myLogin = mDataDao.getLogin();
        mPersonalInfo = mDataDao.getPersonalAllInfo();
    }


    public LiveData<List<Login>> getMyLogin() {
        return myLogin;
    }
    public LiveData<List<ProfileImages>> getAllData() {
        return mPersonalInfo;
    }
    public LiveData<ProfileImages> getmPersonalInfo(int id) {
            return mDataDao.getPersonalInfo(id);
        }
     public int getmCount() {
            return mDataDao.getCount();
        }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert(Login login) {
        new insertAsyncTask(mDataDao).execute(login);
    }

    public void insertPresonalInfo(ProfileImages profileImages) {
        new insertPresonalInfoAsyncTask(mDataDao).execute(profileImages);
    }

    private static class insertAsyncTask extends AsyncTask<Login, Void, Void> {

        private DataDao mAsyncTaskDao;

        insertAsyncTask(DataDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Login... params) {
//            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class insertPresonalInfoAsyncTask extends AsyncTask<ProfileImages, Void, Void> {

        private DataDao mAsyncTaskDao;

        insertPresonalInfoAsyncTask(DataDao dao) {
            mAsyncTaskDao = dao;
        }



        @Override
        protected Void doInBackground(ProfileImages... params) {
            mAsyncTaskDao.insertPresonal(params[0]);
            return null;
        }
    }
}
