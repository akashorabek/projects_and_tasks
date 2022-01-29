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
    <h2 class="title text-center mb-5 mt-4">Добавить тему</h2>
    <div class="container">
        <div class="d-flex align-items-center justify-content-center">
            <form id="add_topic_form" class="my_form">
                <#if hasError??>
                    <div class="alert alert-danger" role="alert">
                        ${SPRING_SECURITY_LAST_EXCEPTION.message}
                    </div>
                </#if>
                <div class="form-group">
                    <label>Название темы</label>
                    <input class="form-control mt-2" type="text" name="name" placeholder="Введите название" required>
                </div>
                <div class="form-group">
                    <label>Описание темы</label>
                    <textarea class="textarea form-control mt-2" name="description" placeholder="Введите описание" required></textarea>
                </div>
                <div class="add_topic_warnings"></div>
                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    onAddTopicFormSubmit()
</script>
</body>
</html>




