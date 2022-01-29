<#import "macros.ftlh" as p>

<!DOCTYPE html>
<html lang="en">
<head>
    <@p.head />
    <title>Личный кабинет</title>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
</head>
<body>
<@p.header />


<div class="container">
    <h2 class="title text-center mb-5 mt-4">Кабинет</h2>
    <div class="cabinet">
        <p>Имя: <b>${user.fullName}</b></p>
        <p>Почта: <b>${user.email}</b></p>
    </div>
</div>

<script>
</script>
</body>
</html>




