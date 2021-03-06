<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<title>로그인</title>
<%@ include file="template/navbar.jspf" %>
<div class="content main">
<div class="content" id="loginIdPw">
<h2 id="loginTitle">Sign in</h2>
<form class="formContents" method="post">
	<!-- csrf -->
	<input type="hidden" name="${_csrf.parameterName }" value="${_csrf.token }"/> 
	<div class="input-group">
		<span class="input-group-text signinId" id="id">ID</span>
		<input type="text" id="username" name="username" class="form-control" placeholder="username" required autofocus>
	</div>
	<div class="input-group">
		<span class="input-group-text signinPw" id="id">Password</span>
		<input type="password" id="password" name="password" class="form-control" placeholder="password" required>
	</div>
	<div class="d-grid gap-2 d-md-flex justify-content-md-end">
	 <button class="btn btn-primary" type="submit" id="signInBtn">Sign in</button>
	 </div>
</form>
<p><a href="forgotIdPw">아이디/비밀번호 찾기</a></p>
</div>
</div>
<c:if test="${param.error != null}">
<div class="modal fade" id="dangerModal" tabindex="-1" role="dialog" aria-labelledby="modalTitle" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<h5 class="modal-title" id="modalTitle">알림</h5>
			<div class="modal-body" id="modalText01">아이디와 패스워드가 불일치히거나 로그인 권한이 없습니다.</div>
			<button type="button" class="btn btn-danger btn-block" data-dismiss="modal" id="closeBtn">확인</button>
		</div>
	</div>
</div>
<script> $('#dangerModal').modal('show'); </script>
</c:if>

<%@ include file="template/footer.jspf" %>
