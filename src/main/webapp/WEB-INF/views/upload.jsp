<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>Title</title>
</head>
<body>
	<form id="formId">
	    姓名:<input type="text" name="name" id="name"><br/>
	    年龄:<input type="password" name="pass" id="pass"><br/>
	    性别:<input type="radio" name="sex" value="m">男
	     <input type="radio" name="sex" value="f">女<br/>
	    爱好:<input type="checkbox" name="hobby" value="basketball">篮球
	     <input type="checkbox" name="hobby" value="football">足球
	     <input type="checkbox" name="hobby" value="pingpang">乒乓球<br/>
	    地址:<input type="text" name="address" id="address"><br/>
	     <input type="button" value="提交" id="sendTo">
	</form>
</body>
<script type="text/javascript">

	$("#sendTo").click(
			function() {
				//获取值
				var name = $("#name").val();
				var age = $("#age").val();
				var sex = $("input[type='radio']").val();
				var hobby = $("input[name='hobby']:checked").serialize(); //此处为复选框,用序列化的方式传递
				var address = $("#address").val();
				$.ajax({
					url : "web/toServer.do",
					type : "post",
					//注意序列化的值一定要放在最前面,并且不需要头部变量,不然获取的值得格式会有问题
					data : hobby + "&name=" + name + "&age=" + age + "&sex="
							+ sex + "&address=" + address,
					dataType : "json",
					success : function(data) {
						// alert(data.result);
						alert(data.result);
					}
				})
			})
</script>
</html>