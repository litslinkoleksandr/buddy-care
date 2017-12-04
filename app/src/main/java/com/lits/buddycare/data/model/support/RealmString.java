package com.lits.buddycare.data.model.support;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by a.trykashnyi on 25.10.16.
 */

public class RealmString extends RealmObject {

    @PrimaryKey
    private String value;

    public RealmString(){}

    public RealmString(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}

