<#import "macros.ftlh" as p>

<!DOCTYPE html>
<html lang="en">
<head>
    <@p.head />
    <title>Register</title>
</head>
<body>
<@p.header />


<div class="container">
    <h2 class="title text-center mb-5 mt-4">Register</h2>
    <div class="d-flex align-items-center justify-content-center">
        <form class="my_form" action="/register" enctype="multipart/form-data" method="post">
            <#if hasServiceErrors??>
                <div class="alert alert-danger" role="alert">
                    ${hasServiceErrors}
                </div>
            </#if>
            <#if bindErrors??>
                <#list bindErrors as error>
                    <div class="alert alert-danger" role="alert">
                        ${error}
                    </div>
                </#list>
            </#if>
            <div class="form-group">
                <label>Your Email</label>
                <input name="email" type="email" class="form-control" placeholder="Enter email" required>
            </div>
            <div class="form-group">
                <label>Your name</label>
                <input name="fullName" type="text" class="form-control" placeholder="Enter name" required>
            </div>
            <div class="form-group">
                <label>Your password</label>
                <input name="password" type="password" class="form-control" placeholder="Enter password" required>
            </div>
            <#if _csrf??>
                <input type="hidden"
                       name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}" />
            </#if>
            <div class="text-center">
                <button type="submit" class="btn btn-primary">Register</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>