<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel = "icon" href = "https://i.ibb.co/nzNd0ZX/clapperboard-cinema-icon-190873.png" type = "image/x-icon">
    <meta charset="UTF-8">
    <title>Moderate</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <style><#include "static/css/style.css"></style>

</head>
<body>
<#include "header.ftl">
<br>
<#list movieList as movie>
    <script>
        $(document).ready(function(){
            $("#${movie.id}").click(function(){
                $("#ul${movie.id}").slideToggle();
            });
        });
    </script>
    <p style="margin: 10px; padding: 10px; background-color: white; border-radius: 15px">
        ${movie.title} - <button class="round-button" id="${movie.id}" style="margin: 0">Toggle</button>
    </p>
    <ul id="ul${movie.id}" style="margin: 10px; display: none">
        <#list movie.temporaryReview as propName, propValue>
            <li style="color: white; margin: 10px;">User ID No.${propName} : ${propValue}</li>
            <button class="round-button" onclick="window.location.href='/approve/item-${movie.id}/${propName}';">
                Approve
            </button>
            <button class="round-button" onclick="window.location.href='/delete/item-${movie.id}/${propName}';">
                Delete review
            </button>
            <button class="round-button" onclick="window.location.href='/restrict/item-${movie.id}/${propName}';">
                Restrict user
            </button>
            <button class="round-button" onclick="window.location.href='/block/item-${movie.id}/${propName}';">
                Block user
            </button>
        </#list>
    </ul>

</#list>

<#include "footer.ftl">
</body>
</html>