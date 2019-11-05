<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>${user.username}</title>
    <script src="/js/javascript.js"></script>
    <link href="/css/form.css" rel="stylesheet">
    <link href="/css/index.css" rel="stylesheet">
    <link href="/css/buttons.css" rel="stylesheet">
</head>
<body>
<div class="test">
    <div class="mainDiv">
        <div class="left">
            <div class="leftLogo">
                <img src="img/index.png">
                <div class="importForm">
                    <c:if test="${username != null}">
                        <h1 class="name" align="center">Логин: ${username}</h1>
                    </c:if>
                    <div style="display: none" id="browse_file">
                        <form enctype="multipart/form-data" action="/import" method="post">
                            <input type="file" name="file">
                            <button class="button2">Импортировать</button>
                        </form>
                    </div>
                    <button class="button2" id="browse_button" onclick="change('browse_file');change('browse_button');">Импортировать
                        контакты
                    </button>
                    <a href="/exportAll">
                        <button class="button2">Экспорт всей книги</button>
                    </a>
                    <a href="/deleteUser">
                        <button class="button2">Удалить аккаунт</button>
                    </a>
                </div>
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
                <table width="100%" align="center">
                    <caption><h1>Ваши контакты:</h1></caption>
                    <c:forEach var="contact" items="${contacts}">
                        <tr>
                            <td><h3><a href="/contact/${contact.id}"> ${contact.firstName}</a></h3></td>
                            <td><h3>${contact.lastName}</h3></td>
                            <td><h3>${contact.organization}</h3></td>
                            <td><h3>${contact.email}</h3></td>
                            <form action="/deleteContact" method="post">
                                <input name="contact_id" type="hidden" value="${contact.id}">
                                <td>
                                    <h3>
                                        <button class="button1">Удалить</button>
                                    </h3>
                                </td>
                            </form>
                            <form action="/export" method="post">
                                <input name="contactId" type="hidden" value="${contact.id}">
                                <td>
                                    <h3>
                                        <button class="button1">Экспорт</button>
                                    </h3>
                                </td>
                            </form>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="3" align="center">
                            <a href="/addContact"><button class="button1">Добавить</button></a>
                        </td>
                        <td>

                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
