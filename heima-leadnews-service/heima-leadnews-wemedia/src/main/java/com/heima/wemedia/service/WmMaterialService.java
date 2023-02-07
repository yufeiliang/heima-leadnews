package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojo.WmMaterial;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService extends IService<WmMaterial> {
    ResponseResult uploadPicture(MultipartFile multipartFile);

    ResponseResult listWM(WmMaterialDto wmMaterialDto);

    ResponseResult collect(Long id);

    ResponseResult cancelCollect(Long id);
}
