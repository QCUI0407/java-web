<%--
  Created by IntelliJ IDEA.
  User: cui
  Date: 1/31/23
  Time: 1:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pizza Form</title>
</head>
<body>
<form action="/pizza-order" method="post">
    <label for="crust">Crust: </label>
    <select name="crust" id="crust">
        <option value="thin">Thin</option>
        <option value="pan">Pan</option>
        <option value="deep">Deep</option>
    </select>
    <label for="sauce">Sauce: </label>
    <select name="sauce" id="sauce">
        <option value="original">Original</option>
        <option value="buffalo">Buffalo</option>
        <option value="garlic">Garlic</option>
    </select>
    <label for="size">Size: </label>
    <select name="size" id="size">
        <option value="small">Small</option>
        <option value="medium">Medium</option>
        <option value="large">Large</option>
    </select>
    <input type="checkbox" id="peperoni" name="toppings" value="peperoni"><label for="peperoni">Peperoni</label>
    <input type="checkbox" id="sausage" name="toppings" value="sausage"><label for="sausage">Sausage</label>
    <input type="checkbox" id="beef" name="toppings" value="beef"><label for="beef">Beef</label>
    <br /> >
    <label for="address">Deliver to: </label>
    <input type="text" id = "address" name="address" />
    <input type="submit" value="Submit Order">

</form>
</body>
</html>
