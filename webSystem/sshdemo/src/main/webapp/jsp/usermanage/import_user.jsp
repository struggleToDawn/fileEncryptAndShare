<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/header.jsp"%>
<html>
<head>
	<title>云存储管理中心</title>
	<%@include file="/common/meta.jsp"%>
	</head>
<body>
	<%@include file="/common/top.jsp" %>
	<%@include file="/common/siderbar.jsp" %>
	<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2 main">
		<div class="row">
			<ol class="breadcrumb">
				<li><a href="${ctx }"><span class="glyphicon glyphicon-home"></span></a></li>
				<li><a href="${ctx }/user">用户属性管理</a></li>
				<li class="active">添加用户</li>
			</ol>
		</div><!--/.row-->
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">添加用户</h3>
			</div>
		</div><!--/.row-->
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-body tabs">
						<ul class="nav nav-tabs">
							<li class="active"><a href="#add_user" data-toggle="tab">添加学生</a></li>
							<li><a href="#add_teacher" data-toggle="tab">添加教师</a></li>
						</ul>
						
						<div class="tab-content">
							<div class="tab-pane fade in active" id="add_user">
								<div class="row">
								<form class="form-horizontal" enctype="multipart/form-data" action="${ctx }/user/import" method="post">
									<label class="col-lg-3 control-label">上传文件 :</label>
									<div class="col-lg-6"><input  type="file" class="form-control" id="file" name="file"></div>
									<div class="col-lg-3"><input type="submit" value="提交" class=" btn btn-primary"></div>
								</form>
								</div>
							</div>
							<div class="tab-pane fade" id="add_teacher">
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>	

</body>
</html>