package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.file.service.FileStorageService;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmMaterialDto;
import com.heima.model.wemedia.pojo.WmMaterial;
import com.heima.utils.thread.WmThreadLocalUtil;
import com.heima.wemedia.mapper.WmMaterialMapper;
import com.heima.wemedia.service.WmMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class WmMaterialServiceImpl extends ServiceImpl<WmMaterialMapper, WmMaterial> implements WmMaterialService {
    @Autowired
    private WmMaterialMapper wmMaterialMapper;
    @Autowired
    private FileStorageService fileStorageService;

    @Override
    public ResponseResult uploadPicture(MultipartFile multipartFile) {
        if (multipartFile == null && multipartFile.getSize() == 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        String fileName = UUID.randomUUID() + "." + extension;
        System.out.println(fileName);
        String url = null;
        try {
            url = fileStorageService
                    .uploadImgFile("", fileName, multipartFile.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("上传文件失败");
        }


        WmMaterial wmMaterial = new WmMaterial();
        wmMaterial.setUserId(WmThreadLocalUtil.getUser().getId());
        wmMaterial.setUrl(url);
        wmMaterial.setIsCollection((short) 0);
        wmMaterial.setType((short) 0);
        wmMaterial.setCreatedTime(new Date());

        wmMaterialMapper.insert(wmMaterial);
        return ResponseResult.okResult(wmMaterial);
    }

    @Override
    public ResponseResult listWM(WmMaterialDto wmMaterialDto) {
        wmMaterialDto.checkParam();

        IPage<WmMaterial> page = new Page<WmMaterial>(wmMaterialDto.getPage(), wmMaterialDto.getSize());
        LambdaQueryWrapper<WmMaterial> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (wmMaterialDto.getIsCollection() != null && wmMaterialDto.getIsCollection() == 1) {
            lambdaQueryWrapper.eq(WmMaterial::getIsCollection, wmMaterialDto.getIsCollection());
        }
        Integer id = WmThreadLocalUtil.getUser().getId();

        lambdaQueryWrapper.eq(WmMaterial::getUserId, WmThreadLocalUtil.getUser().getId());

        lambdaQueryWrapper.orderByDesc(WmMaterial::getCreatedTime);

        IPage<WmMaterial> iPage = wmMaterialMapper.selectPage(page, lambdaQueryWrapper);

        PageResponseResult responseResult = new PageResponseResult(wmMaterialDto.getPage(), wmMaterialDto.getSize(), (int) iPage.getTotal());
        responseResult.setData(iPage.getRecords());

        return responseResult;
    }

    @Override
    public ResponseResult collect(Long id) {
        Integer integer = updateCollect(id);
        if (integer > 0) {
            return ResponseResult.okResult(null);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.EXCEL_REPORT_FAIL, "修改失败!");
    }

    private Integer updateCollect(Long id) {
        WmMaterial material = wmMaterialMapper
                .selectOne(Wrappers.<WmMaterial>lambdaQuery().eq(WmMaterial::getId, id));
        if (ObjectUtils.isNotNull(material) && material.getIsCollection() == 0) {
            material.setIsCollection((short) 1);
        } else {
            material.setIsCollection((short) 0);
        }
        int update = wmMaterialMapper.updateById(material);
        return update;
    }

    @Override
    public ResponseResult cancelCollect(Long id) {
        Integer integer = updateCollect(id);
        if (integer > 0) {
            return ResponseResult.okResult(null);
        }
        return ResponseResult.errorResult(AppHttpCodeEnum.EXCEL_REPORT_FAIL, "修改失败!");
    }
}
