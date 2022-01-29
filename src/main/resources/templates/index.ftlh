<#import "macros.ftlh" as p>

<!DOCTYPE html>
<html lang="en">
<head>
    <@p.head />
    <title>Главная</title>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta name="_csrf_token" content="${_csrf.token}"/>
</head>
<body>
<@p.header />


<div class="container mb-5">
    <h2 class="title text-center mb-5 mt-4">Темы</h2>
    <form id="search_topic_form" class="d-flex align-items-center justify-content-center mb-3">
        <input class="form-control" id="search_topic_input" type="text" name="query" placeholder="Искать тему по совпадению" required>
        <button class="btn btn-primary ml-2" type="submit">Искать</button>
    </form>
    <@security.authorize access="isAuthenticated()">
        <div class="mb-4">
            <a href="/create_topic" class="btn btn-primary">Добавить тему</a>
        </div>
    </@security.authorize>
    <div id="topics" class="topics container"></div>
</div>

<ul class="pagination d-flex justify-content-center"></ul>

<script>
    getAllTopics()
    onSearchFormSubmit()
</script>
</body>
</html>




