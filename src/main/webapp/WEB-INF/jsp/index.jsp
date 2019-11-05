<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Добро пожаловать</title>
    <link href="/css/index.css" rel="stylesheet">
</head>
<body>
<div class="mainDiv">
    <div class="left">
        <div class="leftLogo">
            <img src="img/index.png">
        </div>
        <c:if test="${username != null}">
            <h1 class="name" align="center">Логин: ${username}</h1>
        </c:if>
    </div>
    <div class="right">
        <div class="rightNavigation">
            <nav>
                <ul class="menu">
                    <li>
                        <a href="/">На главную</a>
                    </li>
                    <li>
                        <a href="/main">К списку контактов</a>
                    </li>
                    <li class="dropped-menu">
                        <ul class="dropdown-content">
                            <li>
                                <a href="/login">Авторизация</a>
                            </li>
                            <li>
                                <a href="/logout">Выход</a>
                            </li>
                        </ul>
                        <a>Аккаунт</a>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="rightContent">
            <h2 align="center">Добро пожаловать в телефонную книгу !</h2>
        </div>
    </div>
</div>
</body>
</html>