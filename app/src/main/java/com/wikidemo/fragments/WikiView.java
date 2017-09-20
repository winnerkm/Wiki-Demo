package com.wikidemo.fragments;

import com.wikidemo.model.WikiResponse;

import java.util.List;

/**
 * Created by winnerkm on 20/09/17.
 */

public interface WikiView {
    void apiResponseData(List<WikiResponse.Page> page);
}
