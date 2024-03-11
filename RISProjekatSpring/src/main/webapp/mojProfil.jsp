<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/stil6.css">
<meta charset="UTF-8">
<title>Kladionica</title>
<!-- <style>
body {
	font-family: Arial, sans-serif;
	background-color: #fff6e9;
	color: #0d9276;
	margin: 0;
	padding: 0;
}

.menu {
	background-color: #bbe2ec;
	padding: 10px 0;
}

.menu nav {
	text-align: center;
}

.menu nav a {
	color: #40a2e3;
	text-decoration: none;
	margin: 0 10px;
	font-size: 18px;
}

.menu nav a:hover {
	color: #0d9276;
}

table {
	width: 50%;
	margin: 20px auto;
	border-collapse: collapse;
}

table td, table th {
	padding: 10px;
	border: 1px solid #40a2e3;
}

table th {
	background-color: #bbe2ec;
	color: #0d9276;
}

table tr:nth-child(even) {
	background-color: #ebd9b4;
}

table tr:hover {
	background-color: #f9efd9;
}
</style>-->
</head>
<body>

	<div class="menu">
		<nav>
			<sec:authorize access="hasRole('Korisnik') or hasRole('Radnik')">
				<a href="/RISProjekat/kladjenje/getSportovi">Utakmice</a>
			</sec:authorize>
			<sec:authorize access="hasRole('Radnik')">
				<a href="/RISProjekat/radnik/uplatiSredstvaForma">Uplata</a>
			</sec:authorize>
			<sec:authorize access="hasRole('Radnik')">
				<a href="/RISProjekat/radnik/proveriTiketForma">Proveri tiket</a>
			</sec:authorize>
			<sec:authorize access="hasRole('Administrator')">
				<a href="/RISProjekat/administracija/dodajUtakmicuForma">Dodaj utakmicu</a>
			</sec:authorize>
			<sec:authorize access="hasRole('Administrator')">
				<a href="/RISProjekat/administracija/getZavrseneUtakmice">Dodaj rezultat</a>
			</sec:authorize>
			<sec:authorize access="hasRole('Administrator')">
				<a href="/RISProjekat/administracija/getPredstojeceUtakmice">Uredi kvote</a>
			</sec:authorize>
			<sec:authorize access="hasRole('Administrator')">
				<a href="/RISProjekat/administracija/dodajKorisnikaForma">Dodaj kroisnika</a>
			</sec:authorize>

			<sec:authorize access="isAuthenticated()">
				<a href="/RISProjekat/administracija/mojProfil">Moj profil</a>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
				<a href="/RISProjekat/logout">Odjavi se</a>
			</sec:authorize>

		</nav>
	</div>
	<c:if test="${empty korisnik}">
		<p>Greška</p>
	</c:if>
	<c:if test="${!empty korisnik}">
		<table style="margin-left: auto; margin-right: auto;">
			<tr>
				<td>Korisničko ime</td>
				<td>${korisnik.korisnickoIme}</td>
			</tr>
			<tr>
				<td>Ime</td>
				<td>${korisnik.ime}</td>
			</tr>
			<tr>
				<td>Prezime</td>
				<td>${korisnik.prezime}</td>
			</tr>
			<tr>
				<td>JMBG</td>
				<td>${korisnik.jmbg}</td>
			</tr>
			<tr>
				<td>E-mail</td>
				<td>${korisnik.email}</td>
			</tr>
			<sec:authorize access="hasRole('Korisnik')">
				<tr>
					<td>Stanje na računu</td>
					<td>${korisnik.novac}RSD</td>
				</tr>
			</sec:authorize>
		</table>
		<br>
		<br>
		<sec:authorize access="hasRole('Korisnik')">
			<c:if test="${!empty korisnik.tikets}">
			<h1>Tiketi:</h1>
				<c:forEach items="${korisnik.tikets}" var="tiket">
					<p>
						ID tiketa: <b>${tiket.idTiket}</b>
					</p>
					<p>
						Status tiketa: <b>${tiket.status.status}</b>
					</p>
					<p>
						Ukupna kvota: <b>${tiket.kvotaTiket}</b>
					</p>
					<p>
						Uplata: <b>${tiket.uplata}</b>
					</p>
					<p>
						Dobitak: <b>${tiket.dobitak}</b>
					</p>
					<table border="1" style="width: 50%; text-align: center; vertical-align: middle;" class="table3">
						<tr>
							<th>Utakmica</th>
							<th>Igra</th>
							<th>Kvota</th>
							<th>Status</th>
						</tr>
						<c:forEach items="${tiket.kvotas}" var="k">
							<tr>
								<td>${k.utakmica.timDomaci}-${k.utakmica.timGost}${k.utakmica.vremeUtakmica}</td>
								<td>${k.igra.nazivIgra}</td>
								<td>${k.kvotaKvota}</td>
								<td>${k.status.status}</td>
							</tr>
						</c:forEach>
					</table>
					<br>
				</c:forEach>
			</c:if>
		</sec:authorize>
	</c:if>

</body>
</html>
