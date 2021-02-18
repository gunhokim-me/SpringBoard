<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"  uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/common/requireQuery.jsp"%>
<style>
.navbar {
	margin-bottom: 0;
	border-radius: 0;
}

.row.content {
	height: 450px
}

.sidenav {
	padding-top: 20px;
	background-color: #f1f1f1;
	height: 100%;
}

footer {
	background-color: #555;
	color: white;
	padding: 15px;
}

@media screen and (max-width: 767px) {
	.sidenav {
		height: auto;
		padding: 15px;
	}
	.row.content {
		height: auto;
	}
}
</style>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>

<script>
  	$(function(){
  		$("#save").on("click", function(){
  			let text =$("#summernote").summernote('code');
  			let til = $("#title").val();
  			$("#til").val(til);
  			$("#text").val(text);
  			let bor_num = $("#num").val();
  			$("#bor_num").val(bor_num);
  			$("#sumform").attr("method" , "POST");
  			$("#sumform").attr("action" , "/board/modifyBoardContent");
  			$("#sumform").submit();
  		});
  		 
  		size = $("input[class=filename]").length;
  		
  		$("#attachbnt").on("click", function(){
  			if(size > 0){
  				siez = 0
  			}
  			console.log(size)
  			if($("input[name=files]").length <= 5 - size ){
	  			$("#atdiv").append("<input type='file' name='files'>");
  			}else{
  				alert("최대 첨부파일은 5개 입니다.")
  			}
  		});
  		$(".delete").on("click", function(){
  			$(this).parents(".deleteFile").css("display","none");
  			size = ($("input[class=filename]").length)-1;
  			/* let name = new Array();
  			name.push($("input[class=filename]").val()); */
  			let name = $(this).prev().val();
  			$("input[name=post3]").append("<input type='hidden' name='filename' value='"+name+"'>");
  			$("#filearray").val(name);
  		});
  	});
  </script>
</head>

<body>
	<%@include file="/common/header.jsp"%>
	<div class="container-fluid text-center">
		<div class="row content">
			<div class="col-sm-2 sidenav">
				<c:forEach items="${useboard }" var="board">
					<p>
						<a href="${cp }/boardcontent?bornum=${board.bor_num }">${board.bor_nm }</a>
					</p>
				</c:forEach>
			</div>
			<div class="col-sm-8 text-left">
				<c:forEach items="${boardinfo }" var="select">
					<h2>${select.bor_nm}</h2>
					<input type="hidden" id="num" name="num" value="${select.bor_num }" />
				</c:forEach>
				<form method="post" id="sumform" enctype="multipart/form-data">
					<div style="margin: 10px;">
						제목 : <input type="text" name="title3" id="title" value="${summer.title }" /><br> 
						작성자 :	<label>${S_USER.user_id }</label>
					</div>
					<textarea id="summernote" name="editordata"></textarea>
					<div id="atdiv" style="width: 400px;">
						<c:forEach items="${attlist }" var="list">
							<div class="deleteFile">
								<label>${list.file_nm }</label>
								<input type="hidden" class="filename" value="${list.file_path }"/>
								<input type="button" class="delete" value="X"/>
							</div><br>
						</c:forEach>
						<input type="file" name="files" >
						<button type="button" style="float: right;" id="attachbnt">첨부파일 추가</button>
					</div>
					<div style="width: 370px;">
						<button style="float: right;" type="button" class="btn btn-info" 	name="save" id="save">저장</button>
					</div>
					<input type="hidden" id="text" name="cont3" />
					<input type="hidden" name="user3" value="${userid }" />
					<input type="hidden" name="bor3" value="${bornum }" />
					<input type="hidden" name="post3" value="${postno }" />
					<input type="hidden" name="filearray"/>
				</form>
				<script>
							$(document).ready(function() {
								$("#summernote").summernote('code','${summer.cont}');
								$("#summernote").summernote({
									height: 200,
								});
							})
						</script>
			</div>
			<div class="col-sm-2 sidenav"></div>
		</div>
	</div>
	<footer class="container-fluid text-center">
		<p>카피라이트</p>
	</footer>
</body>
</html>
