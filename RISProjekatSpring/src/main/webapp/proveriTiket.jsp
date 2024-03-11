<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/stil6.css">
<meta charset="UTF-8">
<title>Kladionica</title>
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


	<form action="/RISProjekat/radnik/proveriTiket" method="post">
		<label for="tiketId">ID Tiketa: </label>
		<input type="number" id="tiketId" name="tiketId">
		<input type="submit" value="Proveri tiket">
	</form>
	<br>
	<c:if test="${!empty greska}">
		<p>${greska}</p>
	</c:if>
	<c:if test="${!empty tiket}">
		<p>
			ID tiketa: <b>${tiket.idTiket}</b>
		</p>
		<br>
		<p>
			Status tiketa: <b>${st}</b>
		</p>
		<br>
		<p>
			Ukupna kvota: <b>${tiket.kvotaTiket}</b>
		</p>
		<br>
		<p>
			Uplata: <b>${tiket.uplata}</b>
		</p>
		<br>
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
	</c:if>

</body>
</html>