<#import "macros.ftlh" as p>

<!DOCTYPE html>
<html lang="en">
<head>
    <@p.head />
    <title>Create Project</title>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta name="_csrf_token" content="${_csrf.token}"/>
</head>
<body>
<@p.header />


<div class="container mb-5">
    <h2 class="title text-center mb-5 mt-4">Create project</h2>
    <div class="container">
        <div class="d-flex align-items-center justify-content-center">
            <form id="add_project_form" class="my_form">
                <#if hasError??>
                    <div class="alert alert-danger" role="alert">
                        ${SPRING_SECURITY_LAST_EXCEPTION.message}
                    </div>
                </#if>
                <div class="form-group">
                    <label>Project title</label>
                    <input class="form-control mt-2" type="text" name="name" placeholder="Enter the name" required>
                </div>
                <div class="form-group">
                    <label>Project description</label>
                    <textarea class="textarea form-control mt-2" name="description" placeholder="Enter the description" required></textarea>
                </div>
                <div class="form-group">
                    <label>Project completion date</label>
                    <input type="date" class="form-control mt-2" name="completedAt">
                </div>
                <div class="form-group">
                    <label>Project priority</label>
                    <select class="form-control mt-2" name="priority">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                    </select>
                </div>
                <div class="add_project_warnings"></div>
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Create</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    onAddProjectFormSubmit()
</script>
</body>
</html>




