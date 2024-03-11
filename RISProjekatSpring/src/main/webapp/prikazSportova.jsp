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



	<h1>
		<c:if test="${!empty kvota}">Kvota: ${kvota}</c:if>
		<c:if test="${empty kvota}">Tiket je prazan</c:if>
	</h1>
	<form method="post" action="/RISProjekat/kladjenje/prikazTiketa">
		<button type="submit" class="button1">Vaš tiket</button>
	</form>
	<form action="/RISProjekat/kladjenje/getUtakmice" method="get">
		Sport:
		<select name="idSporta">
			<c:forEach items="${sportovi}" var="s">
				<option value="${s.idSport}" <c:if test="${s.idSport == odabraniSport.idSport }"> selected="selected" </c:if>>${s.nazivSport}
				</option>
			</c:forEach>
		</select>
		<input type="submit" value="Prikaži" />
	</form>
	<br>

	<c:if test="${!empty odabraniSport}">
	Utakmice za odabrani sport: ${odabraniSport.nazivSport}
	<br>


		<c:choose>
			<c:when test="${empty utakmice}">
				Nema utakmica za odabrani sport.
				<br>
			</c:when>
			<c:otherwise>
				<table border="1" style="width: 50%; text-align: center; vertical-align: middle;">
					<tr>
						<th>Domaćin</th>
						<th>Gost</th>
						<th>Vreme</th>
					</tr>
					<c:forEach items="${utakmice}" var="u">
						<tr>
							<td>${u.timDomaci}</td>
							<td>${u.timGost}</td>
							<td>${u.vremeUtakmica}</td>
							<td>
								<a href="getKvote?idUtakmice=${u.idUtakmica}">
									<button>Kvote</button>
								</a>
							</td>
						</tr>
						<c:if test="${u.idUtakmica == odabranaUtakmica.idUtakmica}">
							<tr>
								<td colspan="4">
									<table border="1" style="width: 50%" align="center">
										<tr>
											<form method="post" action="/RISProjekat/kladjenje/dodajKvotu">
												<c:forEach items="${kvote}" var="k">
													<td style="padding: 0">
														<button type="submit" value="${k.idKvota}" name="idKvote"
															style="width: 100%; height: 100%;
															<c:set var="sadrzi" value="false" />
															<c:forEach var="it" items="${tiket}">
																 <c:if test="${it.idKvota == k.idKvota}">
																 	<c:set var="sadrzi" value="true" />
																 </c:if>
															</c:forEach>
															<c:if test="${sadrzi}">background-color: #f44336;</c:if>">
															${k.igra.nazivIgra}</br>${k.kvotaKvota}
														</button>
													</td>
												</c:forEach>
											</form>
										</tr>
									</table>
								</td>
							</tr>
						</c:if>
					</c:forEach>

				</table>
			</c:otherwise>
		</c:choose>


	</c:if>

	<c:if test="${!empty poruka}">
		<script type="text/javascript">
			alert("${poruka}");
			window.location.href = '/RISProjekat/kladjenje/getSportovi';
		</script>
	</c:if>

</body>
</html>