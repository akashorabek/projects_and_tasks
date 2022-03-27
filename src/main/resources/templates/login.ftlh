<#import "macros.ftlh" as p>

<!DOCTYPE html>
<html lang="en">
<head>
    <@p.head />
    <title>Login</title>
</head>
<body>
<@p.header />


<div class="container">
    <h2 class="title text-center mb-5 mt-4">Login</h2>
    <div class="d-flex align-items-center justify-content-center">
        <form class="my_form" action="/login" method="post">
            <#if hasError??>
                <div class="alert alert-danger" role="alert">
                    ${SPRING_SECURITY_LAST_EXCEPTION.message}
                </div>
            </#if>
            <div class="form-group">
                <label for="exampleInputEmail1">Email address</label>
                <input name="username" type="email" class="form-control" aria-describedby="emailHelp" placeholder="Enter email" required>
                <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
            </div>
            <div class="form-group">
                <label for="exampleInputPassword1">Password</label>
                <input name="password" type="password" class="form-control" placeholder="Password" required>
            </div>
            <#if _csrf??>
                <input type="hidden"
                       name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}" />
            </#if>
            <div class="text-center">
                <button type="submit" class="btn btn-primary">Login</button>
            </div>

        </form>
    </div>
</div>
</body>
</html>




