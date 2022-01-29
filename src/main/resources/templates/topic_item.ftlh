<#import "macros.ftlh" as p>

<!DOCTYPE html>
<html lang="en">
<head>
    <@p.head />
    <title>Подробнее о теме</title>
    <#assign security=JspTaglibs["http://www.springframework.org/security/tags"]/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta name="_csrf_token" content="${_csrf.token}"/>
</head>
<body>
<@p.header />


<div class="container">
    <div class="topic_not_found"></div>
   <div class="topic">
        <div class="topic_item_header mb-4 d-flex justify-content-between align-items-center">
            <h3></h3>
            <p></p>
            <span></span>
        </div>
       <div class="topic_item_desc">
            <div class="topic_item_desc_inner">
                <h3>Содержание темы: </h3>
                <p></p>
                <@security.authorize access="isAuthenticated()">
                    <button class="btn btn-primary mt-3" data-toggle="modal" data-target="#exampleModalCenter">Ответить</button>
                </@security.authorize>
            </div>
           <div class="topic_item_answers">

           </div>
           <ul class="pagination d-flex justify-content-center"></ul>
       </div>
   </div>
</div>

<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <form id="answer_form" class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLongTitle">Форма ответа</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <input type="hidden" name="topicId" value="${id}">
                <textarea id="answer_message_input" class="textarea form-control" name="message" placeholder="Введите ваш ответ" required></textarea>
                <div class="answer_form_warnings my-3"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Закрыть</button>
                <button type="submit" class="btn btn-primary">Ответить</button>
            </div>
        </form>
    </div>
</div>

<script>
    getTopicItem(${id})
    onAddAnswerFormSubmit()
</script>
</body>
</html>




