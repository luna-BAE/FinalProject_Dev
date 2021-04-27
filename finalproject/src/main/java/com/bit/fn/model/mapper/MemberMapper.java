package com.bit.fn.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.bit.fn.model.vo.BoardVo;
import com.bit.fn.model.vo.NoticeVo;
import com.bit.fn.model.vo.ReservationVo;

@Repository
@Mapper
public interface MemberMapper {
	
	// 멤버 페이지 게시판 리스트
	public List<BoardVo> memberBoardList();
	
	// 멤버 페이지 공지사항 리스트
	public List<NoticeVo> noticeList();
	
	// 멤버 페이지 게시판 내 공지사항 리스트
	public List<NoticeVo> selectNoticeList();

	// 회의실 예약 현황 리스트
	public List<ReservationVo> meetingRoomList();
	
	// 회의실 전체 예약 현황 조회
	public int checkReservaion(int roomNum, String useStartTime, String reservationDay);
	
	// 회의실 예약 신청
	public int roomReservationApply(ReservationVo reservaion);
	
	// 나의 예약 현황 조회
	public List<ReservationVo> myReservationList();
	
	// 회의실 예약 취소
	public int cancleReservation(int roomNum, String useStartTime, String reservationDay);
	
}
