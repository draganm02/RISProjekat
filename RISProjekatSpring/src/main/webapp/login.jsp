<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/stil6.css">
    <meta charset="UTF-8">
    <title>Kladionica</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #fff6e9;
            color: #0d9276;
            margin: 0;
            padding: 0;
        }

        h2 {
            text-align: center;
            color: #40a2e3;
        }

        form {
            width: 300px;
            margin: 0 auto;
            padding: 20px;
            background-color: #bbe2ec;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        form label {
            display: block;
            margin-bottom: 5px;
            color: #0d9276;
        }

        form input[type="text"],
        form input[type="password"] {
            width: calc(100% - 20px);
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #40a2e3;
            border-radius: 5px;
        }

        form input[type="text"]:focus,
        form input[type="password"]:focus {
            outline: none;
            border-color: #0d9276;
        }

        form button {
            width: 100%;
            padding: 10px;
            background-color: #40a2e3;
            color: #fff6e9;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }

        form button:hover {
            background-color: #0d9276;
        }

        form p {
            text-align: center;
            margin-top: 10px;
            color: #6d6d6d;
        }

        form p a {
            color: #40a2e3;
            text-decoration: none;
        }

        form p a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

<h2>Ulogujte se</h2>

<form action="login" method="post">
    <label for="username"><b>Korisničko ime</b></label>
    <input type="text" placeholder="Unesite korisničko ime" name="username" required>
    <br>
    <label for="password"><b>Lozinka</b></label>
    <input type="password" placeholder="Unesite lozinku" name="password" required>
    <br>
    <button type="submit">Login</button>
    <p>Nemate nalog? <a href="administracija/dodajKorisnikaForma">Napravite ga!</a></p>
</form>

</body>
</html>
