package com.heima.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WmNewsMaterialMapper extends BaseMapper<com.heima.model.wemedia.pojo.WmNewsMaterial> {
    void saveRelations(@Param("materialIds") List<Integer> materialIds, @Param("newsId") Integer newsId, @Param("type")Short type);
}