<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <title>出错了</title>
    <link href="/static/css/lib/sui.min.css" rel="stylesheet">
    <link href="/static/css/lib/sui-append.min.css" rel="stylesheet">
    <script type="text/javascript"
            src="/static/js/lib/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/lib/sui.min.js"></script>
    <style>
        .sui-container {
            text-align: center;
        }

        .sui-container .sui-msg {
            margin-top: 30px;
        }
    </style>
</head>
<body>
<div class="sui-container">
    <div class="sui-msg msg-large msg-error">
        <div class="msg-con">${result.message}</div>
        <s class="msg-icon"></s>
    </div>
</div>
</body>
</html>