<%--
  Created by IntelliJ IDEA.
  User: Red
  Date: 08.10.2019
  Time: 22:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход</title>
    <link href="/css/index.css" rel="stylesheet">
    <link href="/css/buttons.css" rel="stylesheet">
</head>
<body>
<div class="mainDiv">
    <div class="left">
        <div class="leftLogo">
            <img src="img/index.png">
        </div>
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
            <div class="form">
                <form action="/login" method="post">
                    <h2>Введите логин</h2>
                    <input name="username" type="text" required>
                    <h2>Введите пароль</h2>
                    <input name="password" type="password" required>
                    <div>
                        <a href="/registration">Зарегестрироваться</a>
                    </div>
                    <div>
                        <button class="button3" type="submit">Вход</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>


</body>
</html>
