<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="UTF-8">
    <title>MOVIK - ${user.name}<br></title>
    <link rel = "icon" href = "https://i.ibb.co/nzNd0ZX/clapperboard-cinema-icon-190873.png" type = "image/x-icon">
    <style><#include "static/css/style.css"></style>
</head>
<body>
<#include "header.ftl">

<#list movies as movie>

    <div class="hero-container">
    <div class="ticket-container-d">
        <div class="under-poster">
            <a href="/item/${movie.id}">
                <#if movie.image??>
                    <img src="${movie.image}" class="poster" alt="Movie poster"/>
                </#if>
            </a>
        </div>
        <div class="ticket_content">
            <h2 class="ticket__movie-title">${movie.title}</h2>
            <p class="ticket__movie-slogan">
                &#x2B50; ${movie.rating} (${movie.ratingCount} rates)
                (${movie.year?string("##0")})
            </p>
            <p class="movie-description">${movie.description}</p>
            <button class="round-button" style="height: fit-content" onclick="window.location.href='/item/delete-favourite/${movie.id}';">
                Delete from favourite
            </button>
        </div>
    </div>
    </div>

</#list>
<#include "footer.ftl">
</body>
</html>