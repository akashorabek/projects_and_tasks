<#macro head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/styles.css">
    <script src="/js/jquery.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/script.js"></script>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
</#macro>

<#macro header>

    <header class="bg-light mb-5">
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light">
                <a class="navbar-brand" href="/">Test</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <ul class="navbar-nav">
                        <@security.authorize access="hasRole('ROLE_ANONYMOUS')">
                            <li class="nav-item">
                                <a class="nav-link" href="/login">Login</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/register">Register</a>
                            </li>
                        </@security.authorize>
                        <@security.authorize access="isAuthenticated()">
                            <li class="nav-item">
                                <form action="/logout" method="post">
                                    <input type="hidden"
                                           name="${(_csrf.parameterName)!'csrf-param-name'}"
                                           value="${(_csrf.token)!'csrf-token'}" />
                                    <button type="submit" class="btn btn-primary">Logout</button>
                                </form>
                            </li>
                            <li class="nav-item ml-3">
                                <a href="/cabinet" class="nav-link">Cabinet</a>
                            </li>
                        </@security.authorize>
                    </ul>
                </div>
            </nav>
        </div>
    </header>
</#macro>