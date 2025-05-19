package com.timeless.saya.feature.auth.domain;

import com.timeless.saya.core.data.Result;
import com.timeless.saya.feature.auth.data.model.LoggedInUser;

public interface LoginCallback {
    void onLoginComplete(Result<LoggedInUser> result);
}
