<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%--
 访问控制测试界面
 @author  chenbin
 @Date    20160113
 @Operate create
 @address Peking University
--%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>AccessControlTest</title>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="Access,Control,Test">
	<meta http-equiv="description" content="AccessControlTest">
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-2.2.3.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/accesscontrol.js"></script>
  </head>
  
  <body>
    <h1><center> Test </center></h1> <br/><br/>
    <center>
   	<%--查询特定权限(方法一)：使用REST风格GET请求 --%>
    <a href="queryaccess/1/123/24325435435344f/allowShareFloder">GET方式查询特定权限</a><br/><br/>
    <%--查询特定权限(方法二)：使用POST请求(JQuery方式) --%>
    <input type="button" value="POST方式查询特定权限" id="queryaccessbypost"/><br/><br/>
    <%--查询所有权限(方法一)：使用REST风格GET请求 --%>
    <a href="queryaccessall/1/123/24325435435344f">GET方式查询所有权限</a><br/><br/>
    <%--查询所有权限(方法二)：使用POST请求（表单方式） --%>
    <form action="queryaccessall"  method="post">
    	<table border="1">
		<tr><td>userId: <input type="text" name="userId" value="1"/><br/></td></tr>
		<tr><td>groupId: <input type="text" name="groupId" value="123"/><br/></td></tr>
		<tr><td>fileFolderId:	  <input type="text" name="fileFolderId" value="24325435435344f"/><br/></td></tr>
		<tr><td><input type="submit" name="submit" value="POST方式查询所有权限" /></td></tr>
		</table>
    </form>
    
    <br/><br/>
    <%--查询策略(方法一)：使用REST风格GET请求 --%>
    <a href="querypolicy/1/123/24325435435344f">GET方式查询策略</a><br/><br/>
    <%--查询策略(方法二)：使用POST请求(JQuery方式) --%>
    <input type="button" value="POST方式查询策略" id="querypolicy"/><br/><br/>
    
    <input type="button" value="插入一条策略" id="insertpolicy"/><br/><br/>
    
    <input type="button" value="删除一条策略" id="deletepolicy"/><br/><br/>
    
    <form action="conflictdetection"  method="post">
    	<table border="1">
		<tr><td>环境属性: <input type="text" name="" value="8:00<=loginTime<=17:00"/><br/></td></tr>
		<tr><td>组ID: <input type="text" name="groupId" value="123"/><br/></td></tr>
		<tr><td>文件/目录ID:	  <input type="text" name="fileFolderId" value="24325435435344f"/><br/></td></tr>
		<tr><td>主体属性:	  <input type="text" name="propertyExpression" value="#username='a'#"/><br/></td></tr>
		
		<tr><td>	  <input type="text" name="allowCreateFloder" value="1"/><br/></td></tr>
		<tr><td>	  <input type="text" name="allowShareFloder" value="0"/><br/></td></tr>
		<tr><td>	  <input type="text" name="allowDeleteFloder" value="1"/><br/></td></tr>
		<tr><td>	  <input type="text" name="allowUploadFile" value="0"/><br/></td></tr>
		<tr><td>	  <input type="text" name="allowDownloadFile" value="1"/><br/></td></tr>
		<tr><td>	  <input type="text" name="allowDeleteFile" value="1"/><br/></td></tr>
		
		<tr><td><input type="submit" name="submit" value="冲突检测" /></td></tr>
		</table>
    </form>
    
    
    <br/><br/>
   
    <input type="button" value="冲突检测" id="conflictDetection"/><br/><br/>

    </center>
    
  </body>
</html>
