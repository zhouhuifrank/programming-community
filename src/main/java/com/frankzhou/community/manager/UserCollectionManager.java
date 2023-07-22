package com.frankzhou.community.manager;

import com.frankzhou.community.common.base.DeleteDTO;
import com.frankzhou.community.common.base.PageResultDTO;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionAddDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionQueryDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionUpdateDTO;
import com.frankzhou.community.model.dto.datadict.DataDictAddDTO;
import com.frankzhou.community.model.dto.datadict.DataDictQueryDTO;
import com.frankzhou.community.model.dto.datadict.DataDictUpdateDTO;
import com.frankzhou.community.model.entity.UserCollection;
import com.frankzhou.community.model.vo.DataDictVO;
import com.frankzhou.community.model.vo.UserCollectionVO;

import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description
 * @date 2023-06-24
 */
public interface UserCollectionManager {

    ResultDTO<UserCollectionVO> getById(Long id);

    ResultDTO<Boolean> updateById(UserCollectionUpdateDTO updateDTO);

    ResultDTO<Boolean> deleteById(DeleteDTO deleteDTO);

    ResultDTO<Boolean> insertOne(UserCollectionAddDTO addDTO);

    ResultDTO<Boolean> batchDelete(DeleteDTO deleteDTO);

    ResultDTO<List<UserCollectionVO>> getListByCond(UserCollectionQueryDTO queryDTO);

    PageResultDTO<List<UserCollectionVO>> getPageListByCond(UserCollectionQueryDTO queryDTO);

    List<UserCollection> getCollectionByUserId(Long userId);

    UserCollection getCollectionByUnique(Long collectionId);

    boolean checkUserCollectionDuplicate(Long userId,String collectionName);
}
