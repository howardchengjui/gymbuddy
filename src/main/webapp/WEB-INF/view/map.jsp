<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<script src="js/custom.js"></script>
<link href="css/custom.css" rel="stylesheet">
<meta charset="ISO-8859-1">
<title>Map</title>
</head>
<body>
<div id="map"></div>
<!-- Replace the value of the key parameter with your own API key. -->
<script async defer
src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAvDRZ2uswRWn6vnh5SRjj90z-2YRl2Acc&callback=initMap">
</script>

</body>
</html>