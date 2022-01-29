<#import "macros.ftlh" as p>

<!DOCTYPE html>
<html lang="en">
<head>
    <@p.head />
    <title>Главная</title>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
</head>
<body>
<@p.header />


<div class="container mb-5">
    <h2 class="title text-center mb-5 mt-4">Темы</h2>
    <@security.authorize access="isAuthenticated()">
        <div class="text-center mb-4">
            <a href="/create_topic" class="btn btn-primary">Добавить тему</a>
        </div>
    </@security.authorize>
    <div id="topics" class="topics container"></div>
</div>

<ul class="pagination d-flex justify-content-center"></ul>

<script>
    getAllTopics()
</script>
</body>
</html>




