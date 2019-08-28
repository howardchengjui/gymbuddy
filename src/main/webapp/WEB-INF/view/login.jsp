<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>

<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>GymBuddy</h1>

				<p>
					<form:errors path="users.*" />
				</p>


<h1>Login</h1>
    <p>
					<c:out value="${error}" />
				</p>
    <form method="post" action="/login">
        <p>
            <label for="email">Email</label>
            <input type="text" id="email" name="email" class="form-control" />
        </p>
        <p>
            <label for="password">Password</label>
            <input type="password" id="password" name="password" class="form-control" />
        </p>
        <input type="submit" value="Login!" class="btn btn-primary" />
    </form>
</div>
</div>
</div>
</body>
</html>