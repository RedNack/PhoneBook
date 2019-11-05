<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
    <link href="/css/index.css" rel="stylesheet">
    <link href="/css/form.css" rel="stylesheet">
    <script src="/js/javascript.js"></script>
    <link href="/css/buttons.css" rel="stylesheet">
</head>
<body>
<div class="mainDiv">
    <div class="left">
        <div class="leftLogo">
            <img src="../img/index.png">
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
            <div class="imageContact">
                <img src="/Photo/${contact.photo}" height="240px" width="240px">
                <form enctype="multipart/form-data" action="/editPhoto" method="post">
                    <button class="button3" type="button" id="edit_image"
                            onclick="change('edit_image');change('browse_file');;change('edit_avatar');">Изменить
                    </button>
                    <input type="file" name="file" id="browse_file" style="display: none">
                    <input type="hidden" name="contactId" value="${contact.id}">
                    <button class="button3" id="edit_avatar" style="display: none">Изменить</button>
                </form>
                <form action="/deletePhoto" method="post">
                    <input type="hidden" value="${contact.id}" name="contactId">
                    <button class="button3">Удалить</button>
                </form>
            </div>
            <div class="contactForm">
                <form action="/editContact" method="post">
                    <input name="id" value="${contact.id}" type="hidden">
                    <h2>Имя</h2> <input name="firstName" value="${contact.firstName}" required type="text">
                    <h2>Фамилия</h2> <input name="lastName" value="${contact.lastName}" required type="text">
                    <h2>Организация</h2><input name="organization" value="${contact.organization}" type="text">
                    <h2>E-mail</h2><input name="email" value="${contact.email}" type="text">
                    <button class="button3">Ок !</button>
                </form>
            </div>
            <div class="numberForm">
                <table id="number">
                    <tr>
                        <td><h2>Номер</h2></td>
                        <td><h2>Тип</h2></td>
                        <td colspan="2"><h2>Действие</h2></td>
                    </tr>
                    <c:forEach var="number" items="${contact.numbers}">
                        <tr id="normal${number.id}">
                            <td><h3>${number.number}</h3></td>
                            <td>${number.numberType}</td>
                            <form method="post" action="/contact/deleteNumber">
                                <td>
                                    <input type="hidden" name="contactId" value="${contact.id}">
                                    <input type="hidden" name="numberId" value="${number.id}">
                                    <button class="small_button" id="delete_fake${number.id}" type="submit">Удалить
                                    </button>
                                </td>
                                <td>
                                    <button class="small_button" type="button"
                                            onclick="change('edit${number.id}');change('normal${number.id}')">Изменить
                                    </button>
                                </td>
                            </form>
                        </tr>
                        <tr id="edit${number.id}" style="display: none">
                            <form method="post" action="/contact/editNumber">
                                <input type="hidden" name="contactId" value="${contact.id}">
                                <input type="hidden" name="numberId" value="${number.id}">
                                <td><input type="tel" name="number" value="${number.number}" required maxlength="12">
                                </td>
                                <td>
                                    <select name="numberType">
                                        <option value="mobile">Мобильный</option>
                                        <option value="work">Рабочий</option>
                                        <option value="home">Домашний</option>
                                    </select>
                                </td>
                                <td>
                                    <button class="small_button" type="submit">Изменить</button>
                                </td>
                                <td>
                                    <button class="small_button" type="button"
                                            onclick="change('edit${number.id}');change('normal${number.id}')">Отмена
                                    </button>
                                </td>
                            </form>
                        </tr>
                    </c:forEach>
                    <tr>
                        <form action="/contact/addNumber" method="post">
                            <input type="hidden" name="contactId" value="${contact.id}">
                            <td>
                                <input type="text" name="number" required maxlength="12">
                            </td>
                            <td>
                                <select name="numberType">
                                    <option value="mobile">Мобильный</option>
                                    <option value="work">Рабочий</option>
                                    <option value="home">Домашний</option>
                                </select>
                            </td>
                            <td>
                                <button class="small_button" type="submit">Добавить</button>
                            </td>
                        </form>
                        <script src="/js/validate.js"></script>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
