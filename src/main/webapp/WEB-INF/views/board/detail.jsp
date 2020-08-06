<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<button class="btn btn-secondary" onclick="">Back</button>
	<button id="btn-update" class="btn btn-warning">Edit</button>
	<c:if test="${board.user.id == principal.user.id}">
	<button id="btn-delete" class="btn btn-danger">Delete</button>
	</c:if>
	<br/>
	<br/>
	
	<div>
		글번호 :<span id="id"><i>${board.id} </i></span><br/>
		작성자 :<span ><i>${board.user.username} </i></span>
	</div>
	<br/>
	
		<div>
			<h3>${board.title}</h3>
		</div>

	<hr />

		<div>
			<div>${board.content}</div>
		</div>
	<hr />


</div>

<script src="/js/board.js"></script>
<%@ include file="../layout/footer.jsp"%>