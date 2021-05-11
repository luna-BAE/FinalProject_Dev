<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>회원가입</title>
<%@ include file="template/navbar.jspf" %>

<script type="text/javascript">

var csrfToken = $("meta[name='_csrf']").attr("content");
$.ajaxPrefilter(function(options, originalOptions, jqXHR){
	if (options['type'].toLowerCase() === "post") {
		jqXHR.setRequestHeader('X-CSRF-TOKEN', csrfToken);
	}
});

var pattern_num = /[0-9]/;	// 숫자 

var pattern_eng = /[a-zA-Z]/;	// 문자 

var pattern_spc = /[~!@#$%^&*()_+|<>?:{}]/; // 특수문자

var pattern_kor = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/; // 한글체크

//아이디 중복검사 여부
var check=-1;

//아이디 값 변경 여부
var username="";

$(function(){
	//아이디(이메일) 중복 검사 하기 전 이메일 인증번호 전송버튼 및 인증확인 버튼 닉네임 중복검사 버튼 비활성화
	$(".btncheck").prop('disabled',"true");
	
	
	//비동기 통신 (아이디 중복검사)
	$(".userCheck").click(function(){
		username = $(".username").val().replace(/\s/gi,"");
		console.log(username);
		/* 이메일 여부 확인 보류
		if(username==""||!(username.endsWith("@gmail.com"))){
			document.getElementById('modalText01').innerHTML='공백 및 지메일 아이디를 확인해주세요';
			$('#dangerModal').modal('show');
		}else{
		*/
			$.ajax({
				url: "/usercheck",
				type : "POST",
				data: {username},
				contentType : "application/json; charset=utf-8",
				success: function(data){
							console.log("data",data);
							if(data=="Available"){
								document.getElementById('modalText02').innerHTML='사용가능한 아이디입니다.';
								$('#primaryModal').modal('show');
								//인증요청을 하였을 때 아이디를 바꾸지 못하게 readonly
								$(".username").prop("readonly","readonly");
								$(".joinForm").append($(".btnRegister"));
								check=1;
							}else{
								document.getElementById('modalText01').innerHTML='사용중인 아이디입니다.';
								$('#dangerModal').modal('show');
								check=-1;
							}
						 },
				error: function(error){
					console.log("ajax 에러");
					$('#error').modal('show');
				}
				
			});
		/*	
		}
		*/
	});
	
	//패스워드 16자 제한
	$(".password1").keyup(function(){
		if($('.password1').val().length>16){
			$('.password1').val($('.password1').val().substring(0,16));
	}});
	
	$(".password2").keyup(function(){
		if($('.password2').val().length>16){
			$('.password2').val($('.password2').val().substring(0,16));
	}});
	
	
	//회원가입
	$(".btnRegister").click(function(){
		if(check != 1){
			document.getElementById('modalText01').innerHTML='아이디 중복검증 먼저 부탁드립니다.';
			$('#dangerModal').modal('show');
			return false;
		//아이디 값과 현재 값이 다른지 체크 
		}else if(!(username==$(".username").val())){
			document.getElementById('modalText01').innerHTML='강제 변경된 아이디는 허용하지 않습니다.';
			$('#dangerModal').modal('show');
			return false;
		//패스워드 값 체크
			//1. 8~16자리 이내
		}else if($('.password1').val().length>16 || $('.password1').val().length<8){
			document.getElementById('modalText01').innerHTML='비밀번호는 8~16자리까지 입력해야합니다.';
			$('#dangerModal').modal('show');
			return false;
			
			//2. 숫자,영어,특수문자 모두 포함
		}else if(!(pattern_spc.test($('.password1').val())) ||
				!(pattern_num.test($('.password1').val())) ||
				!(pattern_eng.test($('.password1').val())) ){
			document.getElementById('modalText01').innerHTML='숫자,영어,특수문자를 포함하여 비밀번호를 입력해주세요.';
			$('#dangerModal').modal('show');
			return false;
			//3. 비밀번호 확인 번호와 일치 여부 체크
		}else if(!($('.password1').val()==$('.password2').val())){
			document.getElementById('modalText01').innerHTML='비밀번호와 확인번호가 서로 일치하지 않습니다.';
			$('#dangerModal').modal('show');
			return false;
		}else{
			//값을 모두 체크 후 회원가입 신청 완료
			return true
		}
	});
	
});

</script>

<div class="content main">
	<div class="container register">
		<div class="row left-side">
			<div class="col-md-3 register-left">
				<img src="https://image.ibb.co/n7oTvU/logo_white.png" alt=""/>
				<h3>9'o Clock</h3>
				<p>입주를 환영합니다.</p>
			</div>
			
			<div class="col-md-9 register-right">
				<div class="tab-content" id="myTabContent">
					<div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
						<h3 class="register-heading">마스터 전용 회원 가입</h3>
							<div class="row register-form">
								<div class="col-md-11">
									<form action="/joinMaster" method="post" class="joinForm">  
									<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/>
									<div class="form-group">
									<!-- name=서버로 전달되는 이름 --> 
										<input type="email" class="form-control username" placeholder="아이디(gmail) *" value="" id="emailInput" name="username"/>
									</div>
									<div class="form-group">
										<input type="password" class="form-control password1" placeholder="비밀번호 *" value="" name="password" />
									</div>
									<div class="form-group" id="emailPart">
										<input type="password" class="form-control password2"  placeholder="비밀번호 확인 *" value="" />
									</div>
									<div class="form-group">
									<select name="comCode">      
										<c:forEach items="${company }" var="bean">
								        	<option value="${bean.comCode }" >${bean.comName }</option>
								        </c:forEach>
									</select>
									</div>
									</form> 
									 <input type="submit" class="btnRegister"  value="회원가입"/>
									 
									 <input type="submit" class="userCheck"  value="아이디중복검증"/>
									 
								</div>
								
							<div class="col-md-1">
								<div class="form-group"><input class="tmp form-control"/></div>
								<div class="form-group">
									<!-- Modal (Button trigger) -->
									
									</div>
								</div>
							</div>
					</div>
				</div>
			</div>
						<%//1. danger Modal%>
			<div class="modal fade" id="dangerModal" tabindex="-1" role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<h5 class="modal-title" id="modalTitle">알림</h5>
						<div class="modal-body" id="modalText01"></div>
						<button type="button" class="btn btn-danger btn-block" data-dismiss="modal" id="closeBtn">확인</button>
					</div>
				</div>
			</div>
			
			<%//2. primary Modal%>
			<div class="modal fade" id="primaryModal" tabindex="-1" role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
				<div class="modal-dialog" role="document">
					<div class="modal-content">
						<h5 class="modal-title" id="modalTitle">알림</h5>
						<div class="modal-body" id="modalText02"></div>
						<button type="button" class="btn btn-primary btn-block" data-dismiss="modal" id="closeBtn">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="template/footer.jspf" %>