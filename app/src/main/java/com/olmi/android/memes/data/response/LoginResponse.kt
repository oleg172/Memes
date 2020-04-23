package com.olmi.android.memes.data.response

import com.olmi.android.memes.data.models.User
import com.olmi.android.memes.data.models.UserInfo
import com.olmi.android.memes.data.response.base.BaseResponse

class LoginResponse(private val accessToken: String, private val userInfo: UserInfo) :
    BaseResponse<User> {

    override fun convert(): User = User(
        token = accessToken,
        id = userInfo.id,
        name = userInfo.username,
        firstName = userInfo.firstName,
        lastName = userInfo.lastName
    )

}
