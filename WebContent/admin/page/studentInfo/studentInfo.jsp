<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<title>学生信息添加</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="format-detection" content="telephone=no">
	<link rel="stylesheet" href="<s:url value='/admin/layui/css/layui.css' />" media="all" />
	<link rel="stylesheet" href="<s:url value='/admin/css/font_eolqem241z66flxr.css' />" media="all" />
</head>
<body class="childrenBody">
	<form class="layui-form" action="<s:url value='/studentInfo/save' />">
	<input type="hidden" name="id" value="<s:property value='stu.id' />">
		<div class="layui-form-item">
			<label class="layui-form-label">学生姓名</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input newsName" value="<s:property value='stu_name' />" lay-verify="required" placeholder="请输入学生姓名" name="stu.stu_name">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">学生学号</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input newsName" lay-verify="required" value="<s:property value='stu.stu_id' />" placeholder="请输入学生学号" name="stu.stu_id">
			</div>
		</div>
		<div class="layui-form-item">
				<label class="layui-form-label">性别</label>
			    <div class="layui-input-block sexDiv" value="<s:property value='stu.sex' />">
			    	<input type="radio" name="stu.sex" value="男" title="男" class="sex">
	     			<input type="radio" name="stu.sex" value="女" title="女" class="sex" >
	     			<input type="radio" name="stu.sex" value="保密" title="保密" class="sex">
			    </div>
			</div>
			<div class="layui-form-item">
			    <label class="layui-form-label">家庭住址</label>
			    <div class="layui-input-inline">
	                <select name="province" lay-filter="province" class="province">
	                    <option value="">请选择省</option>
	                </select>
	            </div>
	            <div class="layui-input-inline">
	                <select name="city" lay-filter="city" disabled>
	                    <option value="">请选择市</option>
	                </select>
	            </div>
	            <div class="layui-input-inline">
	                <select name="area" lay-filter="area" disabled>
	                    <option value="">请选择县/区</option>
	                </select>
	            </div>
			</div>
		<input type="hidden" name="stu.address" class="address" value="<s:property value='stu.address' />" >
		<div class="layui-form-item">
			<label class="layui-form-label">身份证号</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input" name="stu.stu_num" placeholder="请输入学生身份证号" value="<s:property value='stu.stu_num' />">
			</div>
		</div>
		<div class="layui-form-item">
			<label class="layui-form-label">所选专业</label>
			<div class="layui-input-block">
				<input type="text" class="layui-input" name="stu.major" placeholder="请输入学生所选专业" value="<s:property value='stu.major' />">
			</div>
		</div>
		<div class="layui-form-item">
			<div class="layui-input-block">
				<button class="layui-btn" lay-submit="" lay-filter="addNews">立即提交</button>
				<button type="reset" class="layui-btn layui-btn-primary">重置</button>
		    </div>
		</div>
		
	</form>
	<script type="text/javascript" src="<s:url value='/admin/js/jquery-3.2.1.min.js' />"></script>
	<script type="text/javascript" src="<s:url value='/admin/layui/layui.js' />"></script>
	<script type="text/javascript" src="<s:url value='/admin/page/studentInfo/studentInfoAdd.js' />"></script>
</body>
<script>
	var radio = $(".sex");
	for(var i = 0;i<radio.length;i++){
		if($(radio[i]).val() == $(".sexDiv").attr("value")){
			$(radio[i]).attr("checked","checked");
			break;
		}
	}

	
</script>
</html>