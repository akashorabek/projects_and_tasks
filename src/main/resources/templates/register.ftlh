<#import "macros.ftlh" as p>

<!DOCTYPE html>
<html lang="en">
<head>
    <@p.head />
    <title>Регистрация</title>
</head>
<body>
<@p.header />


<div class="container">
    <h2 class="title text-center mb-5 mt-4">Регистрация</h2>
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
                <label>Ваша почта</label>
                <input name="email" type="email" class="form-control" placeholder="Введите почту" required>
            </div>
            <div class="form-group">
                <label>Ваше Имя</label>
                <input name="fullName" type="text" class="form-control" placeholder="Введите Имя" required>
            </div>
            <div class="form-group">
                <label>Пароль</label>
                <input name="password" type="password" class="form-control" placeholder="Пароль" required>
            </div>
            <div class="form-group">
                <label>Картинка</label>
                <input name="image" type="file" class="form-control" accept=".jpg, .png, .svg, .jpeg" required>
            </div>
            <#if _csrf??>
                <input type="hidden"
                       name="${(_csrf.parameterName)!'csrf-param-name'}"
                       value="${(_csrf.token)!'csrf-token'}" />
            </#if>
            <div class="text-center">
                <button type="submit" class="btn btn-primary">Регистрироваться</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>