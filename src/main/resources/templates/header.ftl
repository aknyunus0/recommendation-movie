<header>
    <div class="overlay">
        <h2>F&#304;LM ÖNER&#304;</h2>
        <p>HO&#350;GELD&#304;N&#304;Z</p>
        <br>
        <button class="round-button" onclick="window.location.href='/catalog';"><span>ANA SAYFA</span></button>
        <button class="round-button" onclick="window.location.href='/recommended';"><span>&OumlNER&#304;LER</span>
        </button>
        <button class="round-button" onclick="window.location.href='/my-account';"><span>HESABIM</span></button>
        <#if user??>
            <#if user.role=="ADMIN">
                <button class="round-button" onclick="window.location.href='/admin';"><span>Admin </span>
                </button>
            </#if>
            <#if user.role=="MODERATOR">
                <button class="round-button" onclick="window.location.href='/moderate';"><span>Moderation </span>
                </button>
            </#if>
            <form action="/logout" style="display: inline" method="post" class="logout-but">
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <button class="round-button" type="submit"><span>ÇIKIŞ</span></button>
            </form>
        </#if>
        <#if !user??>
            <button class="round-button" onclick="window.location.href='/login';"><span>G&#304;R&#304;&#350;</span></button>
        </#if>
    </div>
</header>

<br>