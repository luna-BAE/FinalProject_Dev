package com.bit.fn.controller;

import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.fn.model.service.CommentService;
import com.bit.fn.model.service.MemberService;
import com.bit.fn.model.service.MemberinfoService;
import com.bit.fn.model.vo.BoardVo;
import com.bit.fn.model.vo.CommentVo;
import com.bit.fn.model.vo.NoticeVo;
import com.bit.fn.model.vo.PaginationVo;
import com.bit.fn.model.vo.ReservationVo;

@Controller
public class MemberController {

	@Autowired
	private MemberService service;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private MemberinfoService memberinfoService;
	
	String id;
	int admin, master, member;
	String role;
	
	
	// 권한 받아오는 메소드
	public String role(Principal  principal) {
		
		role = principal.getName();
		
		admin=principal.toString().indexOf("ROLE_ADMIN");
		master=principal.toString().indexOf("ROLE_MASTER");
		member=principal.toString().indexOf("ROLE_MEMBER");
		
		if ( member != -1 ) { return role; } else { return role = "NotMember"; }
		
	}
	
	
	
	// 멤버 파트 인트로 화면
	@RequestMapping("/intro")
	public String intro(Model model, Principal  principal) {
		
//		String id = role(principal);
//		
//		if ( id.equals("NotMember") ) { // 멤버가 아닌 경우 인트로 페이지에 전달할 모델 객체 셋팅
//			
//			List<ReservationVo> reservationContent = service.reservationListForIntroNotMember();
//			List<BoardVo> boardContent = service.boardListForIntro();
//			List<NoticeVo> noticeContent = service.noticeListForIntro();
//			
//			model.addAttribute("reservationContent",reservationContent);
//			model.addAttribute("boardContent",boardContent);
//			model.addAttribute("noticeContent",noticeContent);
//			
//		} else { // 멤버인 경우 인트로 페이지에 전달할 모델 객체 셋팅
//			
//			List<ReservationVo> reservationContent = service.reservationListForIntro(memberinfoService.selectOne(id).getMemNum());
//			List<BoardVo> boardContent = service.boardListForIntro();
//			List<NoticeVo> noticeContent = service.noticeListForIntro();
//			
//			model.addAttribute("member",memberinfoService.selectOne(id));
//			model.addAttribute("reservationContent",reservationContent);
//			model.addAttribute("boardContent",boardContent);
//			model.addAttribute("noticeContent",noticeContent);
//			
//		}
		
		//아이디
		String id = principal.getName();
		
		//권한 여부
		int admin=principal.toString().indexOf("ROLE_ADMIN");
		int master=principal.toString().indexOf("ROLE_MASTER");
		int member=principal.toString().indexOf("ROLE_MEMBER");
		
		if( member != -1 ) {
			List<ReservationVo> reservationContent = service.reservationListForIntro(memberinfoService.selectOne(id).getMemNum());
			List<BoardVo> boardContent = service.boardListForIntro();
			List<NoticeVo> noticeContent = service.noticeListForIntro();
			
			model.addAttribute("member",memberinfoService.selectOne(id));
			model.addAttribute("reservationContent",reservationContent);
			model.addAttribute("boardContent",boardContent);
			model.addAttribute("noticeContent",noticeContent);
		} else {
			List<ReservationVo> reservationContent = service.reservationListForIntroNotMember();
			List<BoardVo> boardContent = service.boardListForIntro();
			List<NoticeVo> noticeContent = service.noticeListForIntro();
			
			model.addAttribute("reservationContent",reservationContent);
			model.addAttribute("boardContent",boardContent);
			model.addAttribute("noticeContent",noticeContent);
		}
		
		return "memberIntro";
	}
	
	
	
