package com.wikidemo.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wikidemo.model.WikiResponse;
import com.wikidemo.network.ApiClient;

/**
 * Created by winnerkm on 20/09/17.
 */

public class WikiPresenterImpl implements WikiPresenter {

    WikiView mView;
    Context mContext;

    public WikiPresenterImpl(WikiView wikiView, Context context) {
        this.mView = wikiView;
        this.mContext = context;
    }

    @Override
    public void callWikiAPI(String query) {
        ApiClient.getInstance(mContext).apiCall(query);
    }

    public void onEvent(WikiResponse response) {
        if (response != null && response.getQuery() != null && response.getQuery().getPages() != null) {
            mView.apiResponseData(response.getQuery().getPages());
        }
    }
}
