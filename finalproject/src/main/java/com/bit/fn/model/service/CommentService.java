package com.bit.fn.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.fn.model.mapper.CommentMapper;
import com.bit.fn.model.vo.CommentVo;

@Service
public class CommentService {

	@Autowired
	CommentMapper commentMapper;
	
	public int countComment() {
		return commentMapper.countComment();
	}
	
	public List<CommentVo> allComment(int num) {
		return commentMapper.allComment(num);
	}
	
	public Integer searchMaxCommentNumber(int num) {
		return commentMapper.searchMaxCommentNumber(num);
	}
	
	public int checkExistence(int commentNum) {
		return commentMapper.checkExistence(commentNum);
	}
	
	public Integer insertComment(CommentVo comment) {
		return commentMapper.insertComment(comment);
	}
	
	public int updateComment(CommentVo comment) {
		return commentMapper.updateComment(comment);
	}
	
	public int deleteComment(int commentNum) {
		return commentMapper.deleteComment(commentNum);
	}
}
