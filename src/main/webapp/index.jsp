<html>

    <%@ page contentType="text/html;charset=UTF-8" language="java" %>


<body>
<h2>Hello World!</h2>


springmvc上传文件
<form name="form1" action="/mmall_war/manage/product/upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file"/>
    <input type="submit" value="springmvc上传文件">


</form>


富文本上传文件
<form name="form2" action="/mmall_war/manage/product/richtext_img_upload.do" method="post" enctype="multipart/form-data">
    <input type="file" name="upload_file"/>
    <input type="submit" value="富文本上传文件">


</form>

</body>
</html>
