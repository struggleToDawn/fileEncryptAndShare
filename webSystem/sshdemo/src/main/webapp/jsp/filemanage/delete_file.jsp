<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<form class="form-horizontal" action="${ctx}/file/delete" method="get">
	<div class="form-group col-lg-6">
		<label class="col-lg-3 control-label">文件号：</label>
		<div class="col-lg-9">
		<input type="text" class="form-control " id="id" name ="id"></div>
	</div>
	<div class= "row col-lg-12"><p><br></p></div>
	<div class = "col-lg-4 col-xs-4"></div>
	<div class="form-group col-lg-4 col-xs-4">
		<input type="reset" value="重置" class="btn btn-default ">
		&nbsp;&nbsp;&nbsp;
		<input type="submit" value="删除" class="btn btn-primary ">
	</div>
</form>
