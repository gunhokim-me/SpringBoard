<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <!-- Navigation -->
	<nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
      
        <li><a href="/board/main">홈</a></li>
        <li><a href="/board/createBoard">게시판 생성</a></li>
        <c:forEach items="${boardCnt }" var="i">
        	<c:if test="${i.bor_act == 1 }">
	        	<li><a href="/board/selectBoard?bor_num=${i.bor_num }">${i.bor_nm }</a></li>
        	</c:if>
        </c:forEach>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> 로그아웃</a></li>
      </ul>
    </div>
  </div>
</nav>