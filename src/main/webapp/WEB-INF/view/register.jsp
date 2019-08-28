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
<title>Register</title>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>GymBuddy</h1>

				<p>
					<form:errors path="users.*" />
				</p>

				<form:form method="POST" action="/register"
					modelAttribute="users">
					<div class="form-group">
					<p>
						
						<form:label path="first_name">First Name:</form:label>
						<form:input type="first_name" path="first_name" class="form-control" />
					</p>
					</div>
					
					<div class="form-group">

					<p>
						<form:label path="last_name">Last Name:</form:label>
						<form:input type="last_name" path="last_name" class="form-control" />
					</p>
					</div>

					<p>
						<form:label path="email">Email:</form:label>
						<form:input type="email" path="email" class="form-control" />
					</p>
					
					<p>
			<form:label path="password">Password:</form:label>
			<form:password path="password" class="form-control"/>
		</p>
		<p>
			<form:label path="passwordConfirmation">Password Confirmation:</form:label>
			<form:password path="passwordConfirmation" class="form-control"/>
		</p>
		<input type="submit" value="Register!" class="btn btn-primary" />
	</form:form>
</div>

</div>
</div>
</body>
</html>