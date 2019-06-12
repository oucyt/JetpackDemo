package com.yt.jetpackdemo;

import androidx.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yt.jetpackdemo.persistence.User;

import java.util.List;

/**
 * description
 *
 * @author tianyu
 * @create 2019.06.11 20:11
 * @since 1.0.0
 */
public class TestAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

//    private List<User> users;

    public TestAdapter(int layoutResId, @Nullable List<User> data) {
        super(layoutResId, data);
    }

    //
//    public TestAdapter(List<User> users) {
//        this.users = users;
//    }
//
    @Override
    protected void convert(BaseViewHolder helper, User item) {

        helper.setText(android.R.id.text1, item.getId() + "")
                .setText(android.R.id.text2, item.getUserName());
    }

}