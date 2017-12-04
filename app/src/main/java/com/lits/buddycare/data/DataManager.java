package com.lits.buddycare.data;

import android.util.Log;

import com.lits.buddycare.data.model.User;
import com.lits.buddycare.data.network.ApiRestClient;

import io.realm.Realm;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by oleksandrtrykashnyi on 04.12.2017.
 */

public class DataManager {

    private static DataManager instance = new DataManager();

    public static DataManager getInstance() {
        return instance;
    }

    public void loadWishes() {
        ApiRestClient.Creator.getInstance().getWishes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(wishes -> {
                    try (Realm realm = Realm.getDefaultInstance()) {
                        realm.beginTransaction();
                        realm.copyToRealmOrUpdate(wishes);
                        realm.commitTransaction();
                    }
                }, throwable -> Log.e(DataManager.class.getSimpleName(), throwable.getMessage()));
    }

    public Observable<User> loadUser(int userId) {
        return ApiRestClient.Creator.getInstance().getUser(userId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
