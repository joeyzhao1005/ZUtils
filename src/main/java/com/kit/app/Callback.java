package com.kit.app;

import androidx.annotation.Nullable;

/**
 * @author Zhao
 */
public interface Callback<T> {

    /**
     * 回调
     *
     * @param callEntity 值
     */
    void call(@Nullable T callEntity);
}
