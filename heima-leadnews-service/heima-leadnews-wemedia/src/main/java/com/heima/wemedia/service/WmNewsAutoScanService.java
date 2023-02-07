package com.heima.wemedia.service;

import com.heima.model.wemedia.pojo.WmNews;

public interface WmNewsAutoScanService {
    /**
     * 自媒体文章审核
     *
     * @param wmNews
     */
    public void autoScanWmNews(Integer id) throws Exception;
}
