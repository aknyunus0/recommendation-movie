<!DOCTYPE html>
<html lang="TR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catalog</title>
    <link rel="icon" href="https://i.ibb.co/nzNd0ZX/clapperboard-cinema-icon-190873.png" type="image/x-icon">
    <style><#include "static/css/style.css"></style>
</head>
<body>
<#include "header.ftl">
<div style="display: flex;
    justify-content: center;">
    <#if user??>
        <#if user.role=="ADMIN">
            <form action="/catalog" method="post" enctype="multipart/form-data">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <label style="color: white">ID
                    <input type="text" name="imdbId">
                </label>
                <input class="round-button" type="submit" value="Add title">

            </form>
        </#if>
    </#if>
</div>

<main>

    <div class="hero-container">
        <#list movies as movie>
            <div class="main-container">
                <div class="poster-container">
                    <a href="/item/${movie.id}">
                        <#if movie.image??>
                            <img src="${movie.image}" class="poster" alt="some img"/>
                        </#if>
                    </a>
                </div>
                <div class="ticket-container">
                    <div class="ticket__content">
                        <h4 class="ticket__movie-title">${movie.title}</h4>
                        <p class="ticket__movie-slogan" style="display: inline-flex;">
                            &#x2B50; ${movie.rating} (${movie.ratingCount} rates)
                            <br>
                            (${movie.year?string("##0")})
                            <#if user?? && user.role=="ADMIN">
                                <a href="/delete/${movie.id}">
                                    <img class="trash-icon" src="https://i.ibb.co/Jspkkwj/trash.png" alt="Delete">
                                </a>
                            </#if>
                        </p>
                        <button class="show-more-btn" onclick="window.location.href='/item/${movie.id}'">Show more
                        </button>
                    </div>
                </div>
            </div>

        </#list>
    </div>
</main>

<#include "footer.ftl">
</body>
</html>