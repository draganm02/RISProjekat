<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/stil7.css">
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
		<c:when test="${empty utakmica}">
			<form method="post" action="/RISProjekat/administracija/urediKvoteForma">
				<table border="0">
					<tr>
						<th>Utakmica</th>
					</tr>
					<c:forEach items="${utakmice}" var="u">
						<tr>
							<td>${u.timDomaci}-${u.timGost}${u.vremeUtakmica}</td>
							<td>
								<button type="submit" value="${u.idUtakmica}" name="utakmica">Odaberi utakmicu</button>
							</td>
						</tr>
					</c:forEach>
				</table>
			</form>
		</c:when>

		<c:otherwise>
			<form method="post" action="/RISProjekat/administracija/urediKvote">
				<table border="0">
					<tr>
						<th>Igra</th>
					</tr>
					<c:forEach items="${igre}" var="i">
						<tr>
							<td>${i.nazivIgra}</td>
							<td>
								<c:set var="kvota" value="" />
								<c:forEach items="${utakmica.kvotas}" var="k">
									<c:if test="${k.igra.idIgra == i.idIgra}">
										<c:set var="kvota" value="${k.kvotaKvota}" />
									</c:if>
								</c:forEach>
								<input type="text" name="kv_${i.idIgra}" value="${kvota}">
							</td>
						</tr>
					</c:forEach>
				</table>
				<input type="submit">
			</form>
		</c:otherwise>
	</c:choose>

	<c:if test="${!empty poruka}">
		<script type="text/javascript">
			alert("${poruka}");
		</script>
	</c:if>
</body>
</html>
