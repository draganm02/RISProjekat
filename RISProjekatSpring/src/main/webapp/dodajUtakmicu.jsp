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


	<form method="post" action="/RISProjekat/administracija/dodajUtakmicu">

		<table>
			<tr>
				<td>Tim domaći:</td>
				<td>
					<input type="text" id="domaci" name="domaci">
				</td>
			</tr>
			<tr>
				<td>Tim gosti:</td>
				<td>
					<input type="text" id="gost" name="gost">
				</td>
			</tr>
			<tr>
				<td>Sport:</td>
				<td>
					<select name="sport">
						<c:forEach items="${sportovi}" var="s">
							<option value="${s.idSport}">${s.nazivSport}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Vreme:</td>
				<td>
					<input type="datetime-local" id="vreme" name="vreme">
				</td>
			</tr>
		</table>
		<br>
		<input type="submit" value="Dodaj">
<!--
		<label for="domaci">Tim domaći: </label>
		<input type="text" id="domaci" name="domaci">
		<br>
		<label for="gost">Tim gosti: </label>
		<input type="text" id="gost" name="gost">
		<br>
		<label for="sport">Sport: </label>
		<select name="sport">
			<c:forEach items="${sportovi}" var="s">
				<option value="${s.idSport}">${s.nazivSport}</option>
			</c:forEach>
		</select>
		<br>
		<label for="vreme">Vreme: </label>
		<input type="datetime-local" id="vreme" name="vreme">
		<br>
		-->
	</form>
	<c:if test="${!empty poruka}">
		<script type="text/javascript">
			alert("${poruka}");
		</script>
	</c:if>
</body>
<script type="text/javascript">
	window.addEventListener("load", function() {
		var now = new Date();
		var utcString = now.toISOString().substring(0, 19);
		var year = now.getFullYear();
		var month = now.getMonth() + 1;
		var day = now.getDate();
		var hour = now.getHours();
		var minute = now.getMinutes();
		//var second = now.getSeconds();
		var localDatetime = year + "-"
				+ (month < 10 ? "0" + month.toString() : month) + "-"
				+ (day < 10 ? "0" + day.toString() : day) + "T"
				+ (hour < 10 ? "0" + hour.toString() : hour) + ":"
				+ (minute < 10 ? "0" + minute.toString() : minute);
		//+ utcString.substring(16, 19);
		var datetimeField = document.getElementById("vreme");
		datetimeField.value = localDatetime;
	});
</script>
</html>
