<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>MEMBER : BOARD</title>
<%@ include file="template/memberNavBar.jspf" %>
<%@ include file="template/cssForMember.jspf" %>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link href="/webjars/bootstrap/4.6.0-1/css/bootstrap.min.css" rel="stylesheet">
<script src="/webjars/bootstrap/4.6.0-1/js/bootstrap.min.js"></script>

 <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
 <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
 <%@ include file="template/cssForReservation.jspf" %>

<script>

var csrfToken = $("meta[name='_csrf']").attr("content");
$.ajaxPrefilter(function(options, originalOptions, jqXHR){
   if (options['type'].toLowerCase() === "post" || options['type'].toLowerCase() === "put" || options['type'].toLowerCase() === "delete") {
      jqXHR.setRequestHeader('X-CSRF-TOKEN', csrfToken);
   }
});

$(document).ready(function(){

	commentList(); //페이지 로딩시 댓글 목록 출력 
	
	var memNum = $("#memNum").val();
	var writer = $("#writer").val();
	var modifybtn = document.getElementById("modifybtn");
	var deletebtn = document.getElementById("deletebtn");
	var backbtn = document.getElementById("backbtn");
	var num = $("#boardNum").val();
	
	if ( memNum == writer ) {
		modifybtn.style.display = "block";
		deletebtn.style.display = "block";
	} else {
		modifybtn.style.display = "none";
		deletebtn.style.display = "none";
		backbtn.style.left = "94%";
	}
	
	$('#commentInsertBtn').click(function(){ //댓글 등록 버튼 클릭
	    var content = {
			commentContent : document.getElementById('content').value,
			num : $("#boardNum").val()
		};
	    commentInsert(content); //Insert 함수 호출
	});
	
	$('#deletebtn').click(function() {
		deletePost(num); // delete 함수 호출
	});
	
	$('#modifybtn').click(function() {
		if ( $('#modifybtn').html() == "수정" ) {
			$("#modifybtn").html('등록');
			$("#boardContent").attr("readonly", false);
			$("#title").attr("readonly", false);
		} else if ( $('#modifybtn').html() == "등록" ){
			var modify = {
				num : $("#boardNum").val(),
				title : $("#title").val(),
				content : $("#boardContent").val()
			};
			modifyPost(modify); //update 함수 호출
		}
	});
	
});

// 게시글 삭제
function deletePost(num) {
	$.ajax({
		url : "/board/detail/delete",
		type : 'post',
		data : { num },
		success : function(data) {
			alert("요청하신 게시글 삭제가 정상적으로 처리되었습니다.");
			location.href = "/board";
		}
	});
}

// 게시글 수정
function modifyPost(modify) {
	$.ajax({
		url : "/board/detail/modify",
		type : 'post',
		data : modify,
		dataType : "json",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		success : function(data) {
			alert("요청하신 게시글 수정이 정상적으로 처리되었습니다.");
			location.href = "/board";
		}
	});
}

// 댓글 목록
function commentList(){
    $.ajax({
        url : '/board/detail/comment',
        type : 'get',
        data : "num="+$("#boardNum").val(),
        dataType : "json",
        success : function(data){
            var commentContent ='';
            var dataLength = data.commetData.length;
            var comment = data.commetData;
            
            for (var no = 0; no < dataLength; no++ ) {
            	
            	if ( $("#memNum").val() == comment[no].writerNum ) {
            		commentContent += '<div class="commentArea" style="border-bottom:1px solid lightgray; margin-bottom: 15px;">';
            		commentContent += '	<div class="commentInfo'+comment[no].commentNum+'" style="font-size:14px;">';
	                commentContent += comment[no].commentNum+'   |   '+comment[no].commentWriter+'   |   '+comment[no].commentDate+'	|	';
	                commentContent += '<a class = "commentModifyBtn" onclick="commentUpdate(';
	                commentContent += comment[no].commentNum+',\''+comment[no].commentContent+'\');">수정</a>'+'	|	';
	                commentContent += '<a class = "commentModifyBtn" onclick="commentDelete('+comment[no].commentNum+');"> 삭제</a> </div>';
	                commentContent += '<div class="commentContent'+comment[no].commentNum+'"> <p>    '+comment[no].commentContent +'</p>';
	            	commentContent += '</div></div>';
	            	$(".commentList").html(commentContent);
            	} else {
            		commentContent += '<div class="commentArea" style="border-bottom:1px solid lightgray; margin-bottom: 15px;">';
	                commentContent += '	<div class="commentInfo'+comment[no].commentNum+'" style="font-size:14px;">';
	                commentContent += comment[no].commentNum+'   |   '+comment[no].commentWriter+'   |   '+comment[no].commentDate;
	                commentContent += '<div class="commentContent'+comment[no].commentNum+'"> <p>    '+comment[no].commentContent +'</p>';
	            	commentContent += '</div></div>';
	            	$(".commentList").html(commentContent);
            	}


            }
        	
            
        }
    });
}

