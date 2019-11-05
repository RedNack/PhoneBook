<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавить новый контакт</title>
    <link href="/css/index.css" rel="stylesheet">
    <link href="/css/buttons.css" rel="stylesheet">
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
            <div class="form">
                <form action="/addContact" method="post">
                    <h2>Имя</h2> <input name="firstName" type="text" required>
                    <h2>Фамилия</h2> <input name="lastName" type="text" required>
                    <h2>Организация</h2><input name="organization" type="text">
                    <h2>E-mail</h2><input name="email" type="text">
                    <div>
                        <button class="button3">Добавить</button>
                    </div>
                </form>
            </div>
            <div>
                <table>

                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
