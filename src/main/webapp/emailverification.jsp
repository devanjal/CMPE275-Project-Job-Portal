<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

    <title>View your profile</title>

    <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
	<div class="container-fluid">
		<div class="jumbotron">
			<h2 id="title">Verify Your Mail</h2>			
		</div>
	</div>
	<div class="container-fluid" id="register">
		<div class="row-fluid">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<div class="span8">
					
					<form action="${contextPath}/verification" method="post">
						<div class="form-group" ${error != null ? 'has-error' : ''}>
							<label for="token">Verification Code</label> <input type="text"
							class="form-control" name="token">
							<span>${error}</span>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						</div>

						<button type="submit" value="Submit" class="btn btn-default" name="action">Submit</button>
					</form>
					
					
					
				</div>
			</div>
		</div>
	</div>
	
	<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="${contextPath}/resources/js/bootstrap.min.js"></script>

</body>
</html>