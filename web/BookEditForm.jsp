<%--
  Created by IntelliJ IDEA.
  User: fulinhua
  Date: 2016/12/13
  Time: 09:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="jquery-1.8.3/jquery.js"></script>
<head>
    <title>编辑书籍</title>
</head>
<br>

<form:form commandName="book" action="book_update" method="post">
    <form:hidden path="id"/>
    名称:<form:input path="name"></form:input><br>
    作者:<form:input path="author"/><br>
    简介:<form:input path="desc"/><br>
    <input type="submit" value="提交"/>
</form:form>

</body>
</html>
