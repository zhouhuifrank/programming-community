package com.frankzhou.community.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * @author This.FrankZhou
 * @version 1.0
 * @description 帖子控制器 帖子相关接口
 * @date 2023-06-18
 */
@RestController
@RequestMapping("/post")
@Api(tags = {"用户帖子"})
public class PostController {

    /*
    @ApiOperation(value = "分页查询帖子")
    @PostMapping("/list/page")
    public PageResultDTO<List<PostVO>> queryPostByPage(@RequestBody PostQueryDTO queryDTO) {
        return null;
    }

    @ApiOperation(value = "批量查询帖子")
    @PostMapping("/list")
    public PageResultDTO<List<PostVO>> queryPostList(@RequestBody PostQueryDTO queryDTO) {
        return null;
    }

    @ApiOperation(value = "查询帖子明细")
    @GetMapping("/detail/{postId}")
    public ResultDTO<PostDetailVO> queryPostDetail(@PathVariable("postId") Long postId) {
        return null;
    }

    @ApiOperation(value = "发布帖子")
    @PostMapping("/add")
    public ResultDTO<Boolean> addPost(@RequestBody PostAddDTO addDTO) {
        return null;
    }

    @ApiOperation(value = "帖子置顶")
    @PostMapping("/top")
    public ResultDTO<Boolean> topPost(@RequestParam("postId") Long postId) {
        return null;
    }

    @ApiOperation(value = "帖子加精")
    @PostMapping("/essence")
    public ResultDTO<Boolean> essencePost(@RequestParam("postId") Long postId) {
        return null;
    }

    @ApiOperation(value = "编辑帖子")
    @PostMapping("/edit")
    public ResultDTO<Boolean> editPost(@RequestBody PostUpdateDTO updateDTO) {
        return null;
    }

    @ApiOperation(value = "删除帖子")
    @PostMapping("/delete")
    public ResultDTO<Boolean> deletePost(@RequestBody DeleteDTO deleteDTO) {
        return null;
    }
     */


}
