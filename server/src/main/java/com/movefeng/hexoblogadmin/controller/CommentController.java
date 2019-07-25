package com.movefeng.hexoblogadmin.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.movefeng.hexoblogadmin.service.CommentService;
import com.movefeng.hexoblogadmin.vo.CommentVO;
import com.movefeng.hexoblogadmin.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author z
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 创建评论
     *
     * @param commentVO
     * @return
     */
    @PostMapping("create")
    public Result createComment(@RequestBody CommentVO commentVO) {
        return commentService.createComment(commentVO);
    }

    /**
     * 查询某篇文章下的评论
     *
     * @param articleTitle
     * @return
     */
    @RequestMapping("list")
    public Result list(@RequestParam("articleTitle") String articleTitle) {
        return commentService.queryComment(articleTitle);
    }

    /**
     * 查询所有评论
     *
     * @param pageParam
     * @param searchParam
     * @return
     */
    @RequestMapping("listAll")
    public Result listAll(@RequestParam Map<String, Object> pageParam, @RequestBody(required = false) Map<String, Object> searchParam) {

        int pageNum = Integer.parseInt((String) pageParam.get("pageNum"));
        int pageSize = Integer.parseInt((String) pageParam.get("pageSize"));

        PageHelper.startPage(pageNum, pageSize);
        if (searchParam == null) {
            searchParam = new HashMap<>();
        }
        Page<CommentVO> commentVOS = commentService.queryAllComment(searchParam);
        PageInfo<CommentVO> pageInfo = new PageInfo<>(commentVOS);
        return new Result<>(Result.Code.SUCCESS, pageInfo);
    }

    /**
     * 根据id删除一条评论
     *
     * @param id
     * @return
     */
    @PostMapping("delete")
    public Result delete(@RequestParam("id") String id) {
        return commentService.deleteById(id);
    }

    /**
     * 批量删除评论
     *
     * @param map
     * @return
     */
    @PostMapping("deleteBatch")
    public Result deleteBatch(@RequestBody Map<String, Object> map) {

        return commentService.deleteBatchById(map);
    }

    /**
     * 审核评论
     *
     * @param id
     * @param audit
     * @return
     */
    @PostMapping("audit")
    public Result audit(@RequestParam("id") String id, @RequestParam("audit") int audit) {
        return commentService.audit(id, audit);
    }

    /**
     * 审核多条评论
     *
     * @param param
     * @return
     */
    @PostMapping("auditBatch")
    public Result auditBatch(@RequestBody List<Map<String, Object>> param) {

        return commentService.auditBatch(param);
    }


}
