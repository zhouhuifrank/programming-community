package com.frankzhou.community.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpStatus;
import com.frankzhou.community.common.base.*;
import com.frankzhou.community.common.constant.CollectionConstants;
import com.frankzhou.community.common.enums.IdGeneratorType;
import com.frankzhou.community.common.util.ListUtil;
import com.frankzhou.community.common.util.id.IdGenerator;
import com.frankzhou.community.manager.UserCollectionManager;
import com.frankzhou.community.manager.UserManager;
import com.frankzhou.community.model.dto.collection.CollectionArticleQueryDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionAddDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionQueryDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionUpdateDTO;
import com.frankzhou.community.model.entity.UserCollection;
import com.frankzhou.community.model.vo.ArticleVO;
import com.frankzhou.community.model.vo.UserCollectionVO;
import com.frankzhou.community.service.LockService;
import com.frankzhou.community.service.UserCollectionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 用户收藏夹业务逻辑层实现类
 * @date 2023-06-18
 */
@Slf4j
@Service
public class UserCollectionServiceImpl implements UserCollectionService {

    @Resource
    private UserManager userManager;

    @Resource
    private UserCollectionManager collectionManager;

    @Resource
    private LockService lockService;

    @Resource
    private Map<String, IdGenerator> idGeneratorMap;

    @Override
    public ResultDTO<UserCollectionVO> getCollectionInfo(Long collectionId) {
        if (ObjectUtil.isNull(collectionId)) {
            return ResultDTO.getErrorResult(new ResultCodeDTO(91130,"collection id is null","收藏夹编号不能为空"));
        }

        UserCollection collection = collectionManager.getCollectionByUnique(collectionId);
        if (ObjectUtil.isNull(collection)) {
            return ResultDTO.getErrorResult(new ResultCodeDTO(91131,"collection is not exist","收藏夹不存在"));
        }

        UserCollectionVO collectionVO = new UserCollectionVO();
        BeanUtil.copyProperties(collection,collectionVO);
        return ResultDTO.getSuccessResult(collectionVO);
    }

    @Override
    public ResultDTO<Boolean> buildCollection(UserCollectionAddDTO addDTO) {
        if (ObjectUtil.isNull(addDTO)) {
            return ResultDTO.getResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        String collectionName = addDTO.getCollectionName();
        if (StringUtils.isBlank(collectionName)) {
            return ResultDTO.getResult(new ResultCodeDTO(91132,"collection name is null","收藏夹名称不能为空"));
        }

        // 从token中获取用户id
        Long userId = 1L;

        boolean isDuplicated = collectionManager.checkUserCollectionDuplicate(userId, collectionName);
        if (isDuplicated) {
            return ResultDTO.getResult(new ResultCodeDTO(91133,"collection is duplicated","收藏夹名称不能重复"));
        }

        Long collectionId = idGeneratorMap.get(IdGeneratorType.SNOW_FLAKE.getCode()).nextId();
        addDTO.setUserId(userId);
        addDTO.setCollectionId(collectionId);
        addDTO.setIsDefault(2);
        addDTO.setSubscribeCount(0);
        addDTO.setArticleCount(0);

        return collectionManager.insertOne(addDTO);
    }

    @Override
    public ResultDTO<Boolean> editCollectionInfo(UserCollectionUpdateDTO updateDTO) {
        if (ObjectUtil.isNull(updateDTO)) {
            return ResultDTO.getResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        Long collectionId = updateDTO.getCollectionId();
        String collectionName = updateDTO.getCollectionName();
        if (ObjectUtil.isNull(collectionId)) {
            return ResultDTO.getResult(new ResultCodeDTO(91133,"collection id is null","收藏夹编号不能为空"));
        }

        if (StringUtils.isBlank(collectionName)) {
            return ResultDTO.getResult(new ResultCodeDTO(91132,"collection name is null","收藏夹名称不能为空"));
        }

        // 如果收藏夹已被人订阅了，那么就不允许将查看权限更改为
        UserCollection collectionByUnique = collectionManager.getCollectionByUnique(collectionId);
        if (ObjectUtil.isNull(collectionByUnique)) {
            return ResultDTO.getResult(new ResultCodeDTO(91134,"collection is not existed","收藏夹不存在"));
        }

        Integer permission = updateDTO.getPermission();
        Integer subscribeCount = collectionByUnique.getSubscribeCount();
        if (subscribeCount > 0 && permission.equals(CollectionConstants.CollectionPermissionEnum.PRIVATE.getCode())) {
            return ResultDTO.getResult(new ResultCodeDTO(91135,"collection has subscribed","收藏夹已被订阅，不能改为隐私"));
        }

        return collectionManager.updateById(updateDTO);
    }

    @Override
    public ResultDTO<Boolean> deleteCollection(DeleteDTO deleteDTO) {
        if (ObjectUtil.isNull(deleteDTO.getId())) {
            return ResultDTO.getResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        // 用户一定会有一个默认收藏夹，不能够删除默认收藏夹
        Long id = deleteDTO.getId();
        ResultDTO<UserCollectionVO> result = collectionManager.getById(id);
        if (result.getResultCode() != HttpStatus.HTTP_OK) {
            return ResultDTO.getResult(ResultCodeConstant.DB_QUERY_NO_DATA);
        }

        UserCollectionVO collectionVO = result.getData();
        Integer isDefault = collectionVO.getIsDefault();
        if (isDefault.equals(CollectionConstants.IsDefaultEnum.DEFAULT.getCode())) {
            return ResultDTO.getResult(new ResultCodeDTO(91132,"default collection can't delete","默认收藏夹不能删除"));
        }

        return collectionManager.deleteById(deleteDTO);
    }

    @Override
    public ResultDTO<List<UserCollectionVO>> queryCollectionList(UserCollectionQueryDTO queryDTO) {
        return collectionManager.getListByCond(queryDTO);
    }

    @Override
    public PageResultDTO<List<UserCollectionVO>> queryCollectionPage(UserCollectionQueryDTO queryDTO) {
        return collectionManager.getPageListByCond(queryDTO);
    }

    @Override
    public ResultDTO<List<UserCollectionVO>> queryCollectionForPersonal() {
        // 从token获取用户Id
        Long userId = 1L;
        // 用户一定会有一个默认收藏夹
        List<UserCollection> collectionList = collectionManager.getCollectionByUserId(userId);
        List<UserCollectionVO> collectionVOList = new ArrayList<>();
        collectionVOList = ListUtil.listConvert(collectionList, UserCollectionVO.class);
        return ResultDTO.getSuccessResult(collectionVOList);
    }

    @Override
    public PageResultDTO<List<ArticleVO>> getCollectionArticle(CollectionArticleQueryDTO queryDTO) {
        if (ObjectUtil.isNull(queryDTO)) {
            return PageResultDTO.getErrorPageResult(ResultCodeConstant.REQUEST_PARAM_ERROR);
        }

        Long collectionId = queryDTO.getCollectionId();
        if (ObjectUtil.isNull(collectionId)) {
            return PageResultDTO.getErrorPageResult(new ResultCodeDTO(91132,"collection id is null","收藏夹编号不能为空"));
        }

        // 分页查询与collectionId关联的文章
        return null;
    }

    @Override
    public ResultDTO<Boolean> addArticleToCollection(CollectionArticleQueryDTO queryDTO) {
        return null;
    }
}




