package com.bit.fn.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.bit.fn.model.vo.MettingRoomFacilitiesVo;


@Repository
@Mapper
public interface MettingRoomFacilitiesMapper {
	public List<MettingRoomFacilitiesVo> selectAll();
}
