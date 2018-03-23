<%--
  Created by IntelliJ IDEA.
  User: fulinhua
  Date: 2016/12/10
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="jquery-1.8.3/jquery.js"></script>
<head>
    <title>Title</title>
</head>
<body>
<a href="show_cart">我的购物车</a>
<table class="table table-bordered">
    <tr>
        <th>ID</th>
        <th>书名</th>
        <th>作者</th>
        <th>简介</th>
        <th>购买</th>
    </tr>
    <c:forEach items="${books}" var="book">
        <tr>
            <th>${book.id}</th>
            <th>${book.name}</th>
            <th>${book.author}</th>
            <th>${book.desc}</th>
            <th><a href="book_buy/${book.id}">加入购物车</a></th>
        </tr>
    </c:forEach>
</table>


</body>
</html>