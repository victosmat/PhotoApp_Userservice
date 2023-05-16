package com.example.photo_app.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.photo_app.fragment.FragmentHome;
import com.example.photo_app.fragment.FragmentNotification;
import com.example.photo_app.fragment.FragmentProfile;
import com.example.photo_app.fragment.FragmentSearch;
import com.example.photo_app.fragment.FragmentUpload;
import com.example.photo_app.fragment.ratingComment.FragmentViewComments;
import com.example.photo_app.fragment.ratingComment.FragmentViewRatings;
import com.example.photo_app.model.notification.NotificationModel;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<NotificationModel> mNotifications;

    public List<NotificationModel> getmNotifications() {
        return mNotifications;
    }

    public void setmNotifications(List<NotificationModel> mNotifications) {
        this.mNotifications = mNotifications;
    }

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentHome();
//                return new FragmentViewComments();
            case 1:
                return new FragmentSearch();
//                return new FragmentViewRatings();
            case 2:
                return new FragmentUpload();
            case 3:
                return new FragmentProfile();
            case 4:
                return new FragmentNotification(mNotifications);

        }
        return new FragmentHome();
    }

    @Override
    public int getCount() {
        return 5;
    }
}