//댓글 등록
function commentInsert(content){
    $.ajax({
        url : '/board/insertComment',
        type : 'post',
        data : content,
        dataType : "json",
        success : function(data){
            if(data.insertResult == 1) {
                commentList(); //댓글 작성 후 댓글 목록 reload
                $('[name=content]').val('');
            }
        }
    });
}

//댓글 수정 버튼 클릭
function commentUpdate(commentNum, content){
    var updateContent ='';
    
    updateContent += '<div class="input-group">';
    updateContent += '<input type="text" class="form-control" name="content_'+commentNum+'" value="'+content+'"/>';
    updateContent += '<span class="input-group-btn"><button class="btn btn-default" type="button" onclick="commentUpdateProc('+commentNum+');">수정</button> </span>';
    updateContent += '</div>';
    
    $('.commentContent'+commentNum).html(updateContent);
    
}

//댓글 수정
function commentUpdateProc(commentNum){
    var updateContent = $('[name=content_'+commentNum+']').val();
    
    $.ajax({
        url : '/board/updateComment',
        type : 'post',
        data : {'content' : updateContent, 'commentNum' : commentNum},
        success : function(data){
            if(data == 1) commentList(); //댓글 수정후 목록 출력 
        }
    });
}

//댓글 삭제 
function commentDelete(commentNum){
    $.ajax({
        url : '/board/deleteComment/'+commentNum,
        type : 'post',
        success : function(data){
        	if(data.insertResult == 1) {
        		alert("댓글이 삭제되었습니다.");
                commentList(); //댓글 작성 후 댓글 목록 reload
            } 
        }
    });
}

</script>

<body>

	<div class="content bbs" style = "height : 55%;"><!--content start-->
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<form action="memberBoardContentForm">
					
						<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
						<input type="hidden" id = "memNum" name="memNum" value="${member.memNum}"/>
						<input type="hidden" id = "memName" name="memNname" value="${member.memName}"/>
						
						<table id = "bbsTable" class="table table-bordered table-hover">
							<thead id = "boardDetailContent">
								<tr>
									<th class = "detailHeader">번호</th>
									<td class = "headerContent"><input id = "boardNum" hidden = "hidden" value = "${detail.num }"/>${detail.num }</td>
								</tr>
								<tr>
									<th class = "detailHeader">이름</th>
									<td class = "headerContent"><input id = "writer" hidden = "hidden" value = "${detail.memNum }"/>${detail.writer }</td>
								</tr>
								<tr>
									<th class = "detailHeader">소속</th>
									<td class = "headerContent"><input id = "company" hidden = "hidden" value = "${detail.company }"/>${detail.company }</td>
								</tr>
								<tr>
									<th class = "detailHeader">날짜</th>
									<td class = "headerContent"><input id = "date" hidden = "hidden" value = "${detail.date }"/>${detail.date }</td>
								</tr>
								<tr>
									<th class = "detailHeader">제목</th>
									<td class = "headerContent"><input id = "title" type = "text" class = "boardBorderStyle" readonly = "readonly" value = "${detail.title }"/></td>
								</tr>
								<tr>
									<th style = "vertical-align : top;" class = "detailHeader">내용</th>
									<td id = "textField"><textarea rows="15" cols="63" id = "boardContent" style = "border:none" readonly>${detail.content }</textarea></td>
								</tr>
							</thead>
							
						</table>
					</form>
					
					<div id = "detailbtn">
						<button id = "backbtn" type="button" class="btn btn-default" onclick = "history.back()">뒤로</button>
						<button id = "modifybtn" type="button" class="btn btn-default">수정</button>
						<button id = "deletebtn" type="button" class="btn btn-default">삭제</button>
					</div>
				</div>
				    <!--  댓글  -->
			   <div class="container">
			        <label for="content" id = "commentLable">댓글</label>
			        <form name="commentInsertForm" id = "commentBox">
			            <div class="input-group">
			               <input type="text" class="form-control" id="content" name="content" placeholder="내용을 입력하세요.">
			               <span class="input-group-btn">
			                    <button class="btn btn-default" type="button" id = "commentInsertBtn" name="commentInsertBtn">등록</button>
			               </span>
			              </div>
			        </form>
			    </div>
		    	<div class="container">
        			<div class="commentList"></div>
    			</div>
			</div>
		</div>
	</div><!--centent end-->
</body><!--body end-->
<%@ include file="./template/twoDepthFooter.jspf" %>
</html>
