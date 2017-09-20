package com.wikidemo.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.wikidemo.R;
import com.wikidemo.adapters.WikiAdapter;
import com.wikidemo.model.WikiResponse;
import com.wikidemo.utils.Utilities;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by winnerkm on 19/09/17.
 */

public class WikiFragment extends Fragment implements WikiView {

    @Bind(R.id.auto_complete)
    AutoCompleteTextView autocomplete;

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private WikiPresenter mPresenter;
    private WikiAdapter mAdapter;
    private List<WikiResponse.Page> mPageList = new ArrayList<>();


    /* I am locally storing all the keyword you searched we can also extend this list and use Data-Base for that */
    private static List<String> POPULAR_SEARCH = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.wiki_fragment, container, false);
        ButterKnife.bind(this, view);
        Utilities.hideKeyboard(getActivity());
        setSearchAdapter();
        showAutoCompleteList(POPULAR_SEARCH);

        mPresenter = new WikiPresenterImpl(this, getActivity());
        return view;
    }

    private void setSearchAdapter() {

        autocomplete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                autocomplete.showDropDown();
                Utilities.hideKeyboard(getActivity());
                return false;
            }
        });

        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                callApi(POPULAR_SEARCH.get(position));
            }
        });

        autocomplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (autocomplete.isPerformingCompletion()) {
                    // An item has been selected from the list. Ignore.
                    return;
                }
                autocomplete.dismissDropDown();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (autocomplete.getText().toString().isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    showAutoCompleteList(POPULAR_SEARCH);
                } else {
                    if (s.length() >= 4) {

                        /* If element is already in list don't add it */
                        POPULAR_SEARCH.add(s.toString());
                    }

                    callApi(s.toString());
                }
            }
        });
    }

    private void showAutoCompleteList(List<String> popularSearch) {
        Collections.reverse(popularSearch);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_dropdown_item_1line, popularSearch.toArray(new String[0]));
        autocomplete.setAdapter(adapter);

        Collections.reverse(popularSearch);
    }

    private void callApi(String search) {
        if (Utilities.isNetworkAvailable(getActivity()))
            mPresenter.callWikiAPI(search);
        else
            Utilities.showAlert("CONNECTION ERROR", "", getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isRegistered(mPresenter))
            EventBus.getDefault().register(mPresenter);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (EventBus.getDefault().isRegistered(mPresenter))
            EventBus.getDefault().unregister(mPresenter);
    }

    public void setData(List<WikiResponse.Page> pages) {
        mPageList = pages;
        mAdapter = new WikiAdapter(getActivity(), mPageList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void apiResponseData(List<WikiResponse.Page> page) {
        recyclerView.setVisibility(View.VISIBLE);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        setData(page);
    }
}
