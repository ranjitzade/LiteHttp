package com.ranjitzade.litehttp.lib.core;

import java.util.List;
import java.util.Map;

/**
 * Created by ranjit
 */
public interface IHeaderListener {
    void onHeader(Map<String, List<String>> headers);
}
