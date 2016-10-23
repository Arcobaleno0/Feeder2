package com.feeder.android.presenters;

import android.view.View;

import com.feeder.android.mvp.ISubscriptionsView;
import com.feeder.android.mvp.MVPPresenter;
import com.feeder.android.utils.SubscriptionViewObserver;
import com.feeder.domain.DataObserver;
import com.feeder.domain.ResponseState;
import com.feeder.domain.SubscriptionController;
import com.feeder.model.Subscription;

/**
 * @description:
 * @author: Match
 * @date: 8/4/16
 */
public class SubscriptionsPresenter implements MVPPresenter, DataObserver, SubscriptionViewObserver {
    ISubscriptionsView mSubscriptionView;

    public SubscriptionsPresenter(ISubscriptionsView subscriptionsView) {
        mSubscriptionView = subscriptionsView;
        mSubscriptionView.setObserver(this);
    }

    @Override
    public void onCreate() {
        mSubscriptionView.showLoading();
        mSubscriptionView.setDataSource(SubscriptionController.getInstance().getDataSource());
    }

    @Override
    public void onStart() {
        SubscriptionController.getInstance().registerObserver(this);
        SubscriptionController.getInstance().requestUpdate();
    }

    @Override
    public void onStop() {
        SubscriptionController.getInstance().unRegisterObserver(this);
    }

    @Override
    public void onResponse(ResponseState state) {
        switch (state) {
            case SUCCESS:
                mSubscriptionView.hideLoading();
                mSubscriptionView.notifyDataChanged();
                break;
            case NO_CHANGE:
                mSubscriptionView.hideLoading();
                break;
        }
    }

    @Override
    public void onItemClick(View view, Subscription data) {
        mSubscriptionView.showToast(data.getTitle() + " clicked");
    }
}
