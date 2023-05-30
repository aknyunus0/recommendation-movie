<!DOCTYPE html>
<html lang="tr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel = "icon" href = "https://i.ibb.co/nzNd0ZX/clapperboard-cinema-icon-190873.png" type = "image/x-icon">
    <title>User</title>
    <style><#include "static/css/style.css"></style>
</head>
<body>
<#include "header.ftl">

<#list users as user>
<div style="display: flex; flex-direction: row;  border: 2px; align-items: center;">
    <button style="margin-right: 10px;  background-image: url(https://i.ibb.co/Hgs161z/default.jpg); background-size: cover; height: 50px; width: 50px;"
            onclick="window.location.href='/user/${user.id}';">
    </button>
    <p style="color: white">
        ${user.id} -- ${user.name} -- ${user.role} -- <a href="/admin/add-admin/${user.id}">Make admin</a> -
        <a href="/admin/add-moderator/${user.id}">Make Moderator</a> -
        <a href="/admin/delete-admin/${user.id}">Delete</a>
    </p>
</div>
</#list>


<#include "footer.ftl">
</body>
</html>