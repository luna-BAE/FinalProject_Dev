package com.bit.fn.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.bit.fn.model.vo.CommentVo;

@Repository
@Mapper
public interface CommentMapper {
	
	public int countComment();
	
	public List<CommentVo> allComment(int num);
	
	public Integer searchMaxCommentNumber(int num);
	
	public int checkExistence(int commentNum);
	
	public Integer insertComment(CommentVo comment);
	
	public int updateComment(CommentVo comment);
	
	public int deleteComment(int commentNum);
	
}