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
    <div class="mb-3 d-flex">
        <button class="btn btn-success mx-1" id="sort_projects_status_asc">Sort by Status ASC</button>
        <button class="btn btn-success mx-1" id="sort_projects_status_desc">Sort by Status DESC</button>
        <button class="btn btn-success mx-1" id="sort_projects_priority_asc">Sort by Priority ASC</button>
        <button class="btn btn-success mx-1" id="sort_projects_priority_desc">Sort by Priority DESC</button>
        <button class="btn btn-success mx-1" id="sort_projects_creationDate_asc">Sort by Creation date ASC</button>
        <button class="btn btn-success mx-1" id="sort_projects_creationDate_desc">Sort by Creation date DESC</button>
    </div>
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

    // sorting functions
    $('#sort_projects_status_asc').click(function (){
        getAllProjects(0, "&sort=status,ASC")
    })
    $('#sort_projects_status_desc').click(function (){
        getAllProjects(0, "&sort=status,DESC")
    })
    $('#sort_projects_priority_asc').click(function (){
        getAllProjects(0, "&sort=priority,ASC")
    })
    $('#sort_projects_priority_desc').click(function (){
        getAllProjects(0, "&sort=priority,DESC")
    })
    $('#sort_projects_creationDate_asc').click(function (){
        getAllProjects(0, "&sort=createdAt,ASC")
    })
    $('#sort_projects_creationDate_desc').click(function (){
        getAllProjects(0, "&sort=createdAt,DESC")
    })
</script>
</body>
</html>




