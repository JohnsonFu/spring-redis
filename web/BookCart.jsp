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
    <title>购物车</title>
</head>
<body>
<table class="table table-bordered">
    <tr>
        <th>ID</th>
        <th>数量</th>
        <th>移出购物车</th>
    </tr>
    <c:forEach items="${booklist}" var="book">
        <tr>
            <th>${book.id}</th>
            <th>${book.count}</th>
            <th><a href="book_removecart/${book.id}">删除</a></th>
        </tr>
    </c:forEach>
</table>


</body>
</html>