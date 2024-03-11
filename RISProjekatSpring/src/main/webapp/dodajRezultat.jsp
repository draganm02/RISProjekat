<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<c:choose>
	<c:when test="${!empty utakmica}">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/stil7.css">
	</c:when>
	<c:otherwise>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/stil6.css">
	</c:otherwise>
</c:choose>
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


	<c:choose>
		<c:when test="${!empty utakmica}">
			<form action="/RISProjekat/administracija/dodajRezultat" method="post">

				<table>
					<tr>
						<td>Poeni domaćin:</td>
						<td>
							<input type="number" id="domacin" name="domacin">
						</td>
					</tr>
					<tr>
						<td>Poeni gost:</td>
						<td>
							<input type="number" id="gost" name="gost">
					</tr>
				</table>
				<input type="submit" value="Dodaj rezultat">
			</form>
		</c:when>
		<c:when test="${empty utakmice}">
			<h1>Nema završenih utakmica</h1>
			<br>
		</c:when>
		<c:otherwise>
			<table border="1" style="width: 50%; text-align: center; vertical-align: middle;" class="table3">
				<tr>
					<th>Utakmica</th>
					<th>Datum</th>
					<th>Sport</th>
				</tr>
				<c:forEach items="${utakmice}" var="u">
					<tr>
						<td>${u.timDomaci}-${u.timGost}</td>
						<td>${u.vremeUtakmica}</td>
						<td>${u.sport.nazivSport}</td>
						<td>
							<form action="/RISProjekat/administracija/dodajRezultatForma" method="post">
								<button type="submit" name="utakmica" value="${u.idUtakmica}">Dodaj rezultat</button>
							</form>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>

	<c:if test="${!empty poruka}">
		<script type="text/javascript">
			alert("${poruka}");
		</script>
		<c:redirect url="/administracija/getZavrseneUtakmice" />
	</c:if>

</body>
</html>