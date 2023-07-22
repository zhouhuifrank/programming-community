package com.frankzhou.community.service;


import com.frankzhou.community.common.base.DeleteDTO;
import com.frankzhou.community.common.base.PageResultDTO;
import com.frankzhou.community.common.base.ResultDTO;
import com.frankzhou.community.model.dto.collection.CollectionArticleQueryDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionAddDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionQueryDTO;
import com.frankzhou.community.model.dto.collection.UserCollectionUpdateDTO;
import com.frankzhou.community.model.vo.ArticleVO;
import com.frankzhou.community.model.vo.UserCollectionVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 用户收藏夹业务逻辑层
 * @date 2023-06-18
 */
public interface UserCollectionService {

    ResultDTO<UserCollectionVO> getCollectionInfo(Long collectionId);

    ResultDTO<Boolean> buildCollection(UserCollectionAddDTO addDTO);

    ResultDTO<Boolean> editCollectionInfo(UserCollectionUpdateDTO updateDTO);

    ResultDTO<Boolean> deleteCollection(DeleteDTO deleteDTO);

    ResultDTO<List<UserCollectionVO>> queryCollectionList(UserCollectionQueryDTO queryDTO);

    PageResultDTO<List<UserCollectionVO>> queryCollectionPage(UserCollectionQueryDTO queryDTO);

    ResultDTO<List<UserCollectionVO>> queryCollectionForPersonal();

    PageResultDTO<List<ArticleVO>> getCollectionArticle(CollectionArticleQueryDTO queryDTO);

    ResultDTO<Boolean> addArticleToCollection(CollectionArticleQueryDTO queryDTO);
}