	// 멤버 파트 게시판 메인화면
	@RequestMapping("/board")
	public String bbs(Model model, HttpServletRequest request, Principal  principal,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "countPerPage", required = false, defaultValue = "10") int countPerPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "7") int pageSize) {
		
		//아이디
		String id = principal.getName();
		
		//권한 여부
		int admin=principal.toString().indexOf("ROLE_ADMIN");
		int master=principal.toString().indexOf("ROLE_MASTER");
		int member=principal.toString().indexOf("ROLE_MEMBER");
		
		if( member != -1 ) {
			model.addAttribute("member",memberinfoService.selectOne(id));
		} 
		
		// 페이징 처리를 위한 셋팅
		int listCount = service.countBoardList();
        PaginationVo pagination = new PaginationVo(currentPage, countPerPage, pageSize);
        pagination.setTotalRecordCount(listCount);
        pagination.calculation();
		
		// 게시판에 보여줄 게시글 불러오기
		List<PaginationVo> boardList = service.memberBoardPaginationList(pagination);
		List<NoticeVo> selectNotice = service.selectNoticeList();
		
		// 페이징 값 보내기
		model.addAttribute("pagination", pagination);
		
		// 모델 객체에 리스트 담아서 뷰로 전달
		model.addAttribute("boardList", boardList);
		model.addAttribute("NoticeList", selectNotice);
		
		// 멤버 정보 뷰로 전달
		model.addAttribute("member",memberinfoService.selectOne(id));
		
		return "memberBoard";
	}
	
	
	
	// 멤버 파트 게시글 디테일
	@RequestMapping(value = "/board/detail", method = RequestMethod.GET)
	public String boardDetail(Model model, Principal  principal, @RequestParam(value = "selectNum") int selectNum) {
		
//		String id = role(principal);
//		model.addAttribute("member",memberinfoService.selectOne(id));
//		
//		BoardVo detail = service.selectOneContent(selectNum);
//		model.addAttribute("detail", detail);
//		
//		return "memberBoardDetail";
		
		//아이디
		String id = principal.getName();
		
		//권한 여부
		int admin=principal.toString().indexOf("ROLE_ADMIN");
		int master=principal.toString().indexOf("ROLE_MASTER");
		int member=principal.toString().indexOf("ROLE_MEMBER");
		
		//여기서 중점! 권한 여부에 따라 불러오는 테이블 값을 다르게 줄 수 있다!
		if( member != -1 ) {
			model.addAttribute("member",memberinfoService.selectOne(id));
		}
		
		BoardVo detail = service.selectOneContent(selectNum);
		
		model.addAttribute("detail", detail);
		
		return "memberBoardDetail";
	}
	
	
	
	// 멤버 파트 게시판 글쓰기
	@RequestMapping("/board/write")
	public String boardWrite(Model model, Principal  principal) {

//		if ( id.equals("NotMember") ) {
//			
//			return "redirect:/intro";
//			
//		} else {
//			
//			String id = role(principal);
//			model.addAttribute("member",memberinfoService.selectOne(id));
//			
//			return "memberBoardWrite";
//			
//		}
		
		/* member가 아닐 경우 return값은 뷰에 뿌릴 메시지이고,
		 * member일 경우 해당 문자열로 파싱된 페이지로 이동해야 하는데
		 * 두개의 리턴값 타입이 다른 경우 어떻게 해야 할까? 
		*/
		
		//아이디
		String id = principal.getName();
		
		//권한 여부
		int admin=principal.toString().indexOf("ROLE_ADMIN");
		int master=principal.toString().indexOf("ROLE_MASTER");
		int member=principal.toString().indexOf("ROLE_MEMBER");
		
		if( member != -1 ) {
			model.addAttribute("member",memberinfoService.selectOne(id));
		} else {
			return "redirect:/intro";
		}
		
		return "memberBoardWrite";
		
	}
	
	
	
	// 멤버 파트 게시판 글쓰기 저장
	@RequestMapping(value = "/board/save", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> savePost(Model model, Principal  principal, HttpServletRequest request) {
		
		//아이디
		String id = principal.getName();
		
		//권한 여부
		int admin=principal.toString().indexOf("ROLE_ADMIN");
		int master=principal.toString().indexOf("ROLE_MASTER");
		int member=principal.toString().indexOf("ROLE_MEMBER");
		
		//여기서 중점! 권한 여부에 따라 불러오는 테이블 값을 다르게 줄 수 있다!
		if( member != -1 ) {
			model.addAttribute("member",memberinfoService.selectOne(id));
		} 
		
		int memNum = memberinfoService.selectOne(id).getMemNum();
		
		// 객체 생성 후 파라미터 저장
		BoardVo post = new BoardVo();
		post.setMemNum(memNum);
		post.setWriter(memberinfoService.selectOne(id).getMemName());
		post.setCompany(memberinfoService.searchCompanyName(memNum));
		post.setTitle(request.getParameter("title"));
		post.setContent(request.getParameter("content"));
		
		// 게시글 저장 쿼리 실행
		int savePostResult = service.savePost(post);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", savePostResult);
		
		return result;

	}
	
	
	
	// 멤버 파트 게시글 삭제
	@RequestMapping(value = "/board/detail/delete")
	@ResponseBody
	public void deletePost(@RequestParam(value = "num") int num) {
		
		service.deletePost(num);
		
	}
	
	
	
	// 멤버 파트 게시글 수정
	@RequestMapping(value = "/board/detail/modify")
	@ResponseBody
	public ResponseEntity updatePost(BoardVo modify) {
		
		// 항상 조회 먼저 해야지
		
		int result = service.updatePost(modify);
		
		return ResponseEntity.ok().build().ok(result);
	}
	
	
	
	// 멤버 파트 게시판 댓글 리스트
	@RequestMapping(value = "/board/detail/comment")
	@ResponseBody
    public ResponseEntity allComment(Model model, Principal  principal, @RequestParam(value = "num") int num) {
        
		//아이디
		String id = principal.getName();
		
		//권한 여부
		int admin=principal.toString().indexOf("ROLE_ADMIN");
		int master=principal.toString().indexOf("ROLE_MASTER");
		int member=principal.toString().indexOf("ROLE_MEMBER");
		
		if( member != -1 ) { model.addAttribute("member",memberinfoService.selectOne(id)); }
		
		// 데이터 담을 객체 생성
		List<Map<String, String>> dataList = new ArrayList<Map<String,String>>();
		java.util.Date from = new java.util.Date();
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		// 모든 코멘트 출력
		List<CommentVo> comment = commentService.allComment(num);
		
		for ( CommentVo content : comment ) {
			Map<String, String> data = new HashMap<String, String>();
			data.put("commentNum", Integer.toString(content.getCommentNum()));
			data.put("num", Integer.toString(content.getNum()));
			data.put("commentWriter", content.getCommentWriter());
			data.put("writerNum", Integer.toString(content.getWriterNum()));
			data.put("commentContent", content.getCommentContent());
			data.put("commentDate", transFormat.format(content.getCommentDate()));
			dataList.add(data);
		}
		
		// 담겨진 리스트를 뷰로 전달
		Map<String, Object> commentData = new HashMap<String, Object>();
		commentData.put("commetData", dataList);
		
		return ResponseEntity.ok().build().ok(commentData);
    }
	
	
	
	// 멤버 파트 게시판 댓글 작성
	@RequestMapping(value = "/board/insertComment")
	@ResponseBody
	public Map<String, Object> insertComment(Model model, Principal  principal, CommentVo content) {
		
		//아이디
		String id = principal.getName();
		
		//권한 여부
		int admin=principal.toString().indexOf("ROLE_ADMIN");
		int master=principal.toString().indexOf("ROLE_MASTER");
		int member=principal.toString().indexOf("ROLE_MEMBER");
		
		if( member != -1 ) { model.addAttribute("member",memberinfoService.selectOne(id)); } 
		
		CommentVo comment = new CommentVo();
		comment.setCommentNum(commentService.searchMaxCommentNumber(content.getNum())+1);
		comment.setNum(content.getNum());
		comment.setWriterNum(memberinfoService.selectOne(id).getMemNum());
		comment.setCommentWriter(memberinfoService.selectOne(id).getMemName());
		comment.setCommentContent(content.getCommentContent());
		int insertResult = commentService.insertComment(comment);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("insertResult", insertResult);
		
		return result;
	}
	
	
	
	// 멤버 파트 게시판 댓글 수정
	@RequestMapping(value = "/board/updateComment")
	@ResponseBody
	public Map<String, Object> updateComment(Model model, Principal  principal, @RequestParam int commentNum, @RequestParam String commentContent) {
		
		//아이디
		String id = principal.getName();
		
		//권한 여부
		int admin=principal.toString().indexOf("ROLE_ADMIN");
		int master=principal.toString().indexOf("ROLE_MASTER");
		int member=principal.toString().indexOf("ROLE_MEMBER");
		
		if( member != -1 ) {
			model.addAttribute("member",memberinfoService.selectOne(id));
		}
		
		// 수정할 댓글 존재 유무 확인
		int searchComment = commentService.checkExistence(commentNum);
		
		if ( searchComment == 1 ) {
			
			CommentVo comment = new CommentVo();
			comment.setCommentNum(commentNum);
			comment.setCommentContent(commentContent);
			int update = commentService.updateComment(comment);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("insertResult", update);
			return result;
			
		} else {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("insertResult", "no comment");
			return result;
		}

	}
	
	
	
	// 멤버 파트 게시판 댓글 삭제
	@RequestMapping(value = "/board/deleteComment/{commentNum}")
	@ResponseBody
	public Map<String, Object> deleteComment(Model model, Principal  principal, @PathVariable int commentNum) {
		
		//아이디
		String id = principal.getName();
		
		//권한 여부
		int admin=principal.toString().indexOf("ROLE_ADMIN");
		int master=principal.toString().indexOf("ROLE_MASTER");
		int member=principal.toString().indexOf("ROLE_MEMBER");
		
		if( member != -1 ) { model.addAttribute("member",memberinfoService.selectOne(id)); }
		
		int delete = commentService.deleteComment(commentNum);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("insertResult", delete);
		
		return result;
	}


	



	// 멤버 파트 공지 게시판 인트로 페이지
	@RequestMapping("/notice")
	public String notice(Model model, HttpServletRequest request,
			@RequestParam(value = "currentPage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "countPerPage", required = false, defaultValue = "10") int countPerPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize) {
		
		int listCount = service.countNoticeList();
        PaginationVo pagination = new PaginationVo(currentPage, countPerPage, pageSize);
        pagination.setTotalRecordCount(listCount);
        pagination.calculation();
		
		// 게시판에 보여줄 공지 게시글 불러오기
		List<NoticeVo> noticeList = service.noticeList(pagination);
		
		// 페이징 값 보내기
		model.addAttribute("pagination", pagination);
		
		// 모델 객체에 리스트 담아서 뷰로 전달
		model.addAttribute("noticeList", noticeList);
		
		return "memberNotice";
	}
	
	
	
	// 멤버 파트 공지게시판 디테일
	@RequestMapping(value = "/notice/detail", method = RequestMethod.GET)
	public String noticeDetail(Model model, @RequestParam(value = "selectNum") int selectNum) {
		
		NoticeVo detail = service.selectOneNotice(selectNum);
		
		model.addAttribute("detail", detail);
		
		return "memberNoticeDetail";
	}
	

	
	// 멤버 파트 내 스케쥴 관리 인트로 페이지
	@RequestMapping("/schedule")
	public String schedule() {

		return "memberSchedule";
	}
	
}