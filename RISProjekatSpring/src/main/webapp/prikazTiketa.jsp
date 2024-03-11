<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/stil6.css">
<meta charset="UTF-8">
<title>Prikaz tiketa</title>
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


	<c:choose>
		<c:when test="${empty tiket}">
				Vaš tiket je prazan
				<br>
		</c:when>
		<c:otherwise>
			<table border="1" style="width: 50%; text-align: center; vertical-align: middle;">
				<tr>
					<th>Utakmica</th>
					<th>Igra</th>
					<th>Kvota</th>
				</tr>
				<c:forEach items="${tiket}" var="k">
					<tr>
						<td>${k.utakmica.timDomaci}-${k.utakmica.timGost}${k.utakmica.vremeUtakmica}</td>
						<td>${k.igra.nazivIgra}</td>
						<td>${k.kvotaKvota}</td>
					</tr>
				</c:forEach>
			</table>

			<form method="post" action="/RISProjekat/kladjenje/uplatiTiket">
				<h2>Ukupna kvota: ${kvota}</h2>
				<label for="ulog">Ulog:</label>
				<input type="text" id="ulog" name="ulog" oninput="updateLabel(this.value)">

				<p id="outputLabel">Dobitak: 0.00</p>

				<script>
				function updateLabel(value) {
					var outputLabel = document.getElementById("outputLabel");
					outputLabel.textContent = "Dobitak: " + (value * ${kvota}).toFixed(2);
				}
			</script>
				<input type="submit" value="Uplati" />
			</form>

		</c:otherwise>
	</c:choose>


</body>
</html>