<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="https://i.ibb.co/nzNd0ZX/clapperboard-cinema-icon-190873.png" type="image/x-icon">
    <meta charset="UTF-8">
    <title>MOVIK - ${movie.title} (${movie.year?string("##0")})</title>
    <style><#include "static/css/style.css"></style>
</head>
<body>
<#include "header.ftl">

<div class="hero-container">
    <div class="ticket-container-d">
        <div class="under-poster">
            <#if movie.image??>
                <img src="${movie.image}" class="poster" alt="Movie poster"/>
            </#if>
        </div>
        <div class="ticket_content">
            <h2 class="ticket__movie-title">${movie.title}</h2>
            <p class="ticket__movie-slogan">
                &#x2B50; ${movie.rating} (${movie.ratingCount} rates)
                (${movie.year?string("##0")})
            </p>
            <#if user??>
                <#if isFavourite==false>
                    <button class="round-button" onclick="window.location.href='/item/add-favourite/${movie.id}';">
                        Add to favourite
                    </button>
                </#if>
            </#if>
            <p class="movie-description">${movie.description}</p>
            <#if user??>

                <#if user.nonRestricted?string('true', 'false') == 'true'>
                    <#if !userReview??>
                        <form action="/item/${movie.id}" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                            <label>Write review -
                                <input type="text" name="comment">
                            </label>
                            <input class="round-button" type="submit" value="Send">
                        </form>
                    </#if>
                    <#if userReview??>
                        <hr><br>
                        <p class="review-block">My review: (Moderated: ${approved})</p>
                        <span style="padding: 1px; background-color: lightgrey;">${userReview}</span>
                        <button class="round-button" style="margin-left: 10px"
                                onclick="window.location.href='/delete/item-${movie.id}/${user.id}';">
                            Delete review
                        </button>
                    </#if>
                </#if>
            </#if>

        </div>
    </div>
</div>


<br>
<div class="hero-container">
    <div class="comments">
        <p style="font-weight: bold; margin-bottom: 10px">User reviews:</p>
        <#list reviews as propName, propValue>
            <div style="display: flex; flex-direction: row;  border: 2px">
                <button style="border-radius: 15px; background-image: url(https://i.ibb.co/Hgs161z/default.jpg); background-size: cover; height: 50px; width: 50px;"
                        onclick="window.location.href='/user/${propName}';">
                </button>
                <span style="padding: 15px;"><span style="background-color: floralwhite">${propName}</span>: ${propValue}</span>
            </div>
        </#list>
    </div>
</div>

<#include "footer.ftl">
</body>
</html>