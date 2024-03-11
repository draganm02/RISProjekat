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


	<form method="post" action="/RISProjekat/administracija/dodajKorisnika">
		
		<table>
			<tr>
				<td>Ime:</td>
				<td>
					<input type="text" id="ime" name="ime">
				</td>
			</tr>
			<tr>
				<td>Prezime:</td>
				<td>
					<input type="text" id="prezime" name="prezime">
				</td>
			</tr>
			<tr>
				<td>JMBG:</td>
				<td>
					<input type="text" id="jmbg" name="jmbg">
				</td>
			</tr>
			<tr>
				<td>E-mail:</td>
				<td>
					<input type="text" id="mail" name="mail">
				</td>
			</tr>
			<tr>
				<td>Korisničko ime:</td>
				<td>
					<input type="text" id="korIme" name="korIme">
				</td>
			</tr>
			<tr>
				<td>Lozinka:</td>
				<td>
					<input type="password" id="lozinka" name="lozinka">
				</td>
			</tr>
			<tr>
				<td>Ponovo unesite lozinku:</td>
				<td>
					<input type="password" id="lozinka2" name="lozinka2">
				</td>
			</tr>
			<sec:authorize access="hasRole('Administrator')">
				<tr>
					<td>Tip</td>
					<td>
						<select name="tip" id="tip">
							<option value="1">Administrator</option>
							<option value="2">Korisnik</option>
							<option value="3">Radnik</option>
						</select>
					</td>
				</tr>
			</sec:authorize>
		</table>



		<sec:authorize access="hasRole('Korisnik')">
			<input type="hidden" id="tip" name="tip" value="2">
		</sec:authorize>
		<sec:authorize access="!isAuthenticated()">
			<input type="hidden" id="tip" name="tip" value="2">
		</sec:authorize>
		<br>
		<input type="submit" value="Dodaj">
	</form>
	<c:if test="${!empty poruka}">
		<script type="text/javascript">
			alert("${poruka}");
		</script>
	</c:if>
</body>
</html>
