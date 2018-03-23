<%--
  Created by IntelliJ IDEA.
  User: fulinhua
  Date: 2016/12/10
  Time: 15:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
<script src="bootstrap/js/bootstrap.min.js"></script>
<script src="jquery-1.8.3/jquery.js"></script>
<head>
    <title>Add Book Form</title>
</head>
<body>
<form:form  action="book_save" method="post">
    <label>添加书籍</label><br>
    <label >id</label>
    <input type="text" id="id" name="id"/><br>
    <label >书名</label>
    <input type="text" id="name" name="name"/><br>
    <label >作者</label>
    <input type="text" id="author" name="author"/><br>
    <label >简介</label>
    <input input type="text" id="desc" name="desc"/><br>
    <input type="submit" value="提交" >

</form:form>
</body>
</html>
