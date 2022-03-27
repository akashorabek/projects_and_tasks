<#import "macros.ftlh" as p>

<!DOCTYPE html>
<html lang="en">
<head>
    <@p.head />
    <title>Projects</title>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta name="_csrf_token" content="${_csrf.token}"/>
</head>
<body>
<@p.header />


<div class="container mb-5">
    <h2 class="title text-center mb-5 mt-4">Projects</h2>
    <form id="search_project_form" class="d-flex align-items-center justify-content-center mb-3">
        <input class="form-control" id="search_project_input" type="text" name="query" placeholder="Search project by match" required>
        <button class="btn btn-primary ml-2" type="submit">Search</button>
    </form>
    <@security.authorize access="isAuthenticated()">
        <div class="mb-4">
            <a href="/projects/create" class="btn btn-primary">Create project</a>
        </div>
    </@security.authorize>
    <div id="projects" class="topics container"></div>
</div>

<ul class="pagination d-flex justify-content-center"></ul>

<script>
    getAllProjects()
    onSearchFormSubmit()
</script>
</body>
</html>




