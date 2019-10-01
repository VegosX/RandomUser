package com.magnise.random_users.ui;

import androidx.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Constants {

    @IntDef({ViewType.USER_TYPE, ViewType.LOADING_TYPE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
        int USER_TYPE = 1;
        int LOADING_TYPE = 2;
    }
}
