package com.bit.fn.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.bit.fn.model.vo.MemberInfoVo;


@Repository
@Mapper
public interface MemberInfoMapper {
	public List<MemberInfoVo> selectAll();
	public MemberInfoVo selectOne(String id);
	public int insertOne(@Param("memName") String memName,@Param("memNickName") String memNickName,@Param("id") String id,@Param("comCode") int comCode,@Param("dept") String dept,@Param("memPhone") String memPhone );
}
